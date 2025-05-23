package lab04.entidade.robos.robos_terrestres;

import lab04.Ambiente;
import lab04.entidade.robos.Estado;
import lab04.entidade.robos.RoboTerrestre;
import lab04.excecoes.RoboDesligadoException;
import lab04.excecoes.SemAmbienteException;
import lab04.excecoes.SemCombustivelException;

import java.util.Objects;

public class RoboSolar extends RoboTerrestre implements Energizavel {

    // Subclasse de robo terrestre que implementa energia e recarga por painel solar no robo

    private double bateria;
    private double potenciaPainelSolar;


    // Construtor para o robo Solar, considera que começa descarregado e possui uma potência de painel solar (sempre positiva)

    public RoboSolar(Ambiente ambiente, String nome, int posX, int posY, int velocidadeMaxima, double potenciaPainelSolar) {
        super(ambiente, nome, posX, posY, velocidadeMaxima);
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

    public String getDescricao() {
        return "Robo Solar: " + getNome() + "\n" +
                "id: " + this.getId() + "\n" +
                "Bateria: " + getBateria() + "\n" +
                "Potencia do Painel Solar: " + getPotenciaPainelSolar() + "\n" +
                "Velocidade Maxima: " + getVelocidadeMaxima() + "\n" +
                "Posicao Atual: (" + getX() + ", " + getY() + ")";
    }

    public void setBateria(double bateria) {
        this.bateria = bateria;
    }

    public void setPotenciaPainelSolar(double potenciaPainelSolar) {
        this.potenciaPainelSolar = potenciaPainelSolar;
    }


    // Metodo para carregar a bateria do Robo Solar (só carrega durante o dia [atributo do ambiente])


    public void carregar(double carga) {
        if (carga >= 0)                         // Não se remove carga ao carregar
            setBateria(getBateria() + carga);   // O robo ganha uma carga igual a carga
        else
            System.out.printf("Carga de %s precisa ser maior que 0\n", getNome());

    }

    public void carregar() {
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, não pode carregar.");
        }

        if (Objects.equals(getAmbiente().getPeriodo(), "Dia"))
            carregar(getPotenciaPainelSolar());                    // O robo ganha uma carga igual a potencia do painel solar
        else
            System.out.printf("O painel solar de %s só funciona durante o dia\n", getNome());

    }

    public void descarregar(double carga) {
        setBateria(Math.max(getBateria() - carga, 0));
        if (carga >= 0)                                     // Não se adiciona carga ao carregar
            setBateria(Math.max(getBateria() - carga, 0));  // O robo ganha perde carga igual a carga (nao pode ser menor que 0)
        else
            System.out.printf("Carga de %s precisa ser maior que 0\n", getNome());

    }

    // Sobrescrita do metodo mover do robo terrestre considerando bateria e periodo do dia
    // O robo utiliza bateria conforme a distância total que se move (similar a velocidade maxima)

    @Override
    public void moverPara(int deltaX, int deltaY,int deltaZ) {
        if (Objects.equals(getAmbiente().getPeriodo(), "Dia")) {     // O painel solar disponibiliza o movimento durante o dia sem consumir bateria
            super.moverPara(deltaX, deltaY, 0);                                // Assim o robo pode se mover independentemente da sua bateria durante o dia

        } else {    // Caso não esteja no período diurno, a bateria será utilizada para mover

            double distancia = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

            if (distancia <= getBateria()) {
                int x0 = getX();
                int y0 = getY();
                super.moverPara(deltaX, deltaY, 0);
                distancia = Math.sqrt(Math.pow(getX() - x0, 2) + Math.pow(getY() - y0, 2)); // A bateria consumida é calculada depois do mover já que
                descarregar(distancia);                                                     // o robo pode colidir e se movimentar menos do que o previsto

            } else {
                throw new SemCombustivelException("O robo não tem carga suficiente na bateria para a locomoção, carregue a bateria");
            }
        }

    }

    @Override
    public void executarTarefa() {
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode bombardear.");
        }
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado, não pode bombardear.");
        }
    }


}