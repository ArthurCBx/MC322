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
- (Acompanhe essa explicação visualizando o diagrama) Neste diagrama, classes que herdam de outras são apontadas por setas onde o início vem da classe mãe e o fim da flecha aponta para a classe filha. A agregação de robôs e sensores foi representada por uma flecha na horizontal e foi indicado que 1 robo possui 1..* (1 ou mais) sensores. Da mesma forma, a representação foi feita entre obstáculos e ambiente e entre obstáculos e tipo de obstáculo. Já a relação entre ambiente e robô foi explicitada por 2 flechas, indicando que cada robô possui uma ambiente, mas cada ambiente possui 1..* robôs.
- Cada caixa representa uma classe e contém na primeira linha o nome da classe, na segunda linha os atributos e seus tipos e, por fim, uma sequência de métodos, bem como os atributos que eles pedem, são explicitados.
