package lab03;

import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;
import java.util.Objects;

public class Ambiente {

    // Criando classe ambiente com variaveis especificadas no enunciado,
    // altura para atender a classe RoboAereo, e período para atender roboSolar, subclasse de RoboTerrestre

    private final int comprimento;
    private final int largura;
    private final int altura;   // Atributo altura será da classe Ambiente e irá impor restrição de altura maxima para robos aereos
    private String periodo;     // Dia ou Noite. Atributo para atender a subclasse criada RoboSolar
    private ArrayList<Robo> listaRobos;
    private ArrayList<Obstaculo> listaObstaculos;

    // Construtor de ambiente com comprimento, largura e altura:

    public Ambiente(int comprimento, int largura, int altura) {
        // Dimensões do ambiente não podem ser negativas. Caso seja negativa, atribui 0.
        this.comprimento = Math.max(comprimento,0);
        this.largura = Math.max(largura,0);
        this.altura = Math.max(altura,0);
        this.periodo = "Dia";       // Atributo período iniciado como "Dia" por padrão e alterado em metodo mudarTempo

    }

    public Ambiente(int comprimento, int largura, int altura, ArrayList<Robo> listaRobos, ArrayList<Obstaculo> listaObstaculos) {
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        this.periodo = "Dia";
        this.listaRobos = listaRobos;
        this.listaObstaculos = listaObstaculos;
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

    public ArrayList<Robo> getListaRobos() {
        return listaRobos;
    }

    public ArrayList<Obstaculo> getListaObstaculos() {
        return listaObstaculos;
    }

    // Metodo setPeriodo só pode ser acessado por metodos de classe (mudaTempo) que ira verificar se o argumento foi passado corretamente

    private void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /** Metodo mudarTempo altera atributo periodo para "Dia" ou "Noite"
     * Args = "Dia", "Noite"
     */
    public void mudarTempo(String tempo) {
        if (Objects.equals(tempo, "Dia") || Objects.equals(tempo, "Noite")) {   // So aceita "Dia" e "Noite"
            setPeriodo(tempo);
        }else{
            System.out.println("Argumentos válidos são 'Dia' ou 'Noite'. Tente chamar o método novamente com um argumento válido.");
        }

    }


    // Metodo para verificar se uma coordenada esta dentro dos limites do ambiente:

    public boolean dentroDosLimites(int x, int y, int z) {
        // Verifica se as coordenadas passadas são positivas e menores ou iguais às dimensões do ambiente

        return x >= 0 && x <= getLargura() && y >= 0 && y <= getAltura() && z >= 0 && z <= getComprimento();
    }


    // Metodo para adiconar robo à lista de robos do ambiente:

    public void adicionarRobo(Robo robo) {
        // Inicializa lista vazia, se necessário, e adiciona robô a lista

        if (listaRobos == null) {
            listaRobos = new ArrayList<>();
        }
        listaRobos.add(robo);

        // Robos fora do limite do ambiente vao para (0,0,0).
        if (!dentroDosLimites(robo.getPosX(), robo.getPosY(), robo.getAltitude())) {
            System.out.println("Uma das coordenadas do robo não respeita os limites de ambiente. Posicionando-o em (0,0,0).");
            robo.setPosX(0);
            robo.setPosY(0);
            robo.setAltitude(0);
        }

    }


    // Metodo para remover um robo do ambiente:

    public void removerRobo(Robo robo) {
        // Remove robo da lista e apaga seu apontador. Criado para operar com subclasses de RoboAereo

        listaRobos.remove(robo);
        robo = null;
    }

    public void adicionarObstaculo(Obstaculo obstaculo){

        if (listaObstaculos == null) {
            listaObstaculos = new ArrayList<>();
        }
        listaObstaculos.add(obstaculo);
    }
    public void detectarColisoes(){
        // Verifica se algum robo colidiu com algum obstáculo
        // Só há colisão se obstáculo bloqueia passagem
        for (Robo robo : listaRobos) {
            for (Obstaculo obstaculo : listaObstaculos) {
                int roboX = robo.getPosX(); int roboY = robo.getPosY(); int roboZ = robo.getAltitude();
                int obstaculoX1 = obstaculo.getPosX1(); int obstaculoY1 = obstaculo.getPosY1(); int obstaculoX2 = obstaculo.getPosX2(); int obstaculoY2 = obstaculo.getPosY2();

                // nao da pra trocar os 2 ifs de baixo pra tipo: se bloqueia passagem e obstaculo.contemponto(coordenadas robo)???

                if (obstaculo.getTipo().bloqueiaPassagem()) {
                    if (roboX >= obstaculoX1 && roboX <= obstaculoX2 && roboY >= obstaculoY1 && roboY <= obstaculoY2) {
                        if (roboX == obstaculoX1 || roboX == obstaculoX2 || roboY == obstaculoY1 || roboY == obstaculoY2) {
                            System.out.printf("Robo %s colidiu com o obstáculo %s\n", robo.getNome(), obstaculo.getTipo().getNome());
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Obstaculo> detectarObstaculos(int x1, int y1, int z1, int x2, int y2, int z2){

        ArrayList<Obstaculo> obstaculosEncontrados = new ArrayList<>();

        for (Obstaculo obstaculo : getListaObstaculos())
            if(!((obstaculo.getPosX1() < x1 && obstaculo.getPosX2() < x1 ) || (obstaculo.getPosX1() > x2 && obstaculo.getPosX2() > x2 )))
                if(!((obstaculo.getPosY1() < y1 && obstaculo.getPosY2() < y1 ) || (obstaculo.getPosY1() > y2 && obstaculo.getPosY2() > y2 )))
                    if(!((obstaculo.getBase() < z1 && (obstaculo.getBase() + obstaculo.getAltura() ) < z1 ) || (obstaculo.getBase() > z2 && (obstaculo.getBase() + obstaculo.getAltura() ) > z2 )))
                        obstaculosEncontrados.add(obstaculo);

        return obstaculosEncontrados;
    }
    

}