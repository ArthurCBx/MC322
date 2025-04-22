package lab03.obstaculos;

public enum TipoObstaculo {
    PAREDE("Parede",5,true),
    ARVORE("Arvore", 10, true),
    ROCHA("Rocha", 3, true),
    ANIMAL("Animal", 2, true),
    NUVEM("Nuvem", 80, false);

    private final int alturaPadrao; // Altura inicial do tipo de obstáculo, que pode ser alterada na classe obstáculo.
    private final boolean bloqueia; // Se o obstáculo bloqueia a passagem dos robôs ou não
    private final String nome;

    TipoObstaculo(String nome, int altura, boolean bloqueia) {
        this.alturaPadrao = altura;
        this.bloqueia = bloqueia;
        this.nome = nome;
    }

    // Getters:
    public int getAltura() {
        return alturaPadrao;
    }

    public boolean bloqueiaPassagem(){
        return bloqueia;
    }

    public String getNome() {
        return nome;
    }
}
