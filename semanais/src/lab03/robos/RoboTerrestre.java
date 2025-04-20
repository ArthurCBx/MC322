package lab03.robos;

import lab03.Ambiente;

public class RoboTerrestre extends Robo {

    // Subclasse para implementar um robo que é exclusivamente terrestre e possui uma velocidade maxima

    private double velocidadeMaxima;


    // Metodos construtores para o robo Terrestre consideram a velocidade maxima em módulo
    // Um recebe o ambiente como parametro e outro apenas cria um robo sem ambiente

    public RoboTerrestre(String nome, String direcao, int posX, int posY, double velocidadeMaxima) {
        super(nome, direcao, posX, posY);
        this.velocidadeMaxima = Math.abs(velocidadeMaxima); // Velocidade maxima sempre positiva

    }

    public RoboTerrestre(Ambiente ambiente, String nome, String direcao, int posX, int posY, double velocidadeMaxima) {
        super(ambiente, nome, direcao, posX, posY);
        this.velocidadeMaxima = Math.abs(velocidadeMaxima); // Velocidade maxima sempre positiva
    }

    // Setters e Getters:

    public double getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    protected void setVelocidadeMaxima(double velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }


    // Sobreescrita do metodo Mover para o robo Terrestre:
    // A velocidade maxima é considerada radial, ou seja, pode se mover uma velocidade maxima considerando o raio do movimento [raiz(x^2 + y^2)]

    @Override
    public void mover(int deltaX, int deltaY) {
        if (Math.pow(deltaX,2) + Math.pow(deltaY,2) < Math.pow(getVelocidadeMaxima(),2))
            System.out.printf("O movimento de %s excede a velocidade maxima, operação cancelada\n", getNome());
        else
            super.mover(deltaX, deltaY);

    }
}
