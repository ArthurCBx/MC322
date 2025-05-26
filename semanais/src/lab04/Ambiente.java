package lab04;

import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.excecoes.ColisaoException;
import lab04.excecoes.ForaDosLimitesException;
import lab04.obstaculos.Obstaculo;
import lab04.entidade.robos.Robo;
import lab04.obstaculos.TipoObstaculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ambiente {
    // Criando classe ambiente com variaveis especificadas no enunciado.
    // Período para atender roboSolar, subclasse de RoboTerrestre

    private final int comprimento;
    private final int largura;
    private final int altura;   // Atributo altura irá impor restrição de altura maxima para robos aereos
    private String periodo;     // Dia ou Noite. Atributo para atender a subclasse criada RoboSolar
    private ArrayList<Entidade> listaEntidades;
    private TipoEntidade[][][] mapa;

    // Construtor de ambiente com comprimento, largura e altura:

    public Ambiente(int comprimento, int largura, int altura) {
        // Dimensões do ambiente não podem ser negativas. Caso seja negativa, atribui 0.
        this.comprimento = Math.max(comprimento, 0);
        this.largura = Math.max(largura, 0);
        this.altura = Math.max(altura, 0);
        this.periodo = "Dia";       // Atributo período iniciado como "Dia" por padrão e alterado em metodo mudarTempo
        this.listaEntidades = new ArrayList<>();
    }

    // Getters e Setters:

    public int getComprimento() {
        return comprimento;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public String getPeriodo() {
        return periodo;
    }

    public ArrayList<Entidade> getListaEntidades() {
        return listaEntidades;
    }

    public TipoEntidade[][][] getMapa() {
        return mapa;
    }

    public void setMapa(TipoEntidade[][][] mapa) {
        this.mapa = mapa;
    }

    // Metodo setPeriodo só pode ser acessado por metodos de classe (mudaTempo) que ira verificar se o argumento foi passado corretamente
    private void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * Metodo mudarTempo altera atributo periodo para "Dia" ou "Noite"
     * Args = "Dia", "Noite"
     */
    public void mudarTempo(String tempo) {
        if (Objects.equals(tempo, "Dia") || Objects.equals(tempo, "Noite")) {   // So aceita "Dia" e "Noite"
            setPeriodo(tempo);
        } else {
            System.out.println("Argumentos válidos são 'Dia' ou 'Noite'. Tente chamar o método novamente com um argumento válido.");
        }

    }


    // Metodo para verificar se uma coordenada esta dentro dos limites do ambiente:
    public void dentroDosLimites(int x, int y, int z) {
        if (!(x >= 0 && x <= getComprimento() && y >= 0 && y <= getLargura() && z >= 0 && z <= getAltura()))
            throw new ForaDosLimitesException("As coordenadas (" + x + "," + y + "," + z + ") estão fora dos limites do ambiente");

    }

    // Metodo para inicializar o mapa vazio
    public void inicializarMapa() {

        if (mapa != null)
            System.out.printf("O mapa já foi inicializado\n");

        mapa = new TipoEntidade[comprimento + 1][largura + 1][altura + 1];

        for (int x = 0; x <= getComprimento(); x++)
            for (int y = 0; y <= getLargura(); y++)
                for (int z = 0; z <= getAltura(); z++)
                    mapa[x][y][z] = TipoEntidade.VAZIO;

    }

    // Metodo para adiconar entidade à lista e mapa de entidades do ambiente:
    public void adicionarEntidade(Entidade entidade) {

        if (mapa == null) {
            System.out.printf("O ambiente não possui mapa inicializado, inicializando o mapa\n");
            inicializarMapa();
        }

        // Tratamento distinto de robos (pontual) e obstaculos (extenso)

        if (entidade.getTipoEntidade() == TipoEntidade.OBSTACULO) {
            Obstaculo obstaculo = (Obstaculo) entidade;

            // Adiciona obstáculo ao ambiente apenas se ele estiver dentro dos limites do ambiente.
            dentroDosLimites(obstaculo.getPosX1(), obstaculo.getPosY1(), obstaculo.getBase());
            dentroDosLimites(obstaculo.getPosX2(), obstaculo.getPosY2(), obstaculo.getBase() + obstaculo.getAltura());

            // Verificação de possivel colisão:
            for (int x = obstaculo.getPosX1(); x <= obstaculo.getPosX2(); x++)
                for (int y = obstaculo.getPosY1(); y <= obstaculo.getPosY2(); y++)
                    for (int z = obstaculo.getBase(); z <= obstaculo.getBase() + obstaculo.getAltura(); z++)
                        if (estaOcupado(x, y, z))
                            throw new ColisaoException("Obstaculo especificado colide com outra entidade já presente em seu domínio, operação cancelada");

            // Adiciona o obstaculo na lista de entidade e no mapa:
            listaEntidades.add(obstaculo);
            for (int x = obstaculo.getPosX1(); x <= obstaculo.getPosX2(); x++)
                for (int y = obstaculo.getPosY1(); y <= obstaculo.getPosY2(); y++)
                    for (int z = obstaculo.getBase(); z <= obstaculo.getBase() + obstaculo.getAltura(); z++)
                        mapa[x][y][z] = TipoEntidade.OBSTACULO;


        } else {

            // Verificação do robo estar dentro dos limites e se há alguma entidade ocupando suas coordenadas
            dentroDosLimites(entidade.getX(), entidade.getY(), entidade.getZ());
            if (!estaOcupado(entidade.getX(), entidade.getY(), entidade.getZ())) {
                listaEntidades.add(entidade);
                mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = entidade.getTipoEntidade();

            } else {
                throw new ColisaoException("Entidade especificada colide com outra entidade já presente nestas coordenadas, operação cancelada");

            }
        }
    }

    // Metodo para remover uma entidade do ambiente
    public void removerEntidade(Entidade entidade) {
        if (mapa == null) {
            System.out.printf("O ambiente não possui mapa inicializado, inicialize o mapa primeiro\n");
            return;
        }

        if (listaEntidades.contains(entidade)) {
            listaEntidades.remove(entidade);

            // Tratamento distinto de robos (pontual) e obstaculos (extenso):

            if (entidade.getTipoEntidade() == TipoEntidade.OBSTACULO) { // Remove o obstaculo inteiro
                Obstaculo obstaculo = (Obstaculo) entidade;
                for (int x = obstaculo.getPosX1(); x <= obstaculo.getPosX2(); x++)
                    for (int y = obstaculo.getPosY1(); y <= obstaculo.getPosY2(); y++)
                        for (int z = obstaculo.getBase(); z <= obstaculo.getBase() + obstaculo.getAltura(); z++)
                            mapa[x][y][z] = TipoEntidade.VAZIO;


            } else {
                mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;   // Remove o robo
            }

            entidade = null; // Remove a referência ao objeto
        } else {
            System.out.println("A entidade não está no ambiente.");
        }
    }

    // Metodo para verificar se uma ou mais entidades estão em colisão
    public void verificarColisoes() {

        boolean colisao = false;    // Flag para colisão
        // Lista de robos:
        List<Robo> listaRobo = getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).toList();

        int roboX;
        int roboY;
        int roboZ;

        // Verificação entre robo-robo e robo-obstaculo (já que obstaculos são imoveis e não podem colidir entre si [criação desses previne a colisão])
        for (Robo robo : listaRobo) {

            roboX = robo.getX();
            roboY = robo.getY();
            roboZ = robo.getZ();

            // Robo-obstaculo
            for (Obstaculo obstaculo : getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.OBSTACULO).map(entidade -> (Obstaculo) entidade).toList()) {
                if (obstaculo.getTipoObstaculo().bloqueiaPassagem() && obstaculo.contemPonto(roboX, roboY, roboZ)) {
                    System.out.printf("O robo %s colidiu com um obstáculo %s em (%d,%d,%d).\n",
                            robo.getNome(), obstaculo.getTipoObstaculo().getNome(), roboX, roboY, roboZ);
                    colisao = true;
                }
            }
            // Robo-Robo (reporta tanto robo1 colide com robo2 quanto robo2 colide com robo1)
            for (Robo robo2 : listaRobo) {
                if (roboX == robo2.getX() && roboY == robo2.getY() && roboZ == robo2.getZ() && robo2 != robo) {
                    System.out.printf("O robo %s colidiu com o robo %s em (%d,%d,%d).\n",
                            robo.getNome(), robo2.getNome(), roboX, roboY, roboZ);
                    colisao = true;
                }
            }
        }

        if (!colisao) {
            System.out.printf("Não foram verificadas colisões no ambiente %s\n", this);
        } else {
            throw new ColisaoException("Uma ou mais colisões foram detectadas");
        }
    }

    // Metodo para a verificação de ocupação da coordenada a partir do mapa
    public boolean estaOcupado(int x, int y, int z) {
        return mapa[x][y][z] != TipoEntidade.VAZIO;
    }

    // Metodo para mover forçadamente (sem verificação) uma entidade para uma coordenada
    public void moverEntidade(Entidade entidade, int novoX, int novoY, int novoZ) {

        if (entidade.getTipoEntidade() == TipoEntidade.ROBO) {
            mapa[entidade.getX()][entidade.getY()][entidade.getZ()] = TipoEntidade.VAZIO;
            ((Robo) entidade).setPosX(novoX);
            ((Robo) entidade).setPosY(novoY);
            ((Robo) entidade).setPosZ(novoZ);
            mapa[novoX][novoY][novoZ] = TipoEntidade.ROBO;

        } else {
            System.out.printf("A posição de entidades do tipo" + entidade.getTipoEntidade() + "não muda\n");
        }

    }


    // Metodo para acionar todos os sensores de todos os robos no ambiente

    public void executarSensores() {
        // Executa sensores de todos os robôs no ambiente.
        for (Entidade entidade : listaEntidades)
            if (entidade.getTipoEntidade() == TipoEntidade.ROBO) {
                System.out.printf("Monitoramento do Robo %s:\n", ((Robo) entidade).getNome());
                ((Robo) entidade).acionarSensores();
            }

    }
    // Metodo para printar no console a vizualização do mapa do ambiente no nivel z = 0

    public void vizualizarAmbiente() {

        if (mapa == null) {
            System.out.printf("O ambiente não possui mapa inicializado, inicializando o mapa\n");
            inicializarMapa();
        }

        for (int x = 0; x <= comprimento + 2; x++)
            System.out.print('_');                  // Imprime uma borda superior para o mapa
        System.out.println();

        char representacao = '´';

        for (int y = largura; y >= 0; y--) {
            System.out.print("|");                  // Imprime uma borda esquerda para o mapa
            for (int x = 0; x <= comprimento; x++) {
                for (int z = altura; z >= 0; z--) {
                    if (!mapa[x][y][z].equals(TipoEntidade.VAZIO)) {
                        for (Entidade entidade : listaEntidades)    // Para toda entidade
                            if (entidade.getTipoEntidade() == mapa[x][y][z])    // Se a entidade for do mesmo tipo da localizada no mapa
                                if (entidade.getTipoEntidade() == TipoEntidade.ROBO) {  // Tratamento para robo(pontual)

                                    if (entidade.getX() == x && entidade.getY() == y && entidade.getZ() == z) { // Verifica se realmente é este robo neste local
                                        representacao = entidade.getRepresentacao();
                                        break;
                                    }

                                } else {                                                  // Tratamento para obstaculo(extenso)
                                    if (((Obstaculo) entidade).contemPonto(x, y, z)) {  // Verifica se realmente é este obstaculo neste local
                                        representacao = entidade.getRepresentacao();
                                        break;
                                    }

                                }
                    }

                    if(representacao != '´'){
                        break;
                    }

                }

                System.out.printf("%c",representacao);
                representacao = '´';
            }
            System.out.print("|\n");            // Imprime uma borda direita para o mapa


        }
        for (int x = 0; x <= comprimento + 2; x++)
            System.out.print('-');              // Imprime uma borda inferior para o mapa

        System.out.printf("\n\n");

    }

    // Metodo para detectar obstaculos dentro de uma area delimitada
    public ArrayList<Obstaculo> detectarObstaculos(int x1, int y1, int z1, int x2, int y2, int z2) {

        if (x1 > x2) {
            int tempx1 = x1;
            x1 = x2;
            x2 = tempx1;
        }
        if (y1 > y2) {
            int tempy1 = y1;
            y1 = y2;
            y2 = tempy1;
        }
        if (z1 > z2) {
            int tempz1 = z1;
            z1 = z2;
            z2 = tempz1;
        }

        ArrayList<Obstaculo> obstaculosEncontrados = new ArrayList<>();
        List<Obstaculo> obstaculos = getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.OBSTACULO).map(entidade -> (Obstaculo) entidade).toList();
        for (Obstaculo obstaculo : obstaculos)
            if (!((obstaculo.getPosX1() < x1 && obstaculo.getPosX2() < x1) || (obstaculo.getPosX1() > x2 && obstaculo.getPosX2() > x2)))
                if (!((obstaculo.getPosY1() < y1 && obstaculo.getPosY2() < y1) || (obstaculo.getPosY1() > y2 && obstaculo.getPosY2() > y2)))
                    if (!((obstaculo.getBase() < z1 && (obstaculo.getBase() + obstaculo.getAltura()) < z1) || (obstaculo.getBase() > z2 && (obstaculo.getBase() + obstaculo.getAltura()) > z2)))
                        obstaculosEncontrados.add(obstaculo);

        return obstaculosEncontrados;
    }

}