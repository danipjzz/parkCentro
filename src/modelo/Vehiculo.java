package modelo;

public class Vehiculo {
    private String placa;
    private TipoVehiculo tipo;

    public Vehiculo(String placa, TipoVehiculo tipo) {
        this.placa = placa;
        this.tipo = tipo;
    }

    public void setPlaca(String placa){
        this.placa = placa;
    }

    public String getPlaca(){
        return placa;
    }

    public void setTipo(TipoVehiculo tipo){
        this.tipo = tipo;
    }

    public TipoVehiculo getTipo(){
        return tipo;
    }

}
