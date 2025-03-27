package lab02.robos.robos_terrestres;

import lab02.Ambiente;
import lab02.robos.RoboTerrestre;

import java.util.Objects;

public class RoboSolar extends RoboTerrestre {

    // Subclasse de robo terrestre que implemeta energia e recarga por painel solar no robo

    private int bateria;
    private int potenciaPainelSolar;


    // Construtor para o robo Solar, considera que começa descarregado e possi uma potencia de painel solar (sempre positiva)

    public RoboSolar(String nome, int posX, int posY, String direcao, int raioSensor, int velocidadeMaxima, int potenciaPainelSolar) {
        super(nome, posX, posY, direcao, raioSensor, velocidadeMaxima);
        this.potenciaPainelSolar = Math.abs(potenciaPainelSolar);
        this.bateria = 0;

    }


    // Setter e Getters:

    public int getBateria() {
        return bateria;
    }

    public int getPotenciaPainelSolar() {
        return potenciaPainelSolar;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public void setPotenciaPainelSolar(int potenciaPainelSolar) {
        this.potenciaPainelSolar = potenciaPainelSolar;
    }


    // Metodo para carregar a bateria do Robo Solar (só carrega durante o dia [atributo do ambiente])

    public void carregar(Ambiente ambiente) {
        if (Objects.equals(ambiente.getPeriodo(), "Dia"))
            setBateria(getBateria() + getPotenciaPainelSolar());    // O robo ganha uma carga igual a potencia do painel solar
        else
            System.out.printf("O painel solar de %s so funciona durante o dia\n", getNome());

    }


    // Sobreescrita do metodo mover do robo terrestre considerando bateria e periodo do dia

    @Override
    public void mover(int deltaX, int deltaY, Ambiente ambiente) {

        if (Objects.equals(ambiente.getPeriodo(), "Dia")) {     // O painel solar disponibiliza o movimento durante o dia sem consumir bateria
            super.mover(deltaX, deltaY, ambiente);                 // Assim o robo pode se mover independentemente de sua bateria durante o dia

        } else {    // Caso não esteja no periodo diurno, a bateria será utilizada para mover ***
            if (Math.abs(deltaX) + Math.abs(deltaY) <= getBateria()) {
                setBateria(getBateria() - Math.abs(deltaX) - Math.abs(deltaY));
                super.mover(deltaX, deltaY, ambiente);

                // O robo utiliza bateria conforme a distancia que se move em cada direção
                // Ex: se o robo se move +10 em X e -50 em Y, será consumida uma bateria de 60 (10 + 50)

            } else {
                System.out.printf("O robo %s não tem carga suficiente na bateria para a locomoção, carregue a bateria\n", getNome());
            }
        }

    }
}