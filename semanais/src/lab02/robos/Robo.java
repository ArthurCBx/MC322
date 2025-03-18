package lab02.robos;

public class Robo {
    private final String nome;
    private int posX;
    private int posY;

    // No classroom nao e definido se e pra criar um atributo velocidade
    private int velocidade;

    private String direcao;

    public Robo(String nome, int posX, int posY, String direcao) {
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
        this.direcao = direcao;
        this.velocidade = 0;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
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
        System.out.println("O robô " + getNome() + " está na posição (" + getPosX() + ", " + getPosY() + ")");
    }
}



