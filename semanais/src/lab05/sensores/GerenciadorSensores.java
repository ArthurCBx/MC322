package lab05.sensores;

import lab05.entidade.obstaculos.Obstaculo;
import lab05.entidade.robos.Estado;
import lab05.entidade.robos.Robo;
import lab05.excecoes.RoboDesligadoException;
import lab05.excecoes.SemAmbienteException;

import java.util.ArrayList;

public class GerenciadorSensores {
    private Robo robo;

    public GerenciadorSensores(Robo robo) {
        this.robo = robo;
    }

    public void acionarSensores(){
        int count = 1;
        if (robo.getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }
        if (robo.getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (robo.getSensores() == null) {
            System.out.println("O robô solicitado não possui sensores");
            return;
        }
        for (Sensor sensor : robo.getSensores()) {   // Para todos os sensores:
            System.out.printf("O sensor %d está monitorando o ambiente %s\n", count, robo.getAmbiente());
            sensor.monitorar(robo.getAmbiente(), robo);
            System.out.println();
            count++;
        }
        System.out.println("Monitoramento finalizado.");
    }

    public ArrayList<Robo> identificarRobosProximos(){
        if (robo.getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }
        if (robo.getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (robo.getSensores() == null) {
            System.out.println("O robô solicitado não possui sensores");
            return null;
        }
        ArrayList<Robo> robosProximos = new ArrayList<>();
        for (Sensor sensor : robo.getSensores()) {
            ArrayList<Robo> identificados = sensor.listaRobosEncontrados(robo.getAmbiente(), robo);
            for (Robo robo : identificados) {
                if (!robosProximos.contains(robo)) {
                    robosProximos.add(robo);
                }
            }
        }
        return robosProximos;
    }

    public ArrayList<Obstaculo> identificarObstaculosProximos(){
        if (robo.getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado. Não pode executar qualquer ação.");
        }
        if (robo.getAmbiente() == null) {
            throw new SemAmbienteException("O robô não se encontra em um ambiente.");
        }
        if (robo.getSensores() == null) {
            System.out.println("O robô solicitado não possui sensores");
            return null;
        }

        ArrayList<Sensor> sensores = robo.getSensores();
        ArrayList<Obstaculo> obstaculosProximos = new ArrayList<>();
        // Para cada sensor diferente de sensorClasse, lista obstáculos encontrados e adiciona na lista de obstáculos próximos os que ainda não estão.
        for (Sensor sensor : sensores) {
            if (sensor.getClass() != SensorClasse.class) {
                ArrayList<Obstaculo> obstaculoSensor = sensor.listaObstaculosEncontrados(robo.getAmbiente(), robo);

                for (Obstaculo obstaculo : obstaculoSensor) {
                    if (!obstaculosProximos.contains(obstaculo)) {
                        obstaculosProximos.add(obstaculo);
                    }
                }
            }
        }
        return obstaculosProximos;
    }
}
