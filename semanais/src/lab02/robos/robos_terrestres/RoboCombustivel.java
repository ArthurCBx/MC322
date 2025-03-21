package lab02.robos.robos_terrestres;

import lab02.Ambiente;
import lab02.robos.RoboTerrestre;

public class RoboCombustivel extends RoboTerrestre {
    private int combustivel;

    public RoboCombustivel(String nome, int posX, int posY, String direcao, int raioSensor, int velocidadeMaxima, int combustivel) {
        super(nome, posX, posY, direcao, raioSensor, velocidadeMaxima);
        this.combustivel = combustivel;

    }

    public int getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(int combustivel) {
        this.combustivel = combustivel;
    }

    public void abastecer(int gasolina) {
        if (gasolina >= 0) {
            setCombustivel(getCombustivel() + gasolina);
        } else {
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());
        }

    }

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {

        if (Math.abs(deltaX) + Math.abs(deltaY) < getCombustivel()) {
            setCombustivel(getCombustivel() - Math.abs(deltaX) - Math.abs(deltaY));
            super.mover(deltaX, deltaY, ambiente);
        } else {
            System.out.printf("O robo %s não tem combustivel suficiente para a locomoção\n", getNome());
        }

    }
}
