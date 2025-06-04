package lab05.sensores;

import lab05.Ambiente;
import lab05.entidade.Entidade;
import lab05.entidade.TipoEntidade;
import lab05.entidade.robos.Robo;
import lab05.obstaculos.Obstaculo;

import java.util.ArrayList;
import java.util.List;

/* Subclasse de Sensor que herda o metodo de monitorar bidimensionalmente, mas também consegue monitorar
   a altura em um raioZ imediatamente acima ou abaixo da posição do robô ao qual pertence.
 */
public class SensorAltitude extends Sensor {

    private double raioZ;

    // Construtor para sensor:
    public SensorAltitude(double raioZ, double raio) {
        super(raio);
        this.raioZ = Math.abs(raioZ); // RaioZ não pode ser negativo.
    }

    // Getter e Setter:

    public double getRaioZ() {
        return raioZ;
    }

    public void setRaioZ(double raioZ) {
        this.raioZ = Math.abs(raioZ);
    }

    // Sobrescreve as 3 funções desenvolvidas em sensor para considerar agora o raio z:

    @Override
    public void monitorar(Ambiente ambiente, Robo mestre) {
        System.out.println("Monitoramento em XY:");
        super.monitorar(ambiente, mestre);
        System.out.println("Monitoramento em altitude:");
        ArrayList<Entidade> listaEntidades = ambiente.getListaEntidades();
        for (Entidade entidade : listaEntidades) {
            int distancia = Math.abs(entidade.getZ() - mestre.getZ());
            if (distancia <= raioZ && (mestre.getX() == entidade.getX()) && (mestre.getY() == entidade.getY()) && !entidade.equals(mestre)) {
                System.out.printf("Entidade %s encontrada na posição (%d,%d,%d)", entidade.getTipoEntidade().getNome(), entidade.getX(), entidade.getY(), entidade.getZ());
            }
        }

        List<Obstaculo> obstaculos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.OBSTACULO).map(entidade -> (Obstaculo) entidade).toList();
        for (Obstaculo obstaculo : obstaculos) {
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getX() - cX, 2) + Math.pow(mestre.getY() - cY, 2));
            if (distancia == 0) {
                if ((mestre.getZ() >= obstaculo.getBase()) && (mestre.getZ() <= obstaculo.getBase() + obstaculo.getAltura())) {
                    System.out.printf("Robo está dentro do obstáculo %s.\n", obstaculo.getTipoObstaculo().getNome());
                } else if (mestre.getZ() > obstaculo.getBase() + obstaculo.getAltura()) {
                    System.out.printf("Robo está acima do obstáculo %s.\n", obstaculo.getTipoObstaculo().getNome());
                } else if(mestre.getZ() < obstaculo.getBase()) {
                    System.out.printf("Robo está abaixo do obstáculo %s.\n", obstaculo.getTipoObstaculo().getNome());
                }
            }
        }
    }


    // Metodo para retornar um ArrayList com os robôs encontrados pelo sensor.
    @Override
    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre) {
        ArrayList<Robo> listaRobosEncontrados = super.listaRobosEncontrados(ambiente, mestre);
        List<Robo> listaRobos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).toList();

        for (Robo robo : listaRobos) {
            int distancia = Math.abs(robo.getZ() - mestre.getZ());
            if (distancia <= raioZ && (mestre.getX() == robo.getX()) && (mestre.getY() == robo.getY()) && !robo.equals(mestre)) {
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }


    // Metodo para retornar um ArrayList com os obstáculos encontrados pelo sensor.
    @Override
    public ArrayList<Obstaculo> listaObstaculosEncontrados(Ambiente ambiente, Robo mestre) {
        ArrayList<Obstaculo> listaObstaculosEncontrados = super.listaObstaculosEncontrados(ambiente, mestre);
        List<Obstaculo> obstaculos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.OBSTACULO).map(entidade -> (Obstaculo) entidade).toList();
        for (Obstaculo obstaculo : obstaculos){
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getX() - cX, 2) + Math.pow(mestre.getY() - cY, 2));
            int dZ = 0;
            if (distancia == 0) {
                if (mestre.getZ() > (obstaculo.getBase()+obstaculo.getAltura())) {
                    dZ = mestre.getZ() - (obstaculo.getBase()+obstaculo.getAltura());
                } else if(mestre.getZ() < obstaculo.getBase()) {
                    dZ = mestre.getZ() - obstaculo.getBase();
                }

                if (dZ <= raioZ){
                    listaObstaculosEncontrados.add(obstaculo);
                }
            }
        }
        return listaObstaculosEncontrados;
    }
}
