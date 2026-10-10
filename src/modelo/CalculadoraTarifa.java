package modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class CalculadoraTarifa {

    public static double calcularTarifa(Registro registro) {
        Vehiculo vehiculo = registro.getVehiculo();
        LocalDateTime horaEntrada = registro.getHoraEntrada();
        LocalDateTime horaSalida = registro.getHoraSalida();

        if (horaSalida == null) {
            return 0;
        }

        Duration duracion = Duration.between(horaEntrada, horaSalida);
        long minutos = duracion.toMinutes();

        if (minutos <= 5) {
            return 0;
        }

        if (vehiculo.getTipo() == TipoVehiculo.CARRO) {
            double precio = (minutos - 5) * 130;
            return Math.min(precio, 45000);
        } else {
            double precio = (minutos - 5) * 90;
            return Math.min(precio, 20000);
        }
    }
}
