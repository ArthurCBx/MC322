package lab03.robos.robos_terrestres;

import lab03.Ambiente;
import lab03.robos.RoboTerrestre;

import java.util.Objects;

public class RoboSolar extends RoboTerrestre {

    // Subclasse de robo terrestre que implementa energia e recarga por painel solar no robo

    private double bateria;
    private double potenciaPainelSolar;


    // Construtor para o robo Solar, considera que começa descarregado e possui uma potência de painel solar (sempre positiva)

    public RoboSolar(String nome, String direcao, int posX, int posY, int velocidadeMaxima, double potenciaPainelSolar) {
        super(nome, direcao, posX, posY, velocidadeMaxima);
        this.potenciaPainelSolar = Math.abs(potenciaPainelSolar);
        this.bateria = 0;

    }

    public RoboSolar(Ambiente ambiente, String nome, String direcao, int posX, int posY, int velocidadeMaxima, double potenciaPainelSolar) {
        super(ambiente, nome, direcao, posX, posY, velocidadeMaxima);
        this.potenciaPainelSolar = Math.abs(potenciaPainelSolar);
        this.bateria = 0;

    }


    // Setters e Getters:

    public double getBateria() {
        return bateria;
    }

    public double getPotenciaPainelSolar() {
        return potenciaPainelSolar;
    }

    public void setBateria(double bateria) {
        this.bateria = bateria;
    }

    public void setPotenciaPainelSolar(double potenciaPainelSolar) {
        this.potenciaPainelSolar = potenciaPainelSolar;
    }


    // Metodo para carregar a bateria do Robo Solar (só carrega durante o dia [atributo do ambiente])

    public void carregar() {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode carregar-se.\n", getNome());
            return;
        }

        if (Objects.equals(getAmbiente().getPeriodo(), "Dia"))
            setBateria(getBateria() + getPotenciaPainelSolar());    // O robo ganha uma carga igual a potencia do painel solar
        else
            System.out.printf("O painel solar de %s só funciona durante o dia\n", getNome());

    }


    // Sobrescrita do metodo mover do robo terrestre considerando bateria e periodo do dia
    // O robo utiliza bateria conforme a distância total que se move (similar a velocidade maxima)

    @Override
    public void mover(int deltaX, int deltaY) {
        if (getAmbiente() == null){
            System.out.printf("O robo %s não está em um ambiente, logo não pode movimentar-se.\n", getNome());
            return;
        }

        if (Objects.equals(getAmbiente().getPeriodo(), "Dia")) {     // O painel solar disponibiliza o movimento durante o dia sem consumir bateria
            super.mover(deltaX, deltaY);                                // Assim o robo pode se mover independentemente da sua bateria durante o dia

        } else {    // Caso não esteja no período diurno, a bateria será utilizada para mover

            double distancia = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

            if (distancia <= getBateria()) {
                int x0 = getPosX();
                int y0 = getPosY();
                super.mover(deltaX, deltaY);
                distancia = Math.sqrt(Math.pow(getPosX() - x0,2) + Math.pow(getPosY() - y0,2));
                setBateria(getBateria() - distancia);

            } else {
                System.out.printf("O robo %s não tem carga suficiente na bateria para a locomoção, carregue a bateria\n", getNome());
            }
        }

    }
}