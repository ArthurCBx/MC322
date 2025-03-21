package lab02.robos.robos_aereos;

import lab02.Ambiente;
import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.List;

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

        List<Robo> listaRobos = ambiente.getListaRobos();

        int vitimas = 0;

        for (Robo robo : listaRobos)

            if (robo.getClass() == RoboAereo.class) {
                if (Math.abs(getAltitude() - ((RoboAereo) robo).getAltitude()) <= getRaioExplosao())
                    if (Math.abs(getPosX() - robo.getPosX()) <= getRaioExplosao())
                        if (Math.abs(getPosY() - robo.getPosY()) <= getRaioExplosao()) {
                            vitimas++;
                            ambiente.removerRobo(robo);
                            System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
                        }
            } else {
                if (getAltitude() <= getRaioExplosao()) if (Math.abs(getPosX() - robo.getPosX()) <= getRaioExplosao())
                    if (Math.abs(getPosY() - robo.getPosY()) <= getRaioExplosao()) {
                        vitimas++;
                        ambiente.removerRobo(robo);
                        System.out.printf("\"O robo, %s, foi atingido\n", robo.getNome());
                    }

            }

        System.out.printf("O robo explosivo %s destruiu %d robos\n", getNome(), vitimas);

        ambiente.removerRobo(this);

    }
}
