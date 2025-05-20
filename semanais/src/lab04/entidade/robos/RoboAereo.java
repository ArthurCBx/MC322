package lab04.entidade.robos;

import lab04.Ambiente;
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
public abstract class RoboAereo extends Robo{
    private int altitudeMaxima;

    // Metodos construtores para Robo Aereo

    public RoboAereo(Ambiente ambiente, String nome, int posX, int posY, int altitude, int altitudeMaxima) {
        super(ambiente, nome, posX, posY);

        // A altitude é sempre positiva e menor ou igual a altitudeMaxima.
        this.altitudeMaxima = Math.abs(altitudeMaxima);
        int altura = Math.max(Math.min(altitude, this.altitudeMaxima), 0);

        // Verificando se a altitude do robô não é maior que a altura do ambiente.
        if (altura > ambiente.getAltura()){
            System.out.printf("Altitude de %s era maior que a altura do ambiente e foi realocada para a altura do ambiente.\n", nome);
            this.altitude = ambiente.getAltura();
        } else {
            this.altitude = altura;
        }

        if (altitude > altitudeMaxima)
            System.out.printf("Altitude de %s era maior que maxima e foi realocada para a maxima\n", nome);

    }


    // Getter e Setter

    public int getAltitudeMaxima() {
        return altitudeMaxima;
    }

    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;
    }


    // Metodo para subir o robo aereo, considerando a altitude maxima e se ele não sairá do ambiente e não colide com obstáculos.
    // É implementado o mesmo metodo de dividir o movimento em pequenas partes utilizado em "mover" do robo, apenas unidirecional

    public void subir(int deltaAltitude) {
        if (getEstado() == Estado.DESLIGADO){
            throw new RoboDesligadoException("O robô está desligado, não pode se mover.");
        }
        if (getAmbiente() == null){
            throw new SemAmbienteException("O robo não está em um ambiente, não pode subir.");
        }

        // Aceita apenas valores positivos para deltaAltitude:
        int finalAlt = getZ() + Math.max(deltaAltitude, 0);

        // Altitute não pode ser maior que altitudeMaxima:
        finalAlt = Math.min(finalAlt, getAltitudeMaxima());

        // Verificando se nova altitude sairá ou não do ambiente.
        if (!getAmbiente().dentroDosLimites(getX(), getY(), finalAlt)){
            throw new ForaDosLimitesException("O robo está saindo do ambiente, operação cancelada");
        }

        ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarObstaculos(getX(),getY(),getZ(),getX(),getY(),finalAlt);

        double vetorsubir = 0.25;

        double newAlt = getZ();

        for(double newTempAlt = getZ() + vetorsubir; newAlt != finalAlt; newTempAlt = Math.min(finalAlt, newAlt + vetorsubir)) {

            for (Obstaculo obstaculo : obstaculosPresentes)
                if (obstaculo.getTipoObstaculo().bloqueiaPassagem() && obstaculo.contemPonto(getX(), getY(), newTempAlt)) {
                    setAltitude((int)newAlt);
                    throw new ColisaoException("O robo colidiu com um obstáculo "+obstaculo.getTipoObstaculo()+" e foi realocado para a posição ("+getX()+","+getY()+","+getZ()+")");
                }

            newAlt = newTempAlt;
        }

        setAltitude((int) newAlt);

    }

    // Metodo para descer o robo aereo, verificando se a sua nova altitude não será negativa e não colide com obstaculos.
    // É implementado o mesmo metodo de dividir o movimento em pequenas partes utilizado em "mover" do robo, apenas unidirecional

    public void descer(int deltaAltitude) {
        if (getAmbiente() == null){
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode descer.");
        }
        if (getEstado() == Estado.DESLIGADO){
            throw new RoboDesligadoException("O robô " + getNome() + " está desligado, não pode se mover.");
        }

        // Não aceita valores negativos para deltaAltitude:
        int finalAlt = getZ() - Math.max(deltaAltitude, 0);

        // Altitude não pode ser negativa:
        finalAlt = Math.max(finalAlt, 0);


        ArrayList<Obstaculo> obstaculosPresentes = getAmbiente().detectarObstaculos(getX(),getY(),0,getX(),getY(),getZ());

        double vetordescer = 0.25;

        double newAlt = getZ();

        for(double newTempAlt = getZ() - vetordescer; newAlt != finalAlt; newTempAlt = Math.max(finalAlt, newAlt - vetordescer)) {

            for (Obstaculo obstaculo : obstaculosPresentes)
                if (obstaculo.getTipoObstaculo().bloqueiaPassagem() && obstaculo.contemPonto(getX(), getY(), newTempAlt)) {
                    setAltitude((int)newAlt + 1);
                    throw new ColisaoException("O robo colidiu com um obstáculo "+obstaculo.getTipoObstaculo()+" e foi realocado para a posição ("+getX()+","+getY()+","+getZ()+")");
                }

            newAlt = newTempAlt;
        }

        setAltitude((int) newAlt);

    }

}


