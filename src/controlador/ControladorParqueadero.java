package controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import modelo.Parqueadero;
import modelo.ParqueaderoException;
import modelo.Registro;
import modelo.Reporte;
import modelo.TipoVehiculo;
import vista.IVistaParqueadero;
import vista.VistaConsola;
import vista.VistaSwing;

public class ControladorParqueadero {
    private final Parqueadero modelo;
    private final IVistaParqueadero vista;
    private final Scanner scanner;

    public ControladorParqueadero(Parqueadero modelo, IVistaParqueadero vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.scanner = new Scanner(System.in);

        // AQUÍ VINCULAMOS LOS BOTONES SI LA VISTA ES SWING
        if (vista instanceof VistaSwing) {
            VistaSwing vistaSwing = (VistaSwing) vista;
            vistaSwing.getBtnEntrada().addActionListener(e -> registrarEntrada());
            vistaSwing.getBtnSalida().addActionListener(e -> registrarSalida());
            vistaSwing.getBtnCupos().addActionListener(e -> consultarCupos());
            vistaSwing.getBtnListar().addActionListener(e -> listarVehiculos());
            vistaSwing.getBtnReporte().addActionListener(e -> generarReporte());
        }
    }

    public void iniciar() {
        vista.iniciar();

        if (vista instanceof VistaConsola) {
            boolean salir = false;
            while (!salir) {
                int opcion = vista.mostrarMenu();
                switch (opcion) {
                    case 1: registrarEntrada(); break;
                    case 2: registrarSalida(); break;
                    case 3: consultarCupos(); break;
                    case 4: listarVehiculos(); break;
                    case 5: generarReporte(); break;
                    case 6: salir = true; vista.mostrarMensaje("¡Hasta luego!"); break;
                    default: vista.mostrarError("Opción no válida."); break;
                }
            }
        }
    }

    public void registrarEntrada() {
        String placa = vista.pedirPlaca();
        if (placa == null || placa.trim().isEmpty()) return;
        TipoVehiculo tipo = vista.pedirTipo();
        try {
            modelo.registrarEntrada(placa, tipo);
            vista.mostrarMensaje("Entrada registrada con éxito para la placa: " + placa);
        } catch (ParqueaderoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    public void registrarSalida() {
        String placa = vista.pedirPlaca();
        if (placa == null || placa.trim().isEmpty()) return;

        LocalDateTime horaEntrada = null;
        for (Registro reg : modelo.listaVehiculos()) {
            if (reg.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                horaEntrada = reg.getHoraEntrada();
                break;
            }
        }

        if (horaEntrada == null) {
            vista.mostrarError("No se encontró el vehículo con placa " + placa + " dentro del parqueadero.");
            return;
        }

        long minutos = 0;
        if (vista instanceof VistaSwing) {
            String input = JOptionPane.showInputDialog((VistaSwing) vista,
                    "Ingrese los minutos de permanencia a simular (ej: 4, 60, 65):",
                    "Simulación de Tiempo",
                    JOptionPane.QUESTION_MESSAGE);
            if (input == null || input.trim().isEmpty()) return;
            try {
                minutos = Long.parseLong(input.trim());
            } catch (NumberFormatException e) {
                vista.mostrarError("Número de minutos inválido.");
                return;
            }
        } else {
            System.out.print("Ingrese los minutos de permanencia a simular (ej: 4, 60, 65): ");
            try {
                minutos = Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                vista.mostrarError("Número de minutos inválido.");
                return;
            }
        }

        LocalDateTime horaSalidaSimulada = horaEntrada.plusMinutes(minutos);

        try {
            double tarifa = modelo.registrarSalida(placa, horaSalidaSimulada);
            vista.mostrarMensaje(String.format("Salida registrada. Placa: %s | Minutos: %d | Valor a pagar: $%.2f", placa, minutos, tarifa));
        } catch (ParqueaderoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    public void consultarCupos() {
        int cuposCarro = modelo.consultarCupos(TipoVehiculo.CARRO);
        int cuposMoto = modelo.consultarCupos(TipoVehiculo.MOTO);
        vista.mostrarCupos(cuposCarro, cuposMoto);
    }

    public void listarVehiculos() {
        List<Registro> lista = modelo.listaVehiculos();
        vista.mostrarVehiculos(lista);
    }

    public void generarReporte() {
        Reporte reporte = modelo.generarReporte();
        vista.mostrarReporte(reporte);
    }
}