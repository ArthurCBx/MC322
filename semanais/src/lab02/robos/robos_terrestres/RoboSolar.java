package lab02.robos.robos_terrestres;

import lab02.Ambiente;
import lab02.robos.RoboTerrestre;

import java.util.Objects;

public class RoboSolar extends RoboTerrestre {
    private int bateria;
    private int potenciaPainelSolar;


    public RoboSolar(String nome, int posX, int posY, String direcao, int raioSensor, int velocidadeMaxima, int potenciaPainelSolar) {
        super(nome, posX, posY, direcao, raioSensor, velocidadeMaxima);
        this.potenciaPainelSolar = Math.abs(potenciaPainelSolar);
        this.bateria = 0;

    }

    public int getBateria() {
        return bateria;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public int getPotenciaPainelSolar() {
        return potenciaPainelSolar;
    }

    public void setPotenciaPainelSolar(int potenciaPainelSolar) {
        this.potenciaPainelSolar = potenciaPainelSolar;
    }


    private void carregar(Ambiente ambiente) {
        if (Objects.equals(ambiente.getSol(), "Dia")) {
            setBateria(getBateria() + getPotenciaPainelSolar());
        }
    }

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {
        if (Objects.equals(ambiente.getSol(), "Dia")) {
            super.mover(deltaX, deltaY, ambiente);
        } else if (Math.abs(deltaX) + Math.abs(deltaY) < getBateria()) {
            setBateria(getBateria() - Math.abs(deltaX) - Math.abs(deltaY));
            super.mover(deltaX, deltaY, ambiente);
        } else {
            System.out.printf("O robo %s não tem carga suficiente na bateria para a locomoção, carregue a bateria\n", getNome());
        }
    }
}