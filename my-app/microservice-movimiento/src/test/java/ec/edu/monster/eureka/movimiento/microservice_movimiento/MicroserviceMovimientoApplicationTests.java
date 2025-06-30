package ec.edu.monster.eureka.movimiento.microservice_movimiento;

import ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.servicios.EurekaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EurekaServiceTest {

	@InjectMocks
	private EurekaService eurekaService;

	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement preparedStatement;

	@Mock
	private ResultSet resultSet;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		when(dataSource.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	}

	@Test
	void testLeerMovimientos_cuentaNula_oVacia_retornaListaVacia() {
		List<Movimiento> resultNull = eurekaService.leerMovimientos(null);
		List<Movimiento> resultEmpty = eurekaService.leerMovimientos("");

		assertNotNull(resultNull);
		assertTrue(resultNull.isEmpty());

		assertNotNull(resultEmpty);
		assertTrue(resultEmpty.isEmpty());
	}

	@Test
	void testLeerMovimientos_noHayResultados_retornaListaVacia() throws Exception {
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		List<Movimiento> lista = eurekaService.leerMovimientos("00100001");

		assertNotNull(lista);
		assertTrue(lista.isEmpty());

		verify(preparedStatement).setString(1, "00100001");
		verify(preparedStatement).executeQuery();
	}

	@Test
	void testLeerMovimientos_hayResultados_retornaListaConMovimientos() throws Exception {
		when(preparedStatement.executeQuery()).thenReturn(resultSet);

		// Simular dos filas de resultados
		when(resultSet.next()).thenReturn(true, true, false);

		when(resultSet.getString("cuenta")).thenReturn("00100001", "00100001");
		when(resultSet.getInt("nromov")).thenReturn(1, 2);
		Timestamp ts1 = Timestamp.valueOf("2025-05-19 10:00:00");
		Timestamp ts2 = Timestamp.valueOf("2025-05-19 11:00:00");
		when(resultSet.getTimestamp("fecha")).thenReturn(ts1, ts2);
		when(resultSet.getString("tipo")).thenReturn("Tipo1", "Tipo2");
		when(resultSet.getString("accion")).thenReturn("Accion1", "Accion2");
		when(resultSet.getDouble("importe")).thenReturn(100.0, 200.0);

		List<Movimiento> lista = eurekaService.leerMovimientos("00100001");

		assertNotNull(lista);
		assertEquals(2, lista.size());

		Movimiento primero = lista.get(0);
		assertEquals("00100001", primero.getCuenta());
		assertEquals(1, primero.getNromov());
		assertEquals(ts1, primero.getFecha());
		assertEquals("Tipo1", primero.getTipo());
		assertEquals("Accion1", primero.getAccion());
		assertEquals(100.0, primero.getImporte());

		Movimiento segundo = lista.get(1);
		assertEquals("00100001", segundo.getCuenta());
		assertEquals(2, segundo.getNromov());
		assertEquals(ts2, segundo.getFecha());
		assertEquals("Tipo2", segundo.getTipo());
		assertEquals("Accion2", segundo.getAccion());
		assertEquals(200.0, segundo.getImporte());

		verify(preparedStatement).setString(1, "00100001");
		verify(preparedStatement).executeQuery();
		verify(resultSet, times(3)).next(); // se llama 3 veces: 2 filas + false
	}

	@Test
	void testLeerMovimientos_sqlException_lanzaRuntimeException() throws Exception {
		when(dataSource.getConnection()).thenThrow(new SQLException("Error de conexión"));

		RuntimeException thrown = assertThrows(RuntimeException.class,
				() -> eurekaService.leerMovimientos("00100001"));

		assertTrue(thrown.getMessage().contains("Error de conexión"));
	}
}

