package lab03.robos;

import lab03.Ambiente;

public class RoboTerrestre extends Robo {

    // Subclasse para implementar um robo que é exclusivamente terrestre e possui uma velocidade maxima

    private int velocidadeMaxima;


    // Metodos construtores para o robo Terrestre, considera a velocidade maxima (em modulo)
    // Um recebe o ambiente como parametro e outro apenas cria um robo sem ambiente

    public RoboTerrestre(String nome, String direcao, int posX, int posY, int velocidadeMaxima, int raioSensor) {
        super(nome, direcao, posX, posY, raioSensor);
        this.velocidadeMaxima = Math.abs(velocidadeMaxima); // Velocidade maxima sempre positiva

    }

    public RoboTerrestre(Ambiente ambiente, String nome, String direcao, int posX, int posY, int velocidadeMaxima, int raioSensor) {
        super(ambiente, nome, direcao, posX, posY, raioSensor);
        this.velocidadeMaxima = Math.abs(velocidadeMaxima); // Velocidade maxima sempre positiva
    }

    // Setters e Getters:

    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    protected void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }


    // Sobreescrita do metodo Mover para o robo Terrestre:
    // A velocidade maxima é considerada unidimensional, ou seja, pode se mover uma velocidade maxima para cada direção

    @Override
    public void mover(int deltaX, int deltaY) {
        if (Math.abs(deltaX) > getVelocidadeMaxima() || Math.abs(deltaY) > getVelocidadeMaxima())
            System.out.printf("O movimento de %s excede a velocidade maxima, operação cancelada\n", getNome());
        else
            super.mover(deltaX, deltaY);

    }
}
