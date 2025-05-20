package lab04.entidade.escavador;

/*
 Nova classe que implementará a interface Entidade.
 Ela será capaz de escavar o solo, adicionando novos obstáculos do tipo BURACO no ambiente.
 Esse tipo de obstáculo é pontual, ou seja, X1 = X2 e Y1 = Y2.
 A profundidade do buraco será 0 para não alterar propriedades do ambiente.
 O obstáculo não bloqueará a passagem de outras entidades que se movem.
 */

import lab04.Ambiente;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.obstaculos.Obstaculo;
import lab04.obstaculos.TipoObstaculo;

public class Escavador implements Entidade {
    private int posX;
    private int posY;
    private final Ambiente ambiente;
    private final int posZ = 0;
    private final TipoEntidade tipoEntidade = TipoEntidade.ESCAVADOR;

    public Escavador(int posX, int posY, Ambiente ambiente){
        this.posX = Math.min(Math.max(posX,0), ambiente.getComprimento());
        this.posY = Math.min(Math.max(posY,0), ambiente.getLargura());
        this.ambiente = ambiente; ambiente.adicionarEntidade(this);

        if(posX != this.posX || posY != this.posY)
            System.out.println("Posicao do escavador foi realocada para dentro do ambiente");
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

    public int getZ() {
        return 0;
    }

    public void setX(int x) {
        this.posX = x;
    }
    public void setY(int y) {
        this.posY = y;
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public String getDescricao() {
        return "Entidade escavadora capaz de criar buracos no solo\n" +
                "Posicao atual: ("+ getX() +", "+ getY() +", "+ getZ() +")\n";
    }

    public char getRepresentacao() {
        return 'E';
    }

    /**
     Metodo que escava o solo, adicionando um novo obstáculo do tipo BURACO no ambiente.
     A ação é realizada na posição atual da entidade e cria um obstáculo pontual.*/
    public void escavar() {
        Obstaculo buraco = new Obstaculo(getX(),getY(),getX(),getY(),0,0, TipoObstaculo.BURACO);
        ambiente.adicionarEntidade(buraco);
    }

    public void mover(int x, int y) {

    }

}
