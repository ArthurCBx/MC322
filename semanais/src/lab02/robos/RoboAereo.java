package lab02.robos;

public class RoboAereo extends Robo{
    private int altitude;
    private int altitudeMaxima;

    public RoboAereo(String nome,  int posX, int posY, String direcao, int altitude, int altitudeMaxima) {
        super(nome, posX, posY, direcao);
        this.altitude = Math.max(Math.min(altitude,altitudeMaxima),0);
        this.altitudeMaxima = Math.abs(altitudeMaxima);

        if(posX < 0 || posY < 0)
            System.out.println("Altitude de "+ nome +" era maior que maxima e foi realocada para a maxima");


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
        int newAltitude = getAltitude() + Math.abs(deltaAltitude);

        setAltitude(Math.min(newAltitude,altitudeMaxima));
    /*
        if (newAltitude <= altitudeMaxima){
            setAltitude(newAltitude);
        }else{
            setAltitude(getAltitudeMaxima());
        }
    */
    }


    @Override
    public void exibirPosicao() {
        System.out.printf("O robo aereo %s está na posição: (%d, %d, %d)\n", getNome(), getPosX(), getPosY(), getAltitude());

    }

    public void descer(int deltaAltitude){
        int newAltitude = getAltitude() - Math.abs(deltaAltitude);

        setAltitude(Math.max(newAltitude,0));
    /*
        if (newAltitude >= 0){
            setAltitude(newAltitude);
        }else{
            setAltitude(0);
        }
    */
    }

}