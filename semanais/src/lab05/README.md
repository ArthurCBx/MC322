# Alterações feitas do lab04 para o lab05:

## Nova classe AgenteInteligente
Essa classe é intermediária entre Robo e RoboAereo/Terrestre. Ela é abstrata e possibilita os seus descendentes lidarem com missões atribuídas a eles. Para isso, seus descendentes devem implementar o método abstrato `executarMissao()`, que apenas verifica se há missões atribuídas e as executa.

## Interface Missao
Classes que implementam essa interface possuem o método `executar(Robo r, Ambiente a)` e serão as missões que podem ser atribuídas aos robôs. Essa interface possibilita aos robôs que definem o método `executarMissao()` generalizar a chamada para qualquer classe de missão.

## Missões 
Todas as missões verificam inicialmente se serão lançadas exceções, como robô desligado ou robô sem ambiente, antes da execução ser iniciada. Caso alguma seja lançada, ela será escrita no arquivo log.txt definido dentro do diretório lab05.
Além disso, todo missão bem executada escreverá o horário de início e o horário de fim de sua execução.

### MissaoBuscarPonto
Robôs que possuem essa missão deverão se mover para o ponto (x, y) definido na construção da missão. Caso ocorra uma colisão no caminho, o texto correspondente será escrito no arquivo log.txt e uma exceção será lançada. Caso contrário, serão escritas as informações de missão bem executada.

### MissaComunicar
Robôs que possuem essa missão irão tentar se comunicar com algum outro robô. Ambas mensagem e outro robô serão definidos na construção da missão. Ademais, será escrito o conteúdo da mensagem e os robôs envolvidos na comunicação no arquivo log.txt. 

Para essa escrita ser bem sucedida, alteramos os métodos de monitorar nos sensores, que agora retornam um stringBuilder usado para escrita no arquivo.


### MissaoMonitorar
Robôs que possuem essa missão deverão ativar seus sensores e descrever o que for percebido por cada sensor. As informações serão escritas no terminal e no arquivo log.txt caso o robô possua sensores ativos. Caso contrário, a missão não será executada, mas será escrito em log.txt que o robô não possui sensores.

## Subsistemas internos dos robôs
Agora robôs deverão ser construídos com 3 novas propriedades, que serão responsáveis por lidar com funções lidadas antes pelo próprio robô. São elas:
- ControleMovimento, que possui apenas o método `moverPara(int x,int y,int z)`;
- ModuloComunicacao, que possui os métodos `enviarMensagem(Comunicavel destinatario, String mensagem)` e `receberMensagem(String mensagem)`;
- GerenciadorSensores, que possui os métodos `acionarSensores()`, `identificarRobosProximos()` e `identificarObstaculosProximos()`.

## Visualização do diagrama
- Para acessar o diagrama que explicita as relações de herança, composição, agregação e dependência, basta baixar o arquivo "diagrama_lab05.jpg" e acessá-lo.
- (Acompanhe essa explicação visualizando o diagrama) Neste diagrama, classes que herdam de outras são apontadas por setas onde o início vem da classe mãe e o fim da flecha aponta para a classe filha, já a implementação de interfaces é definida por setas pontilhadas que começam na interface e terminam em quem a implementa.
- A agregação de robôs e sensores foi representada por uma flecha na horizontal e foi indicado que 1 robô possui 1..* (1 ou mais) sensores. Da mesma forma, a representação foi feita entre entidades e ambiente, robô e ambiente, entre obstáculos e tipo de obstáculo, entre robôs e estados e entre entidades e tipos de entidades.
- Cada caixa representa um componente do código, possui o nome e o tipo na primeira linha, em conjunção com a cor da caixa, com a legenda a seguir, na segunda linha os atributos e seus tipos e, por fim, uma sequência de métodos, bem como os atributos que eles pedem, são explicitados.
- Os sinais de + antes dos métodos dizem que os métodos são implementados nesta caixa, mas não são criados nesta caixa (como herança de métodos ou por interfaces), sinais de - representam a criação de um método não implementado nesta caixa e, por fim, quando não há sinal significa que o método foi criado e implementado nesta caixa.

Legenda de cores:
- Azul: Classe concreta
- Azul claro: Classe abstrata
- Verde claro: Interface
- Roxo claro: Enumeração




