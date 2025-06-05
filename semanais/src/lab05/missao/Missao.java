package lab05.missao;

import lab05.Ambiente;
import lab05.entidade.robos.Robo;

public interface Missao {
    void executarMissao(Robo r, Ambiente a);
}
