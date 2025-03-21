package lab02.robos;

public class Robo {
    private final String nome;
    private int posX;
    private int posY;
    private String direcao;

    public Robo(String nome, int posX, int posY, String direcao) {
        this.nome = nome;
        this.posX = Math.max(posX,0);
        this.posY = Math.max(posY,0);
        this.direcao = direcao;

        if(posX < 0 || posY < 0)
            System.out.println("Coordenadas negativas de "+ getNome() +" foram realocadas para 0");

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

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void mover(int deltaX, int deltaY){
        int newPosX = getPosX() + deltaX;
        setPosX(newPosX);

        int newPosY = getPosY() + deltaY;
        setPosY(newPosY);
    }

    public void exibirPosicao(){
        System.out.printf("O robô %s está na posição (%d, %d)\n", getNome(), getPosX(), getPosY());
    }
}



