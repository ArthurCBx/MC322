package lab03.robos;

import lab03.Ambiente;
import lab03.sensores.Sensor;
import lab03.sensores.SensorClasse;

import java.util.ArrayList;
import java.util.List;

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

    // Metodo para mover o robo em deltaX e deltaY, verificando se o movimento é válido.

    public void mover(int deltaX, int deltaY) {
        ambiente = getAmbiente();
        if (ambiente == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode movimentar-se.\n", getNome());
            return;
        }

        int newPosX = getPosX() + deltaX;
        int newPosY = getPosY() + deltaY;

        // É preciso verificar se o movimento leva o robô para fora do ambiente.
        if (ambiente.dentroDosLimites(newPosX, newPosY, getAltitude())) {
            setPosX(newPosX);
            setPosY(newPosY);
        } else {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
        }
    }


    // Metodo para exibiar a posição do robo:
    public void exibirPosicao() {
        ambiente = getAmbiente();
        if (ambiente == null) {
            System.out.printf("O robô %s não se encontra em um ambiente.\n", getNome());
            return;
        }

        System.out.printf("O robô %s está no ambiente %s na posição (%d, %d, %d) e observando %s\n", getNome(), getAmbiente(), getPosX(), getPosY(), getAltitude(), getDirecao());
    }

    //DA UMA OLHADA NESSA AQUI, MAS ACHO Q TA CERTA
    public ArrayList<Robo> identificarRedondezas(){
        ambiente = getAmbiente();
        sensores = getSensores();
        if (ambiente == null || sensores == null){
            System.out.printf("O robô %s não está em um ambiente ou não possui um sensor, então não consegue analisar o que está em sua volta.\n", getNome());
            return null;
        }
        ArrayList<Robo> robosProximos = new ArrayList<>();
        for (Sensor sensor : sensores){
            ArrayList<Robo> identificados = sensor.listaRobosEncontrados(getAmbiente(),this);
            for (Robo robo : identificados){
                if (!robosProximos.contains(robo)){
                    robosProximos.add(robo);
                }
            }
        }
        return robosProximos;
    }

    // Metodo para identificar obstaculos no ambiente (o robo ainda pode se mover pelos obstaculos já que é considerado como pequeno)
    public void identificarObstaculos() {
        if (getAmbiente() == null || getSensores() == null){
            System.out.printf("O robo %s não está em um ambiente ou não possui um sensor para identificar obstáculos.\n", getNome());
            return;
        }

        sensores = getSensores();
        int counter = 0;

        }
    }




