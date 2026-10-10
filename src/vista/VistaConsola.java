package vista;

<<<<<<< HEAD
import java.util.List;
import java.util.Scanner;
import modelo.Registro;
import modelo.Reporte;
import modelo.TipoVehiculo;

public class VistaConsola implements IVistaParqueadero {
    private final Scanner scanner;

    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int mostrarMenu() {
        System.out.println("\n===== PARQUEADERO PARKCENTRO =====");
        System.out.println("1. Registrar Entrada");
        System.out.println("2. Registrar Salida");
        System.out.println("3. Consultar Cupos Disponibles");
        System.out.println("4. Listar Vehículos en Parqueadero");
        System.out.println("5. Generar Reporte Diario");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String pedirPlaca() {
        System.out.print("Ingrese la placa del vehículo: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    @Override
    public TipoVehiculo pedirTipo() {
        while (true) {
            System.out.print("Tipo de vehículo (1. CARRO / 2. MOTO): ");
            String opcion = scanner.nextLine().trim();
            if (opcion.equals("1") || opcion.equalsIgnoreCase("CARRO")) {
                return TipoVehiculo.CARRO;
            } else if (opcion.equals("2") || opcion.equalsIgnoreCase("MOTO")) {
                return TipoVehiculo.MOTO;
            }
            System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println("[ÉXITO] " + mensaje);
    }

    @Override
    public void mostrarError(String error) {
        System.out.println("[ERROR] " + error);
    }

    @Override
    public void mostrarVehiculos(List<Registro> registros) {
        System.out.println("\n--- VEHÍCULOS EN EL PARQUEADERO ---");
        if (registros.isEmpty()) {
            System.out.println("No hay vehículos parqueados actualmente.");
            return;
        }
        for (Registro reg : registros) {
            System.out.printf("Placa: %-8s | Tipo: %-5s | Hora Entrada: %s%n",
                    reg.getVehiculo().getPlaca(),
                    reg.getVehiculo().getTipo(),
                    reg.getHoraEntrada().toString());
        }
    }

    @Override
    public void mostrarCupos(int cuposCarros, int cuposMotos) {
        System.out.println("\n--- CUPOS DISPONIBLES ---");
        System.out.println("Carros: " + cuposCarros);
        System.out.println("Motos: " + cuposMotos);
    }

    @Override
    public void mostrarReporte(Reporte reporte) {
        System.out.println("\n===== REPORTE DE CIERRE DIARIO =====");
        System.out.println("Carros atendidos : " + reporte.getCarrosAtendidos());
        System.out.printf("Total recaudado carros : $%.2f%n", reporte.getTotalCarros());
        System.out.println("Motos atendidas  : " + reporte.getMotosAtendidas());
        System.out.printf("Total recaudado motos  : $%.2f%n", reporte.getTotalMotos());
        System.out.printf("RECAUDO TOTAL        : $%.2f%n", (reporte.getTotalCarros() + reporte.getTotalMotos()));
    }

    @Override
    public void iniciar() {
        // En consola la interacción es dirigida por el ciclo principal en el controlador.
    }
}
=======
public class VistaConsola {
}
>>>>>>> origin/main
