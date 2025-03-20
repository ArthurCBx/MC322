package lab02.robos;

public class RoboAereo extends Robo{
    private int altitude;
    private int altitudeMaxima;

    public RoboAereo(String nome, int posX, int posY, String direcao, int altitude, int altitudeMaxima) {
        super(nome, posX, posY, direcao);
        this.altitude = altitude;
        this.altitudeMaxima = altitudeMaxima;


        setAltitude(10);
        if(posX < 0 || posY < 0)
            System.out.println("Coordenadas222 negativas de "+ nome +" foram realocadas para 0");


    }

    @Override
    protected void validarEntradas(){

        if(getPosX() < 0 || getPosY() < 0 || getAltitude() < 0 || getAltitude() > getAltitudeMaxima())
            System.out.println("Coordenadas negativas de "+ getNome() +" foram realocadas para 0, ou altitude era maior que maxima e foi realocada para a maxima");

        if(getPosX() < 0)    setPosX(0);
        if(getPosY() < 0)    setPosY(0);

        if(getAltitude() < 0)                       setAltitude(0);
        if(getAltitude() > getAltitudeMaxima())     setAltitude(getAltitudeMaxima());
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