package lab04.sensores;

import lab04.Ambiente;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.obstaculos.Obstaculo;
import lab04.entidade.robos.Robo;

import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private double raio;

    // Construtor para sensor:
    public Sensor(double raio) {
        this.raio = Math.abs(raio); // Raio não pode ser negativo.
    }


    // Getter e Setter:

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = Math.abs(raio);
    }


    // Função basica de monitoramento do ambiente:
    public void monitorar(Ambiente ambiente, Robo mestre) {
        // Verifica no ambiente se existe algum robo dentro do raio do sensor (XY)
        ArrayList<Entidade> listaEntidade = ambiente.getListaEntidades();
        ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>();

        for (Entidade entidade : listaEntidade){
            if (entidade.getTipoEntidade() == TipoEntidade.OBSTACULO) obstaculos.add((Obstaculo) entidade);
        }

        for (Entidade entidade : listaEntidade) {
            double distancia = Math.sqrt(Math.pow(entidade.getX() - mestre.getX(), 2) + Math.pow(entidade.getY() - mestre.getY(), 2));
            if (distancia <= raio && !entidade.equals(mestre) && (entidade.getZ() == mestre.getZ())) {
                System.out.printf("Entidade %s encontrada na posição (%d,%d)\n",entidade.getTipoEntidade().getNome(), entidade.getX(), entidade.getY());
            }
        }

        // Verifica se algum obstáculo está dentro do raio do sensor ou se o robô está dentro do obstáculo
        for (Obstaculo obstaculo : obstaculos) {
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getX() - cX,2) + Math.pow(mestre.getY() - cY,2));
            if ((mestre.getZ() >= obstaculo.getBase()) && (mestre.getZ() <= (obstaculo.getBase() + obstaculo.getAltura()) )){
                if (distancia == 0) {
                    System.out.printf("Robô está dentro do obstáculo %s\n", obstaculo.getTipoObstaculo().getNome());
                } else if (distancia <= raio) {
                    System.out.printf("Obstáculo %s encontrado a %.2f metros\n", obstaculo.getTipoObstaculo().getNome(), distancia);
                }
            }
        }
    }

    // Função para retornar listas de robôs se estão dentro do raio(XY):
    public ArrayList<Robo> listaRobosEncontrados(Ambiente ambiente, Robo mestre){
        List<Robo> listaRobos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).toList();
        ArrayList<Robo> listaRobosEncontrados = new ArrayList<>();

        for (Robo robo : listaRobos) {
            double distancia = Math.sqrt(Math.pow(robo.getX() - mestre.getX(), 2) + Math.pow(robo.getY() - mestre.getY(), 2));
            if (distancia <= raio && !robo.equals(mestre) && (robo.getZ() == mestre.getZ())) {
                listaRobosEncontrados.add(robo);
            }
        }
        return listaRobosEncontrados;
    }

    // Função para retornar listas de obstáculos se estão dentro do raio(XY):
    public ArrayList<Obstaculo> listaObstaculosEncontrados(Ambiente ambiente, Robo mestre){
        List<Obstaculo> obstaculos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.OBSTACULO).map(entidade -> (Obstaculo) entidade).toList();
        ArrayList<Obstaculo> listaObstaculosEncontrados = new ArrayList<>();

        for (Obstaculo obstaculo : obstaculos) {
            int cX = Math.max(obstaculo.getPosX1(), Math.min(mestre.getX(), obstaculo.getPosX2()));
            int cY = Math.max(obstaculo.getPosY1(), Math.min(mestre.getY(), obstaculo.getPosY2()));

            double distancia = Math.sqrt(Math.pow(mestre.getX() - cX,2) + Math.pow(mestre.getY() - cY,2));
            if (distancia <= raio && (mestre.getZ() >= obstaculo.getBase()) && (mestre.getZ() <= obstaculo.getBase() + obstaculo.getAltura())){
                // Verifica se o robô está dentro do obstáculo ou consegue ver o obstáculo na mesma altura em que está
                listaObstaculosEncontrados.add(obstaculo);
            }
        }
        return listaObstaculosEncontrados;
    }

}
