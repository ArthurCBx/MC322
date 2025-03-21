package lab02.robos;

import lab02.Ambiente;

import java.util.List;

public class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;

    public RoboAereo(String nome, int posX, int posY, String direcao, int raioSensor, int altitude, int altitudeMaxima) {
        super(nome, posX, posY, direcao, raioSensor);
        this.altitude = Math.max(Math.min(altitude, altitudeMaxima), 0);
        this.altitudeMaxima = Math.abs(altitudeMaxima);

        if (posX < 0 || posY < 0)
            System.out.printf("Altitude de %s era maior que maxima e foi realocada para a maxima", nome);

    }


    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public void subir(int deltaAltitude, Ambiente ambiente) {
        int newAltitude = getAltitude() + Math.max(deltaAltitude, 0);

        newAltitude = Math.min(newAltitude, getAltitudeMaxima());

        if (ambiente.dentroDosLimites(getPosX(), getPosY(), newAltitude)) {
            setAltitude(newAltitude);

        } else {
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada", getNome());
        }

        if (altitude < 0) System.out.println("deltaAltitude de " + getAltitude() + " era negativa e virou 0");

    }

    public void descer(int deltaAltitude) {
        int newAltitude = getAltitude() - Math.max(deltaAltitude, 0);

        setAltitude(Math.max(newAltitude, 0));

        if (altitude < 0) System.out.printf("deltaAltitude de %s era negativa e virou 0\n", getNome());

    }

    @Override
    public void exibirPosicao() {
        System.out.printf("O robo aereo %s está na posição: (%d, %d, %d)\n", getNome(), getPosX(), getPosY(), getAltitude());

    }

    @Override
    public void IdentificarObstaculos(Ambiente ambiente) {

        List<Robo> listaRobos = ambiente.getListaRobos();

        for (Robo robo : listaRobos)

            if (robo.getClass() == RoboAereo.class) {
                if (Math.abs(getAltitude() - ((RoboAereo) robo).getAltitude()) <= getRaioSensor())
                    if (Math.abs(getPosX() - robo.getPosX()) <= getRaioSensor())
                        if (Math.abs(getPosY() - robo.getPosY()) <= getRaioSensor())
                            System.out.printf("Há um obstaculo, %s, em (%d, %d, %d)\n", robo.getNome(), robo.getPosX(), robo.getPosY(), ((RoboAereo) robo).getAltitude());
            } else {
                if (getAltitude() <= getRaioSensor()) if (Math.abs(getPosX() - robo.getPosX()) <= getRaioSensor())
                    if (Math.abs(getPosY() - robo.getPosY()) <= getRaioSensor())
                        System.out.printf("Há um obstaculo, %s, em (%d, %d, 0)\n", robo.getNome(), robo.getPosX(), robo.getPosY());

            }

    }
}


