package lab02.robos;

public class RoboAereo extends Robo{
    private int altitudeMaxima;
    private int altitude;



    public RoboAereo(String nome, int posX, int posY, String direcao, int altitudeMaxima, int altitude) {
        this.nome = nome;
        this.posX = Math.max(posX,0);
        this.posY = Math.max(posY,0);;
        this.direcao = direcao;
        this.altitudeMaxima = altitudeMaxima;
        this.altitude = altitude;

        if(posX < 0 || posY < 0)
            System.out.println("Coordenadas negativas de "+ nome +" foram realocadas para 0");


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