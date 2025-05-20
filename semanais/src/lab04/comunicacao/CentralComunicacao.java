package lab04.comunicacao;

import java.util.ArrayList;

public class CentralComunicacao {
    private ArrayList<String> mensagens = new ArrayList<String>();
    public CentralComunicacao(){}

    public void registrarMensagem(String remetente, String msg){
        mensagens.add(remetente + ": " + msg);
    }
    public void exibirMensagens(){
        for (String msg : mensagens) {
            System.out.println(msg);
        }
    }
}
