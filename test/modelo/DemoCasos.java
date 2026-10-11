package modelo;

import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Ejecuta los 7 casos de prueba del enunciado y muestra en pantalla
 * el resultado esperado frente al obtenido.
 * Ejecutar: java -cp out modelo.DemoCasos
 */
public class DemoCasos {

    private static final Locale CO = Locale.forLanguageTag("es-CO");

    private static String pesos(double valor) {
        return String.format(CO, "$%,.0f", valor);
    }

    private static void encabezado(int caso, String entrada, String esperado) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("CASO " + caso + ": " + entrada);
        System.out.println("  Esperado: " + esperado);
    }

    private static void resultadoValor(double obtenido, double esperado) {
        String marca = Math.abs(obtenido - esperado) < 0.001 ? "[OK]" : "[FALLA]";
        System.out.println("  Obtenido: Valor a pagar: " + pesos(obtenido) + "  " + marca);
    }

    private static void resultadoError(ParqueaderoException e, Class<?> tipoEsperado) {
        String marca = tipoEsperado.isInstance(e) ? "[OK]" : "[FALLA]";
        System.out.println("  Obtenido: Error \"" + e.getMessage() + "\"");
        System.out.println("            (excepcion: " + e.getClass().getSimpleName() + ")  " + marca);
    }

    public static void main(String[] args) throws ParqueaderoException {
        System.out.println("=============== CASOS DE PRUEBA PARKCENTRO ===============");

        // Caso 1
        encabezado(1, "Carro ABC123 permanece 4 minutos", "Valor a pagar: $0");
        Parqueadero p = new Parqueadero();
        p.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        resultadoValor(p.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(4)), 0);

        // Caso 2
        encabezado(2, "Carro ABC123 permanece 60 minutos", "(60 - 5) x 130 = $7.150");
        p = new Parqueadero();
        p.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        resultadoValor(p.registrarSalida("ABC123", LocalDateTime.now().plusMinutes(60)), 7150);

        // Caso 3
        encabezado(3, "Moto XYZ45K permanece 10 horas", "Supera el tope -> $20.000");
        p = new Parqueadero();
        p.registrarEntrada("XYZ45K", TipoVehiculo.MOTO);
        resultadoValor(p.registrarSalida("XYZ45K", LocalDateTime.now().plusHours(10)), 20000);

        // Caso 4
        encabezado(4, "Ingresar ABC123 cuando ya esta dentro",
                "Error: \"El vehiculo ya se encuentra en el parqueadero\"");
        p = new Parqueadero();
        p.registrarEntrada("ABC123", TipoVehiculo.CARRO);
        try {
            p.registrarEntrada("ABC123", TipoVehiculo.CARRO);
            System.out.println("  Obtenido: no lanzo error  [FALLA]");
        } catch (ParqueaderoException e) {
            resultadoError(e, VehiculoRegistradoException.class);
        }

        // Caso 5
        encabezado(5, "Ingresar carro con placa ABC12D", "Error: formato de placa invalido");
        p = new Parqueadero();
        try {
            p.registrarEntrada("ABC12D", TipoVehiculo.CARRO);
            System.out.println("  Obtenido: no lanzo error  [FALLA]");
        } catch (ParqueaderoException e) {
            resultadoError(e, PlacaInvalidaException.class);
        }

        // Caso 6
        encabezado(6, "Ingresar el carro numero 41", "Error: no hay cupos para carros");
        p = new Parqueadero();
        for (int i = 0; i < 40; i++) {
            p.registrarEntrada(String.format("AAA%03d", i), TipoVehiculo.CARRO);
        }
        try {
            p.registrarEntrada("BBB001", TipoVehiculo.CARRO);
            System.out.println("  Obtenido: no lanzo error  [FALLA]");
        } catch (ParqueaderoException e) {
            resultadoError(e, SinCupoException.class);
        }

        // Caso 7
        encabezado(7, "Salida de una placa no registrada",
                "Error: no existe entrada para esa placa");
        p = new Parqueadero();
        try {
            p.registrarSalida("ZZZ999", LocalDateTime.now());
            System.out.println("  Obtenido: no lanzo error  [FALLA]");
        } catch (ParqueaderoException e) {
            resultadoError(e, VehiculoNoEncontradoException.class);
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("[OK] = valor o tipo de error correcto. Compara el texto del");
        System.out.println("mensaje obtenido con el esperado: puede diferir en la redaccion.");
    }
}