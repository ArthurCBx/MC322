package lab03.sensores;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;

public class Sensor {
    private double raio;

    public Sensor(double raio) {
        this.raio = Math.abs(raio); // Raio não pode ser negativo.
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = Math.abs(raio);
    }

    public void monitorar(Ambiente ambiente, Robo mestre) {
        // Verifica no ambiente se existe algum robo dentro do raio do sensor
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt(Math.pow(robo.getPosX() - mestre.getPosX(), 2) + Math.pow(robo.getPosY() - mestre.getPosY(), 2));
            if (distancia <= raio && !robo.equals(mestre) && (robo.getAltitude() == mestre.getAltitude())) {
                System.out.printf("Robo encontrado na posição (%d,%d).\n", robo.getPosX(), robo.getPosY());
            }
        }

        ArrayList<Obstaculo> obstaculos = ambiente.getListaObstaculos();
        for (Obstaculo obstaculo : obstaculos) {
            int deltaX1 = obstaculo.getPosX1() - mestre.getPosX(); int deltaX2 = obstaculo.getPosX2() - mestre.getPosX();
            int deltaY1 = obstaculo.getPosY1() - mestre.getPosY(); int deltaY2 = obstaculo.getPosY2() - mestre.getPosY();

            if ((deltaX1 < 0 && deltaX2 > 0) || deltaX1 == 0 || deltaX2 == 0){
                if((deltaY1 < 0 && deltaY2 > 0) || deltaY1 == 0 || deltaY2 == 0){
                    System.out.printf("Robo está dentro do obstáculo %s.\n",obstaculo.getTipo().getNome());
                }
            }
            else{
                double distancia = Math.sqrt(Math.pow(Math.min(deltaX1,deltaX2) - mestre.getPosX(), 2) + Math.pow(Math.min(deltaY1,deltaY2) - mestre.getPosY(), 2));
                if (distancia <= raio) {
                    System.out.printf("Obstáculo %s encontrado a %d metros em X e %d metros em Y.\n",obstaculo.getTipo().getNome(), Math.min(deltaX1,deltaX2), Math.min(deltaY1,deltaY2));
                }
            }
        }
    }

    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre){
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        ArrayList<Robo> listaRobosEncontrados = new ArrayList<>();
        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt(Math.pow(robo.getPosX() - mestre.getPosX(), 2) + Math.pow(robo.getPosY() - mestre.getPosY(), 2));
            if (distancia <= raio && !robo.equals(mestre) && (robo.getAltitude() == mestre.getAltitude())) {
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }
}
