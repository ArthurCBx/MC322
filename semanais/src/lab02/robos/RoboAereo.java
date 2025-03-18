package lab02.robos;

public class RoboAereo extends Robo{
    private int altitudeMaxima;
    private int altitude;

    public RoboAereo(String nome, int posX, int posY, String direcao, int altitudeMaxima, int altitude) {
        super(nome, posX, posY, direcao);
        this.altitudeMaxima = altitudeMaxima;
        this.altitude = altitude;
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

    public void subir(int deltaAltitude){
        int newAltitude = getAltitude() + deltaAltitude;
        if (newAltitude <= altitudeMaxima){
            setAltitude(newAltitude);
        }else{
            setAltitude(getAltitudeMaxima());
        }
    }

    public void descer(int deltaAltitude){
        int newAltitude = getAltitude() - deltaAltitude;
        if (newAltitude >= 0){
            setAltitude(newAltitude);
        }else{
            setAltitude(0);
        }
    }
}