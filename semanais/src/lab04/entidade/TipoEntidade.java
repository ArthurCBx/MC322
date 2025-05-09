package lab04.entidade;

public enum TipoEntidade {
    ROBO("Robo"),
    OBSTACULO("Obstáculo"),
    VAZIO("Vazio");

    private final String nome;

    TipoEntidade(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
