package lab02.robos.robos_aereos;

import lab02.Ambiente;
import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.List;
import java.util.Objects;

public class RoboExplosivo extends RoboAereo {
    private final int raioExplosao;


    public RoboExplosivo(String nome, int posX, int posY, String direcao, int raioSensor, int altitude, int altitudeMaxima, int raioExplosao) {
        super(nome, posX, posY, direcao, raioSensor, altitude, altitudeMaxima);
        this.raioExplosao = Math.abs(raioExplosao);

    }

    public int getRaioExplosao() {
        return raioExplosao;
    }

    public void explodir(Ambiente ambiente) {

        List<Robo> robosAtingidos = identificarRedondezas(getRaioExplosao(), ambiente);

        for (Robo robo : robosAtingidos) {
            System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
            ambiente.removerRobo(robo);
        }

        System.out.printf("O robo explosivo %s destruiu %d robos\n", getNome(), robosAtingidos.size());

        ambiente.removerRobo(this);

    }
}
