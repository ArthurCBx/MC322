package lab04.entidade.robos;

import lab04.Ambiente;
import lab04.comunicacao.CentralComunicacao;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.excecoes.*;
import lab04.obstaculos.Obstaculo;
import lab04.sensores.Sensor;
import lab04.sensores.SensorClasse;

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
    private Estado estado;
    private ArrayList<Sensor> sensores = new ArrayList<Sensor>();
    private final TipoEntidade tipoEntidade = TipoEntidade.ROBO;


    // Construtor para robo:
    // Não permite coordenadas negativas, atribui 0 caso necessário.

    public Robo(Ambiente ambiente, String nome, int posX, int posY) {
        this.ambiente = ambiente;
        this.nome = nome;
        this.posX = Math.min(Math.max(posX, 0), ambiente.getComprimento());
        this.posY = Math.min(Math.max(posY, 0), ambiente.getLargura());
        this.id = UUID.randomUUID().toString();

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
            System.out.printf("O robô %s não possui sensores.\n", getNome());
            return null;
        } else {
            return sensores;
        }
    }

    public void addSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    public Estado getEstado() {
        return estado;
    }

    public String getId() {
        return id;
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


    // Metodo para mover o robo em deltaX, deltaY e deltaZ, verificando se o movimento é válido (linha ligando posição inicial e final não colide com obstaculos [com uma tolerancia])
    // A linha é feita por uma verificação de seções de 0.25*sqrt(2) do vetor unitario que representa o vetor de movimento
    // Ou seja, o movimento é dividido em pequenas seções e verificadas as colisões em cada uma, finalizando se alguma colidir

    public void moverPara(int deltaX, int deltaY, int deltaZ) {
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô " + getNome() + " está desligado, não pode se mover.");
        }
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode movimentar-se.");
        }

        int[] finalPos = {getX() + deltaX, getY() + deltaY, getZ() + deltaZ};

        getAmbiente().dentroDosLimites(finalPos[0], finalPos[1], finalPos[2]);

        // REFAZER PELO AMBIENTE SER DIFERENTE
        // ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarEntidades(getX(), getY(), getZ(), finalPos[0], finalPos[1], finalPos[2]);

        double norma = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));

        double[] vetorMove = {
                (norma != 0) ? 0.25 * ((double) deltaX / norma) : 0,
                (norma != 0) ? 0.25 * ((double) deltaY / norma) : 0,
                (norma != 0) ? 0.25 * ((double) deltaZ / norma) : 0
        };

        double[] newPos = {getX(), getY(), getZ()};
        double[] newTempPos = {0, 0, 0};

        while (true) {

            // O if dentro do for abaixo é utilizados para verificar se o movimento não extrapola o ponto final do movimento
            // Que, por sua vez, é indicado pela diferença de sinal do vetor que liga o ponto final ao ponto proximo do movimento

            for (int i = 0; i < 3; i++)
                if ((finalPos[i] - (newPos[i] + vetorMove[i]) * vetorMove[i]) >= 0)
                    newTempPos[i] = newPos[i] + vetorMove[i];
                else
                    newTempPos[i] = finalPos[i];
            
            if (((int) newTempPos[0]) != getX() || ((int) newTempPos[1]) != getY() || ((int) newTempPos[2]) != getZ())
                if (getAmbiente().estaOcupado((int) newTempPos[0], (int) newTempPos[1], (int) newTempPos[2])) {

                    getAmbiente().moverEntidade(this, (int) newPos[0], (int) newPos[1], (int) newPos[2]);

                    throw new ColisaoException("O robo colidiu com uma entidade " + getAmbiente().estaOcupado((int) newTempPos[0], (int) newTempPos[1], (int) newTempPos[2]) + " e foi realocado para a posição (" + getX() + "," + getY() + "," + getZ() + ")");

                }

            newPos[0] = newTempPos[0];
            newPos[1] = newTempPos[1];
            newPos[2] = newTempPos[2];

            if (newPos[0] == finalPos[0] && newPos[1] == finalPos[1] && newPos[2] == finalPos[2])
                break;

        }

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
        if (estado == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (sensores == null) {
            System.out.println("O robô solicitado não possui sensores");
            return null;
        }
        ArrayList<Robo> robosProximos = new ArrayList<>();
        for (Sensor sensor : getSensores()) {
            ArrayList<Robo> identificados = sensor.listaRobosEncontrados(getAmbiente(), this);
            for (Robo robo : identificados) {
                if (!robosProximos.contains(robo)) {
                    robosProximos.add(robo);
                }
            }
        }
        return robosProximos;
    }

    // Metodo para identificar obstaculos no ambiente:
    public void identificarObstaculosProximos() {
        if (estado == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (sensores == null) {
            System.out.println("O robô solicitado não possui sensores");
            return;
        }

        sensores = getSensores();
        ArrayList<Obstaculo> obstaculosProximos = new ArrayList<>();
        // Para cada sensor diferente de sensorClasse, lista obstáculos encontrados e adiciona na lista de obstáculos próximos os que ainda não estão.
        for (Sensor sensor : sensores) {
            if (sensor.getClass() != SensorClasse.class) {
                ArrayList<Obstaculo> obstaculoSensor = sensor.listaObstaculosEncontrados(this.getAmbiente(), this);

                for (Obstaculo obstaculo : obstaculoSensor) {
                    if (!obstaculosProximos.contains(obstaculo)) {
                        obstaculosProximos.add(obstaculo);
                    }
                }
            }
        }
    }

    public void acionarSensores() {
        int count = 1;
        if (estado == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (sensores == null) {
            System.out.println("O robô solicitado não possui sensores");
            return;
        }
        for (Sensor sensor : getSensores()) {
            System.out.printf("O sensor %d está monitorando o ambiente %s\n", count, getAmbiente());
            sensor.monitorar(getAmbiente(), this);
            System.out.println();
            count++;
        }
        System.out.println("Monitoramente finalizado.");
    }

    public void ligar() {
        if (estado == Estado.LIGADO) {
            System.out.printf("O robô %s já está ligado.\n", getNome());
        } else {
            estado = Estado.LIGADO;
            System.out.printf("O robô %s foi ligado.\n", getNome());
        }
    }

    public void desligar() {
        if (estado == Estado.DESLIGADO) {
            System.out.printf("O robô %s já está desligado.\n", getNome());
        } else {
            estado = Estado.DESLIGADO;
            System.out.printf("O robô %s foi desligado.\n", getNome());
        }
    }

    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        if (this.estado == Estado.DESLIGADO) {
            throw new ErroComunicacaoException("Mensagem não enviada. O robô que tentou enviar está desligado.");
        }
        if (((Robo) (destinatario)).getEstado() == Estado.DESLIGADO) {
            throw new ErroComunicacaoException("Mensagem não enviada. O robô destinatário está desligado.");
        }

        String remetente = String.format("%s(id:%s)", getNome(), getId());
        CentralComunicacao.registrarMensagem(remetente, mensagem);

        ((Robo) destinatario).receberMensagem(mensagem);
    }

    public void receberMensagem(String mensagem) {
        // Não exige verificação de estado, pois o robô que enviou a mensagem já fez a verificação.
        System.out.printf("Robô %s recebeu a seguinte mensagem:%s\n", getNome(), mensagem);
    }

    public abstract void executarTarefa();

}




