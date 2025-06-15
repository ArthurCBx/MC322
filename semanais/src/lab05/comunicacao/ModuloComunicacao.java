package lab05.comunicacao;

import lab05.entidade.robos.Estado;
import lab05.entidade.robos.Robo;
import lab05.excecoes.ErroComunicacaoException;

/*
  Classe que implementa o módulo de comunicação dos robôs.
  Permite o envio e recebimento de mensagens entre robôs.
  Robôs agora possuem um módulo de comunicação por propriedade passado no construtor.
 */

public class ModuloComunicacao implements Comunicavel {
    private Robo robo;

    public ModuloComunicacao(Robo robo) {
        this.robo = robo;
    }

    public void setRobo(Robo robo) {
        this.robo = robo;
    }

    // Metodo para enviar mensagem a outro robo
    @Override
    public void enviarMensagem(Comunicavel destinatario, String mensagem) {
        if (robo.getEstado() == Estado.DESLIGADO) {
            throw new ErroComunicacaoException("Mensagem não enviada. O robô que tentou enviar está desligado.");
        }
        if (((Robo) (destinatario)).getEstado() == Estado.DESLIGADO) {
            throw new ErroComunicacaoException("Mensagem não enviada. O robô destinatário está desligado.");
        }

        String remetente = String.format("%s(id:%s)", robo.getNome(), robo.getId());
        CentralComunicacao.registrarMensagem(remetente, mensagem);

        ((Robo) destinatario).receberMensagem(mensagem);
    }

    // Metodo para o recebimento de mensagens e leitura
    @Override
    public void receberMensagem(String mensagem) {
        // Não exige verificação de estado, pois o robô que enviou a mensagem já fez a verificação.
        System.out.printf("Robô %s recebeu a seguinte mensagem:%s\n", robo.getNome(), mensagem);
    }

}
