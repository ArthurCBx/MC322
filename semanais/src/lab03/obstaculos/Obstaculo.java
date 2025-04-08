package lab03.obstaculos;

public class Obstaculo {
    private final int posX1;
    private final int posY1;
    private final int posX2;
    private final int posY2;
    private int altura;


    public Obstaculo(int posX1, int posY1, int posX2, int posY2, int altura) {
        this.posX1 = posX1;
        this.posY1 = posY1;
        this.posX2 = posX2;
        this.posY2 = posY2;
        this.altura = altura;

    }

    public int getPosX1() {
        return posX1;
    }

    public int getPosY1() {
        return posY1;
    }

    public int getPosX2() {
        return posX2;
    }

    public int getPosY2() {
        return posY2;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }




// FALTA ENUM e o resto





}
