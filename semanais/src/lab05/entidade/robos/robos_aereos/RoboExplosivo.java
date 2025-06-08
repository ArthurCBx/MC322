package lab05.entidade.robos.robos_aereos;

import lab05.Ambiente;
import lab05.comunicacao.ModuloComunicacao;
import lab05.entidade.robos.Autonomo;
import lab05.entidade.robos.Estado;
import lab05.entidade.robos.Robo;
import lab05.entidade.robos.RoboAereo;
import lab05.excecoes.RoboDesligadoException;
import lab05.excecoes.SemAmbienteException;
import lab05.sensores.GerenciadorSensores;

import java.util.ArrayList;
import java.util.List;

/*
 Subclasse de RoboAereo que possui a habilidade de se autodestruir, levando consigo os robos num raio de explosão (raio deve ser indicado no construtor).
 Para isso, ele possui o metodo explodir, que recebe o ambiente em que está inserido como atributo.
 */

public class RoboExplosivo extends RoboAereo implements Explosivos, Autonomo {
    private final int raioExplosao;


    public RoboExplosivo(Ambiente ambiente, String nome, int posX, int posY, int altitude, int altitudeMaxima, int raioExplosao, ModuloComunicacao comunicacao, GerenciadorSensores gerenciadorSensores) {
        super(ambiente, nome, posX, posY, altitude, altitudeMaxima, comunicacao, gerenciadorSensores);
        this.raioExplosao = Math.abs(raioExplosao);

    }

    // Getters e Setters:

    public int getRaioExplosao() {
        return raioExplosao;
    }

    public String getDescricao() {
        return "Robo Explosivo: " + getNome() + "\n" +
                "id: " + this.getId() + "\n" +
                "Raio de Explosão: " + getRaioExplosao() + "\n" +
                "Altura Maxima: " + getAltitudeMaxima() + "\n" +
                "Altura Atual: " + getZ() + "\n" +
                "Posicao Atual: (" + getX() + ", " + getY() + ", " + getZ() + ")";
    }


    // Metodo para se autodestruir e levar consigo os robos num raio de explosao.
    public List<Robo> explodir() {
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode explodir.");
        }
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado, não pode explodir.");
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

    // Metodo do movimento autonomo em XYZ (move uma direção aleatoria com norma maxima do movimento definido em norma)
    @Override
    public void moveAutomatico(double norma) {

        int[] move = {  (int) ((norma / Math.sqrt(3)) * (2 * Math.random() - 1)),
                        (int) ((norma / Math.sqrt(3)) * (2 * Math.random() - 1)),
                        (int) ((norma / Math.sqrt(3)) * (2 * Math.random() - 1))};

        // Para o robo explosivo em especifico, há o respeito dos limites do ambiente

        move[0] = Math.max(0, Math.min(getX() + move[0], getAmbiente().getComprimento()));
        move[1] = Math.max(0, Math.min(getY() + move[1], getAmbiente().getLargura()));
        move[2] = Math.max(0, Math.min(getZ() + move[2], getAmbiente().getAltura()));

        moverPara(move[0], move[1], move[2]);

    }

    // Tarefa do robo explosivo para procurar robos automaticamente e os destruir
    @Override
    public void executarTarefa() {
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode realizar sua tarefa.");
        }
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado, não pode realizar sua tarefa.");
        }
        if (getSensores() == null) {
            System.out.println("O robô solicitado não possui sensores, logo não pode realizar sua tarefa.");
            return;
        }


        System.out.printf("Executando a busca e destruição de robos\n");

        List<Robo> robosencontrados;

        for (int i = 0; i < 5; i++) {   // Procura por robos 5 vezes (ou ate colidir)
            System.out.printf("\nBusca numero %d:\n\n",i+1);

            robosencontrados = identificarRobosProximos();
            exibirPosicao();
            acionarSensores();  // Vizualização da busca por robos no terminal

            if (!robosencontrados.isEmpty()) {
                System.out.printf("Alvo encontrado\n");
                Robo roboescolhido = robosencontrados.get((int) (Math.random() * (robosencontrados.size())));   // Se encontra mais de um robo, seleciona um aleatorio
                int posXadjacente = roboescolhido.getX() + (getX() - roboescolhido.getX())/Math.abs((getX() - roboescolhido.getX()) == 0 ? 1: (getX() - roboescolhido.getX()));
                int posYadjacente = roboescolhido.getY() + (getY() - roboescolhido.getY())/Math.abs((getY() - roboescolhido.getY()) == 0 ? 1: (getY() - roboescolhido.getY()));
                int posZadjacente = roboescolhido.getZ() + (getZ() - roboescolhido.getZ())/Math.abs((getZ() - roboescolhido.getZ()) == 0 ? 1: (getZ() - roboescolhido.getZ()));
                moverPara(posXadjacente, posYadjacente, posZadjacente);
                exibirPosicao();
                explodir();
                return;

            } else {
                moveAutomatico(20);
            }
        }

    }

    @Override
    public void executarMissao() {

    }
}
