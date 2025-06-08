package lab05.comunicacao;

// Metodo para robos que possuem algum tipo de aparato de comunicação

public interface Comunicavel {
    void enviarMensagem(Comunicavel destinatario, String mensagem);

    void receberMensagem(String mensagem);
}
