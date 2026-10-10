package vista;

<<<<<<< HEAD
import java.util.List;
import modelo.Registro;
import modelo.Reporte;
import modelo.TipoVehiculo;

public interface IVistaParqueadero {
    int mostrarMenu();
    String pedirPlaca();
    TipoVehiculo pedirTipo();
    void mostrarMensaje(String mensaje);
    void mostrarError(String error);
    void mostrarVehiculos(List<Registro> registros);
    void mostrarCupos(int cuposCarros, int cuposMotos);
    void mostrarReporte(Reporte reporte);
    void iniciar();
}
=======
public class IVistaParqueadero {
}
>>>>>>> origin/main
