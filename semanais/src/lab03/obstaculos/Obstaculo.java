package lab03.obstaculos;

public class Obstaculo {
    private int posX1;
    private int posY1;
    private int posX2;
    private int posY2;
    private int altura;
    private int base;
    private final TipoObstaculo tipo;


    public Obstaculo(int posX1, int posY1, int posX2, int posY2, int altura,int base, TipoObstaculo tipo) {
        this.posX1 = Math.min(posX1,posX2);
        this.posY1 = Math.min(posY1,posY2);

        this.posX2 = Math.max(posX1,posX2);
        this.posY2 = Math.max(posY1,posY2);

        this.altura = altura;
        this.tipo = tipo;
        this.base = base;
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

    public TipoObstaculo getTipo() {
        return tipo;
    }

    public int getAltura() {
        return altura;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setPosX1(int posX1) {
        this.posX1 = posX1;
    }

    public void setPosY1(int posY1) {
        this.posY1 = posY1;
    }

    public void setPosX2(int posX2) {
        this.posX2 = posX2;
    }

    public void setPosY2(int posY2) {
        this.posY2 = posY2;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }




// FALTA o resto





}
