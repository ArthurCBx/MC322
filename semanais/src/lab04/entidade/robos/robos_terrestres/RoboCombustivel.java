package lab04.entidade.robos.robos_terrestres;

import lab04.Ambiente;
import lab04.entidade.robos.Estado;
import lab04.entidade.robos.RoboTerrestre;
import lab04.excecoes.RoboDesligadoException;
import lab04.excecoes.SemAmbienteException;
import lab04.excecoes.SemCombustivelException;

public class RoboCombustivel extends RoboTerrestre implements Energizavel {

    // Subclasse para lidar com robos que utilizam combustível para locomoção

    private double combustivel;    // Se assume que sempre possuem um tanque suficiente para o combustivel


    // Contrutor do robo Combustivel, considera o combustivel presente no robo (minimo de zero)

    public RoboCombustivel(Ambiente ambiente, String nome, int posX, int posY, int velocidadeMaxima, double combustivel) {
        super(ambiente, nome, posX, posY, velocidadeMaxima);
        this.combustivel = Math.min(0, combustivel);

    }


    // Setters e Getters:

    public double getCombustivel() {
        return combustivel;
    }

    public String getDescricao() {
        return "Robo Combustivel: " + getNome() + "\n" +
                "id: " + this.getId() + "\n" +
                "Combustivel disponivel: " + getCombustivel() + "\n" +
                "Velocidade Maxima: " + getVelocidadeMaxima() + "\n" +
                "Posicao Atual: (" + getX() + ", " + getY() + ")";
    }

    protected void setCombustivel(double combustivel) {
        this.combustivel = combustivel;
    }


    // Metodo para abastecer o robo:

    public void carregar(double gasolina) {
        if (gasolina >= 0)      // Não se remove gasolina do robo no abastecimento
            setCombustivel(getCombustivel() + gasolina);
        else
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());

    }

    public void descarregar(double gasolina) {
        if (gasolina >= 0)      // Não se adiciona gasolina do robo no abastecimento
            setCombustivel(Math.max(getCombustivel() - gasolina, 0));
        else
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());

    }

    // Sobreescrita do metodo Mover do robo Terrestre para acomodar o uso de gasolina
    // O robo precisa ter no minimo uma quantia igual à distância total percorrida (similar à velocidade maxima)

    @Override
    public void moverPara(int deltaX, int deltaY, int deltaZ) {
        double distancia = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if (distancia <= getCombustivel()) {
            int x0 = getX();
            int y0 = getY();

            super.moverPara(deltaX, deltaY, 0);

            // Considera o se o movimento foi impedido por obstáculo e remove da gasolina:
            distancia = Math.sqrt(Math.pow(getX() - x0, 2) + Math.pow(getY() - y0, 2));
            descarregar(distancia);

        } else {
            throw new SemCombustivelException("O robo não tem combustível suficiente para a locomoção.");
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
