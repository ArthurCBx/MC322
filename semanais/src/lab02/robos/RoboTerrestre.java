package lab02.robos;

public class RoboTerrestre extends Robo{
    private int velocidadeMaxima;
    public RoboTerrestre(String nome, int posX, int posY, String direcao) {
        super(nome, posX, posY, direcao);
    }

    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(int velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    @Override
    public void mover(int deltaX, int deltaY) {             // Velocidade maxima unidimencional
        deltaX = Math.min(deltaX, getVelocidadeMaxima());
        deltaY = Math.min(deltaY, getVelocidadeMaxima());
        super.mover(deltaX,deltaY);


    }
}
