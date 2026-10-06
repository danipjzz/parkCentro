package modelo;

public class Reporte {
    private int carrosAtendidos;
    private int motosAtendidas;
    private double totalCarros;
    private double totalMotos;

    public Reporte(int carrosAtendidos, int motosAtendidas, double totalCarros, double totalMotos){
        this.carrosAtendidos = carrosAtendidos;
        this.motosAtendidas = motosAtendidas;
        this.totalCarros = totalCarros;
        this.totalMotos = totalMotos;
    }

    public int getCarrosAtendidos() {
        return carrosAtendidos;
    }

    public int getMotosAtendidas() {
        return motosAtendidas;
    }

    public double getTotalCarros() {
        return totalCarros;
    }

    public double getTotalMotos() {
        return totalMotos;
    }
}
