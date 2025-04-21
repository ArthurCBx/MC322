package lab03.sensores;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;

public class SensorAltitude extends Sensor{
    /* Subclasse de Sensor que herda o metodo de monitorar bidimensionalmente, mas também consegue monitorar
     a altura em um raioZ imediatamente acima ou abaixo da posição do robô ao qual pertence.
     */

    private double raioZ;

    // Construtor para sensor:
    public SensorAltitude(double raioZ, double raio) {
        super(raio);
        this.raioZ = Math.abs(raioZ); // RaioZ não pode ser negativo.
    }

    // Getter e Setter:

    public double getRaioZ() {
        return raioZ;
    }

    public void setRaioZ(double raioZ) {
        this.raioZ = Math.abs(raioZ);
    }

    // Função para monitorar o posicionamento do robo em relação aos outros robos e obstaculos

    public void monitoraAltitude(Ambiente ambiente, Robo mestre) {
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            int distancia = Math.abs(robo.getAltitude() - mestre.getAltitude());
            if (distancia <= raioZ && (mestre.getPosX() == robo.getPosX()) && (mestre.getPosY() == robo.getPosY()) && !robo.equals(mestre)) {
                System.out.printf("Robo encontrado a %d metros de altitude.\n", distancia);
            }
        }

        ArrayList<Obstaculo> obstaculos = ambiente.getListaObstaculos();
        for (Obstaculo obstaculo : obstaculos) {
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getPosX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getPosY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getPosX() - cX, 2) + Math.pow(mestre.getPosY() - cY, 2));
            if (distancia == 0) {
                if ((mestre.getAltitude() >= obstaculo.getBase()) && (mestre.getAltitude() <= obstaculo.getBase() + obstaculo.getAltura())) {
                    System.out.printf("Robo está dentro do obstáculo %s.\n", obstaculo.getTipo().getNome());
                } else if (mestre.getAltitude() > obstaculo.getBase() + obstaculo.getAltura()) {
                    System.out.printf("Robo está acima do obstáculo %s.\n", obstaculo.getTipo().getNome());
                } else if(mestre.getAltitude() < obstaculo.getBase()) {
                    System.out.printf("Robo está abaixo do obstáculo %s.\n", obstaculo.getTipo().getNome());
                }
            }
        }
    }


    // Metodo para retornar um ArrayList com os robôs encontrados pelo sensor.
    @Override
    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre) {
        ArrayList<Robo> listaRobosEncontrados = new ArrayList<>();
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();

        for (Robo robo : listaRobos) {
            int distancia = Math.abs(robo.getAltitude() - mestre.getAltitude());
            if (distancia <= raioZ && (mestre.getPosX() == robo.getPosX()) && (mestre.getPosY() == robo.getPosY()) && !robo.equals(mestre)) {
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }


    // Metodo para retornar um ArrayList com os obstaculos encontrados pelo sensor.
    @Override
    public ArrayList<Obstaculo> listaObstaculosEncontrados(Ambiente ambiente, Robo mestre) {
        ArrayList<Obstaculo> listaObstaculosEncontrados = super.listaObstaculosEncontrados(ambiente, mestre);
        ArrayList<Obstaculo> obstaculos = ambiente.getListaObstaculos();
        for (Obstaculo obstaculo : obstaculos){
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getPosX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getPosY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getPosX() - cX, 2) + Math.pow(mestre.getPosY() - cY, 2));
            int dZ = 0;
            if (distancia == 0) {
                if (mestre.getAltitude() > (obstaculo.getBase()+obstaculo.getAltura())) {
                    dZ = mestre.getAltitude() - (obstaculo.getBase()+obstaculo.getAltura());
                } else if(mestre.getAltitude() < obstaculo.getBase()) {
                    dZ = mestre.getAltitude() - obstaculo.getBase();
                }

                if (dZ <= raioZ){
                    listaObstaculosEncontrados.add(obstaculo);
                }
            }
        }
        return listaObstaculosEncontrados;
    }
}
