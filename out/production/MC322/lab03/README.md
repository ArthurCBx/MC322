Para este laboratório, foram criados duas subclasses para a classe Robo: RoboAereo e RoboTerrestre.
Para cada uma dessas subclasses, mais 2 subclasses foram criadas. Para RoboAereo foram criadas as subclasses RoboBombardeiro e RoboExplosivo. 
Já para RoboTerrestre foram criadas as classes RoboSolar e RoboCombustível.
O metodo identificar obstáculos foi criado para considerar apenas robos que estejam dentro de um campo de visão do robo que está chamando o método.
Logo, uma modificação foi feita à classe Robo, que agora possui o atributo raioSensor.

A classe RoboBombardeiro possui a habilidade de destruir todos os robos que se encontram na mesma coordenada (x,y), mas em alturas menores ou iguais às do próprio robo. 
Essa habilidade é acessada pelo método bombardear e exige como atributo o ambiente que o robo está para acessar a lista de robos.
Ele possui os atributos bombas (quantidade de vezes que pode usar o método bombardear) e capacidade de bombas.

A classe RoboExplosivo possui a habilidade de se autodestruir, levando consigo os robos dentro de um raio de explosão, que ele recebe como atributo no construtor. 
Para isso, ele possui o método explodir.

A classe RoboSolar é uma classe que simula um carro elétrico, em que a bateria é carregada por meio de um painel solar. 
Ele possui os atributos bateria (energia utilizada para se mover) e potenciaPainelSolar (quanto a bateria é carregada toda vez que o método carregar for chamado).
Para atender esse robo, uma modificação foi feita na classe ambiente. Essa classe agora possui o atributo periodo, que indica se está fazendo sol (Dia) ou não (Noite) e permitirá ou não o RoboSolar carregar.
Ele possui o método específico carregar e sobreescreve o método mover.

A classe RoboCombustivel simular um carro comum e gasta combustível para se mover. Ele possui o atributo específco combustível e o método abastecer para repô-lo.

Outra modficação criada para este laboratório foi para a classe Ambiente, que agora além de possuir o atributo período também possui o atributo altura. 
Este atributo é criado para considerar os limites que um RoboAereo pode voar, mesmo que sua altitudeMaxima seja maior que a do ambiente.
