package lab03.sensores;

import lab03.Ambiente;
import lab03.robos.Robo;

import java.util.ArrayList;

public class SensorAltitude extends Sensor{
    // Subclasse de Sensor que herda o metodo de monitorar bidimensionalmente, mas tambem consegue monitorar a altura de robos em um raioZ
    private double raioZ;

    public SensorAltitude(double raio, double raioZ) {
        super(raio);
        this.raioZ = Math.abs(raioZ); // RaioZ não pode ser negativo.
    }

    public double getRaioZ() {
        return raioZ;
    }

    public void setRaioZ(double raioZ) {
        this.raioZ = Math.abs(raioZ);
    }

    public void monitoraAltitude(Ambiente ambiente, Robo mestre){
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            int distancia = Math.abs(robo.getAltitude() - mestre.getAltitude());
            if (distancia <= raioZ){
                System.out.printf("Robo foi encontrado na altura %d\n",robo.getAltitude());
            }
        }
    }

}
