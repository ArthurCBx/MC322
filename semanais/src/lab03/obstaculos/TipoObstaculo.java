package lab03.obstaculos;

public enum TipoObstaculo {
    PAREDE("Parede",5,true),
    ARVORE("Arvore", 10, true),
    ROCHA("Rocha", 3, true),
    ANIMAL("Animal", 0, true),
    NUVEM("Nuvem", 80, false);

    private final int alturaPadrao; //
    private final boolean bloqueia;
    private final String nome;

    TipoObstaculo(String nome, int altura, boolean bloqueia) {
        this.alturaPadrao = altura;
        this.bloqueia = bloqueia;
        this.nome = nome;
    }

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
