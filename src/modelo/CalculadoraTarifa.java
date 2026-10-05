package modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class CalculadoraTarifa {

    public static double calcularTarifa(Registro registro) {
        Vehiculo vehiculo = registro.getVehiculo();
        LocalDateTime horaEntrada = registro.getHoraEntrada();
        LocalDateTime horaSalida = registro.getHoraSalida();
        Duration duracion = Duration.between(horaEntrada, horaSalida);
        long minutos = duracion.toMinutes();
        double precio = 0;

        if (minutos <= 5) {
            return precio;
        } else if (vehiculo.getTipo() == TipoVehiculo.CARRO){
            precio = (minutos - 5) * 130;
            if (precio < 45000) {
                return precio;
            } else {
                return 45000;
            }
        } else{
           precio = (minutos - 5) * 90;
           if (precio<20000){
              return precio;
           }else{
               return 20000;
           }
           }
        }
}
