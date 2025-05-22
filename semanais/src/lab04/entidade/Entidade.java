package lab04.entidade;

// Interface que define características padrões às entidades neste código (Robôs e obstáculos)

public interface Entidade {
    int getX();
    int getY();
    int getZ();

    TipoEntidade getTipoEntidade(); // Retorna o TipoEntidade.
    String getDescricao();          // Retorna a descrição da entidade
    char getRepresentacao();        // Retorna o caractere de representação da entidade no mapa


}
