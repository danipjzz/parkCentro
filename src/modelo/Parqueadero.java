package modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class
pParqueadero {
    private int limiteCarro = 40;
    private int limiteMoto = 20;
    private ArrayList<Registro> registros;

    public Parqueadero(){
        registros = new ArrayList<>();
    }

    public void registrarEntrada(String placa, TipoVehiculo tipo){
        if (!validarPlaca(placa, tipo)) {
            // placa inválida
        }

        if (vehiculoEstaDentro(placa)) {
            // vehículo ya está dentro
        }

        if (cupoLleno(tipo)) {
            // no hay cupo
        }
        Vehiculo vehiculo = new Vehiculo(placa, tipo);
        Registro registro = new Registro(vehiculo, LocalDateTime.now());
        registros.add(registro);
    }

    public boolean validarPlaca(String placa, TipoVehiculo tipo){
        if (tipo == TipoVehiculo.CARRO){
            return placa.matches("[A-Z]{3}[0-9]{3}");
        }else {
            return placa.matches("[A-Z]{3}[0-9]{2}[A-Z]");
        }
    }

    public boolean vehiculoEstaDentro(String placa){
        for (Registro registro: registros){
            if (registro.getVehiculo().getPlaca().equals(placa)){
                if (registro.getHoraSalida() == null){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cupoLleno(TipoVehiculo tipo){
        int cantidad = 0;
        for (Registro registro: registros){
            if (registro.getVehiculo().getTipo() == tipo && (registro.getHoraSalida()==null)){
                cantidad++;
            }
        }
        if (tipo == TipoVehiculo.CARRO){
            return cantidad >= limiteCarro;
        } else {
            return cantidad >= limiteMoto;
        }
    }
}
