package lab02.robos;

import lab02.Ambiente;

public class RoboTerrestre extends Robo {

    // Subclasse para implementar um robo que é exclusivamente terrestre e possui uma velocidade maxima

    private int velocidadeMaxima;


    // Metodo construtor para o robo Terrestre, considera a velocidade maxima (em modulo)

    public RoboTerrestre(String nome, int posX, int posY, String direcao, int raioSensor, int velocidadeMaxima) {
        super(nome, posX, posY, direcao, raioSensor);
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
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {
        if (Math.abs(deltaX) > getVelocidadeMaxima() || Math.abs(deltaY) > getVelocidadeMaxima())
            System.out.printf("O movimento de %s excede a velocidade maxima, operação cancelada\n", getNome());
        else
            super.mover(deltaX, deltaY, ambiente);

    }
}
