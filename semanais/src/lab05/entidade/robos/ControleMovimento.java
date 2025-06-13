package lab05.entidade.robos;

import lab05.excecoes.ColisaoException;
import lab05.excecoes.RoboDesligadoException;
import lab05.excecoes.SemAmbienteException;

/*
    Classe controleMovimento responsável por gerir o metodo mover do robô e das classes que herdam dele.
    Possui apenas o robô como parâmetro e o seu único metodo é o moverPara(x, y, z).
 */

public class ControleMovimento {
    private Robo robo;

    public ControleMovimento(Robo robo){
        this.robo = robo;
    }

    public void moverPara(int x, int y, int z){
        if (robo.getEstado() == Estado.DESLIGADO) {
            throw new RoboDesligadoException("O robô " + robo.getNome() + " está desligado, não pode se mover.");
        }
        if (robo.getAmbiente() == null) {
            throw new SemAmbienteException("O robo não está em um ambiente, logo não pode movimentar-se.");
        }

        int deltaX = x - robo.getX();
        int deltaY = y - robo.getY();
        int deltaZ = z - robo.getZ();

        int[] finalPos = {robo.getX() + deltaX, robo.getY() + deltaY, robo.getZ() + deltaZ};   // Posição final ideal do robo

        robo.getAmbiente().dentroDosLimites(finalPos[0], finalPos[1], finalPos[2]);  // Verifica se o movimento ideal não sai do ambiente

        // Variaveis para a verificação de colisão no caminho do robo
        double norma = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2) + Math.pow(deltaZ, 2));
        double[] vetorMove = {
                (norma != 0) ? 0.5 * ((double) deltaX / norma) : 0,
                (norma != 0) ? 0.5 * ((double) deltaY / norma) : 0,
                (norma != 0) ? 0.5 * ((double) deltaZ / norma) : 0
        };
        double[] newPos = {robo.getX(), robo.getY(), robo.getZ()};
        double[] newTempPos = {0, 0, 0};

        // Funcionamento do movimento: se verifica se uma posição logo a frente [newTempPos](posição atual + vetorMove) não colide com nada
        // Se não colidir a nova posição sem colisão é atualizada [newPos], se repete esse processo até que uma posição logo a frente colida
        // ou chegue na posição final ideal

        while (true) {

            // O if dentro do for abaixo é utilizado para verificar se o movimento não extrapola o ponto final do movimento
            // Que, por sua vez, é indicado pela diferença de sinal do vetor que liga o ponto final ao ponto proximo do movimento
            // Além disso também calcula a posição logo a frente para teste

            for (int i = 0; i < 3; i++)
                if ((finalPos[i] - (newPos[i] + vetorMove[i])) * vetorMove[i] >= 0)
                    newTempPos[i] = newPos[i] + vetorMove[i];
                else
                    newTempPos[i] = finalPos[i];

            // Verificação de colisão entre outros robos e obstaculos (não pode ser o proprio robo[primeiro if])

            if (((int) newTempPos[0]) != robo.getX() || ((int) newTempPos[1]) != robo.getY() || ((int) newTempPos[2]) != robo.getZ())
                if (robo.getAmbiente().estaOcupado((int) newTempPos[0], (int) newTempPos[1], (int) newTempPos[2])) {

                    robo.getAmbiente().moverEntidade(robo, (int) newPos[0], (int) newPos[1], (int) newPos[2]);

                    throw new ColisaoException("O robo colidiu com uma entidade do tipo " + robo.getAmbiente().getMapa()[(int) newTempPos[0]][(int) newTempPos[1]][(int) newTempPos[2]] + " e foi realocado para a posição (" + robo.getX() + "," + robo.getY() + "," + robo.getZ() + ")");

                }

            // Como não houve colisões, a nova posição sem colisão é atualizada

            newPos[0] = newTempPos[0];
            newPos[1] = newTempPos[1];
            newPos[2] = newTempPos[2];

            // Se chegou no destino
            if (newPos[0] == finalPos[0] && newPos[1] == finalPos[1] && newPos[2] == finalPos[2])
                break;

        }

        // Como não há colisões no movimento do robo, é seguro utilizar o metodo sem verificação do ambiente
        robo.getAmbiente().moverEntidade(robo, finalPos[0], finalPos[1], finalPos[2]);
    }
}
