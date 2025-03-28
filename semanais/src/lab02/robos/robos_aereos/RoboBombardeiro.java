package lab02.robos.robos_aereos;

import lab02.Ambiente;
import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.ArrayList;
import java.util.List;

/* RoboBombardeiro é uma subclasse de RoboAereo que tem a característica específica de destruir os robos que estão na mesma posição (x,y) que ele e na mesma altitude ou inferior.
Caso o RoboBombardeiro esteja na altitude 0, ele também é destruído.

Atributos:
Bombas: quantidade de bombas que o RoboBombardeiro possui.
CapacidadeBombas: quantidade máxima de bombas que o RoboBombardeiro pode carregar.
* */

public class RoboBombardeiro extends RoboAereo {
    private int bombas;
    private final int capacidadeBombas;

    public RoboBombardeiro(String nome, int posX, int posY, String direcao, int raioSensor, int altitude, int altitudeMaxima, int capacidadeBombas) {
        super(nome, posX, posY, direcao, raioSensor, altitude, altitudeMaxima);
        this.bombas = 0;
        this.capacidadeBombas = Math.abs(capacidadeBombas);

    }
    // Getters e Setters específicos do bombardeiro
    public int getBombas() {
        return bombas;
    }

    public int getCapacidadeBombas() {
        return capacidadeBombas;
    }

    // Set como protected porque nao deve ser acessado fora desta classe ou subclasses.
    // Há metodo específico para mudar numero de bombas.
    protected void setBombas(int bombas) {
        this.bombas = bombas;
    }

    // Adiciona bombas ao RoboBombardeiro, verificando se a capacidade limite foi atingida. Não carrega valores negatgivos negativo.
    public void carregarBombas(int bombas) {
        if (bombas >= 0)
            setBombas(Math.min(getBombas() + bombas, getCapacidadeBombas()));
        else
            System.out.printf("Quantia de bombas de %s precisa ser maior que 0\n", getNome());

    }

    // Metodo que bombardeia robos na mesma posição (x,y) e altitude igual ou inferior.
    public void bombardear(Ambiente ambiente) {

        // Não pode bombardear se o robo estiver sem bombas.
        if (getBombas() <= 0) {
            System.out.printf("O robo %s não tem bombas\n", getNome());
            return;
        }

        setBombas(getBombas() - 1);

        List<Robo> listaRobos = ambiente.getListaRobos();
        List<Robo> robosAtingidos = new ArrayList<>();

        int altitude;
        int vitimas = 0;

        for (Robo robo : listaRobos) {
            // Para cada robo da lista do ambiente, verifica primeiro se é aereo (para pegar altitude) ou terrestre (altitude 0).
            altitude = robo instanceof RoboAereo ? ((RoboAereo) robo).getAltitude() : 0;

            if (getPosX() == robo.getPosX() && getPosY() == robo.getPosY() && altitude <= getAltitude()) {
                vitimas++;
                robosAtingidos.add(robo);
            }
        }
        // Remove ele mesmo da lista de robos atingidos.
        robosAtingidos.remove(this);
        vitimas--;

        // Remove os robos atingidos do ambiente.
        for (Robo robo : robosAtingidos) {
            System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
            ambiente.removerRobo(robo);
        }

        System.out.printf("O robo bombardeiro %s destruiu %d robos\n", getNome(), vitimas);

        // Se autodestroi se estava no solo.
        if (getAltitude() == 0) {
            System.out.printf("O robo %s não sobreviveu\n", getNome());
            ambiente.removerRobo(this);
        }

    }
}
