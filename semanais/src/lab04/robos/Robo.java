package lab04.robos;

import lab04.Ambiente;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.obstaculos.Obstaculo;
import lab04.sensores.Sensor;
import lab04.sensores.SensorClasse;

import java.util.ArrayList;

public abstract class Robo implements Entidade {

    // Criando classe Robo com atributos especificados no enunciado

    private Ambiente ambiente;
    private final String nome;
    private String direcao;
    private int posX;
    private int posY;
    protected int altitude = 0; // Altura padrão do robô, que é alterada em robos aéreos.
    private ArrayList<Sensor> sensores = new ArrayList<Sensor>();
    private final TipoEntidade tipoEntidade = TipoEntidade.ROBO;


    // Construtores para robo:
    // Não permite coordenadas negativas, atribui 0 caso necessário.
    // Um recebe o ambiente como parâmetro e outro apenas cria um robô sem ambiente.

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

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getZ() {
        return altitude;
    }

    public char getRepresentacao() {
        return 'R';
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
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
    // A linha é feita por uma verificação de seções de 0.25*sqrt(2) do vetor unitario que representa o vetor de movimento
    // Ou seja, o movimento é dividido em pequenas seções e verificadas as colisões em cada uma, finalizando se alguma colidir

    public void mover(int deltaX, int deltaY) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode movimentar-se.\n", getNome());
            return;
        }

        int finalPosX = getX() + deltaX;
        int finalPosY = getY() + deltaY;
        if (!getAmbiente().dentroDosLimites(finalPosX, finalPosY,getZ())) {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
            return;
        }

        ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarObstaculos(getX(),getY(),getZ(),finalPosX,finalPosY,getZ());

        double norma = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));

        double[] vetorMove = {
                (norma != 0) ? 0.25*((double)deltaX/norma) : 0,
                (norma != 0) ? 0.25*((double)deltaY/norma) : 0
        };

        double[] newPos = {getX(),getY()};
        double[] newTempPos = {0,0};

        while(true){

            // Os 2 ifs abaixo são utilizados para verificar se o movimento não extrapola o ponto final do movimento
            // Que, por sua vez, é indicado pela diferença de sinal do vetor que liga o ponto final ao ponto proximo do movimento
            if( (finalPosX - (newPos[0] + vetorMove[0]) * vetorMove[0] ) >= 0 )
                newTempPos[0] = newPos[0] + vetorMove[0];
            else
                newTempPos[0] = finalPosX;

            if( (finalPosY - (newPos[1] + vetorMove[1]) * vetorMove[1] ) >= 0 )
                newTempPos[1] = newPos[1] + vetorMove[1];
            else
                newTempPos[1] = finalPosY;

            for (Obstaculo obstaculo : obstaculosPresentes)
                if(obstaculo.getTipoObstaculo().bloqueiaPassagem() && obstaculo.contemPonto(newTempPos[0],newTempPos[1],getZ())){
                    setPosX((int)newPos[0]);
                    setPosY((int)newPos[1]);

                    // Arredonda para cima
                    if(vetorMove[0] < 0)    setPosX(getX() + 1);
                    if(vetorMove[1] < 0)    setPosY(getY() + 1);

                    System.out.printf("O robo %s colidiu com um obstáculo %s e foi realocado para a posição (%d, %d, %d)\n", getNome(), obstaculo.getTipoObstaculo().getNome(), getX(), getY(),getZ());
                    return;
                }

            newPos[0] = newTempPos[0];
            newPos[1] = newTempPos[1];

            if(newPos[0] == finalPosX && newPos[1] == finalPosY)
                break;

        }

            setPosX(finalPosX); setPosY(finalPosY);

    }


    // Metodo para exibiar a posição do robo:
    public void exibirPosicao() {
        if (getAmbiente() == null) {
            System.out.printf("O robô %s não se encontra em um ambiente.\n", getNome());
            return;
        }

        System.out.printf("O robô %s está no ambiente %s na posição (%d, %d, %d) e observando %s\n", getNome(), getAmbiente(), getX(), getY(), getZ(), getDirecao());
    }

    // Metodo para identificar robos proximos:
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

    // Metodo para identificar obstaculos no ambiente:
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
        int count = 1;
        if (getSensores() != null)
            for (Sensor sensor : getSensores()){
                System.out.printf("O sensor %d está monitorando o ambiente %s\n", count, getAmbiente());
                sensor.monitorar(getAmbiente(),this);
                System.out.println();
                count++;
            }
        System.out.println("Monitoramente finalizado.");
    }

}




