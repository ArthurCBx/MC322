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

    // Metodo para mover o robo em deltaX e deltaY, verificando se o movimento é válido.

    public void mover(int deltaX, int deltaY) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode movimentar-se.\n", getNome());
            return;
        }

        int newPosX = getPosX() + deltaX;
        int newPosY = getPosY() + deltaY;
        if (!getAmbiente().dentroDosLimites(newPosX, newPosY,getAltitude())) {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());
            return;
        }

        // Verifica se o robô não vai colidir com um obstáculo.
        ArrayList<Obstaculo> obstaculos = getAmbiente().getListaObstaculos();
        for (Obstaculo obstaculo : obstaculos) {
            if (obstaculo.contemPonto(newPosX,newPosY,getAltitude())) {
                // Aqui assume-se que não há sobreposição de obstáculos.
                // Se newPos(x,y) for dentro de um obstáculo, o robô é realocado para a interseção mais próxima e a função termina.
                double[] newPos = calcularIntersecaoMaisProxima(getPosX(), getPosY(), newPosX, newPosY, obstaculo);
                setPosX((int) newPos[0]);
                setPosY((int) newPos[1]);
                System.out.printf("O robo %s colidiu com o obstáculo %s e foi realocado para a posição (%d, %d)\n", getNome(), obstaculo.getTipo().getNome(), getPosX(), getPosY());
                return;
            }
        }
        // Se o robô não colidir com nenhum obstáculo, ele pode se mover.
        setPosX(newPosX); setPosY(newPosY);
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

    // Metodo para identificar obstaculos no ambiente (o robo ainda pode se mover pelos obstaculos já que é considerado como pequeno)
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

    public double[] calcularIntersecaoMaisProxima(int x, int y, int newPosX, int newPosY, Obstaculo obstaculo) {
        double[] intersecao = new double[2];
        double menorDistancia = Double.MAX_VALUE;

        // Arrays com as coordenadas das linhas do retângulo
        int[] xLinha = {obstaculo.getPosX1(), obstaculo.getPosX2(), obstaculo.getPosX2(), obstaculo.getPosX1()};
        int[] yLinha = {obstaculo.getPosY1(), obstaculo.getPosY1(), obstaculo.getPosY2(), obstaculo.getPosY2()};

        // Para cada linha do retângulo
        for (int i = 0; i < 4; i++) {
            int x1 = xLinha[i];
            int y1 = yLinha[i];
            int x2 = xLinha[(i + 1) % 4];
            int y2 = yLinha[(i + 1) % 4];

            // Calcula a interseção entre a trajetória e a linha atual
            double denominador = (x - newPosX) * (y1 - y2) - (y - newPosY) * (x1 - x2);
            if (denominador != 0) {
                double t = ((x - x1) * (y1 - y2) - (y - y1) * (x1 - x2)) / denominador;
                double u = -((x - newPosX) * (y - y1) - (y - newPosY) * (x - x1)) / denominador;

                if (t >= 0 && t <= 1 && u >= 0 && u <= 1) {
                    double xIntersecao = x + t * (newPosX - x);
                    double yIntersecao = y + t * (newPosY - y);
                    double distancia = Math.sqrt(Math.pow(x - xIntersecao, 2) + Math.pow(y - yIntersecao, 2));

                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        intersecao[0] = xIntersecao;
                        intersecao[1] = yIntersecao;
                    }
                }
            }
        }
        return intersecao;
    }
}




