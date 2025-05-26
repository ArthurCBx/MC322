package lab04.comunicacao;

import java.util.ArrayList;

// Central de comunicação utilizada para armazenar e exibir mensagens

public class CentralComunicacao {
    private static ArrayList<String> mensagens = new ArrayList<String>();

    public CentralComunicacao() {
    }

    public static void registrarMensagem(String remetente, String msg) {
        mensagens.add(remetente + " enviou: " + msg);
    }

    public static void exibirMensagens() {
        if (!mensagens.isEmpty())
            for (String msg : mensagens) {
                System.out.println(msg);
            }
        else{
            System.out.println("Central de Comunicação não possui mensagens armazenadas");
        }
    }

    public static void deletarMensagens() {
        mensagens.clear();
    }
}
