package lab05.entidade.robos;

import lab05.Ambiente;
import lab05.comunicacao.CentralComunicacao;
import lab05.comunicacao.Comunicavel;
import lab05.comunicacao.ModuloComunicacao;
import lab05.entidade.Entidade;
import lab05.entidade.TipoEntidade;
import lab05.excecoes.ColisaoException;
import lab05.excecoes.ErroComunicacaoException;
import lab05.excecoes.RoboDesligadoException;
import lab05.excecoes.SemAmbienteException;
import lab05.entidade.obstaculos.Obstaculo;
import lab05.sensores.GerenciadorSensores;
import lab05.sensores.Sensor;
import lab05.sensores.SensorClasse;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Robo implements Entidade, Sensoreavel, Comunicavel {

    // Criando classe Robo com atributos especificados no enunciado

    private Ambiente ambiente;
    private final String nome;
    private int posX;
    private int posY;
    protected int posZ = 0; // Altura padrão do robô, que é alterada em robos aéreos.
    private final String id;
    private Estado estado = Estado.LIGADO;
    private ArrayList<Sensor> sensores = new ArrayList<Sensor>();
    private final TipoEntidade tipoEntidade = TipoEntidade.ROBO;
    private ModuloComunicacao comunicacao;
    private GerenciadorSensores gerenciadorSensores;

    // Construtor para robo:
    // Não permite coordenadas negativas, atribui 0 caso necessário.

    public Robo(Ambiente ambiente, String nome, int posX, int posY, int posZ, ModuloComunicacao comunicacao, GerenciadorSensores gerenciadorSensores) {
        this.ambiente = ambiente;
        this.nome = nome;
        this.posX = Math.min(Math.max(posX, 0), ambiente.getComprimento());
        this.posY = Math.min(Math.max(posY, 0), ambiente.getLargura());
        this.posZ = Math.min(Math.max(posZ, 0), ambiente.getAltura());
        this.id = UUID.randomUUID().toString();
        this.comunicacao = comunicacao;
        this.gerenciadorSensores = gerenciadorSensores;

        ambiente.adicionarEntidade(this);

        if (posX != this.posX || posY != this.posY)
            System.out.println("Posicao do robô foi realocada para dentro do ambiente");

    }


    // Getters e Setters:

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public String getNome() {
        return nome;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getZ() {
        return posZ;
    }

    public char getRepresentacao() {
        return 'R';
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public ArrayList<Sensor> getSensores() {
        if (sensores.isEmpty()) {
            return null;
        } else {
            return sensores;
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public String getId() {
        return id;
    }

    public ModuloComunicacao getModuloComunicacao() {
        return comunicacao;
    }

    public void setModuloComunicacao(ModuloComunicacao comunicacao) {
        this.comunicacao = comunicacao;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosZ(int posZ) {
        this.posZ = posZ;
    }


    // Metodo para adicionar sensor no robo

    public void addSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    // Metodo para mover o robo em deltaX, deltaY e deltaZ, verificando se o movimento é válido (linha ligando posição inicial e final não colide com obstaculos [com uma tolerancia])
    // A linha é feita por uma verificação de secções de 0.2 do vetor unitario que representa o vetor de movimento
    // Ou seja, o movimento é dividido em pequenas secções e verificadas as colisões em cada uma, finalizando se alguma colidir

    public void moverPara(int x, int y, int z) {
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô " + getNome() + " está desligado, não pode se mover.");
        }
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode movimentar-se.");
        }

        int deltaX = x - getX();
        int deltaY = y - getY();
        int deltaZ = z - getZ();

        int[] finalPos = {getX() + deltaX, getY() + deltaY, getZ() + deltaZ};   // Posição final ideal do robo

        getAmbiente().dentroDosLimites(finalPos[0], finalPos[1], finalPos[2]);  // Verifica se o movimento ideal não sai do ambiente

        // Variaveis para a verificação de colisão no caminho do robo
        double norma = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
        double[] vetorMove = {
                (norma != 0) ? 0.5 * ((double) deltaX / norma) : 0,
                (norma != 0) ? 0.5 * ((double) deltaY / norma) : 0,
                (norma != 0) ? 0.5 * ((double) deltaZ / norma) : 0
        };
        double[] newPos = {getX(), getY(), getZ()};
        double[] newTempPos = {0, 0, 0};

        // Funcionamento do movimento: se verifica se uma posição logo a frente [newTempPos](posição atual + vetorMove) não colide com nada
        // Se não colidir a nova posição sem colisão é atualizada [newPos], se repete esse processo até que uma posição logo a frente colida
        // ou chegue na posição final ideal

        while (true) {

            // O if dentro do for abaixo é utilizado para verificar se o movimento não extrapola o ponto final do movimento
            // Que, por sua vez, é indicado pela diferença de sinal do vetor que liga o ponto final ao ponto proximo do movimento
            // Além disso também calcula a posição logo a frente para teste

            for (int i = 0; i < 3; i++)
                if ((finalPos[i] - (newPos[i] + vetorMove[i])) * vetorMove[i] >= 0)
                    newTempPos[i] = newPos[i] + vetorMove[i];
                else
                    newTempPos[i] = finalPos[i];

                // Verificação de colisão entre outros robos e obstaculos (não pode ser o proprio robo[primeiro if])

            if (((int) newTempPos[0]) != getX() || ((int) newTempPos[1]) != getY() || ((int) newTempPos[2]) != getZ())
                if (getAmbiente().estaOcupado((int) newTempPos[0], (int) newTempPos[1], (int) newTempPos[2])) {

                    getAmbiente().moverEntidade(this, (int) newPos[0], (int) newPos[1], (int) newPos[2]);

                    throw new ColisaoException("O robo colidiu com uma entidade do tipo " + getAmbiente().getMapa()[(int) newTempPos[0]][(int) newTempPos[1]][(int) newTempPos[2]] + " e foi realocado para a posição (" + getX() + "," + getY() + "," + getZ() + ")");

                }

            // Como não houve colisões, a nova posição sem colisão é atualizada

            newPos[0] = newTempPos[0];
            newPos[1] = newTempPos[1];
            newPos[2] = newTempPos[2];

            // Se chegou no destino
            if (newPos[0] == finalPos[0] && newPos[1] == finalPos[1] && newPos[2] == finalPos[2])
                break;

        }

        // Como não há colisões no movimento do robo, é seguro utilizar o metodo sem verificação do ambiente
        getAmbiente().moverEntidade(this, finalPos[0], finalPos[1], finalPos[2]);

    }


    // Metodo para exibiar a posição do robo:
    public void exibirPosicao() {
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }

        System.out.printf("O robô %s está na posição (%d, %d, %d)\n", getNome(), getX(), getY(), getZ());
    }

    // Metodo para identificar robos proximos:
    public ArrayList<Robo> identificarRobosProximos() {
        return gerenciadorSensores.identificarRobosProximos();
    }

    // Metodo para identificar obstaculos no ambiente:
    public ArrayList<Obstaculo> identificarObstaculosProximos() {
        return gerenciadorSensores.identificarObstaculosProximos();
    }


    // Metodo para acionar todos os sensores do robo
    public void acionarSensores() {
        gerenciadorSensores.acionarSensores();
    }

    // Metodo para ligar o robo
    public void ligar() {
        if (estado == Estado.LIGADO) {
            System.out.printf("O robô %s já está ligado.\n", getNome());
        } else {
            estado = Estado.LIGADO;
            System.out.printf("O robô %s foi ligado.\n", getNome());
        }
    }

    // Metodo para desligar o robo
    public void desligar() {
        if (estado == Estado.DESLIGADO) {
            System.out.printf("O robô %s já está desligado.\n", getNome());
        } else {
            estado = Estado.DESLIGADO;
            System.out.printf("O robô %s foi desligado.\n", getNome());
        }
    }

    @Override
    public void receberMensagem(String mensagem) {
        comunicacao.receberMensagem(mensagem);
    }

    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        comunicacao.enviarMensagem(destinatario, mensagem);
    }

    // Metodo abstrato para a execução de uma tarefa generica
    public abstract void executarTarefa();

}
