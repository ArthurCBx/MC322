package lab03.robos;

import lab03.Ambiente;

import java.util.ArrayList;
import java.util.List;
/*
Subclasse de Robo que possui os atributos próprios altitude e altitudeMaxima.
Ele possui os métodos subir e descer, que alteram a altitude do robo, verificando se ele não sairá do ambiente.
 */
public class RoboAereo extends Robo {
    private int altitude;
    private int altitudeMaxima;

    public RoboAereo(String nome, int posX, int posY, String direcao, int raioSensor, int altitude, int altitudeMaxima) {
        super(nome, posX, posY, direcao, raioSensor);

        // A altitude é sempre positiva e menor ou igual a altitudeMaxima.
        this.altitudeMaxima = Math.abs(altitudeMaxima);
        this.altitude = Math.max(Math.min(altitude, this.altitudeMaxima), 0);

        if (altitude > altitudeMaxima)
            System.out.printf("Altitude de %s era maior que maxima e foi realocada para a maxima\n", nome);

    }
    // Getters e Setters
    public int getAltitude() {
        return altitude;
    }

    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }

    // Metodo para subir o robo aereo, considerando a altitude maxima e se ele não sairá do ambiente.
    public void subir(int deltaAltitude, Ambiente ambiente) {
        int newAltitude = getAltitude() + Math.max(deltaAltitude, 0);
                                        // Aceita apenas valores positivos para deltaAltitude.

        // Altitute não pode ser maior que altitudeMaxima.
        newAltitude = Math.min(newAltitude, getAltitudeMaxima());

        // Verificando se nova altitude sairá ou não do ambiente.
        if (ambiente.dentroDosLimites(getPosX(), getPosY(), newAltitude))
            setAltitude(newAltitude);
        else
            System.out.printf("O robo %s está saindo do ambiente, operação cancelada\n", getNome());

    }

    // Metodo para descer o robo aereo, verificando se a sua nova altitude não será negativa.
    public void descer(int deltaAltitude, Ambiente ambiente) {
        int newAltitude = getAltitude() - Math.max(deltaAltitude, 0);
                                            // Não aceita valores negativos para deltaAltitude.
        // Altitude não pode ser negativa.
        setAltitude(Math.max(newAltitude, 0));
    }

    @Override
    public void exibirPosicao() {
        // Override para mostrar a sua altitude.
        System.out.printf("O robo aereo %s está na posição: (%d, %d, %d)\n", getNome(), getPosX(), getPosY(), getAltitude());
    }

    @Override
    public List<Robo> identificarRedondezas(int raio, Ambiente ambiente) {
        // Override para considerar agora, além do que já era considerado, a altitude dos robos.
        // Isso porque este robo também pode estar voando e isso deve ser considerado para verificar o seu raio sensor.
        List<Robo> listaRobos = super.identificarRedondezas(raio, ambiente);
        List<Robo> robosEncontrados = new ArrayList<>();

        int altitude;

        for (Robo robo : listaRobos) {

            // Se o robo da iteração for aereo, pega a sua altitude, senão, ele é terrestre e a sua altitude é 0.
            altitude = robo instanceof RoboAereo ? ((RoboAereo) robo).getAltitude() : 0;

            if (Math.abs(getAltitude() - altitude) <= raio)
                robosEncontrados.add(robo);

        }

        return robosEncontrados;

    }

    @Override
    public void identificarObstaculos(Ambiente ambiente) {
    // Override para considerar a altitude dos robos.
        List<Robo> listaRobos = identificarRedondezas(getRaioSensor(), ambiente);

        int altitude;

        for (Robo robo : listaRobos) {

            altitude = robo instanceof RoboAereo ? ((RoboAereo) robo).getAltitude() : 0;

            System.out.printf("Há um obstaculo, %s, em (%d, %d, %d)\n", robo.getNome(), robo.getPosX(), robo.getPosY(), altitude);

        }

    }
}


