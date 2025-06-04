package lab05.entidade;

// Enumeração que define os tipos de entidades que podem existir no ambiente e estarão no mapa.

public enum TipoEntidade {
    ROBO("Robo"),
    OBSTACULO("Obstáculo"),
    VAZIO("Vazio"),
    DESCONHECIDO("Desconhecido");

    private final String nome;

    TipoEntidade(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
