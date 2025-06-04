package lab05.entidade.robos.agente_inteligente;

import lab05.Ambiente;
import lab05.entidade.robos.Robo;
import lab05.missao.Missao;

public abstract class AgenteInteligente extends Robo {
    protected Missao missao;

    public AgenteInteligente(Ambiente ambiente, String nome, int posX, int posY, int posZ) {
        super(ambiente,nome, posX, posY, posZ);
    }

    public void setMissao(Missao missao){
        this.missao = missao;
    }

    public boolean temMissao(){
        return this.missao != null;
    }

    public abstract void executarMissao();
}
