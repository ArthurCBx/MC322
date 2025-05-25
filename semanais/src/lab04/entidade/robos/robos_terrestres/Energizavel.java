package lab04.entidade.robos.robos_terrestres;

// Interface para robos que possuem algum tipo de energia ou combustivel para a locomoção ou outras tarefas

public interface Energizavel {

    void carregar(double carga);

    void descarregar(double carga);

}
