package lab02.robos.robos_aereos;

import lab02.Ambiente;
import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.List;

public class RoboBombardeiro extends RoboAereo {
    private int bombas;
    private final int capacidadeBombas;

    public RoboBombardeiro(String nome, int posX, int posY, String direcao, int raioSensor, int altitude, int altitudeMaxima, int capacidadeBombas) {
        super(nome, posX, posY, direcao, raioSensor, altitude, altitudeMaxima);
        this.bombas = 0;
        this.capacidadeBombas = Math.abs(capacidadeBombas);
    }

    public int getBombas() {
        return bombas;
    }

    public void setBombas(int bombas) {
        this.bombas = bombas;
    }

    public int getCapacidadeBombas() {
        return capacidadeBombas;
    }

    private void carregarBombas(int bombas) {
        if (bombas >= 0) {
            setBombas(Math.min(getBombas() + bombas, getCapacidadeBombas()));
        } else {
            System.out.printf("Quantia de bombas de %s precisa ser maior que 0\n", getNome());
        }

    }

    private void bombardear(Ambiente ambiente) {

        if (getBombas() <= 0) {
            System.out.printf("O robo %s não tem bombas\n", getNome());
            return;
        }

        setBombas(getBombas() - 1);

        List<Robo> listaRobos = ambiente.getListaRobos();

        int vitimas = 0;

        for (Robo robo : listaRobos)
            if (getPosX() == robo.getPosX() && getPosY() == robo.getPosY()) {
                vitimas++;
                System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
                ambiente.removerRobo(robo);
            }


        System.out.printf("O robo explosivo %s destruiu %d robos\n", getNome(), vitimas);

        if (getAltitude() == 0) {
            System.out.printf("O robo %s não sobreviveu\n", getNome());
            ambiente.removerRobo(this);
        }

    }
}
