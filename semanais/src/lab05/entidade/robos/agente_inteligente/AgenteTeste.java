package lab05.entidade.robos.agente_inteligente;

import lab05.Ambiente;
/*
 Classe apenas para teste, não deve ser utilizada no projeto.
 */

public class AgenteTeste extends AgenteInteligente {

    public AgenteTeste(Ambiente ambiente, String nome, int posX, int posY, int posZ) {
        super(ambiente, nome, posX, posY, posZ, null, null);
    }

    @Override
    public void executarMissao() {
        // Implementação específica do agente de teste
        System.out.println("Executando missão de teste para o agente " + getNome());
    }

    @Override
    public void executarTarefa() {
        return;
    }

    @Override
    public String getDescricao() {
        return "";
    }
}
