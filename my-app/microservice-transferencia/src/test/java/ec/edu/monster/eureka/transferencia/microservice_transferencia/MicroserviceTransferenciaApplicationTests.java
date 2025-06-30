package ec.edu.monster.eureka.transferencia.microservice_transferencia;

import ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.servicios.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

	@InjectMocks
	private TransferenciaService transferenciaService;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRegistrarTransferencia_casoExitoso() {
		String cuentaOrigen = "00100001";
		String cuentaDestino = "00100002";
		double importe = 100.0;
		String codEmp = "003";

		Map<String, Object> origenData = new HashMap<>();
		origenData.put("dec_cuensaldo", 500.0);
		origenData.put("int_cuencontmov", 10);

		Map<String, Object> destinoData = new HashMap<>();
		destinoData.put("dec_cuensaldo", 200.0);
		destinoData.put("int_cuencontmov", 5);

		when(jdbcTemplate.queryForMap(anyString(), eq(cuentaOrigen))).thenReturn(origenData);
		when(jdbcTemplate.queryForMap(anyString(), eq(cuentaDestino))).thenReturn(destinoData);

		transferenciaService.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);

		// Verifica que se hicieron las actualizaciones adecuadas
		verify(jdbcTemplate).update(anyString(), eq(400.0), eq(11), eq(cuentaOrigen));
		verify(jdbcTemplate).update(anyString(), eq(cuentaOrigen), eq(11), eq(codEmp), eq(importe));
		verify(jdbcTemplate).update(anyString(), eq(300.0), eq(6), eq(cuentaDestino));
		verify(jdbcTemplate).update(anyString(), eq(cuentaDestino), eq(6), eq(codEmp), eq(importe));
	}

	@Test
	void testRegistrarTransferencia_saldoInsuficiente() {
		String cuentaOrigen = "00100001";
		String cuentaDestino = "00100002";
		double importe = 1000.0;
		String codEmp = "003";

		Map<String, Object> origenData = new HashMap<>();
		origenData.put("dec_cuensaldo", 200.0);
		origenData.put("int_cuencontmov", 10);

		when(jdbcTemplate.queryForMap(anyString(), eq(cuentaOrigen))).thenReturn(origenData);

		RuntimeException exception = assertThrows(RuntimeException.class, () ->
				transferenciaService.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp)
		);

		assertTrue(exception.getMessage().contains("saldo insuficiente"));
	}


}
