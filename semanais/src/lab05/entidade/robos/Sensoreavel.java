package lab05.entidade.robos;

import lab05.sensores.Sensor;

import java.util.ArrayList;

// Interface para rebos que possuem sensores

public interface Sensoreavel {

    void acionarSensores();

    ArrayList<Sensor> getSensores();

    void addSensor(Sensor sensor);

}
