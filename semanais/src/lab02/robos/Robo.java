package lab02.robos;

import lab02.Ambiente;

import java.util.ArrayList;
import java.util.List;

public class Robo {
    private final String nome;
    private int posX;
    private int posY;
    private String direcao;
    private int raioSensor;

    public Robo(String nome, int posX, int posY, String direcao, int raioSensor) {
        this.nome = nome;
        this.posX = Math.max(posX, 0);
        this.posY = Math.max(posY, 0);
        this.direcao = direcao;
        this.raioSensor = Math.abs(raioSensor);

        if (posX < 0 || posY < 0) System.out.printf("Coordenadas negativas de %s foram realocadas para 0\n", getNome());

    }

    public String getNome() {
        return nome;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getDirecao() {
        return direcao;
    }

    public int getRaioSensor() {
        return raioSensor;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void setRaioSensor(int raioSensor) {
        this.raioSensor = raioSensor;
    }

    public void mover(int deltaX, int deltaY, Ambiente ambiente) {
        int newPosX = getPosX() + deltaX;
        int newPosY = getPosY() + deltaY;

        if (ambiente.dentroDosLimites(newPosX, newPosY, 0)) {
            setPosX(newPosX);
            setPosY(newPosY);
        } else {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
        }
    }

    public void exibirPosicao() {
        System.out.printf("O robô %s está na posição (%d, %d)\n", getNome(), getPosX(), getPosY());
    }

    public List<Robo> identificarRedondezas(int raio, Ambiente ambiente) {       // Raio unidimensional

        List<Robo> listaRobos = ambiente.getListaRobos();
        List<Robo> robosEncontrados = new ArrayList<>();

        for (Robo robo : listaRobos)
            if (Math.abs(getPosX() - robo.getPosX()) <= raio)
                if (Math.abs(getPosY() - robo.getPosY()) <= raio)
                    if (!(this instanceof RoboAereo) && robo instanceof RoboAereo) {
                        if (((RoboAereo) robo).getAltitude() <= raio)
                            robosEncontrados.add(robo);
                    } else {
                        robosEncontrados.add(robo);
                    }

        robosEncontrados.remove(this);

        return robosEncontrados;

    }

    public void identificarObstaculos(Ambiente ambiente) {

        List<Robo> listaRobos = identificarRedondezas(getRaioSensor(), ambiente);

        for (Robo robo : listaRobos)
            if (!(robo instanceof RoboAereo) || ((RoboAereo) robo).getAltitude() == 0)
                System.out.printf("Há um obstaculo, %s, em (%d, %d)\n", robo.getNome(), robo.getPosX(), robo.getPosY());

    }

}



