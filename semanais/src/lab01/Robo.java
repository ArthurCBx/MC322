package lab01;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

public class Robo {
    private final String nome;
    private int posX;
    private int posY;

    public Robo(String nome, int posX, int posY) {
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
    }

    public String getNome() {
        return nome;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void mover(int x, int y){
        setPosX(x);
        setPosY(y);
    }

    public void exibirPosicao(){
        System.out.println("O robô " + getNome() + " está na posição (" + getPosX() + ", " + getPosY() + ")");
    }
}



