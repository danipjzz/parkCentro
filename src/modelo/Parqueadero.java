package modelo;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Parqueadero {
    private int limiteCarro = 40;
    private int limiteMoto = 20;
    private ArrayList<Registro> registros;

    public Parqueadero(){
        registros = new ArrayList<>();
    }

    public void registrarEntrada(String placa, TipoVehiculo tipo) throws ParqueaderoException{
        if (!validarPlaca(placa, tipo)) {
            throw new PlacaInvalidaException("La placa no tiene un formato válido.");
        }

        if (vehiculoEstaDentro(placa)) {
            throw new VehiculoRegistradoException("El vehiculo ya se encuentra registrado");
        }

        if (cupoLleno(tipo)) {
            throw new SinCupoException("Ya no hay cupo en el parqueadero");
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

    public double registrarSalida(String placa, LocalDateTime horaSalida) throws ParqueaderoException{
        for (Registro registro: registros){
            if (registro.getVehiculo().getPlaca().equals(placa) && registro.getHoraSalida()==null){
                registro.setHoraSalida(horaSalida);
                return CalculadoraTarifa.calcularTarifa(registro);
            }
        }
        throw new VehiculoNoEncontradoException("No se encontró el vehiculo");
    }

    public int consultarCupos(TipoVehiculo tipo){
        int cantidad = 0;

        for (Registro registro : registros) {
            if (registro.getVehiculo().getTipo() == tipo && registro.getHoraSalida() == null) {
                cantidad++;
            }
        }

        if (tipo == TipoVehiculo.CARRO) {
            return limiteCarro - cantidad;
        } else {
            return limiteMoto - cantidad;
        }
    }

    public ArrayList<Registro> listaVehiculos(){
        ArrayList<Registro> vehiculosDentro = new ArrayList<>();
        for (Registro registro: registros){
            if (registro.getHoraSalida() ==null){
                vehiculosDentro.add(registro);
            }
        }
        return vehiculosDentro;
    }

    public Reporte generarReporte(){
        int carrosAtendidos = 0;
        int motosAtendidas = 0;
        double totalCarros = 0;
        double totalMotos = 0;

        for (Registro registro: registros){
            if(registro.getHoraSalida()!=null){
                double tarifa = CalculadoraTarifa.calcularTarifa(registro);
                if(registro.getVehiculo().getTipo() == TipoVehiculo.CARRO){
                    carrosAtendidos++;
                    totalCarros += tarifa;

                } else{
                    motosAtendidas++;
                    totalMotos += tarifa;
                }
            }
        }
        return new Reporte(carrosAtendidos, motosAtendidas, totalCarros, totalMotos);
    }
}
