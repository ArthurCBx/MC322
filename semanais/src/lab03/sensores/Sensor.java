package lab03.sensores;

import lab03.Ambiente;
import lab03.robos.Robo;

import java.util.ArrayList;

public class Sensor {
    private double raio;

    public Sensor(double raio){
        this.raio = Math.abs(raio); // Raio não pode ser negativo.
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = Math.abs(raio);
    }

    public void monitorar(Ambiente ambiente){
        // Verifica no ambiente se existe algum robo dentro do raio do sensor
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt(Math.pow(robo.getPosX(),2) + Math.pow(robo.getPosY(),2));
            if (distancia <= raio){
                System.out.printf("Robo foi encontrado na posição (%d,%d)",robo.getPosX(),robo.getPosY());
            }
        }
    }
}
