package lab03.robos.robos_terrestres;

import lab03.Ambiente;
import lab03.robos.RoboTerrestre;

public class RoboCombustivel extends RoboTerrestre {

    // Subclasse para lidar com robos que utilizam combustivel para locoção

    private int combustivel;    // Se assume que sempre possuem um tanque suficiente para o combustivel


    // Contrutor do robo Combustivel, considera o combustivel presente no robo (minimo de zero)

    public RoboCombustivel(String nome, String direcao, int posX, int posY, int velocidadeMaxima, int raioSensor, int combustivel) {
        super(nome, direcao, posX, posY, velocidadeMaxima, raioSensor);
        this.combustivel = Math.min(0, combustivel);

    }

    public RoboCombustivel(Ambiente ambiente, String nome, String direcao, int posX, int posY, int velocidadeMaxima, int raioSensor, int combustivel) {
        super(ambiente, nome, direcao, posX, posY, velocidadeMaxima, raioSensor);
        this.combustivel = Math.min(0, combustivel);

    }


    // Setters e Getters:

    public int getCombustivel() {
        return combustivel;
    }

    protected void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }


    // Metodo para abastecer o robo:

    public void abastecer(int gasolina) {
        if (gasolina >= 0)      // Não se remove gasolina do robo no abastecimento
            setCombustivel(getCombustivel() + gasolina);
        else
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());

    }


    // Sobreescrita do metodo Mover do robo Terrestre para acomodar o uso de gasolina
    // O robo precisa ter no minimo uma quantia igual à soma das das coordenadas movidas unidirecionalmente
    // Ou seja, se ele se move 3 em X e -10 em Y, consome 13 de gasolina (3 + 10)

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {

        if ((Math.abs(deltaX) + Math.abs(deltaY)) <= getCombustivel()) {
            setCombustivel(getCombustivel() - Math.abs(deltaX) - Math.abs(deltaY));
            super.mover(deltaX, deltaY, ambiente);

        } else {
            System.out.printf("O robo %s não tem combustivel suficiente para a locomoção\n", getNome());
        }

    }
}
