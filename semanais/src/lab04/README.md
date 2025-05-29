Alterações feitas do lab02 para o lab03:
# Obstáculos e como Robôs lidam com eles:
- Criação da classe obstáculo e adição de uma lista de obstáculos ao ambiente, que podem ou não impedir o movimento dos robôs;
- O método mover dos robôs agora lida com obstáculos e caso haja colisão, ele não chega ao destino planejado inicialmente, mas fica parado em frente ao obstáculo;
- Os métodos subir e descer de robôs aéreos também lidarão com obstáculos da mesma forma;
- A detectação de colisões é realizada na classe ambiente, informando quais robôs estão em contato com obstáculos que bloqueiam movimento.

# Sensor e classes que herdam dele:
- Classe Sensor consegue identificar obstáculos e robôs dentro de um raio que é fornecido quando ele é criado;
- Classe Sensor também é capaz de retornar uma lista de obstáculo ou uma lista de robôs dentro deste raio. Para isso temos os métodos listaRobosEncontrados e listaObstaculosEncontrados;
- Classe SensorAltitude herda de Sensor a habilidade de monitoramente bidimensional, mas também consegue utilizar um raioZ para identificar obstáculos e robos imediatamente acima ou abaixo de sua posição no plano XY;
- Classe SensorClasse consegue identificar robôs e sua classe em um raio tridimensional, mas perde a capacidade de identificar obstáculos. 

# Visualização do diagrama
- Para acessar o diagrama que explicita as relações de herança, composição, agregação e dependência, basta baixar o arquivo "diagrama_lab03.drawio" e acessá-lo pelo site online gratuito no link _https://app.diagrams.net/?src=about_.
- (Acompanhe essa explicação visualizando o diagrama) Neste diagrama, classes que herdam de outras são apontadas por setas onde o início vem da classe mãe e o fim da flecha aponta para a classe filha. A agregação de robôs e sensores foi representada por uma flecha na horizontal e foi indicado que 1 robo possui 1..* (1 ou mais) sensores. Da mesma forma, a representação foi feita entre obstáculos e ambiente e entre obstáculos e tipo de obstáculo. Já a relação entre ambiente e robô foi explicitada por 2 flechas, indicando que cada robô possui um ambiente, mas cada ambiente possui 1..* robôs.
- Cada caixa representa uma classe e contém na primeira linha o nome da classe, na segunda linha os atributos e seus tipos e, por fim, uma sequência de métodos, bem como os atributos que eles pedem, são explicitados.


Alterações feitas do lab03 para o lab04:

# Implementação de diversas interfaces:

### Entidade:

- Disponibiliza metodos de caracterização basica dos componentes do ambiente, como getXYZ, descrição ou representação no mapa do ambiente;
- Cria-se uma nova enumeração para definir o tipo de entidade, TipoEntidade (Robo, obstaculo, vazio...).

### Sensoreavel:

- Interface para robos com sensores, lidando com a listagem e utilização de sensores de maneira padronizada.

### Comunicavel e Central de comunicação:

- Interface que habilita a comunicação entre robos via a nova classe CentralComunicao, que, por sua vez, trata mensagens entre robos e as armazena por meio de metodos estaticos.

### Autonomo, Explosivo e Energizavel:

- Interfaces que disponibilizam metodos para funções autonomas, movimentações que envolvam algum tipo de energia e metodos que envolvem explosões que afetam outros componentes do ambiente.

# Alterações nas classes já presentes:

Com as implementações das interfaces, foi-se necessario alterar a maior parte das classes presentes para acomodar tais mudanças:

### Robo e subclasses:

- Teve a implementação dos metodos de diversas interfaces (como descrito no diagrama do codigo);
- Criação de diversas propriedades, como Estado (e sua enumeração), Id e TipoEntidade;
- Classes Robo, RoboTerrestre e RoboAereo se tornaram classes abstratas na medida que não definem metodos da interface Entidade (getDescrição, por exemplo) e se cria um metodo não implementado executarTarefa();
- Classes que herdam de RoboTerrestre e RoboAereo são concretas na medida que implementam todos os metodos necessarios e constroem um executarTarefa() proprio e personalizado para cada robo;
- Metodo mover(...) tornou-se moverPara(...) e é implmenetado em Robo com movimentação em XYZ e detecção de colisão entre robos e obstaculos e robos com robos, RoboTerrestre da override no metodo para limitar a movimentação em XY.

### Ambiente:

- Troca-se as listas de Robos e obstaculos para uma lista polimorfica de Entidades, englobando quaisqueres componentes do ambiente;
- Se alteram os metodos de adicionar robos/obstaculos para lidar com entidades;
- Se cria o mapa do ambiente, armazenando cada tipo de entidade em cada coordenada XYZ, além disso pode-se observar o mapa de uma visão aerea por meio do metodo vizualizarMapa();
- Outros metodos para facilitar impmenetação de metodos mais complexos como moverPara(...), por exemplo, como estaOcupado(...), dentroDosLimites(...) ou verificarColisões(...).

### Sensores:

- Alterações minimas para acomodar a interface Entidade.

# Exceções:

- Foram implmenetadas diversas exceções durantes os metodos e alterações descritos acima para personalizar e facilitar o tratamento de casos unicos;
- Exceções são tratadas na main dentro do menu interativo.

# Menu Interativo e Main:

- Foram reestruturados os testes na main para acomodar as mudanças descritas;
- Novos testes criados para metodos e exceções novas (vizualizarAmbiente, ou movimentação quando desligado, por exemplo);
- O Menu Interativo teve uma completa reescrita para a acomodação das diversas novas funções;
- Se implmeneta um delay entre interfaces para a melhor leitura das saidas (pode-se desabilitar tal delay para rapidez nos testes).






  
