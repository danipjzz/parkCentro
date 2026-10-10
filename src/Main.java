<<<<<<< HEAD
import controlador.ControladorParqueadero;
import modelo.Parqueadero;
import vista.IVistaParqueadero;
import vista.VistaConsola;
import vista.VistaSwing;

public class Main {
    public static void main(String[] args) {
        Parqueadero modelo = new Parqueadero();

        // Para usar en Consola:
        //IVistaParqueadero vista = new VistaConsola();

        // Para cambiar a la interfaz gráfica Swing (Punto extra) se tiene la siguiente linea
        IVistaParqueadero vista = new VistaSwing();

        ControladorParqueadero controlador = new ControladorParqueadero(modelo, vista);
        controlador.iniciar();
    }
=======
public static void main(String[] args) {

>>>>>>> origin/main
}