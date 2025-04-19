package lab03.robos.robos_terrestres;

import lab03.Ambiente;
import lab03.robos.RoboTerrestre;

public class RoboCombustivel extends RoboTerrestre {

    // Subclasse para lidar com robos que utilizam combustivel para locoção

    private double combustivel;    // Se assume que sempre possuem um tanque suficiente para o combustivel


    // Contrutor do robo Combustivel, considera o combustivel presente no robo (minimo de zero)

    public RoboCombustivel(String nome, String direcao, int posX, int posY, int velocidadeMaxima, double combustivel) {
        super(nome, direcao, posX, posY, velocidadeMaxima);
        this.combustivel = Math.min(0, combustivel);

    }

    public RoboCombustivel(Ambiente ambiente, String nome, String direcao, int posX, int posY, int velocidadeMaxima, double combustivel) {
        super(ambiente, nome, direcao, posX, posY, velocidadeMaxima);
        this.combustivel = Math.min(0, combustivel);

    }


    // Setters e Getters:

    public double getCombustivel() {
        return combustivel;
    }

    protected void setCombustivel(double combustivel) {
        this.combustivel = combustivel;
    }


    // Metodo para abastecer o robo:

    public void abastecer(double gasolina) {
        if (gasolina >= 0)      // Não se remove gasolina do robo no abastecimento
            setCombustivel(getCombustivel() + gasolina);
        else
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());

    }


    // Sobreescrita do metodo Mover do robo Terrestre para acomodar o uso de gasolina
    // O robo precisa ter no minimo uma quantia igual à dsitancia total percorrida (similar a velocidade maxima)

    @Override
    public void mover(int deltaX, int deltaY) {
        double movetotal = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));

        if (movetotal <= getCombustivel()) {
            int x0 = getPosX();
            int y0 = getPosY();

            super.mover(deltaX, deltaY);

            // Considera o se o movimento foi impedido por obstaculo e remove da gasolina:
            movetotal = Math.sqrt(Math.pow(getPosX() - x0,2) + Math.pow(getPosY() - y0,2));
            setCombustivel(getCombustivel() - movetotal);

        } else {
            System.out.printf("O robo %s não tem combustivel suficiente para a locomoção\n", getNome());
        }

    }
}
