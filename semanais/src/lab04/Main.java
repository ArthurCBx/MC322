package lab04;

import lab04.comunicacao.CentralComunicacao;
import lab04.entidade.Entidade;
import lab04.entidade.robos.robos_terrestres.Energizavel;
import lab04.excecoes.*;
import lab04.obstaculos.Obstaculo;
import lab04.obstaculos.TipoObstaculo;
import lab04.entidade.robos.Robo;
import lab04.entidade.robos.RoboAereo;
import lab04.entidade.robos.robos_aereos.RoboBombardeiro;
import lab04.entidade.robos.robos_aereos.RoboExplosivo;
import lab04.entidade.robos.robos_terrestres.RoboCombustivel;
import lab04.entidade.robos.robos_terrestres.RoboSolar;
import lab04.sensores.Sensor;
import lab04.sensores.SensorAltitude;
import lab04.sensores.SensorClasse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);


        /*

        Ambiente ambiente = new Ambiente(50, 50, 50);

        Robo robo1 = new RoboBombardeiro(ambiente, "teste1", 0, 0, 0, 120, 10);
        Robo robo2 = new RoboExplosivo(ambiente, "teste2", 0, 0, 0, 100, 5);
        Robo robo3 = new RoboCombustivel(ambiente, "teste3", 0, 0, 50, 100);
        Robo robo4 = new RoboSolar(ambiente, "teste4", 0, 0, 80, 20);

        Sensor sensorComum20 = new Sensor(20);
        Sensor sensorComum50 = new Sensor(50);
        SensorAltitude sensorAltitude80XY20 = new SensorAltitude(80,20);
        SensorAltitude sensorAltitude40XY10 = new SensorAltitude(40,10);
        SensorClasse sensorClasse50 = new SensorClasse(50);
        SensorClasse sensorClasse100 = new SensorClasse(100);

        Obstaculo obstaculoRocha = new Obstaculo(5, 5, 7, 7, 0, TipoObstaculo.ROCHA);
        Obstaculo obstaculoArvore = new Obstaculo(20, 20, 30, 30, 0, TipoObstaculo.ARVORE);
        Obstaculo obstaculoNuvem = new Obstaculo(50, 50, 90, 90, 95, TipoObstaculo.NUVEM);
        Obstaculo obstaculoParede = new Obstaculo(70, 70, 90, 90, 0, TipoObstaculo.PAREDE);
        Obstaculo obstaculoAnimal = new Obstaculo(30, 30, 32, 32, 0, TipoObstaculo.ANIMAL);

        ambiente.adicionarObstaculo(obstaculoRocha);
        ambiente.adicionarObstaculo(obstaculoArvore);
        ambiente.adicionarObstaculo(obstaculoNuvem);
        ambiente.adicionarObstaculo(obstaculoParede);
        ambiente.adicionarObstaculo(obstaculoAnimal);

        ArrayList<Robo> robosVivos = new ArrayList<Robo>() {{
            add(robo1);
            add(robo2);
            add(robo3);
            add(robo4);
        }};

        String nomeRobo;
        Robo robo = null;


 */
        // TESTES: --------------------------------

        Ambiente tambiente = new Ambiente(50, 50, 50);

        Robo roboteste1 = new RoboExplosivo(tambiente, "Teste1", 0, 0, 0, 20, 20);
        Robo roboteste2 = new RoboBombardeiro(tambiente, "Teste2", 1, 1, 0, 30, 20);
        Robo roboteste3 = new RoboSolar(tambiente, "Teste3", 2, 2, 30, 40);
        Robo roboteste4 = new RoboCombustivel(tambiente, "Teste4", 3, 3, 30, 200);

        Sensor tsensorComum20 = new Sensor(20);
        SensorAltitude tsensorAltitude80XY20 = new SensorAltitude(80,20);
        SensorClasse tsensorClasse20 = new SensorClasse(20);

        roboteste1.addSensor(tsensorComum20);
        roboteste1.addSensor(tsensorAltitude80XY20);
        roboteste1.addSensor(tsensorClasse20);

        roboteste2.addSensor(tsensorComum20);

        roboteste3.addSensor(tsensorClasse20);

        roboteste4.addSensor(tsensorComum20);
        roboteste4.addSensor(tsensorClasse20);

        Obstaculo obstaculoteste = new Obstaculo(5, 5, 10, 10, 0, 10, TipoObstaculo.ROCHA);
        tambiente.adicionarEntidade(obstaculoteste);

        ArrayList<Robo> robosteste = new ArrayList<Robo>() {{
            add(roboteste1);
            add(roboteste2);
            add(roboteste3);
            add(roboteste4);

        }};

        System.out.printf("\n------- INICIO DOS TESTES: ---------\n");

        // Vizualização inicial do mapa:

        System.out.println();

        try {
            System.out.println("\n\nVIZUALIZAR AMBIENTE:------------");
            tambiente.vizualizarAmbiente();

            System.out.println("\n\nEXECUTAR SENSORES:------------");
            tambiente.executarSensores();

            System.out.println("\n\nDENTRO DOS LIMITES:------------");
            tambiente.dentroDosLimites(-1, -1, -1);
        } catch (ForaDosLimitesException e){
            System.out.println(e.getMessage());  // (-1,-1,-1) está fora dos limites
        }

        try{    // Continuação de dentro dos limites:
            roboteste1.moverPara(-1,-1,-1);
        }catch (ForaDosLimitesException e){
            System.out.println(e.getMessage());  // (-1,-1,0) está fora dos limites [robo aereo so vai ate o chao {0}]
        }

        System.out.println("\n\nVERIFICAR COLISOES:------------");

        try{
            tambiente.moverEntidade(roboteste1,1,1,0);   // Posição é a mesma do roboteste2
            tambiente.verificarColisoes();
        }catch (ColisaoException e){
            System.out.println(e.getMessage());  // Colisão esperada
            tambiente.moverEntidade(roboteste1,0,0,0);   //  retorna para posição inicial

        }

        try{    // Sem colisões:
            tambiente.verificarColisoes();
        }catch (ColisaoException e){
            System.out.println(e.getMessage());  // Colisão esperada

        }


        // Funcioalidades de Entidade (fora getX, getY, getZ):

        for (Robo robot : robosteste) {
            System.out.printf("\nEntidade %s:\nRepresentação: '%c'\nTipoEntidade '%s'\nDescrição:\n%s\n\n", robot.getNome(), robot.getRepresentacao(), robot.getTipoEntidade(), robot.getDescricao());
        }

        // Novo movimento de robos (atualizado para colisão 3D e com outros robos) [apenas robo aereos já que terrestres são a mesma coisa so que em 2D]

        try {
            roboteste1.exibirPosicao();
            roboteste1.moverPara(4, 4, 4);        // Movimento sem problemas em 3D
            roboteste1.exibirPosicao();

            roboteste2.exibirPosicao();
            roboteste2.moverPara(2, 2, 2);        // Teste2 vai para (2,2,2)
            roboteste2.exibirPosicao();

            roboteste1.exibirPosicao();
            roboteste1.moverPara(0, 0, 0);     // Teste1 tenta voltar para (0,0,0) mas colide com Teste2 em (2,2,2)

        } catch (ColisaoException e) {
            System.out.printf("\nColisão com robo como esperado:\n");
            System.out.printf("Robo que estava se movendo: ");
            roboteste1.exibirPosicao();
            System.out.printf("Robo que estava se parado: ");
            roboteste2.exibirPosicao();
        }

        // Colisão com obstauclo:

        System.out.printf("\n\nColisão com obstaculo que pussuem dominio de: (5,5,0) a (10,10,10)\n");

        System.out.printf("\nColisão com canto:\n");

        try {
            roboteste1.moverPara(3, 3, 6);        // Sobe um pouco
            roboteste1.exibirPosicao();
            roboteste1.moverPara(5, 5, 6);        // Colisão em um canto do obstaculo


        } catch (ColisaoException e) {
            System.out.println(e.getMessage());

        }

        System.out.printf("\nColisão com quina:\n");

        try {
            roboteste1.moverPara(4, 4, 11);
            roboteste1.exibirPosicao();
            roboteste1.moverPara(5, 5, 10);     // Colisão em uma quina do obstaculo

        } catch (ColisaoException e) {
            System.out.println(e.getMessage());

        }

        System.out.printf("\n LIGAR//DESLIGAR e EXCEÇAO:\n");

        try{
            roboteste1.desligar();
            roboteste1.moverPara(10,10,10);

        }catch (RoboDesligadoException e){
            System.out.println(e.getMessage());
            roboteste1.ligar();
        }

        System.out.printf("\n COMUNICAÇAO:\n");


        try{

            roboteste1.enviarMensagem(roboteste2,"Bom dia");

            System.out.println();

            roboteste2.desligar();
            roboteste1.enviarMensagem(roboteste2,"Boa noite");  // Falha pelo destinatario estar desligado


        }catch (ErroComunicacaoException e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println();

            roboteste1.desligar();
            roboteste2.ligar();
            roboteste1.enviarMensagem(roboteste2,"Boa noite");  // Falha pelo remetente estar desligado


        }catch (ErroComunicacaoException e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.println();

            roboteste1.ligar();
            roboteste1.enviarMensagem(roboteste2,"Boa noite");


        }catch (ErroComunicacaoException e){
            System.out.println(e.getMessage());
        }

        System.out.println("\nMensagens armazenadas na cetral:");

        CentralComunicacao.exibirMensagens();

        System.out.printf("\n TAREFAS ESPECIFICAS DOS ROBOS:\n\n");

        try{
            System.out.printf("Robo combustivel:\n");
            roboteste4.executarTarefa();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.printf("\nRobo Solar:\n");
            tambiente.moverEntidade(roboteste3,30,30,0);
            tambiente.moverEntidade(roboteste2,30,30,5);

            roboteste3.executarTarefa();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            System.out.printf("\nRobo Bombardeiro:(tambem testa a remoção de entidades)\n");
            tambiente.moverEntidade(roboteste3,30,30,0);
            tambiente.moverEntidade(roboteste2,30,30,5);

            roboteste2.executarTarefa();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.printf("\nNumero de entidades presentes(eram 5 inicialmente): %d\n\n",tambiente.getListaEntidades().size());

        try{
            System.out.printf("\nRobo Explosivo:\n");
            tambiente.moverEntidade(roboteste1,50,50,5);     // Os robos foram posicionados para que a primeira verificação não encontre robos
            tambiente.moverEntidade(roboteste2,30,45,20);    // e seja necessario que o robo se locomova automaticamente para os encontrar
            tambiente.moverEntidade(roboteste4,45,30,0);     // É possivel que o robo não consiga encontrar nenhum outro robo ou colida em outras entidades

            roboteste1.executarTarefa();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Deleta os objetos utilizados nos testes:

        for (Entidade entidade : tambiente.getListaEntidades())
            tambiente.removerEntidade(entidade);

        tsensorComum20 = null;
        tsensorClasse20 = null;
        tsensorAltitude80XY20 = null;
        tambiente = null;

        System.out.printf("\n------FIM DOS TESTES------------\n");


        /*

        System.out.printf("MENU ITERATIVO:");

        // MENU INTERATIVO: ------------------------------------

        while (true) {
            String nomes = robosVivos.stream()
                    .map(Robo::getNome)
                    .collect(Collectors.joining(", "));
            if (nomes.isEmpty()) {
                System.out.println("\nTodos os robôs foram destruídos. Finalizando o programa...");
                return;
            }

            // Menu principal:

            System.out.println("\nDigite o comando que será realizado:\n" +
                    "[1] - Adicionar Sensor em um robô\n" +
                    "[2] - Comandar um robo para monitorar seu arredor\n" +
                    "[3] - Comandar um robo para exibir sua posição\n" +
                    "[4] - Comandar um robo para mover-se\n" +
                    "[5] - Usar habilidade específica da classe de robô\n" +
                    "[6] - Mudar o periodo do ambiente\n" +
                    "[7] - Verificar colisões no ambiente\n" +
                    "[8] - Finalizar o programa\n");

            // Leitura de comando e switch para determinar o que acontece

            int comando = scanner.nextInt();
            scanner.nextLine();

            switch (comando) {
                case 1: // Adicionar sensor
                    System.out.printf("Digite o nome do robô que receberá o sensor. Opções: %s\n",nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) { // Em qual robo se coloca o sensor
                        case "teste1":
                            if (robosVivos.contains(robo1))
                                robo = robo1;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste2":
                            if (robosVivos.contains(robo2))
                                robo = robo2;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste3":
                            if (robosVivos.contains(robo3))
                                robo = robo3;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste4":
                            if (robosVivos.contains(robo4))
                                robo = robo4;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        default:
                            System.out.println("Nome de robô inválido.");
                            continue;
                    }
                    if (robo != null) {

                        // Tipos de sensor
                        System.out.println("Digite o tipo de sensor que deseja adicionar. Opções:\n" +
                                "[1] - SensorComum20\n" +
                                "[2] - SensorComum50\n" +
                                "[3] - SensorAltitude80XY20\n" +
                                "[4] - SensorAltitude40XY10\n" +
                                "[5] - SensorClasse50\n" +
                                "[6] - SensorClasse100");
                        // Sensor Comum20: Classe sensor base com raio de 20
                        // Sensor Comum50: Classe sensor base com raio de 50
                        // Sensor Altitude80XY20: Classe sensor altitude com raioZ de 80 e raio 20
                        // Sensor Altitude40XY10: Classe sensor altitude com raioZ de 40 e raio 10
                        // Sensor Classe50: Classe sensor classe com raio de 50 (XYZ radial)
                        // Sensor Classe100: Classe sensor classe com raio de 100 (XYZ radial)

                        int comando2 = scanner.nextInt();
                        scanner.nextLine();

                        switch (comando2) {
                            case 1:
                                robo.addSensor(sensorComum20);
                                break;
                            case 2:
                                robo.addSensor(sensorComum50);
                                break;
                            case 3:
                                robo.addSensor(sensorAltitude80XY20);
                                break;
                            case 4:
                                robo.addSensor(sensorAltitude40XY10);
                                break;
                            case 5:
                                robo.addSensor(sensorClasse50);
                                break;
                            case 6:
                                robo.addSensor(sensorClasse100);
                                break;
                            default:
                                System.out.printf("Opção invalida\n");

                        }
                        System.out.printf("Sensor adicionado ao robô %s com sucesso\n", robo.getNome());
                        break;
                    }

                case 2: // Monitorar ambiente
                    System.out.printf("Digite o nome do robô que irá monitorar seu arredor. Opções: %s\n", nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) { // Escolha do robo
                        case "teste1":
                            if (robosVivos.contains(robo1))
                                robo = robo1;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste2":
                            if (robosVivos.contains(robo2))
                                robo = robo2;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste3":
                            if (robosVivos.contains(robo3))
                                robo = robo3;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste4":
                            if (robosVivos.contains(robo4))
                                robo = robo4;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        default:
                            System.out.println("Nome de robô inválido.");
                            continue;
                    }
                    if (robo != null) {
                        robo.monitorar();
                    }
                    break;

                case 3: // Exibir posição de um robo
                    System.out.printf("Digite o nome do robô que irá exibir sua posição. Opções: %s\n", nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) { // Escolha do robo
                        case "teste1":
                            if (robosVivos.contains(robo1))
                                robo = robo1;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste2":
                            if (robosVivos.contains(robo2))
                                robo = robo2;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste3":
                            if (robosVivos.contains(robo3))
                                robo = robo3;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste4":
                            if (robosVivos.contains(robo4))
                                robo = robo4;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        default:
                            System.out.println("Nome de robô inválido.");
                            continue;
                    }
                    if (robo != null)
                        robo.exibirPosicao();

                    break;

                case 4: // Movimento em XYZ (subir/descer depois XY)
                    System.out.printf("Digite o nome do robô que irá se mover. Opções: %s\n",nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) { // Escolha do robo
                        case "teste1":
                            if (robosVivos.contains(robo1))
                                robo = robo1;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste2":
                            if (robosVivos.contains(robo2))
                                robo = robo2;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste3":
                            if (robosVivos.contains(robo3))
                                robo = robo3;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste4":
                            if (robosVivos.contains(robo4))
                                robo = robo4;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        default:
                            System.out.println("Nome de robô inválido.");
                            continue;
                    }

                    if (robo != null) { // Coordenadas XY
                        System.out.printf("Digite o valor de deltaX que deseja mover o robô %s: ", robo.getNome());
                        int deltaX = scanner.nextInt();
                        System.out.printf("Digite o valor de deltaY que deseja mover o robô %s: ", robo.getNome());
                        int deltaY = scanner.nextInt();

                        // Se for robo aereo pode mover em Z

                        if (robo instanceof RoboAereo) {
                            System.out.printf("Digite o valor de deltaZ que deseja mover o robô %s: ", robo.getNome());
                            int deltaZ = scanner.nextInt();
                            if (deltaZ >= 0) {
                                ((RoboAereo) robo).subir(deltaZ);
                            } else {
                                ((RoboAereo) robo).descer(Math.abs(deltaZ));
                            }
                        }

                        robo.moverPara(deltaX, deltaY);

                        System.out.printf("Robo parou em (%d,%d,%d)\n",robo.getPosX(),robo.getPosY(),robo.getAltitude());
                    }
                    break;
                case 5: // Metodos especificos de robos (explodir/bombardear/carregar....)
                    System.out.printf("Digite o nome do robô que irá usar a habilidade específica. Opções: %s\n",nomes);
                    nomeRobo = scanner.nextLine();
                    robo = null;
                    switch (nomeRobo) { // Escolha do robo
                        case "teste1":
                            if (robosVivos.contains(robo1))
                                robo = robo1;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste2":
                            if (robosVivos.contains(robo2))
                                robo = robo2;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste3":
                            if (robosVivos.contains(robo3))
                                robo = robo3;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        case "teste4":
                            if (robosVivos.contains(robo4))
                                robo = robo4;
                            else
                                System.out.println("Robô foi morto.");
                            break;
                        default:
                            System.out.println("Nome de robô inválido.");
                            continue;
                    }
                    if (robo != null) {
                        switch (robo.getNome()){
                            case "teste1":  // Robo bombardeiro pode bombardear ou carregar bombas
                                System.out.println("Se deseja bombardear digite [1] ou [2] para carregar bombas");
                                int comandoBombas = scanner.nextInt();
                                if (comandoBombas == 1){
                                    List<Robo> robosAtingidos = ((RoboBombardeiro) robo).bombardear();
                                    if (robosAtingidos != null)
                                        for (Robo roboAtingido : robosAtingidos) {
                                            robosVivos.remove(roboAtingido);
                                        }
                                }else{
                                    System.out.printf("Digite a quantidade de bombas que deseja carregar o robô %s: ", robo.getNome());
                                    int bombas = scanner.nextInt();
                                    ((RoboBombardeiro) robo).carregarBombas(bombas);
                                }
                                break;

                            case "teste2":  // Robo explosivo explode
                                System.out.printf("O robô %s está prestes a explodir...\n", robo.getNome());
                                Thread.sleep(2000);
                                List<Robo> robosAtingidos = ((RoboExplosivo) robo).explodir();
                                for (Robo roboAtingido : robosAtingidos) {
                                    robosVivos.remove(roboAtingido);
                                }
                                break;

                            case "teste3":  // Robo combustivel abastece combustivel
                                System.out.printf("Digite a quantidade de gasolina que deseja abastecer o robô %s: ", robo.getNome());
                                int gasolina = scanner.nextInt();
                                ((RoboCombustivel) robo).abastecer(gasolina);
                                break;

                            case "teste4":  // Robo solar carrega a bateria com o sol
                                System.out.printf("O robô %s está prestes a carregar %.2f energia pelo painel solar\n", robo.getNome(), ((RoboSolar) robo).getPotenciaPainelSolar());
                                ((RoboSolar) robo).carregar();
                                break;
                        }
                    }
                    break;
                case 6: // Muda o periodo do ambiente
                    System.out.printf("Digite o novo periodo do dia do ambiente: 'Dia', 'Noite'\n");
                    nomeRobo = scanner.nextLine();
                    if(nomeRobo.equals("Dia") || nomeRobo.equals("Noite")) {
                        ambiente.mudarTempo(nomeRobo);
                        System.out.printf("O período do ambiente é %s\n",nomeRobo);
                    }else{
                        System.out.printf("\"%s\" não corresponde a uma das opções dadas, tente inserir uma opção válida.\n",nomeRobo);
                    }
                    break;

                case 7:
                    ambiente.verificarColisoes();
                    break;
                case 8:
                    System.out.println("Finalizando o programa...");
                    scanner.close();
                    return;
                default:
                    System.out.printf("Opção invalida\n");
            }
        }

 */
    }
}