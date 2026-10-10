package modelo;
import java.time.LocalDateTime;

public class Registro {
    private Vehiculo vehiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;

    public Registro(Vehiculo vehiculo, LocalDateTime horaEntrada){
        this.vehiculo = vehiculo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = null;
    }


    public Vehiculo getVehiculo(){
        return vehiculo;
    }

    public LocalDateTime getHoraEntrada(){
        return horaEntrada;
    }

    public void setHoraSalida(LocalDateTime horaSalida){
        this.horaSalida = horaSalida;
    }

    public LocalDateTime getHoraSalida(){
        return horaSalida;
    }
}
