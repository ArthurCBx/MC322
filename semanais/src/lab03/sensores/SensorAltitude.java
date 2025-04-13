package lab03.sensores;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;

public class SensorAltitude extends Sensor{
    // Subclasse de Sensor que herda o metodo de monitorar bidimensionalmente, mas tambem consegue monitorar a altura em um raioZ.
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
            if (distancia <= raioZ && (mestre.getPosX() == robo.getPosX()) && (mestre.getPosY() == robo.getPosY())){
                System.out.printf("Robo encontrado a %d metros de altitude.\n", distancia);
            }
        }

        ArrayList<Obstaculo> obstaculos = ambiente.getListaObstaculos();
        for (Obstaculo obstaculo : obstaculos){
            int deltaX1 = obstaculo.getPosX1() - mestre.getPosX(); int deltaX2 = obstaculo.getPosX2() - mestre.getPosX();
            int deltaY1 = obstaculo.getPosY1() - mestre.getPosY(); int deltaY2 = obstaculo.getPosY2() - mestre.getPosY();

            if ((deltaX1 < 0 && deltaX2 > 0) || deltaX1 == 0 || deltaX2 == 0){
                if((deltaY1 < 0 && deltaY2 > 0) || deltaY1 == 0 || deltaY2 == 0){
                    int deltaZ1 = obstaculo.getBase() - mestre.getAltitude(); int deltaZ2 = obstaculo.getBase() + obstaculo.getAltura() - mestre.getAltitude();
                    if ((deltaZ1 < 0 && deltaZ2 > 0) || deltaZ1 == 0 || deltaZ2 == 0){
                        System.out.printf("Robo está dentro do obstáculo %s", obstaculo.getTipo().getNome());
                    }else if(deltaZ1 < raioZ && (mestre.getAltitude() < obstaculo.getBase())){
                        System.out.printf("Robo está abaixo de %s", obstaculo.getTipo().getNome());
                    }else if(deltaZ2 < raioZ && (mestre.getAltitude() > (obstaculo.getBase() + obstaculo.getAltura()))){
                        System.out.printf("Robo está acima de %s", obstaculo.getTipo().getNome());
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre) {
        // Metodo para retornar um ArrayList com os robôs encontrados pelo sensor
        ArrayList<Robo> listaRobosEncontrados = new ArrayList<>();
        ArrayList<Robo> listaRobos = ambiente.getListaRobos();
        for (Robo robo : listaRobos) {
            int distancia = Math.abs(robo.getAltitude() - mestre.getAltitude());
            if (distancia <= raioZ && (mestre.getPosX() == robo.getPosX()) && (mestre.getPosY() == robo.getPosY())){
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }
}
