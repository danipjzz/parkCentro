package modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CalculadoraTarifaTest {

    private final LocalDateTime entrada = LocalDateTime.of(2026, 10, 10, 8, 0);

    private double cobro(String placa, TipoVehiculo tipo, long minutos) {
        Registro registro = new Registro(new Vehiculo(placa, tipo), entrada);
        registro.setHoraSalida(entrada.plusMinutes(minutos));
        return CalculadoraTarifa.calcularTarifa(registro);
    }

    // Caso 1: carro 4 minutos -> $0
    @Test
    void carro4Minutos_noPaga() {
        assertEquals(0.0, cobro("ABC123", TipoVehiculo.CARRO, 4), 0.001);
    }

    // Caso 2: carro 60 minutos -> (60 - 5) x 130 = 7.150
    @Test
    void carro60Minutos_descuentaMinutosGratis() {
        assertEquals(7150.0, cobro("ABC123", TipoVehiculo.CARRO, 60), 0.001);
    }

    // Caso 3: moto 10 horas -> tope $20.000
    @Test
    void moto10Horas_cobraTarifaPlena() {
        assertEquals(20000.0, cobro("XYZ45K", TipoVehiculo.MOTO, 10 * 60), 0.001);
    }

    @Test
    void carroExactamente5Minutos_noPaga() {
        assertEquals(0.0, cobro("ABC123", TipoVehiculo.CARRO, 5), 0.001);
    }

    @Test
    void carro6Minutos_pagaUnMinuto() {
        assertEquals(130.0, cobro("ABC123", TipoVehiculo.CARRO, 6), 0.001);
    }

    @Test
    void moto30Minutos_usaTarifaDeMoto() {
        assertEquals(2250.0, cobro("XYZ45K", TipoVehiculo.MOTO, 30), 0.001);
    }

    // (350 - 5) x 130 = 44.850, todavia por debajo del tope
    @Test
    void carroJustoDebajoDelTope_noAplicaTope() {
        assertEquals(44850.0, cobro("ABC123", TipoVehiculo.CARRO, 350), 0.001);
    }

    @Test
    void carroMuchasHoras_cobraTarifaPlena() {
        assertEquals(45000.0, cobro("ABC123", TipoVehiculo.CARRO, 400), 0.001);
    }

    // (260 - 5) x 90 = 22.950 > 20.000 -> tope de moto
    @Test
    void motoSuperaTope_cobra20000() {
        assertEquals(20000.0, cobro("XYZ45K", TipoVehiculo.MOTO, 260), 0.001);
    }
}