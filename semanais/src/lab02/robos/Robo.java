package lab02.robos;

import lab02.Ambiente;

import java.util.ArrayList;
import java.util.List;

public class Robo {
    // Criando classe Robo com atributos especificados no enunciado e raioSensor para identificar obstaculos.
    private final String nome;
    private int posX;
    private int posY;
    private String direcao;
    private int raioSensor;

    public Robo(String nome, int posX, int posY, String direcao, int raioSensor) {
        // Construtor para robo. Não permite coordenadas negativas, atribui 0 caso necessário.
        this.nome = nome;
        this.posX = Math.max(posX, 0);
        this.posY = Math.max(posY, 0);
        this.direcao = direcao;
        this.raioSensor = Math.abs(raioSensor); // Raio não pode ser negativo.

        if (posX < 0 || posY < 0) System.out.printf("Coordenadas negativas de %s foram realocadas para 0\n", getNome());

    }

    // Getters e Setters
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
        // Metodo para mover o robo em deltaX e deltaY, verificando se o movimento é válido.
        int newPosX = getPosX() + deltaX;
        int newPosY = getPosY() + deltaY;

        // Assume-se que os robos sempre estarão em um ambiente, logo é preciso verificar se o movimento leva o robô para fora do ambiente.
        if (ambiente.dentroDosLimites(newPosX, newPosY, 0)) {
            setPosX(newPosX);
            setPosY(newPosY);
        } else {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
        }
    }

    public void exibirPosicao() {
        System.out.printf("O robô %s está na posição (%d, %d) e observando %s\n", getNome(), getPosX(), getPosY(), getDirecao());
    }

    // Metodo auxiliar para retornar lista de robos em um raio.
    private List<Robo> identificarRedondezas(int raio, Ambiente ambiente) {
        // Metodo para identificar robos em um raio e retornar uma lista com os robos dentro do raio.
        // Assume-se que o raioSensor dos robos é a distância máxima que ele pode identificar obstáculos em uma dimensão.
        // Logo, para identificar obstáculos em um raio, é preciso verificar se a diferença entre as coordenadas dos robos é menor ou igual ao raio.

        List<Robo> listaRobos = ambiente.getListaRobos();
        List<Robo> robosEncontrados = new ArrayList<>();

        // Verifica, para cada dimensão, se a diferença entre as coordenadas dos robos é menor ou igual ao raio.
        // Metodo apenas para robos terrestres, robos aéreos possuem metodo próprio.
        for (Robo robo : listaRobos)
            if (Math.abs(getPosX() - robo.getPosX()) <= raio)
                if (Math.abs(getPosY() - robo.getPosY()) <= raio)
                    if (robo instanceof RoboAereo) {
                        if (((RoboAereo) robo).getAltitude() <= raio)
                            robosEncontrados.add(robo);
                    } else {
                        robosEncontrados.add(robo);
                    }

        // Remove o próprio robo da lista de robos encontrados.
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



