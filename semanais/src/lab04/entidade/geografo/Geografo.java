package lab04.entidade.geografo;

/*
 Classe Geografo que implementa a interface Entidade.
 Ela será capaz de observar o ambiente e descrever os seus limites X, Y, Z. Além disso, ela identificará os obstáculos que estão dentro do ambiente,
 trazendo a descrição de cada um deles.
 */

import lab04.Ambiente;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.obstaculos.Obstaculo;
import java.util.List;

public class Geografo implements Entidade {
    private int posX;
    private int posY;
    private final int posZ = 0;
    private final Ambiente ambiente;
    private final TipoEntidade tipoEntidade = TipoEntidade.GEOGRAFO;

    public Geografo(int posX, int posY, Ambiente ambiente){
        this.posX = Math.min(Math.max(posX,0), ambiente.getComprimento());
        this.posY = Math.min(Math.max(posY,0), ambiente.getLargura());
        this.ambiente = ambiente; ambiente.adicionarEntidade(this);

        if(posX != this.posX || posY != this.posY)
            System.out.println("Posicao do geografo foi realocada para dentro do ambiente");
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
        return "Entidade geografO capaz de observar o ambiente e verificar obstáculos\n" +
                "Posicao atual: ("+ getX() +", "+ getY() +", "+ getZ() +")\n";
    }

    public char getRepresentacao() {
        return 'G';
    }

    public void mover(int deltaX, int deltaY) {
    }

    /**
     Metodo que observa o ambiente, retornando uma série de comentários com a descrição dos limites do ambiente e os obstáculos presentes.*/
    public void observar(){
        System.out.printf("Limites do ambiente: (%d, %d, %d)\n", ambiente.getComprimento(), ambiente.getLargura(), ambiente.getAltura());
        List<Obstaculo> obstaculos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.OBSTACULO).map(entidade -> (Obstaculo) entidade).toList();
        if(obstaculos.isEmpty()){
            System.out.println("Nenhum obstáculo encontrado.");
        }else{
            for(Obstaculo obstaculo : obstaculos){
            System.out.printf(obstaculo.getDescricao());
            }
        }
    }
}
