package lab03.sensores;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;

public class SensorClasse extends Sensor{
    // Subclasse de Sensor que consegue descobrir a classe de robos num raio tridimensional

    public SensorClasse(double raio) {
        super(raio);
    }

    @Override
    public void monitorar(Ambiente ambiente, Robo mestre){
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();

        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt((Math.pow(robo.getPosX() - mestre.getPosX(), 2) + Math.pow(robo.getPosY() - mestre.getPosY(), 2) + Math.pow(robo.getAltitude() - mestre.getAltitude(), 2)));
            if (distancia <= getRaio()){
                System.out.printf("Robo da classe %s foi encontrado na posicao (%d, %d, %d).\n", robo.getClass(), robo.getPosX(), robo.getPosY(), robo.getAltitude());
            }
        }
    }

    @Override
    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre) {
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        ArrayList<Robo> listaRobosEncontrados = new ArrayList<>();

        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt((Math.pow(robo.getPosX() - mestre.getPosX(), 2) + Math.pow(robo.getPosY() - mestre.getPosY(), 2) + Math.pow(robo.getAltitude() - mestre.getAltitude(), 2)));
            if (distancia <= getRaio()) {
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }

    @Override
    public ArrayList<Obstaculo> listaObstaculosEncontrados(Ambiente ambiente, Robo mestre) {
        //SensorClasse não consegue identificar obstáculos.
        return null;
    }
}
