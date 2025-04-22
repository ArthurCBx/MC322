package lab03;

import lab03.obstaculos.Obstaculo;
import lab03.robos.Robo;

import java.util.ArrayList;
import java.util.Objects;

public class Ambiente {
    // Criando classe ambiente com variaveis especificadas no enunciado.
    // Período para atender roboSolar, subclasse de RoboTerrestre

    private final int comprimento;
    private final int largura;
    private final int altura;   // Atributo altura irá impor restrição de altura maxima para robos aereos
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

    // Sobrecarga do construtor para adicionar lista de robos e obstaculos.
    public Ambiente(int comprimento, int largura, int altura, ArrayList<Robo> listaRobos, ArrayList<Obstaculo> listaObstaculos) {
        this.comprimento = Math.max(comprimento,0);
        this.largura = Math.max(largura,0);
        this.altura = Math.max(altura,0);
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

        return x >= 0 && x <= getLargura() && y >= 0 && y <= getComprimento() && z >= 0 && z <= getAltura();
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

    public void removerRobo(Robo robo) {
        // Remove robo da lista e apaga seu apontador.

        listaRobos.remove(robo);
        robo = null;
    }

    public void adicionarObstaculo(Obstaculo obstaculo){

        if (listaObstaculos == null) {
            listaObstaculos = new ArrayList<>();
        }

        // Adiciona obstáculo ao ambiente apenas se ele estiver dentro dos limites do ambiente
        if(dentroDosLimites(obstaculo.getPosX1(), obstaculo.getPosY1(),obstaculo.getBase())){
            if(dentroDosLimites(obstaculo.getPosX2(), obstaculo.getPosY2(),obstaculo.getBase() + obstaculo.getAltura())) {
                listaObstaculos.add(obstaculo);
            }else{
                System.out.println("Uma das coordenadas do obstáculo não respeita os limites de ambiente. Altere-a para adicionar o obstáculo.");
            }
        }else{
            System.out.println("Uma das coordenadas do obstáculo não respeita os limites de ambiente. Altere-a para adicionar o obstáculo.");
        }

    }
    public void detectarColisoes(){
        // Verifica se algum robo colidiu com algum obstáculo.
        // Só há colisão se obstáculo bloqueia passagem e robo está na borda desse obstáculo, pois robô não o atravessa.
        for (Robo robo : listaRobos) {
            for (Obstaculo obstaculo : listaObstaculos) {
                int roboX = robo.getPosX(); int roboY = robo.getPosY(); int roboZ = robo.getAltitude();
                int obstaculoX1 = obstaculo.getPosX1(); int obstaculoY1 = obstaculo.getPosY1();
                int obstaculoX2 = obstaculo.getPosX2(); int obstaculoY2 = obstaculo.getPosY2();

                if (obstaculo.getTipo().bloqueiaPassagem() && obstaculo.contemPonto(roboX,roboY,roboZ)) {
                    System.out.printf("O robo %s colidiu com um obstáculo %s em (%d,%d,%d).\n",
                            robo.getNome(), obstaculo.getTipo().getNome(),roboX,roboY,roboZ);
                }
            }
        }
    }

    // Metodo auxiliar para verificar se há obstáculo num plano definido por dois pontos (x1,y1,z1) e (x2,y2,z2)
    // Movimento é ou bidimensional em robos ou unidimensional em robos aéreos nos metodos subir e descer.

    public ArrayList<Obstaculo> detectarObstaculos(int x1, int y1, int z1, int x2, int y2, int z2){

        if(x1 > x2){
            int tempx1 = x1;
            x1 = x2;
            x2 = tempx1;
        }
        if(y1 > y2){
            int tempy1 = y1;
            y1 = y2;
            y2 = tempy1;
        }
        if(z1 > z2){
            int tempz1 = z1;
            z1 = z2;
            z2 = tempz1;
        }

        ArrayList<Obstaculo> obstaculosEncontrados = new ArrayList<>();

        for (Obstaculo obstaculo : getListaObstaculos())
            if(!((obstaculo.getPosX1() < x1 && obstaculo.getPosX2() < x1 ) || (obstaculo.getPosX1() > x2 && obstaculo.getPosX2() > x2 )))
                if(!((obstaculo.getPosY1() < y1 && obstaculo.getPosY2() < y1 ) || (obstaculo.getPosY1() > y2 && obstaculo.getPosY2() > y2 )))
                    if(!((obstaculo.getBase() < z1 && (obstaculo.getBase() + obstaculo.getAltura() ) < z1 ) || (obstaculo.getBase() > z2 && (obstaculo.getBase() + obstaculo.getAltura() ) > z2 )))
                        obstaculosEncontrados.add(obstaculo);

        return obstaculosEncontrados;
    }


}