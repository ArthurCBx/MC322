package lab03.robos;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;
import lab03.sensores.Sensor;
import lab03.sensores.SensorClasse;

import java.util.ArrayList;

public class Robo {

    // Criando classe Robo com atributos especificados no enunciado e raioSensor para identificar obstaculos.

    private Ambiente ambiente;
    private final String nome;
    private String direcao;
    private int posX;
    private int posY;
    protected int altitude = 0;
    private ArrayList<Sensor> sensores = new ArrayList<Sensor>();


    // Construtores para robo:
    // Não permite coordenadas negativas, atribui 0 caso necessário.
    // Um recebe o ambiente como parâmetro e outro apenas cria um robô sem ambiente


    public Robo(String nome, String direcao, int posX, int posY) {
        this.nome = nome;
        this.direcao = direcao;
        this.posX = Math.max(posX, 0);
        this.posY = Math.max(posY, 0);
        if (posX < 0 || posY < 0) System.out.printf("Coordenadas negativas de %s foram realocadas para 0\n", getNome());

    }

    // Sobrecarga do construtor para adicionar o ambiente
    public Robo(Ambiente ambiente, String nome, String direcao, int posX, int posY) {
        this.ambiente = ambiente;
        this.direcao = direcao;
        this.nome = nome;
        this.posX = Math.max(posX, 0);
        this.posY = Math.max(posY, 0);

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

    public ArrayList<Sensor> getSensores() {
        if (sensores.isEmpty()) {
            System.out.printf("O robô %s não possui sensores.\n", getNome());
            return null;
        }else{
            return sensores;
        }
    }

    public void addSensor(Sensor sensor) {
        sensores.add(sensor);
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


    // Metodo para mover o robo em deltaX e deltaY, verificando se o movimento é válido (linha ligando posição inicial e final não colide com obstaculos [com uma tolerancia])

    public void mover(int deltaX, int deltaY) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode movimentar-se.\n", getNome());
            return;
        }

        int finalPosX = getPosX() + deltaX;
        int finalPosY = getPosY() + deltaY;
        if (!getAmbiente().dentroDosLimites(finalPosX, finalPosY,getAltitude())) {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
            return;
        }

        ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarObstaculos(getPosX(),getPosY(),getAltitude(),finalPosX,finalPosY,getAltitude());

        double[] vetorMove = {0.25*((double)deltaX/Math.abs(deltaX)),0.25*((double)deltaX/Math.abs(deltaX))};

        double[] newPos = {getPosX(),getPosY()};
        double[] newTempPos = {0,0};

        while(true){

            if( (finalPosX - (newPos[0] + vetorMove[0]) * vetorMove[0] ) >= 0 )
                newTempPos[0] = newPos[0] + vetorMove[0];
            else
                newTempPos[0] = finalPosX;

            if( (finalPosY - (newPos[1] + vetorMove[1]) * vetorMove[1] ) >= 0 )
                newTempPos[1] = newPos[1] + vetorMove[1];
            else
                newTempPos[1] = finalPosX;

            for (Obstaculo obstaculo : obstaculosPresentes)
                if(obstaculo.contemPonto(newTempPos[0],newTempPos[1],getAltitude())){
                    newTempPos[0] = -1;
                    System.out.printf("O robo %s colidiu com um obstáculo %s", getNome(), obstaculo.getTipo().getNome());
                    break;
                }

            if(newTempPos[0] == -1)
                break;

            newPos[0] = newTempPos[0];
            newPos[1] = newTempPos[1];

            if(newPos[0] == finalPosX)
                break;

        }

        if(newPos[0] == finalPosX){
            setPosX(finalPosX); setPosY(finalPosY);
        }
        else{
            setPosX((int)newPos[0]); setPosY((int)newPos[1]);
            System.out.printf(" e foi realocado para a posição (%d, %d, %d)\n", getPosX(), getPosY(),getAltitude());
        }

    }


    // Metodo para exibiar a posição do robo:
    public void exibirPosicao() {
        if (getAmbiente() == null) {
            System.out.printf("O robô %s não se encontra em um ambiente.\n", getNome());
            return;
        }

        System.out.printf("O robô %s está no ambiente %s na posição (%d, %d, %d) e observando %s\n", getNome(), getAmbiente(), getPosX(), getPosY(), getAltitude(), getDirecao());
    }

    public ArrayList<Robo> identificarRobosProximos(){
        if (getAmbiente() == null || getSensores() == null){
            System.out.printf("O robô %s não está em um ambiente ou não possui um sensor, então não consegue analisar o que está em sua volta.\n", getNome());
            return null;
        }
        ArrayList<Robo> robosProximos = new ArrayList<>();
        for (Sensor sensor : getSensores()){
            ArrayList<Robo> identificados = sensor.listaRobosEncontrados(getAmbiente(),this);
            for (Robo robo : identificados){
                if (!robosProximos.contains(robo)){
                    robosProximos.add(robo);
                }
            }
        }
        return robosProximos;
    }

    // Metodo para identificar obstaculos no ambiente
    public void identificarObstaculosProximos() {
        if (getAmbiente() == null || getSensores() == null){
            System.out.printf("O robo %s não está em um ambiente ou não possui um sensor para identificar obstáculos.\n", getNome());
            return;
        }

        sensores = getSensores();
        ArrayList<Obstaculo> obstaculosProximos = new ArrayList<>();
        // Para cada sensor diferente de sensorClasse, lista obstáculos encontrados e adiciona na lista de obstáculos próximos os que ainda não estão.
        for (Sensor sensor : sensores){
            if(sensor.getClass() != SensorClasse.class){
                ArrayList<Obstaculo> obstaculoSensor = sensor.listaObstaculosEncontrados(this.getAmbiente(), this);

                for (Obstaculo obstaculo : obstaculoSensor){
                    if (!obstaculosProximos.contains(obstaculo)){
                        obstaculosProximos.add(obstaculo);
                    }
                }
            }
        }
    }
    public void monitorar(){
        int count = 0;
        for (Sensor sensor : getSensores()){
            System.out.printf("O sensor %d está monitorando o ambiente %s\n", count, getAmbiente());
            sensor.monitorar(getAmbiente(),this);
            System.out.println();
            count++;
        }
    }

}




