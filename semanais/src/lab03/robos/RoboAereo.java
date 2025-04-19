package lab03.robos;

import lab03.Ambiente;
import lab03.obstaculos.Obstaculo;

import java.util.ArrayList;
import java.util.List;
/*
Subclasse de Robo que possui os atributos próprios altitude e altitudeMaxima.
Ele possui os métodos subir e descer, que alteram a altitude do robo, verificando se ele não sairá do ambiente.
 */
public class RoboAereo extends Robo {
    private int altitudeMaxima;

    // Metodos contrutores para Robo Aereo
    // Um recebe o ambiente como parametro e outro apenas cria um robo sem ambiente

    public RoboAereo(String nome, String direcao, int posX, int posY, int altitude, int altitudeMaxima) {
        super(nome, direcao, posX, posY);

        // A altitude é sempre positiva e menor ou igual a altitudeMaxima.
        this.altitudeMaxima = Math.abs(altitudeMaxima);
        this.altitude = Math.max(Math.min(altitude, this.altitudeMaxima), 0);

        if (altitude > altitudeMaxima)
            System.out.printf("Altitude de %s era maior que maxima e foi realocada para a maxima\n", nome);

    }

    public RoboAereo(Ambiente ambiente, String nome, String direcao, int posX, int posY, int altitude, int altitudeMaxima) {
        super(ambiente, nome, direcao, posX, posY);

        // A altitude é sempre positiva e menor ou igual a altitudeMaxima.
        this.altitudeMaxima = Math.abs(altitudeMaxima);
        this.altitude = Math.max(Math.min(altitude, this.altitudeMaxima), 0);

        if (altitude > altitudeMaxima)
            System.out.printf("Altitude de %s era maior que maxima e foi realocada para a maxima\n", nome);

    }


    // Getters e Setters

    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }


    // Metodo para subir o robo aereo, considerando a altitude maxima e se ele não sairá do ambiente e não colide com obstaculos.
    public void subir(int deltaAltitude) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode subir.\n", getNome());
            return;
        }

        // Aceita apenas valores positivos para deltaAltitude:
        int finalAlt = getAltitude() + Math.max(deltaAltitude, 0);

        // Altitute não pode ser maior que altitudeMaxima:
        finalAlt = Math.min(finalAlt, getAltitudeMaxima());

        // Verificando se nova altitude sairá ou não do ambiente.
        if (!getAmbiente().dentroDosLimites(getPosX(), getPosY(), finalAlt)){
            System.out.printf("O robo %s está saindo do ambiente %s, operação cancelada\n", getNome(), getAmbiente());
            return;
        }

        ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarObstaculos(getPosX(),getPosY(),getAltitude(),getPosX(),getPosY(),finalAlt);

        double vetorsubir = 0.25;

        double newAlt = getAltitude();

        for(double newTempAlt = getAltitude() + vetorsubir; newAlt == finalAlt; newTempAlt = Math.min(finalAlt, newAlt + vetorsubir)) {

            for (Obstaculo obstaculo : obstaculosPresentes)
                if (obstaculo.getTipo().bloqueiaPassagem() && obstaculo.contemPonto(getPosX(), getPosY(), newTempAlt)) {
                    setAltitude((int) newAlt);
                    System.out.printf("O robo %s colidiu com um obstáculo %s e foi realocado para a posição (%d, %d, %d)\n", getNome(), obstaculo.getTipo().getNome(), getPosX(), getPosY(),getAltitude());
                    return;
                }

            newAlt = newTempAlt;
        }

        setAltitude((int) newAlt);

    }

    // Metodo para descer o robo aereo, verificando se a sua nova altitude não será negativa e não colide com obstaculos.
    public void descer(int deltaAltitude) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode descer.\n", getNome());
            return;
        }

        // Não aceita valores negativos para deltaAltitude:
        int finalAlt = getAltitude() - Math.max(deltaAltitude, 0);

        // Altitude não pode ser negativa:
        finalAlt = Math.max(finalAlt, 0);


        ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarObstaculos(getPosX(),getPosY(),0,getPosX(),getPosY(),getAltitude());

        double vetordescer = 0.25;

        double newAlt = getAltitude();

        for(double newTempAlt = getAltitude() - vetordescer; newAlt == finalAlt; newTempAlt = Math.max(finalAlt, newAlt - vetordescer)) {

            for (Obstaculo obstaculo : obstaculosPresentes)
                if (obstaculo.getTipo().bloqueiaPassagem() && obstaculo.contemPonto(getPosX(), getPosY(), newTempAlt)) {
                    setAltitude((int) newAlt);
                    System.out.printf("O robo %s colidiu com um obstáculo %s e foi realocado para a posição (%d, %d, %d)\n", getNome(), obstaculo.getTipo().getNome(), getPosX(), getPosY(),getAltitude());
                    return;
                }

            newAlt = newTempAlt;
        }

        setAltitude((int) newAlt);

    }



}


