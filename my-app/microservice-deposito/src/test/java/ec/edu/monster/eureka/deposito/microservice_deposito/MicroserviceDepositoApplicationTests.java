package ec.edu.monster.eureka.deposito.microservice_deposito;

import ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.servicios.MovimientoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovimientoServiceTest {

	@InjectMocks
	private MovimientoService movimientoService;

	@Mock
	private EntityManager entityManager;

	@Mock
	private Query query;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegistrarDeposito_casoExitoso() {
		// Arrange
		String cuenta = "0100001";
		double importe = 100.0;
		String codEmp = "003";
		Object[] resultado = new Object[]{500.0, 20}; // saldo y número de movimientos

		// Mock del SELECT del saldo
		when(entityManager.createNativeQuery(contains("SELECT dec_cuensaldo")))
				.thenReturn(query);
		when(query.setParameter(eq("cuenta"), anyString())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(resultado);

		// Mock del SELECT COUNT
		Query countQuery = mock(Query.class);
		when(entityManager.createNativeQuery(contains("SELECT COUNT"))).thenReturn(countQuery);
		when(countQuery.setParameter(eq("cuenta"), anyString())).thenReturn(countQuery);
		when(countQuery.setParameter(eq("cont"), any())).thenReturn(countQuery);
		when(countQuery.getSingleResult()).thenReturn(0L);

		// Mock del UPDATE
		Query updateQuery = mock(Query.class);
		when(entityManager.createNativeQuery(startsWith("UPDATE cuenta"))).thenReturn(updateQuery);
		when(updateQuery.setParameter(anyInt(), any())).thenReturn(updateQuery);
		when(updateQuery.executeUpdate()).thenReturn(1);

		// Mock del INSERT
		Query insertQuery = mock(Query.class);
		when(entityManager.createNativeQuery(startsWith("INSERT INTO movimiento"))).thenReturn(insertQuery);
		when(insertQuery.setParameter(anyInt(), any())).thenReturn(insertQuery);
		when(insertQuery.executeUpdate()).thenReturn(1);

		// Act & Assert
		assertDoesNotThrow(() -> movimientoService.registrarDeposito(cuenta, importe, codEmp));

		// Mensaje para indicar que el test pasó correctamente
		System.out.println("Test testRegistrarDeposito_casoExitoso pasó correctamente.");
	}
}
