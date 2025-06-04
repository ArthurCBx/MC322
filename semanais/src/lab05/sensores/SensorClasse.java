package lab05.sensores;

import lab05.Ambiente;
import lab05.entidade.TipoEntidade;
import lab05.entidade.robos.Robo;
import lab05.obstaculos.Obstaculo;

import java.util.ArrayList;
import java.util.List;

public class SensorClasse extends Sensor {
    // Subclasse de Sensor que consegue descobrir a classe de robos num raio tridimensional, mas não sabe sobre outras entidades.


    // Construtor para sensor:

    public SensorClasse(double raio) {
        super(raio);
    }

    @Override
    public void monitorar(Ambiente ambiente, Robo mestre){
        List<Robo> listaRobos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).toList();

        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt((Math.pow(robo.getX() - mestre.getX(), 2) + Math.pow(robo.getY() - mestre.getY(), 2) + Math.pow(robo.getZ() - mestre.getZ(), 2)));
            if (distancia <= getRaio() && !robo.equals(mestre)){
                System.out.printf("Robo da classe %s foi encontrado na posicao (%d, %d, %d).\n", robo.getClass(), robo.getX(), robo.getY(), robo.getZ());
            }
        }
    }

    @Override
    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre) {
        List<Robo> listaRobos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).toList();
        ArrayList<Robo> listaRobosEncontrados = new ArrayList<>();

        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt((Math.pow(robo.getX() - mestre.getX(), 2) + Math.pow(robo.getY() - mestre.getY(), 2) + Math.pow(robo.getZ() - mestre.getZ(), 2)));
            if (distancia <= getRaio() && !robo.equals(mestre)) {
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }

    @Override
    public ArrayList<Obstaculo> listaObstaculosEncontrados(Ambiente ambiente, Robo mestre) {
        //SensorClasse não consegue identificar obstáculos.
        return null;
    }
}
