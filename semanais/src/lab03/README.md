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
