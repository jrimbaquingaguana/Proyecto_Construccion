package ec.edu.monster.eureka.retiro.microservice_retiro;

import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.servicios.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceTest {

	@InjectMocks
	private CuentaService cuentaService;

	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		when(dataSource.getConnection()).thenReturn(connection);
	}

	@Test
	void testRegistrarRetiro_casoExitoso() throws Exception {
		String cuenta = "00100001";
		double importe = 100.0;
		String codEmp = "003";

		// Mocks para PreparedStatement y ResultSet
		PreparedStatement selectStmt = mock(PreparedStatement.class);
		ResultSet selectRs = mock(ResultSet.class);
		PreparedStatement updateStmt = mock(PreparedStatement.class);
		PreparedStatement insertStmt = mock(PreparedStatement.class);

		// Cuando prepareStatement es llamado, devuelve el mock correspondiente según la consulta
		when(connection.prepareStatement(anyString())).thenAnswer(invocation -> {
			String sql = invocation.getArgument(0);
			sql = sql.trim().toUpperCase();
			if (sql.startsWith("SELECT")) return selectStmt;
			if (sql.startsWith("UPDATE CUENTA")) return updateStmt;
			if (sql.startsWith("INSERT INTO MOVIMIENTO")) return insertStmt;
			return null;
		});

		// Simular resultado del SELECT FOR UPDATE
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(true);
		when(selectRs.getDouble("dec_cuensaldo")).thenReturn(500.0);
		when(selectRs.getInt("int_cuencontmov")).thenReturn(20);

		// Simular ejecución exitosa de UPDATE e INSERT
		when(updateStmt.executeUpdate()).thenReturn(1);
		when(insertStmt.executeUpdate()).thenReturn(1);

		// Ejecutar método a probar
		cuentaService.registrarRetiro(cuenta, importe, codEmp);

		// Verificar comportamiento transaccional
		verify(connection).setAutoCommit(false);
		verify(connection).commit();

		// Verificar llamada al SELECT y parámetros
		verify(selectStmt).setString(1, cuenta);
		verify(selectStmt).executeQuery();

		// Verificar actualización con saldo descontado e incremento de movimientos
		verify(updateStmt).setDouble(1, 400.0); // 500 - 100
		verify(updateStmt).setInt(2, 21);       // 20 + 1
		verify(updateStmt).setString(3, cuenta);
		verify(updateStmt).executeUpdate();

		// Verificar inserción del movimiento
		verify(insertStmt).setString(1, cuenta);
		verify(insertStmt).setInt(2, 21);
		verify(insertStmt).setString(3, codEmp);
		verify(insertStmt).setDouble(4, importe);
		verify(insertStmt).executeUpdate();

		// Verificar cierre de recursos
		verify(selectRs).close();
		verify(selectStmt).close();
		verify(updateStmt).close();
		verify(insertStmt).close();
		verify(connection).close();
	}

	@Test
	void testRegistrarRetiro_cuentaNoExiste_lanzaExcepcion() throws Exception {
		String cuenta = "0100002";
		double importe = 100.0;
		String codEmp = "003";

		PreparedStatement selectStmt = mock(PreparedStatement.class);
		ResultSet selectRs = mock(ResultSet.class);

		when(connection.prepareStatement(anyString())).thenAnswer(invocation -> {
			String sql = invocation.getArgument(0);
			if (sql.trim().toUpperCase().startsWith("SELECT")) return selectStmt;
			return mock(PreparedStatement.class);
		});
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(false);

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> cuentaService.registrarRetiro(cuenta, importe, codEmp));

		assertTrue(exception.getMessage().contains("ERROR, la cuenta no existe o no está activa."));

		verify(connection).rollback();
		verify(selectRs).close();
		verify(selectStmt).close();
		verify(connection).close();
	}

	@Test
	void testRegistrarRetiro_saldoInsuficiente_lanzaExcepcion() throws Exception {
		String cuenta = "00100001";
		double importe = 600.0; // Ahora es mayor que el saldo 500.0 para simular error
		String codEmp = "003";

		PreparedStatement selectStmt = mock(PreparedStatement.class);
		ResultSet selectRs = mock(ResultSet.class);

		when(connection.prepareStatement(anyString())).thenAnswer(invocation -> {
			String sql = invocation.getArgument(0);
			if (sql.trim().toUpperCase().startsWith("SELECT")) return selectStmt;
			return mock(PreparedStatement.class);
		});
		when(selectStmt.executeQuery()).thenReturn(selectRs);
		when(selectRs.next()).thenReturn(true);
		when(selectRs.getDouble("dec_cuensaldo")).thenReturn(500.0);
		when(selectRs.getInt("int_cuencontmov")).thenReturn(10);

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> cuentaService.registrarRetiro(cuenta, importe, codEmp));

		assertTrue(exception.getMessage().contains("ERROR, saldo insuficiente."));

		verify(connection).rollback();
		verify(selectRs).close();
		verify(selectStmt).close();
		verify(connection).close();
	}

	@Test
	void testRegistrarRetiro_errorCerrarRecursos_lanzaExcepcion() throws Exception {
		// Simular error al cerrar recursos para probar el manejo en finally
		Connection badConnection = mock(Connection.class);
		PreparedStatement badStmt = mock(PreparedStatement.class);
		ResultSet badRs = mock(ResultSet.class);

		when(dataSource.getConnection()).thenReturn(badConnection);
		when(badConnection.prepareStatement(anyString())).thenReturn(badStmt);
		when(badStmt.executeQuery()).thenReturn(badRs);

		when(badRs.next()).thenReturn(true);
		when(badRs.getDouble(anyString())).thenReturn(500.0);
		when(badRs.getInt(anyString())).thenReturn(20);

		// Lanzar excepción al cerrar ResultSet para simular fallo
		doThrow(new SQLException("Error al cerrar")).when(badRs).close();

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> cuentaService.registrarRetiro("00100001", 100.0, "003"));

		assertTrue(exception.getMessage().contains("Error al cerrar recursos"));

		verify(badConnection).rollback();
		verify(badRs).close();
		verify(badStmt).close();
		verify(badConnection).close();
	}
}
