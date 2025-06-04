package lab05.entidade.robos.robos_aereos;

import lab05.entidade.robos.Robo;

import java.util.List;

// Interface para robos que possuem algum tipo de metodo explosivo

public interface Explosivos {
    List<Robo> explodir();  // Retorna robos afetados
}
