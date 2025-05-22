package lab04.comunicacao;

import java.util.ArrayList;

public class CentralComunicacao {
    private static ArrayList<String> mensagens = new ArrayList<String>();
    public CentralComunicacao(){}

    public static void registrarMensagem(String remetente, String msg){
        mensagens.add(remetente + " enviou: " + msg);
    }

    public static void exibirMensagens(){
        for (String msg : mensagens) {
            System.out.println(msg);
        }
    }
}
