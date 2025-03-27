package lab02;

import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.ArrayList;
import java.util.Objects;

public class Ambiente {

    // Criando classe ambiente com variaveis especificadas no enunciado,
    // altura para atender a classe RoboAereo, e periodo para atender roboSolar, subclasse de RoboTerrestre

    private final int comprimento;
    private final int largura;
    private final int altura;   // Atributo altura será da classe Ambiente e irá impor restrição de altura maxima para robos aereos
    private String periodo;     // Dia ou Noite. Atributo para atender a subclasse criada RoboSolar
    private ArrayList<Robo> listaRobos;


    // Construtor de ambiente com comprimento, largura e altura:

    public Ambiente(int comprimento, int largura, int altura) {
        // Dimensões do ambiente não podem ser negativas. Caso seja negativa, atribui 0.
        this.comprimento = Math.max(comprimento,0);
        this.largura = Math.max(largura,0);
        this.altura = Math.max(altura,0);
        this.periodo = "Dia";       // Atributo periodo iniciado como "Dia" por padrao e alterado em metodo mudarTempo

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
        // Verifica se as coordenadas passadas são positivas e menores ou iguais as dimensões do ambiente

        return x >= 0 && x <= getLargura() && y >= 0 && y <= getAltura() && z >= 0 && z <= getComprimento();
    }


    // Metodo para adiconar robo à lista de robos do ambiente:

    public void adicionarRobo(Robo robo) {
        // Inicializa lista vazia, se necessário, e adiciona robo a lista

        if (listaRobos == null) {
            listaRobos = new ArrayList<>();
        }
        listaRobos.add(robo);

        // Variável altura recebe altura do robo se for RoboAereo, senão recebe 0
        int altura = robo instanceof RoboAereo ? ((RoboAereo) robo).getAltitude() : 0;

        // Robos fora do limite do ambiente vao para (0,0,0).
        if (!dentroDosLimites(robo.getPosX(), robo.getPosY(), altura)) {
            System.out.println("Uma das coordenadas do robo não respeita os limites de ambiente. Posicionando-o em (0,0,0).");
            robo.setPosX(0);
            robo.setPosY(0);
            if (robo instanceof RoboAereo) ((RoboAereo) robo).setAltitude(0);
        }

    }

    
    // Metodo para remover um robo do ambiente:

    public void removerRobo(Robo robo) {
        // Remove robo da lista e apaga seu apontador. Criado para operar com subclasses de RoboAereo

        listaRobos.remove(robo);
        robo = null;
    }

}
