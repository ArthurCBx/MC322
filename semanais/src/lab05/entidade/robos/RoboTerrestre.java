package lab05.entidade.robos;

import lab05.Ambiente;

/* Subclasse para implementar um robo que é exclusivamente terrestre e possui uma velocidade maxima.
   O atributo velocidadeMaxima pode ser um limitador para o movimento do robo.
 */
public abstract class RoboTerrestre extends Robo {

    private double velocidadeMaxima;


    // Metodo construtor para o robo Terrestre consideram a velocidade maxima em módulo

    public RoboTerrestre(Ambiente ambiente, String nome, int posX, int posY, double velocidadeMaxima) {
        super(ambiente, nome, posX, posY,0);
        this.velocidadeMaxima = Math.abs(velocidadeMaxima); // Velocidade maxima sempre positiva
    }

    // Setter e Getter:

    public double getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    protected void setVelocidadeMaxima(double velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }


    // Sobrescrita do metodo Mover para o robo Terrestre:
    // A velocidade maxima é considerada radial, ou seja, pode se mover uma velocidade maxima considerando o raio do movimento [raiz(x^2 + y^2)]
    @Override
    public void moverPara(int x, int y, int z) {
        if (Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) > Math.pow(getVelocidadeMaxima(), 2))
            System.out.printf("O movimento de %s excede a velocidade maxima, operação cancelada\n", getNome());
        else
            super.moverPara(x, y, getZ());

    }

}
