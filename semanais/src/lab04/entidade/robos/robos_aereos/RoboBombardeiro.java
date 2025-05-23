package lab04.entidade.robos.robos_aereos;

import lab04.Ambiente;
import lab04.excecoes.RoboDesligadoException;
import lab04.entidade.robos.Estado;
import lab04.entidade.robos.Robo;
import lab04.entidade.robos.RoboAereo;
import lab04.excecoes.SemAmbienteException;

import java.util.ArrayList;
import java.util.List;

/* RoboBombardeiro é uma subclasse de RoboAereo que tem a característica específica de destruir os robos que estão na mesma posição (x,y) que ele e na mesma altitude ou inferior.
Caso o RoboBombardeiro esteja na altitude 0, ele também é destruído.

Atributos:
Bombas: quantidade de bombas que o RoboBombardeiro possui.
CapacidadeBombas: quantidade máxima de bombas que o RoboBombardeiro pode carregar.
*/

public class RoboBombardeiro extends RoboAereo implements Explosivos{
    private int bombas;
    private final int capacidadeBombas;

    public RoboBombardeiro(Ambiente ambiente, String nome, int posX, int posY, int posZ, int altitudeMaxima, int capacidadeBombas) {
        super(ambiente, nome, posX, posY, posZ, altitudeMaxima);
        this.bombas = 0;
        this.capacidadeBombas = Math.abs(capacidadeBombas);

    }


    // Getters e Setters:

    public int getBombas() {
        return bombas;
    }

    public int getCapacidadeBombas() {
        return capacidadeBombas;
    }

    // Set como protected porque não deve ser acessado fora desta classe ou subclasses.
    // Há metodo específico para mudar numero de bombas.
    protected void setBombas(int bombas) {
        this.bombas = bombas;
    }


    public String getDescricao() {
        return "Robo Bombardeiro: " + getNome() + "\n" +
                "id: " + this.getId() + "\n" +
                "Bombas: " + getBombas() + "\n" +
                "Capacidade Bombas: " + getCapacidadeBombas() + "\n" +
                "Altura Maxima: " + getAltitudeMaxima() + "\n" +
                "Altura Atual: " + getZ() + "\n" +
                "Posicao Atual: (" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    // Adiciona bombas ao RoboBombardeiro, verificando se a capacidade limite foi atingida. Não carrega valores negativos.
    public void carregarBombas(int bombas) {
        if (bombas >= 0)
            setBombas(Math.min(getBombas() + bombas, getCapacidadeBombas()));
        else
            System.out.printf("Quantia de bombas de %s precisa ser maior que 0\n", getNome());

    }


    // Metodo que bombardeia robos na mesma posição (x, y) e altitude igual ou inferior.

    public List<Robo> explodir() {
        if (getAmbiente() == null){
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode bombardear.");
        }
        if (getEstado() == Estado.DESLIGADO){
            throw new RoboDesligadoException("O robô está desligado, não pode bombardear.");
        }

        // Não pode bombardear se o robo estiver sem bombas.
        if (getBombas() <= 0) {
            System.out.printf("O robo %s não tem bombas\n", getNome());
            return null;
        }

        setBombas(getBombas() - 1);

        List<Robo> listaRobos = getAmbiente().getListaEntidades().stream().filter(entidade -> entidade instanceof Robo).map(entidade -> (Robo) entidade).toList();
        List<Robo> robosAtingidos = new ArrayList<>();

        int vitimas = 0;

        for (Robo robo : listaRobos) {
            if (getX() == robo.getX() && getY() == robo.getY() && robo.getZ() <= getZ()) {
                vitimas++;
                robosAtingidos.add(robo);
            }
        }
        // Se remove da lista de robos atingidos.
        robosAtingidos.remove(this);
        vitimas--;

        // Remove os robos atingidos do ambiente.
        for (Robo robo : robosAtingidos) {
            System.out.printf("O robo, %s, foi atingido\n", robo.getNome());
            getAmbiente().removerEntidade(robo);
        }

        System.out.printf("O robo bombardeiro %s destruiu %d robos\n", getNome(), vitimas);

        // Se autodestroi se estava no solo.
        if (getZ() == 0) {
            System.out.printf("O robo %s não sobreviveu\n", getNome());
            getAmbiente().removerEntidade(this);
            robosAtingidos.add(this);
        }
        return robosAtingidos;

    }

    @Override
    public void executarTarefa() {
        if (getAmbiente() == null){
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode bombardear.");
        }
        if (getEstado() == Estado.DESLIGADO){
            throw new RoboDesligadoException("O robô está desligado, não pode bombardear.");
        }
    }



}
