package lab05.entidade.robos.robos_terrestres;

import lab05.Ambiente;
import lab05.comunicacao.ModuloComunicacao;
import lab05.entidade.robos.ControleMovimento;
import lab05.entidade.robos.Estado;
import lab05.entidade.robos.RoboTerrestre;
import lab05.excecoes.RoboDesligadoException;
import lab05.excecoes.SemAmbienteException;
import lab05.excecoes.SemCombustivelException;
import lab05.sensores.GerenciadorSensores;

// Subclasse para lidar com robos que utilizam combustível para locomoção
public class RoboCombustivel extends RoboTerrestre implements Energizavel {


    private double combustivel;    // Se assume que sempre possuem um tanque suficiente para o combustivel


    // Contrutor do robo Combustivel, considera o combustivel presente no robo (minimo de zero)

    public RoboCombustivel(Ambiente ambiente, String nome, int posX, int posY, int velocidadeMaxima, double combustivel, ModuloComunicacao comunicacao, GerenciadorSensores gerenciadorSensores, ControleMovimento controleMovimento) {
        super(ambiente, nome, posX, posY, velocidadeMaxima, comunicacao, gerenciadorSensores, controleMovimento);
        this.combustivel = Math.max(0, combustivel);

    }


    // Setters e Getters:

    public double getCombustivel() {
        return combustivel;
    }

    protected void setCombustivel(double combustivel) {
        this.combustivel = combustivel;
    }

    public String getDescricao() {
        return "Robo Combustivel: " + getNome() + "\n" +
                "id: " + this.getId() + "\n" +
                "Combustivel disponivel: " + getCombustivel() + "\n" +
                "Velocidade Maxima: " + getVelocidadeMaxima() + "\n" +
                "Posicao Atual: (" + getX() + ", " + getY() + ")";
    }


    // Metodo para abastecer o robo:
    public void carregar(double gasolina) {
        if (gasolina >= 0)      // Não se remove gasolina do robo no abastecimento
            setCombustivel(getCombustivel() + gasolina);
        else
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());

    }

    // Metodo para remover gasolina do robo:
    public void descarregar(double gasolina) {
        if (gasolina >= 0)      // Não se adiciona gasolina do robo no abastecimento
            setCombustivel(Math.max(getCombustivel() - gasolina, 0));
        else
            System.out.printf("Gasolina de %s precisa ser maior que 0\n", getNome());

    }

    // Sobreescrita do metodo Mover do robo Terrestre para acomodar o uso de gasolina
    // O robo precisa ter no minimo uma quantia igual à distância total percorrida (similar à velocidade maxima)
    @Override
    public void moverPara(int x, int y, int z) {
        double distancia = Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));

        if (distancia <= getCombustivel()) {
            int x0 = getX();
            int y0 = getY();

            super.moverPara(x, y, 0);

            // Considera o se o movimento foi impedido por obstáculo e remove da gasolina:
            distancia = Math.sqrt(Math.pow(getX() - x0, 2) + Math.pow(getY() - y0, 2));
            descarregar(distancia);

        } else {
            throw new SemCombustivelException("O robo não tem combustível suficiente para a locomoção.");
        }

    }

    // Tarefa do robo combustivel para aumentar a sua propria velocidade maxima em troca de uma quantia imediata de gasolina
    @Override
    public void executarTarefa() {
        if (getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode bombardear.");
        }
        if (getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô está desligado, não pode bombardear.");
        }

        if (getCombustivel() >= 100) {  // Custa 100 de gasolina para o incremento da velocidade
            descarregar(100);
            setVelocidadeMaxima(getVelocidadeMaxima() * 1.25);  // Aumento de 25%
            System.out.printf("Velocidade maxima incrementada em X1.25 (de %.1f para %.1f)\n", (getVelocidadeMaxima() * 0.8), getVelocidadeMaxima());
        } else {
            throw new SemCombustivelException("O robo Combustivel não possui gasolina suficiente (" + getCombustivel() + " de 100) para aumentar sua velocidade maxima\n");
        }
    }

    @Override
    public void executarMissao() {
        if(temMissao()){
            missao.executar(this, getAmbiente());
        }
        else{
            System.out.printf("O robo não possui missão\n");
        }
    }
}
