package lab05.entidade.robos.agente_inteligente;

import lab05.Ambiente;
import lab05.comunicacao.ModuloComunicacao;
import lab05.entidade.robos.ControleMovimento;
import lab05.entidade.robos.Robo;
import lab05.missao.Missao;
import lab05.sensores.GerenciadorSensores;

public abstract class AgenteInteligente extends Robo {
    protected Missao missao;

    public AgenteInteligente(Ambiente ambiente, String nome, int posX, int posY, int posZ, ModuloComunicacao comunicacao, GerenciadorSensores gerenciadorSensores, ControleMovimento controleMovimento) {
        super(ambiente,nome, posX, posY, posZ, comunicacao, gerenciadorSensores, controleMovimento);
    }

    public void setMissao(Missao missao){
        this.missao = missao;
    }

    public void encerrarMissao(){
        setMissao(null);
    }

    public boolean temMissao(){
        return this.missao != null;
    }

    public abstract void executarMissao();
}
