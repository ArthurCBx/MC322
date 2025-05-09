package lab04.robos.robos_aereos;

import lab04.Ambiente;
import lab04.entidade.Entidade;
import lab04.excecoes.RoboDesligadoException;
import lab04.robos.Estado;
import lab04.robos.Robo;
import lab04.robos.RoboAereo;

import java.util.ArrayList;
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

    public String getDescricao() {
        return "Robo Explosivo: " + getNome() + "\n" +
                "Raio de Explosão: " + getRaioExplosao() + "\n" +
                "Altura Maxima: " + getAltitudeMaxima() + "\n" +
                "Altura Atual: " + getZ() + "\n" +
                "Posicao Atual: (" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
// Metodo para se autodestruir e levar consigo os robos num raio de explosao.

    public List<Robo> explodir() {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode explodir.\n", getNome());
            return null;
        }
        if (getEstado() == Estado.DESLIGADO){
            throw new RoboDesligadoException("O robô " + getNome() + " está desligado, não pode explodir.");
        }

        // Metodo identificarRedondezas retorna uma lista de robos que estão no raio de explosao.
        List<Robo> robosAtingidos = new ArrayList<>();

        List<Robo> listaRobos = getAmbiente().getListaEntidades().stream().filter(entidade -> entidade instanceof Robo).map(entidade -> (Robo) entidade).toList();
        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt(Math.pow(robo.getX() - getX(), 2) + Math.pow(robo.getY() - getY(), 2) + Math.pow(robo.getZ() - getZ(), 2));
            if (distancia <= getRaioExplosao() && !robo.equals(this)) {
                robosAtingidos.add(robo);
            }
        }

        // Para cada robo dentro do raio de explosão, remove-o do ambiente e faz o seu apontador ser nulo.
        for (Robo robo : robosAtingidos) {
            System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
            getAmbiente().removerEntidade(robo);
        }

        System.out.printf("O robo explosivo %s destruiu %d robos\n", getNome(), robosAtingidos.size());

        // Por fim, remove o robo explosivo do ambiente e torna o seu apontador nulo.
        getAmbiente().removerEntidade(this);
        robosAtingidos.add(this);
        return robosAtingidos;
    }
}
