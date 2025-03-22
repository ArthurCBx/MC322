package lab02.robos;

import lab02.Ambiente;

public class RoboTerrestre extends Robo {
    private int velocidadeMaxima;

    public RoboTerrestre(String nome, int posX, int posY, String direcao, int raioSensor, int velocidadeMaxima) {
        super(nome, posX, posY, direcao, raioSensor);
        this.velocidadeMaxima = Math.abs(velocidadeMaxima);

    }

    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {             // Velocidade maxima unidimencional
        if (Math.abs(deltaX) > getVelocidadeMaxima() || Math.abs(deltaY) > getVelocidadeMaxima())
            System.out.printf("O movimento de %s excede a velocidade maxima, operação cancelada\n",getNome());
        else
            super.mover(deltaX, deltaY, ambiente);

    }
}
