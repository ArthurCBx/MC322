package lab03.sensores;

import lab03.Ambiente;
import lab03.robos.Robo;

import java.util.ArrayList;

public class SensorClasse extends Sensor{
    // Subclasse de Sensor que herda o metodo de monitorar bidimensionalmente, mas tambem consegue descobrir a classe de robos num raio tridimensional
    public double raio3D;

    public SensorClasse(double raio, double raio3D) {
        super(raio);
        this.raio3D = raio3D;
    }

    public void monitoraClasse(Ambiente ambiente, Robo mestre){
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt((Math.pow(robo.getPosX() - mestre.getPosX(), 2) + Math.pow(robo.getPosY() - mestre.getPosY(), 2) + Math.pow(robo.getAltitude() - mestre.getAltitude(), 2)));
            if (distancia <= raio3D){
                System.out.printf("Robo da classe %s foi encontrado na posicao (%d, %d, %d)\n", robo.getClass(), robo.getPosX(), robo.getPosY(), robo.getAltitude());
            }
        }
    }
}
