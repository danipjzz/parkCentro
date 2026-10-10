package vista;

<<<<<<< HEAD
import java.awt.*;
import java.util.List;
import javax.swing.*;
import modelo.Registro;
import modelo.Reporte;
import modelo.TipoVehiculo;

public class VistaSwing extends JFrame implements IVistaParqueadero {
    private JTextArea areaSalida;
    private JButton btnEntrada;
    private JButton btnSalida;
    private JButton btnCupos;
    private JButton btnListar;
    private JButton btnReporte;

    public VistaSwing() {
        super("ParkCentro - Sistema de Parqueadero");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 5, 5));
        btnEntrada = new JButton("Registrar Entrada");
        btnSalida = new JButton("Registrar Salida");
        btnCupos = new JButton("Consultar Cupos");
        btnListar = new JButton("Listar Vehículos");
        btnReporte = new JButton("Cierre Diario");

        panelBotones.add(btnEntrada);
        panelBotones.add(btnSalida);
        panelBotones.add(btnCupos);
        panelBotones.add(btnListar);
        panelBotones.add(btnReporte);

        add(panelBotones, BorderLayout.NORTH);

        // Área central de resultados
        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);
    }

    @Override
    public int mostrarMenu() {
        return 0; // En Swing las acciones son manejadas por eventos de botones.
    }

    @Override
    public String pedirPlaca() {
        return JOptionPane.showInputDialog(this, "Ingrese la placa del vehículo:", "Entrada de Datos", JOptionPane.QUESTION_MESSAGE);
    }

    @Override
    public TipoVehiculo pedirTipo() {
        String[] opciones = {"CARRO", "MOTO"};
        int seleccion = JOptionPane.showOptionDialog(this, "Seleccione el tipo de vehículo", "Tipo Vehículo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        return seleccion == 1 ? TipoVehiculo.MOTO : TipoVehiculo.CARRO;
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        areaSalida.append("[OK] " + mensaje + "\n");
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mostrarError(String error) {
        areaSalida.append("[ERROR] " + error + "\n");
        JOptionPane.showMessageDialog(this, error, "Error de Operación", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void mostrarVehiculos(List<Registro> registros) {
        areaSalida.setText("--- VEHÍCULOS DENTRO DEL PARQUEADERO ---\n");
        if (registros.isEmpty()) {
            areaSalida.append("No hay vehículos en este momento.\n");
            return;
        }
        for (Registro reg : registros) {
            areaSalida.append(String.format("Placa: %-8s | Tipo: %-5s | Entrada: %s\n",
                    reg.getVehiculo().getPlaca(),
                    reg.getVehiculo().getTipo(),
                    reg.getHoraEntrada().toString()));
        }
    }

    @Override
    public void mostrarCupos(int cuposCarros, int cuposMotos) {
        areaSalida.setText("--- CUPOS DISPONIBLES ---\n");
        areaSalida.append("Carros: " + cuposCarros + "\n");
        areaSalida.append("Motos: " + cuposMotos + "\n");
    }

    @Override
    public void mostrarReporte(Reporte reporte) {
        areaSalida.setText("===== REPORTE DE CIERRE DIARIO =====\n");
        areaSalida.append("Carros Atendidos : " + reporte.getCarrosAtendidos() + "\n");
        areaSalida.append(String.format("Total Carros      : $%.2f\n", reporte.getTotalCarros()));
        areaSalida.append("Motos Atendidas  : " + reporte.getMotosAtendidas() + "\n");
        areaSalida.append(String.format("Total Motos       : $%.2f\n", reporte.getTotalMotos()));
        areaSalida.append(String.format("RECAUDO TOTAL     : $%.2f\n", (reporte.getTotalCarros() + reporte.getTotalMotos())));
    }

    @Override
    public void iniciar() {
        setVisible(true);
    }

    // Métodos para vincular acciones del controlador a los botones Swing
    public JButton getBtnEntrada() { return btnEntrada; }
    public JButton getBtnSalida() { return btnSalida; }
    public JButton getBtnCupos() { return btnCupos; }
    public JButton getBtnListar() { return btnListar; }
    public JButton getBtnReporte() { return btnReporte; }
}
=======
public class VistaSwing {
}
>>>>>>> origin/main
