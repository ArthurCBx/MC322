package lab02.robos.robos_terrestres;

import lab02.Ambiente;
import lab02.robos.RoboTerrestre;

public class RoboCombustivel extends RoboTerrestre {

    // Subclasse para lidar com robos que utilizam combustivel para locoção

    private int combustivel;    // Se assume que sempre possuem um tanque suficiente para o combustivel


    // Contrutor do robo Combustivel, considera o combustivel presente no robo (minimo de zero)

    public RoboCombustivel(String nome, int posX, int posY, String direcao, int raioSensor, int velocidadeMaxima, int combustivel) {
        super(nome, posX, posY, direcao, raioSensor, velocidadeMaxima);
        this.combustivel = Math.min(0, combustivel);

    }


    // Setters e Getters:

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
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
