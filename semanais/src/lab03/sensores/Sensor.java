package lab03.sensores;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;

public class Sensor {
    private double raio;

    // Construtor para sensor:
    public Sensor(double raio) {
        this.raio = Math.abs(raio); // Raio não pode ser negativo.
    }


    // Getter e Setter:

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = Math.abs(raio);
    }


    // Função basica de monitoramento do ambiente:

    public void monitorar(Ambiente ambiente, Robo mestre) {
        // Verifica no ambiente se existe algum robo dentro do raio do sensor
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt(Math.pow(robo.getPosX() - mestre.getPosX(), 2) + Math.pow(robo.getPosY() - mestre.getPosY(), 2));
            if (distancia <= raio && !robo.equals(mestre) && (robo.getAltitude() == mestre.getAltitude())) {
                System.out.printf("Robo encontrado na posição (%d,%d).\n", robo.getPosX(), robo.getPosY());
            }
        }

        // Verifica se algum obstáculo está dentro do raio do sensor ou se o robô está dentro do obstáculo
        ArrayList<Obstaculo> obstaculos = ambiente.getListaObstaculos();
        for (Obstaculo obstaculo : obstaculos) {
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getPosX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getPosY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getPosX() - cX,2) + Math.pow(mestre.getPosY() - cY,2));
            if ((mestre.getAltitude() >= obstaculo.getBase()) && (mestre.getAltitude() <= (obstaculo.getBase() + obstaculo.getAltura()) )){
                if (distancia == 0) {
                    System.out.printf("Robo está dentro do obstáculo %s\n", obstaculo.getTipo().getNome());
                } else if (distancia <= raio) {
                    System.out.printf("Obstáculo %s encontrado a %.2f metros\n", obstaculo.getTipo().getNome(), distancia);
                }
            }
        }
    }

    // Função para retornar listas de robôs se estão dentro do raio:
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

    // Função para retornar listas de obstáculos se estão dentro do raio:
    public ArrayList<Obstaculo> listaObstaculosEncontrados(Ambiente ambiente, Robo mestre){
        ArrayList<Obstaculo> obstaculos = ambiente.getListaObstaculos();
        ArrayList<Obstaculo> listaObstaculosEncontrados = new ArrayList<>();

        for (Obstaculo obstaculo : obstaculos) {
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getPosX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getPosY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getPosX() - cX,2) + Math.pow(mestre.getPosY() - cY,2));
            if (distancia <= raio && (mestre.getAltitude() >= obstaculo.getBase()) && (mestre.getAltitude() <= obstaculo.getBase() + obstaculo.getAltura())){
                // Verifica se o robô está dentro do obstáculo ou consegue ver o obstáculo na mesma altura em que está
                listaObstaculosEncontrados.add(obstaculo);
            }
        }
        return listaObstaculosEncontrados;
    }

}
