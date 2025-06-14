package lab05.missao;

import lab05.Ambiente;
import lab05.entidade.robos.Estado;
import lab05.entidade.robos.Robo;
import lab05.entidade.robos.agente_inteligente.AgenteInteligente;
import lab05.excecoes.ColisaoException;
import lab05.excecoes.RoboDesligadoException;
import lab05.excecoes.SemAmbienteException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
    Classe MissaoBuscarPonto fornece ao robô a obrigação de se mover até determinada coordenada.
    Se o robô estiver desligado ou não tiver um ambiente associado, a missão não poderá ser executada.
    Quando a missão for executada, os dados de execução serão registrados num log.
    Somente Agentes Inteligentes podem executar essa missão.
    Após a sua execução, o robô encerra a missão automaticamente.
 */

public class MissaoBuscarPonto implements Missao{
    private int x;
    private int y;

    public MissaoBuscarPonto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void executar(Robo r, Ambiente a){
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
        if (r.getEstado() == Estado.DESLIGADO) {
            s.append("O robô ").append(r.getNome()).append(" não conseguiu executar sua missão porque está desligado.\n");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
                writer.write(s.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RoboDesligadoException("O robô está desligado e não pode se realizar a busca.");
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

        if (r instanceof AgenteInteligente){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            s.append("O robô ").append(r.getNome()).append(" começou a missão de buscar ponto em: ").append(LocalDateTime.now().format(formatter)).append("\n");
            s.append("Ele tentará se mover até o ponto (").append(x).append(", ").append(y).append(", ").append(r.getZ()).append(")\n");
            try{
                r.moverPara(x,y, r.getZ());
            } catch(ColisaoException e){
                s.append("Ele não conseguiu se mover para o ponto determinado por colidir com um obstáculo.\n");
            }
            s.append("O robô ").append(r.getNome()).append(" finalizou a missão de buscar ponto em: ").append(LocalDateTime.now().format(formatter)).append("\n\n");

            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
                writer.write(s.toString());
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ((AgenteInteligente) r).encerrarMissao();
        }
    }
}
