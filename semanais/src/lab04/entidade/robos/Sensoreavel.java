package lab04.entidade.robos;

import lab04.sensores.Sensor;

import java.util.ArrayList;

// Interface para rebos que possuem sensores

public interface Sensoreavel {

    void acionarSensores();

    ArrayList<Sensor> getSensores();

    void addSensor(Sensor sensor);

}
