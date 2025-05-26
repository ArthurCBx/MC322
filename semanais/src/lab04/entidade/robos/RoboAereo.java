package lab04.entidade.robos;

import lab04.Ambiente;
import lab04.Main;
import lab04.excecoes.ColisaoException;
import lab04.excecoes.ForaDosLimitesException;
import lab04.excecoes.RoboDesligadoException;
import lab04.excecoes.SemAmbienteException;
import lab04.obstaculos.Obstaculo;

import java.util.ArrayList;

/*
Subclasse de Robo que possui os atributos próprios altitude e altitudeMaxima.
Ele possui os métodos subir e descer, que alteram a altitude do robo, verificando se ele não sairá do ambiente.
 */
public abstract class RoboAereo extends Robo {
    private int altitudeMaxima;

    // Metodo construtor para Robo Aereo

    public RoboAereo(Ambiente ambiente, String nome, int posX, int posY, int posZ, int altitudeMaxima) {
        super(ambiente, nome, posX, posY, Math.min(posZ, Math.abs(altitudeMaxima)));

        // A posZ é sempre positiva e menor ou igual a altitudeMaxima.
        this.altitudeMaxima = Math.abs(altitudeMaxima);

        // Verificando se a posZ do robô não é maior que a altura do ambiente.
        if (posZ > ambiente.getAltura()) {
            System.out.printf("Altitude de %s era maior que a altura do ambiente e foi realocada para a altura do ambiente.\n", nome);
        }


        if (posZ > altitudeMaxima)
            System.out.printf("Altitude de %s era maior que maxima e foi realocada para a maxima\n", nome);

    }


    // Getter e Setter

    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }

    // Metodo mover precisa considerar os limites de altitude e chão (entre 0 e altitude maxima do robo)
    @Override
    public void moverPara(int x, int y, int z) {
        super.moverPara(x, y, Math.max(Math.min(z, getAltitudeMaxima()), 0));
    }

}


