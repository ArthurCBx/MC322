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
    private ControleMovimento controleMovimento;

    // Construtor para robo:
    // Não permite coordenadas negativas, atribui 0 caso necessário.

    public Robo(Ambiente ambiente, String nome, int posX, int posY, int posZ, ModuloComunicacao comunicacao, GerenciadorSensores gerenciadorSensores, ControleMovimento controleMovimento) {
        this.ambiente = ambiente;
        this.nome = nome;
        this.posX = Math.min(Math.max(posX, 0), ambiente.getComprimento());
        this.posY = Math.min(Math.max(posY, 0), ambiente.getLargura());
        this.posZ = Math.min(Math.max(posZ, 0), ambiente.getAltura());
        this.id = UUID.randomUUID().toString();
        this.comunicacao = comunicacao;
        this.gerenciadorSensores = gerenciadorSensores;
        this.controleMovimento = controleMovimento;

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

    public ControleMovimento getControleMovimento() {
        return controleMovimento;
    }

    public void setControleMovimento(ControleMovimento controleMovimento) {
        this.controleMovimento = controleMovimento;
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
        controleMovimento.moverPara(x, y, z);
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
