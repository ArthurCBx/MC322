package lab05;

import lab05.entidade.robos.Robo;
import lab05.entidade.robos.agente_inteligente.AgenteTeste;
import lab05.entidade.robos.robos_aereos.RoboBombardeiro;
import lab05.entidade.robos.robos_aereos.RoboExplosivo;
import lab05.entidade.robos.robos_terrestres.RoboCombustivel;
import lab05.entidade.robos.robos_terrestres.RoboSolar;
import lab05.missao.MissaoComunicar;
import lab05.missao.MissaoMonitorar;
import lab05.sensores.Sensor;
import lab05.sensores.SensorAltitude;
import lab05.sensores.SensorClasse;


public class Teste2 {
    public static void main(String[] args) {
        Ambiente tambiente = new Ambiente(50, 50, 50);
        Robo roboteste1 = new RoboExplosivo(tambiente, "Teste1", 0, 0, 0, 20, 20);
        Robo roboteste2 = new RoboBombardeiro(tambiente, "Teste2", 1, 1, 0, 30, 20);
        Robo roboteste3 = new RoboSolar(tambiente, "Teste3", 2, 2, 30, 40);
        Robo roboteste4 = new RoboCombustivel(tambiente, "Teste4", 3, 3, 30, 200);

        Sensor tsensorComum20 = new Sensor(20);
        SensorAltitude tsensorAltitude80XY20 = new SensorAltitude(80, 20);
        SensorClasse tsensorClasse20 = new SensorClasse(20);

        AgenteTeste agente = new AgenteTeste(tambiente, "AgenteTeste", 4, 4, 0);
        agente.addSensor(tsensorComum20);
        agente.addSensor(tsensorAltitude80XY20);
        agente.addSensor(tsensorClasse20);
        MissaoMonitorar monitora = new MissaoMonitorar();
        MissaoComunicar comunica = new MissaoComunicar();

        try {
            monitora.executar(agente, tambiente);
            comunica.executar(agente, tambiente, roboteste1, "RECEBA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
