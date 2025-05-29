Alterações feitas do lab03 para o lab04:

# Implementação de diversas interfaces:

### Entidade:

- Disponibiliza métodos de caracterização básica dos componentes do ambiente, como getXYZ, descrição ou representação no mapa do ambiente;
- Cria-se uma nova enumeração para definir o tipo de entidade, TipoEntidade (Robô, obstáculo, vazio...).

### Sensoreavel:

- Interface para robôs com sensores, lidando com a listagem e utilização de sensores de maneira padronizada.

### Comunicável e Central de comunicação:

- Interface que habilita a comunicação entre robôs via a nova classe CentralComunicao, que, por sua vez, trata mensagens entre robôs e as armazena por meio de métodos estáticos.

### Autônomo, Explosivo e Energizavel:

- Interfaces que disponibilizam métodos para funções autônomas, movimentações que envolvam algum tipo de energia e métodos que envolvem explosões que afetam outros componentes do ambiente.

# Alterações nas classes já presentes:

Com as implementações das interfaces, foi-se necessario alterar a maior parte das classes presentes para acomodar tais mudanças:

### Robo e subclasses:

- Teve a implementação dos métodos de diversas interfaces (como descrito no diagrama do código);
- Criação de diversas propriedades, como Estado (e sua enumeração), Id e TipoEntidade;
- Classes Robô, Robô Terrestre e Robô Aéreo se tornaram classes abstratas na medida que não definem métodos da interface Entidade (getDescrição, por exemplo) e se cria um método não implementado executarTarefa();
- Classes que herdam de Robô Terrestre e Robô Aéreo são concretas na medida que implementam todos os métodos necessários e constroem um executarTarefa() próprio e personalizado para cada robô;
- Método mover(...) tornou-se moverPara(...) e é implementado em Robo com movimentação em XYZ e detecção de colisão entre robôs e obstáculos e robôs com robos, Robo Terrestre da override no método para limitar a movimentação em XY.

### Ambiente:

- Troca-se as listas de Robôs e obstáculos para uma lista polimórfica de Entidades, englobando quaisqueres componentes do ambiente;
- Se alteram os métodos de adicionar robôs/obstáculos para lidar com entidades;
- Se cria o mapa do ambiente, armazenando cada tipo de entidade em cada coordenada XYZ, além disso pode-se observar o mapa de uma visão aérea por meio do método vizualizarMapa();
- Outros métodos para facilitar implementação de métodos mais complexos como moverPara(...), por exemplo, como estaOcupado(...), dentroDosLimites(...) ou verificarColisões(...).

### Sensores:

- Alterações mínimas para acomodar a interface Entidade.

# Exceções:

Foram implementadas diversas exceções durantes os métodos e alterações descritos acima para personalizar e facilitar o tratamento de casos únicos:

  #### ColisaoException:
  - verificarColisoes()[Ambiente] e moverPara(...)[Robo];
  - Métodos que implementan moverPara(...):
  - moverPara(...) herdados dos robos, moveAutomatico(...) e executarTarefa() para alguns robos.

  #### ForaDosLimitesException:
  - dentroDosLimites(...)[Ambiente];
  - Metodos que impl menem tam dentroDosLimites(...):
  - moverPara(...) e os métodos citados acima, adicionarEntidade(...).

  #### ErroComunicacaoException:
  - enviarMensagem(...)[Robo].
   
  #### RoboDesligadoException:
  - A maior parte dos métodos dos Robôs e suas subclasses;
  - Exclui-se métodos getters e setters, de adicionar algum tipo de recurso (energia//bombas).

  #### SemAmbienteException:
  - A maior parte dos métodos dos Robôs e suas subclasses, similar a RoboDesligadoException;
  - Exclui-se métodos getters e setters, de adicionar algum tipo de recurso (energia//bombas).

  #### SemCombustivelException:
  - moverPara(...) de robos que implementam a interface Energizavel (ambos robôs terrestres);
  - Método executarTarefa() do robô combustível.
 
Além disso, o menu interativo apresenta tratamento considerável de todas as exceções (várias vezes constituindo do cancelamento da operação)

# Menu Interativo e Main:

- Foram reestruturados os testes na main para acomodar as mudanças descritas;
- Novos testes criados para métodos e exceções novas (vizualizarAmbiente, ou movimentação quando desligado, por exemplo);
- O Menu Interativo teve uma completa reescrita para a acomodação das diversas novas funções;
- Se implementa um delay entre interfaces para a melhor leitura das saídas (pode-se desabilitar tal delay para rapidez nos testes).

# Visualização do diagrama

- Para acessar o diagrama que explicita as relações de herança, composição, agregação e dependência, basta baixar o arquivo "diagrama_lab04.jpg" e acessá-lo.
- (Acompanhe essa explicação visualizando o diagrama) Neste diagrama, classes que herdam de outras são apontadas por setas onde o início vem da classe mãe e o fim da flecha aponta para a classe filha, já a implementação de interfaces é definida por setas pontilhadas que começam na interface e terminam em quem a implementa.
- A agregação de robôs e sensores foi representada por uma flecha na horizontal e foi indicado que 1 robô possui 1..* (1 ou mais) sensores. Da mesma forma, a representação foi feita entre entidades e ambiente, robô e ambiente, entre obstáculos e tipo de obstáculo, entre robôs e estados e entre entidades e tipos de entidades.
- Cada caixa representa um componente do código, possui o nome e o tipo na primeira linha, em conjunção com a cor da caixa, com a legenda a seguir, na segunda linha os atributos e seus tipos e, por fim, uma sequência de métodos, bem como os atributos que eles pedem, são explicitados.
- Os sinais de + antes dos métodos dizem que os métodos são implementados nesta caixa, mas não são criados nesta caixa (como herança de métodos ou por interfaces), sinais de - representam a criação de um método não implementado nesta caixa e, por fim, quando não há sinal significa que o método foi criado e implementado nesta caixa.

Legenda de cores:
- Azul: Classe concreta
- Azul claro: Classe abstrata
- Verde claro: Interface
- Roxo claro: Enumeração




