package lab03.robos.robos_aereos;

import lab03.Ambiente;
import lab03.robos.Robo;
import lab03.robos.RoboAereo;

import java.util.List;

/*
Subclasse de RoboAereo que possui a habilidade de se autodestruir, levando consigo os robos num raio de explosão (raio deve ser indicado no construtor).
 Para isso, ele possui o metodo explodir, que recebe o ambiente em que está inserido como atributo.
 */

public class RoboExplosivo extends RoboAereo {
    private final int raioExplosao;


    public RoboExplosivo(String nome, String direcao, int posX, int posY, int altitude, int altitudeMaxima, int raioExplosao) {
        super(nome, direcao, posX, posY, altitude, altitudeMaxima);
        this.raioExplosao = Math.abs(raioExplosao);

    }

    public RoboExplosivo(Ambiente ambiente, String nome, String direcao, int posX, int posY, int altitude, int altitudeMaxima, int raioExplosao) {
        super(ambiente, nome, direcao, posX, posY, altitude, altitudeMaxima);
        this.raioExplosao = Math.abs(raioExplosao);

    }

    public int getRaioExplosao() {
        return raioExplosao;
    }

    // REFAZER O EXPLODIR

    // Metodo para se autodestruir e levar consigo os robos num raio de explosao.
    public void explodir(Ambiente ambiente) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode explodir.\n", getNome());
            return;
        }

        // Metodo identificarRedondezas retorna uma lista de robos que estão no raio de explosao.
        List<Robo> robosAtingidos = identificarRedondezas();

        // Para cada robo dentro do raio de explosão, remove-o do ambiente e faz o seu apontador ser nulo.
        for (Robo robo : robosAtingidos) {
            System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
            ambiente.removerRobo(robo);
        }

        System.out.printf("O robo explosivo %s destruiu %d robos\n", getNome(), robosAtingidos.size());

        // Por fim, remove o robo explosivo do ambiente e torna o seu apontador nulo.
        ambiente.removerRobo(this);

    }
}
