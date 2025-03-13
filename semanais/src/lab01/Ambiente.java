package lab01;

public class Ambiente {
    private final int largura;
    private final int altura;

    public Ambiente(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public boolean dentroDosLimites(int x, int y){
        return x >= 0 && x <= getLargura() && y >= 0 && y <= getAltura();
    }
}
