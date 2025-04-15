package lab03;

import lab03.robos.Robo;
import lab03.sensores.Sensor;

public class Teste {
    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente(100,100,100);
        Robo robo1 = new Robo(ambiente,"teste1", "norte",0,0);
        Robo robo2 = new Robo(ambiente,"teste2", "norte",0,0);
        Sensor sensor1 = new Sensor(10);
        ambiente.adicionarRobo(robo1);
        ambiente.adicionarRobo(robo2);
        robo1.setSensor(sensor1);
        robo1.identificarObstaculos();
    }
}
