package lab02;

import lab02.robos.Robo;

import java.util.ArrayList;

public class Ambiente {
    private final int comprimento;
    private final int largura;
    private final int altura;
    private ArrayList<Robo> listaRobos;

    public Ambiente(int comprimento, int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        this.comprimento = comprimento;
    }

    public int getComprimento() {
        return comprimento;
    }

    public void adicionarRobo(Robo robo){
        if (listaRobos == null){
            listaRobos = new ArrayList<>();
        }
        listaRobos.add(robo);
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public boolean dentroDosLimites(int x, int y, int z){
        return x >= 0 && x <= getLargura() && y >= 0 && y <= getAltura() && z >= 0 && z <= getComprimento();
    }
}
