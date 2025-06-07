package lab05.missao;

/*
    Classe MissaoMonitorar fornece ao robô a obrigação de utilizar os seus sensores para monitorar o ambiente.
    Se o robô estiver desligado, não tiver um ambiente associado ou não possuir sensores, a missão não poderá ser executada.
    Quando a missão for executada, os dados de execução serão registrados num log.
    Somente Agentes Inteligentes podem executar essa missão.
 */

import lab04.excecoes.SemAmbienteException;
import lab05.Ambiente;
import lab05.entidade.robos.Estado;
import lab05.entidade.robos.Robo;
import lab05.entidade.robos.agente_inteligente.AgenteInteligente;
import lab05.excecoes.RoboDesligadoException;
import lab05.sensores.Sensor;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MissaoMonitorar {

    public void executar(Robo r, Ambiente ambiente) {
        StringBuilder s = new StringBuilder();
        if (r.getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado e não pode monitorar o ambiente.");
        }
        if (r.getAmbiente() == null) {
            throw new SemAmbienteException("O robô não está associado a um ambiente.");
        }
        if (r.getSensores() == null) {
            System.out.println("O robô ainda não possui sensores para completar a missão. Adicione sensor(es) antes de executá-la novamente.");
        }
        if (r instanceof AgenteInteligente) {
            int count = 1;
            ArrayList<Sensor> sensores = r.getSensores();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            s.append("O robô ").append(r.getNome()).append(" começou a monitorar em: ").append(LocalDateTime.now().format(formatter)).append("\n");

            for (Sensor sensor : sensores) {
                s.append("Sensor ").append(count++).append(": \n");
                s.append(sensor.monitorar(ambiente, r));
            }
            s.append("O robô ").append(r.getNome()).append(" finalizou a missão de monitorar em: ").append(LocalDateTime.now().format(formatter)).append("\n");
            ((AgenteInteligente) r).encerrarMissao();
        }else{
            System.out.println("O robô " + r.getNome() + " não é um Agente Inteligente e não pode executar a missão de monitoramento.");
        }
        String file = "semanais/src/lab05/log.txt";
        File logFile = new File(file);

        if(!logFile.exists() || !logFile.isFile()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
