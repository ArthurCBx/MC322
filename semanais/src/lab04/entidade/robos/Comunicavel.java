package lab04.entidade.robos;

public interface Comunicavel {
    void enviarMensagem(Comunicavel destinatario,String mensagem);
    void receberMensagem(String mensagem);
}
