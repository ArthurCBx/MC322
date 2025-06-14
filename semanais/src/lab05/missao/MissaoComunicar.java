package lab05.missao;

import lab04.excecoes.ErroComunicacaoException;
import lab05.excecoes.SemAmbienteException;
import lab05.Ambiente;
import lab05.entidade.robos.Estado;
import lab05.entidade.robos.Robo;
import lab05.entidade.robos.agente_inteligente.AgenteInteligente;
import lab05.excecoes.RoboDesligadoException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
    Classe MissaoComunicar fornece ao robô a obrigação de se comunicar com outro robô.
    Se o robô estiver desligado ou não tiver um ambiente associado, a missão não poderá ser executada.
    Quando a missão for executada, os dados de execução serão registrados num log.
    Somente Agentes Inteligentes podem executar essa missão.
    Após a sua execução, o robô encerra a missão automaticamente.
 */

public class MissaoComunicar implements Missao{
    private String mensagem; // Conteúdo da mensagem a ser enviada
    private Robo outroRobo;  // Robo com o qual o robô que possui a missão irá se comunicar

    public MissaoComunicar(String msg, Robo outroRobo) {
        this.mensagem = msg;
        this.outroRobo = outroRobo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Robo getOutroRobo() {
        return outroRobo;
    }

    public void setOutroRobo(Robo outroRobo) {
        this.outroRobo = outroRobo;
    }

    @Override
    public void executar(Robo r, Ambiente a) {
        String file = "semanais/src/lab05/log.txt";
        File logFile = new File(file);

        if(!logFile.exists() || !logFile.isFile()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        StringBuilder s = new StringBuilder();
        if (r.getEstado() == Estado.DESLIGADO ) {
            s.append("O robô ").append(r.getNome()).append(" não conseguiu executar sua missão porque está desligado.\n");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
                writer.write(s.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RoboDesligadoException("O robô está desligado e não pode se comunicar.");
        }

        if (r.getAmbiente() == null) {
            s.append("O robô ").append(r.getNome()).append(" não conseguiu executar sua missão porque não está associado a um ambiente.\n");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
                writer.write(s.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new SemAmbienteException("O robô não está associado a um ambiente.");
        }

        if (outroRobo.getEstado() == Estado.DESLIGADO){
            s.append("Houve uma falha na comunicação com o robô ").append(outroRobo.getNome()).append(" porque ele está desligado.\n");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
                writer.write(s.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new ErroComunicacaoException("Houve uma falha na comunicação com o robô " + outroRobo.getNome() + " porque ele está desligado.");
        }

        if(r instanceof AgenteInteligente){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            s.append("O robô ").append(r.getNome()).append(" começou a missão de comunicação em: ").append(LocalDateTime.now().format(formatter)).append("\n");
            r.enviarMensagem(outroRobo, mensagem);
            s.append("Ele enviou a mensagem: \n").append(mensagem).append(" para o robô ").append(outroRobo.getNome()).append("\n");
            s.append("O robô ").append(r.getNome()).append(" finalizou a missão de comunicação em: ").append(LocalDateTime.now().format(formatter)).append("\n\n");
            ((AgenteInteligente) r).encerrarMissao();
        }else {
            s.append("O robô ").append(r.getNome()).append(" não é um Agente Inteligente e não pode executar a missão de comunicação.\n");

        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(s.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
