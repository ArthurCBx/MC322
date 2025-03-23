package lab02;

import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ambiente {
    private final int comprimento;
    private final int largura;
    private final int altura;
    private String sol;
    private ArrayList<Robo> listaRobos;

    public Ambiente(int comprimento, int largura, int altura) {
        this.comprimento = comprimento;
        this.largura = largura;
        this.altura = altura;
        this.sol = "Dia";

    }

    public int getComprimento() {
        return comprimento;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public String getSol() {
        return sol;
    }

    public ArrayList<Robo> getListaRobos() {
        return listaRobos;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    /**
     * Args = "Dia", "Noite"
     */
    public void mudarTempo(String tempo) {
        if (Objects.equals(tempo, "Dia") || Objects.equals(tempo, "Noite")) {
            setSol(tempo);
        }

    }

    public boolean dentroDosLimites(int x, int y, int z) {
        return x >= 0 && x <= getLargura() && y >= 0 && y <= getAltura() && z >= 0 && z <= getComprimento();
    }


    public void adicionarRobo(Robo robo) {
        if (listaRobos == null) {
            listaRobos = new ArrayList<>();
        }
        listaRobos.add(robo);

        int altitude = robo instanceof RoboAereo ? ((RoboAereo) robo).getAltitude() : 0;

        if (!dentroDosLimites(robo.getPosX(), robo.getPosY(), altitude)) {     // Robos fora do limite do ambiente vao para (0,0,0)
            robo.setPosX(0);
            robo.setPosY(0);
            if (robo instanceof RoboAereo) ((RoboAereo) robo).setAltitude(0);
        }

    }

    public void removerRobo(Robo robo) {
        listaRobos.remove(robo);
        robo = null;
    }

}
