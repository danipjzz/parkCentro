package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * registrarEntrada() usa LocalDateTime.now() como hora de entrada, asi que
 * las salidas se simulan con now().plusMinutes(...).
 */
class ParqueaderoTest {

    private Parqueadero parqueadero;

    @BeforeEach
    void setUp() {
        parqueadero = new Parqueadero();
    }

    // Caso 1
    @Test
    void carro4Minutos_pagaCero() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        double valor = parqueadero.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(4));
        assertEquals(0.0, valor, 0.001);
    }

    // Caso 2
    @Test
    void carro60Minutos_paga7150() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        double valor = parqueadero.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(60));
        assertEquals(7150.0, valor, 0.001);
    }

    // Caso 3
    @Test
    void moto10Horas_pagaTope20000() throws ParqueaderoException {
        parqueadero.registrarEntrada("XYZ45K", TipoVehiculo.MOTO);
        double valor = parqueadero.registrarSalida("XYZ45K", LocalDateTime.now().plusHours(10));
        assertEquals(20000.0, valor, 0.001);
    }

    // Caso 4
    @Test
    void placaRepetida_lanzaVehiculoRegistradoException() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        assertThrows(VehiculoRegistradoException.class,
                () -> parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO));
    }

    // Caso 5
    @Test
    void carroConPlacaDeMoto_lanzaPlacaInvalidaException() {
        assertThrows(PlacaInvalidaException.class,
                () -> parqueadero.registrarEntrada("ABC12D", TipoVehiculo.CARRO));
    }

    @Test
    void motoConPlacaDeCarro_lanzaPlacaInvalidaException() {
        assertThrows(PlacaInvalidaException.class,
                () -> parqueadero.registrarEntrada("ABC123", TipoVehiculo.MOTO));
    }

    @Test
    void placaEnMinusculas_lanzaPlacaInvalidaException() {
        assertThrows(PlacaInvalidaException.class,
                () -> parqueadero.registrarEntrada("abc123", TipoVehiculo.CARRO));
    }

    // Caso 6
    @Test
    void carro41_lanzaSinCupoException() throws ParqueaderoException {
        for (int i = 0; i < 40; i++) {
            parqueadero.registrarEntrada(String.format("AAA%03d", i), TipoVehiculo.CARRO);
        }
        assertTrue(parqueadero.cupoLleno(TipoVehiculo.CARRO));
        assertThrows(SinCupoException.class,
                () -> parqueadero.registrarEntrada("BBB001", TipoVehiculo.CARRO));
    }

    @Test
    void moto21_lanzaSinCupoException() throws ParqueaderoException {
        for (int i = 0; i < 20; i++) {
            parqueadero.registrarEntrada(String.format("AAA%02dA", i), TipoVehiculo.MOTO);
        }
        assertThrows(SinCupoException.class,
                () -> parqueadero.registrarEntrada("BBB01B", TipoVehiculo.MOTO));
    }

    // Caso 7
    @Test
    void salidaSinEntrada_lanzaVehiculoNoEncontradoException() {
        assertThrows(VehiculoNoEncontradoException.class,
                () -> parqueadero.registrarSalida("ZZZ999", LocalDateTime.now()));
    }

    // RF4
    @Test
    void cupos_seDescuentanPorTipoYSeLiberanAlSalir() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        parqueadero.registrarEntrada("XYZ45K", TipoVehiculo.MOTO);
        assertEquals(39, parqueadero.consultarCupos(TipoVehiculo.CARRO));
        assertEquals(19, parqueadero.consultarCupos(TipoVehiculo.MOTO));

        parqueadero.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(10));
        assertEquals(40, parqueadero.consultarCupos(TipoVehiculo.CARRO));
        assertEquals(19, parqueadero.consultarCupos(TipoVehiculo.MOTO));
    }

    // RF5
    @Test
    void listaVehiculos_soloMuestraLosQueEstanDentro() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        parqueadero.registrarEntrada("DEF456", TipoVehiculo.CARRO);
        parqueadero.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(10));

        assertEquals(1, parqueadero.listaVehiculos().size());
        assertEquals("DEF456", parqueadero.listaVehiculos().get(0).getVehiculo().getPlaca());
    }

    @Test
    void placaPuedeReingresarDespuesDeSalir() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        parqueadero.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(10));
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        assertTrue(parqueadero.vehiculoEstaDentro("ABC123"));
    }

    @Test
    void vehiculoQueSalio_yaNoEstaDentro() throws ParqueaderoException {
        parqueadero.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        parqueadero.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(10));
        assertFalse(parqueadero.vehiculoEstaDentro("ABC123"));
    }

    @Test
    void validarPlaca_aceptaFormatosCorrectos() {
        assertTrue(parqueadero.validarPlaca("ABC123", TipoVehiculo.CARRO));
        assertTrue(parqueadero.validarPlaca("XYZ45K", TipoVehiculo.MOTO));
    }
}