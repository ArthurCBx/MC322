package lab03.robos;

import lab03.Ambiente;

import java.util.ArrayList;
import java.util.List;

public class Robo {

    // Criando classe Robo com atributos especificados no enunciado e raioSensor para identificar obstaculos.

    private Ambiente ambiente;
    private final String nome;
    private String direcao;
    private int posX;
    private int posY;
    protected int altitude;
    private int raioSensor;


    // Construtores para robo:
    // Não permite coordenadas negativas, atribui 0 caso necessário.
    // Um recebe o ambiente como parametro e outro apenas cria um robo sem ambiente


    public Robo(String nome, String direcao, int posX, int posY, int raioSensor) {
        this.nome = nome;
        this.direcao = direcao;
        this.posX = Math.max(posX, 0);
        this.posY = Math.max(posY, 0);
        this.raioSensor = Math.abs(raioSensor); // Raio não pode ser negativo.

        if (posX < 0 || posY < 0) System.out.printf("Coordenadas negativas de %s foram realocadas para 0\n", getNome());

    }

    public Robo(Ambiente ambiente, String nome, String direcao, int posX, int posY, int raioSensor) {
        this.ambiente = ambiente;
        this.direcao = direcao;
        this.nome = nome;
        this.posX = Math.max(posX, 0);
        this.posY = Math.max(posY, 0);
        this.raioSensor = Math.abs(raioSensor); // Raio não pode ser negativo.

        ambiente.adicionarRobo(this);

        if (posX < 0 || posY < 0) System.out.printf("Coordenadas negativas de %s foram realocadas para 0\n", getNome());

    }


    // Getters e Setters:

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public String getDirecao() {
        return direcao;
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

    public int getAltitude() {
        return altitude;
    }

    public int getRaioSensor() {
        return raioSensor;
    }


    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public void setRaioSensor(int raioSensor) {
        this.raioSensor = raioSensor;
    }


    // Metodo para mover o robo em deltaX e deltaY, verificando se o movimento é válido.

    public void mover(int deltaX, int deltaY) {

        // VERIFICAR SE AMBIENTE != NULL

        int newPosX = getPosX() + deltaX;
        int newPosY = getPosY() + deltaY;

        // Assume-se que os robos sempre estarão em um ambiente, logo é preciso verificar se o movimento leva o robô para fora do ambiente.
        if (getAmbiente().dentroDosLimites(newPosX, newPosY, getAltitude())) {
            setPosX(newPosX);
            setPosY(newPosY);
        } else {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
        }
    }


    // Metodo para exibiar a posição do robo:

    public void exibirPosicao() {
        // VERIFICAR SE AMBIENTE != NULL

        System.out.printf("O robô %s está no ambiente %s na posição (%d, %d, %d) e observando %s\n", getNome(), getAmbiente(), getPosX(), getPosY(), getAltitude(), getDirecao());
    }


    // Metodo auxiliar para retornar lista de robos em um raio especifico:
    // É util na medida que há distintos raios de efeito para subclasses de robo.

    protected List<Robo> identificarRedondezas(int raio, Ambiente ambiente) {
        // Identificar robos em um raio e retorna uma lista com os robos dentro do raio.
        // O robo identificando as redondezas verifica as coordenadas de todos os robos, os que estiverem
        // dentro do raio sao adicionados na lista de robos encontrados.

        List<Robo> listaRobos = ambiente.getListaRobos();
        List<Robo> robosEncontrados = new ArrayList<>();

        // Verifica, para cada dimensão, se a diferença entre as coordenadas dos robos é menor ou igual ao raio.
        // Assume-se que o rai dos robos é a distância máxima que ele esta identificando obstáculos em uma dimensão.
        // Logo, para identificar robos em um raio, é preciso verificar se a diferença entre as coordenadas dos robos é menor ou igual ao raio.

        // Metodo apenas para robos terrestres, robos aéreos possuem metodo próprio.**
        for (Robo robo : listaRobos)
            if (Math.abs(getPosX() - robo.getPosX()) <= raio)
                if (Math.abs(getPosY() - robo.getPosY()) <= raio)
                    if (!(this instanceof RoboAereo) && robo instanceof RoboAereo) {    // **Explicação abaixo**
                        if (((RoboAereo) robo).getAltitude() <= raio)
                            robosEncontrados.add(robo);
                    } else {
                        robosEncontrados.add(robo);
                    }

        // ** O robo escaneia em primeiro em X e Y para verificar quais robos estao dentro de seu raio
        // Como há subclasses com altitudes, há tratamentos distintos para certos casos, para uma maior flexixbilização desta função:
        // Caso o robo analisando é terrestre, so irá detectar robos com altitude menor ou igual ao seu raio
        // Caso o robo sendo analisado é aereo ele verifica essa condição, caso terrestre é adicionado já que sua altitude é zero
        // Caso o robo analizando é um robo aereo, o tratamento deve ser diferente, já que pode haver robos abaixo deste
        // Entretanto, para reutilizar essa função, basta adiconar robos independetemente da altura e depois filtrar para o caso do aereo
        // Assim, não não é necessario reescrever toda a função, apenas filtrar para o caso aereo.


        // Remove o próprio robo da lista de robos encontrados já que o robo analisando se adiciona nos robos encontrados.
        robosEncontrados.remove(this);
        return robosEncontrados;
    }


    // Metodo para identificar obstaculos no ambiente (o robo ainda pode se mover pelos obstaculos já que é considerado como pequeno)

    public void identificarObstaculos(Ambiente ambiente) {

        // Identifica redondezas com o raio do sensor do robo
        List<Robo> listaRobos = identificarRedondezas(getRaioSensor(), ambiente);

        for (Robo robo : listaRobos)
            if (!(robo instanceof RoboAereo) || ((RoboAereo) robo).getAltitude() == 0)  // Robos Aereos que não estão na altitude zero não sao obstaculos
                System.out.printf("Há um obstaculo, %s, em (%d, %d)\n", robo.getNome(), robo.getPosX(), robo.getPosY());

    }

}



