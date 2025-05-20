package lab04.entidade.ladrao;

/*
 Classe Ladrao que implementa a interface Entidade.
 Ela será capaz de se mover e roubar sensores de robos que estejam no solo.
 Para isso, ela deve se mover até a exata posição em que um robô está.
 Só será capaz de roubar um sensor por vez.
 */

import lab04.Ambiente;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.entidade.robos.Robo;
import lab04.obstaculos.Obstaculo;

import java.util.List;

public class Ladrao implements Entidade {
    private int posX;
    private int posY;
    private final int posZ = 0;
    private final TipoEntidade tipoEntidade = TipoEntidade.LADRAO;
    private final Ambiente ambiente;

    public Ladrao(int posX, int posY, Ambiente ambiente){
        this.posX = Math.min(Math.max(posX,0), ambiente.getComprimento());
        this.posY = Math.min(Math.max(posY,0), ambiente.getLargura());
        this.ambiente = ambiente; ambiente.adicionarEntidade(this);

        if(posX != this.posX || posY != this.posY)
            System.out.println("Posicao do ladrao foi realocada para dentro do ambiente");

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

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public String getDescricao() {
        return "Entidade Ladrao capaz de roubar sensores de robôs no solo\n" +
                "Posicao atual: ("+ getX() +", "+ getY() +", "+ getZ() +")\n";
    }

    public char getRepresentacao() {
        return 'L';
    }

    public void mover(int x, int y) {

    }

    /** Metodo que permite ao ladrão roubar um único sensor de um robô.
        Ele exige que as duas entidades estejam na mesma posição (x, y, z).
        Caso o robô tenha sensores, o ladrão remove o primeiro sensor da lista de sensores.
     */
    public void roubar(){
        List<Robo> robos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).toList();
        for (Robo robo : robos){
            if(robo.getX() == getX() && robo.getY() == getY() && robo.getZ() == getZ()){
                if (!robo.getSensores().isEmpty()) {
                    System.out.printf("Ladrão roubou um sensor %s do robô %s\n",robo.getSensores().getFirst() ,robo.getNome());
                    robo.getSensores().removeFirst();
                    return;
                }
            }
        }
    }

}
