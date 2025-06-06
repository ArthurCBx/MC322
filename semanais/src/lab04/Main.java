package lab04;

import lab04.comunicacao.CentralComunicacao;
import lab04.entidade.Entidade;
import lab04.entidade.TipoEntidade;
import lab04.entidade.robos.*;
import lab04.entidade.robos.robos_aereos.Explosivos;
import lab04.entidade.robos.robos_terrestres.Energizavel;
import lab04.excecoes.*;
import lab04.obstaculos.Obstaculo;
import lab04.obstaculos.TipoObstaculo;
import lab04.entidade.robos.robos_aereos.RoboBombardeiro;
import lab04.entidade.robos.robos_aereos.RoboExplosivo;
import lab04.entidade.robos.robos_terrestres.RoboCombustivel;
import lab04.entidade.robos.robos_terrestres.RoboSolar;
import lab04.sensores.Sensor;
import lab04.sensores.SensorAltitude;
import lab04.sensores.SensorClasse;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // TESTES: --------------------------------

        Ambiente tambiente = new Ambiente(50, 50, 50);

        Robo roboteste1 = new RoboExplosivo(tambiente, "Teste1", 0, 0, 0, 20, 20);
        Robo roboteste2 = new RoboBombardeiro(tambiente, "Teste2", 1, 1, 0, 30, 20);
        Robo roboteste3 = new RoboSolar(tambiente, "Teste3", 2, 2, 30, 40);
        Robo roboteste4 = new RoboCombustivel(tambiente, "Teste4", 3, 3, 30, 200);

        Sensor tsensorComum20 = new Sensor(20);
        SensorAltitude tsensorAltitude80XY20 = new SensorAltitude(80, 20);
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
        } catch (ForaDosLimitesException e) {
            System.out.println(e.getMessage());  // (-1,-1,-1) está fora dos limites
        }

        try {    // Continuação de dentro dos limites:
            roboteste1.moverPara(-1, -1, -1);
        } catch (ForaDosLimitesException e) {
            System.out.println(e.getMessage());  // (-1,-1,0) está fora dos limites [robo aereo so vai ate o chao {0}]
        }

        System.out.println("\n\nVERIFICAR COLISOES:------------");

        try {
            tambiente.moverEntidade(roboteste1, 1, 1, 0);   // Posição é a mesma do roboteste2
            tambiente.verificarColisoes();
        } catch (ColisaoException e) {
            System.out.println(e.getMessage());  // Colisão esperada
            tambiente.moverEntidade(roboteste1, 0, 0, 0);   //  retorna para posição inicial

        }

        try {    // Sem colisões:
            tambiente.verificarColisoes();
        } catch (ColisaoException e) {
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

        try {
            roboteste1.desligar();
            roboteste1.moverPara(10, 10, 10);

        } catch (RoboDesligadoException e) {
            System.out.println(e.getMessage());
            roboteste1.ligar();
        }

        System.out.printf("\n COMUNICAÇAO:\n");


        try {

            roboteste1.enviarMensagem(roboteste2, "Bom dia");

            System.out.println();

            roboteste2.desligar();
            roboteste1.enviarMensagem(roboteste2, "Boa noite");  // Falha pelo destinatario estar desligado


        } catch (ErroComunicacaoException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println();

            roboteste1.desligar();
            roboteste2.ligar();
            roboteste1.enviarMensagem(roboteste2, "Boa noite");  // Falha pelo remetente estar desligado


        } catch (ErroComunicacaoException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println();

            roboteste1.ligar();
            roboteste1.enviarMensagem(roboteste2, "Boa noite");


        } catch (ErroComunicacaoException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nMensagens armazenadas na cetral:");

        CentralComunicacao.exibirMensagens();

        System.out.printf("\n TAREFAS ESPECIFICAS DOS ROBOS:\n\n");

        try {
            System.out.printf("Robo combustivel:\n");
            roboteste4.executarTarefa();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.printf("\nRobo Solar:\n");
            tambiente.moverEntidade(roboteste3, 30, 30, 0);
            tambiente.moverEntidade(roboteste2, 30, 30, 5);

            roboteste3.executarTarefa();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.printf("\nRobo Bombardeiro:(tambem testa a remoção de entidades)\n");
            tambiente.moverEntidade(roboteste3, 30, 30, 0);
            tambiente.moverEntidade(roboteste2, 30, 30, 5);

            roboteste2.executarTarefa();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.printf("\nNumero de entidades presentes(eram 5 inicialmente): %d\n\n", tambiente.getListaEntidades().size());

        try {
            System.out.printf("\nRobo Explosivo:\n");
            tambiente.moverEntidade(roboteste1, 50, 50, 5);     // Os robos foram posicionados para que a primeira verificação não encontre robos
            tambiente.moverEntidade(roboteste2, 30, 45, 20);    // e seja necessario que o robo se locomova automaticamente para os encontrar
            tambiente.moverEntidade(roboteste4, 45, 30, 0);     // É possivel que o robo não consiga encontrar nenhum outro robo ou colida em outras entidades

            roboteste1.executarTarefa();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Deleta os objetos utilizados nos testes:

        while (!tambiente.getListaEntidades().isEmpty())
            tambiente.removerEntidade(tambiente.getListaEntidades().get(0));

        tsensorComum20 = null;
        tsensorClasse20 = null;
        tsensorAltitude80XY20 = null;
        tambiente = null;
        CentralComunicacao.deletarMensagens();

        System.out.printf("\n------FIM DOS TESTES------------\n");


        System.out.printf("\nMENU ITERATIVO:-------------------\n\n");

        // MENU INTERATIVO: ------------------------------------

        Ambiente ambiente = new Ambiente(100, 100, 200);

        ambiente.inicializarMapa();

        Robo robo1 = new RoboBombardeiro(ambiente, "Bombardeiro", 5, 0, 10, 80, 10);
        Robo robo2 = new RoboExplosivo(ambiente, "Explosivo", 0, 5, 20, 100, 5);
        Robo robo3 = new RoboCombustivel(ambiente, "Combustivel", 10, 0, 30, 100);
        Robo robo4 = new RoboSolar(ambiente, "Solar", 0, 10, 60, 40);

        Sensor sensorComum25 = new Sensor(25);
        SensorAltitude sensorAltitude60XY30 = new SensorAltitude(60, 30);
        SensorClasse sensorClasse50 = new SensorClasse(50);

        robo1.addSensor(sensorAltitude60XY30);
        robo2.addSensor(sensorClasse50);
        robo3.addSensor(sensorComum25);
        robo4.addSensor(sensorComum25);

        Obstaculo obstaculoRocha = new Obstaculo(5, 5, 7, 7, 0, TipoObstaculo.ROCHA);
        Obstaculo obstaculoArvore = new Obstaculo(20, 20, 30, 30, 0, TipoObstaculo.ARVORE);
        Obstaculo obstaculoParede = new Obstaculo(70, 70, 90, 90, 0, TipoObstaculo.PAREDE);
        Obstaculo obstaculoAnimal = new Obstaculo(31, 31, 33, 33, 0, TipoObstaculo.ANIMAL);

        ambiente.adicionarEntidade(obstaculoRocha);
        ambiente.adicionarEntidade(obstaculoArvore);
        ambiente.adicionarEntidade(obstaculoParede);
        ambiente.adicionarEntidade(obstaculoAnimal);

        ArrayList<Robo> robosVivos;

        String nomeRobo = robo1.getNome();
        Robo robo = null;
        Robo robosel = robo1;
        int menu = 0;
        int comando = 0;
        int selecao = 0;
        int newpos[] = {0, 0, 0};
        int d = 1;
        double norma = 0;
        String mensagem;

        while (true) {

            robosVivos = ambiente.getListaEntidades().stream().filter(entidade -> entidade.getTipoEntidade() == TipoEntidade.ROBO).map(entidade -> (Robo) entidade).collect(Collectors.toCollection(ArrayList::new));

            if (robosVivos.isEmpty()) {
                System.out.println("\nTodos os robôs foram destruídos. Finalizando o programa...");
                return;
            }

            if (robosVivos.contains(robosel)) {
                nomeRobo = robosel.getNome();
            } else {
                robosel = robosVivos.get(0);
                nomeRobo = robosel.getNome();
            }


            switch (menu) {

                case (0):    // Menu principal

                    System.out.println(
                            "\nDigite o comando que será realizado:\n" +
                                    "[1] - Status do ambiente\n" +                              //XXXXXXX terminado
                                    "[2] - Vizualizar o mapa do ambiente\n" +                   //XXXXXXX terminado
                                    "[3] - Alterar periodo do ambiente\n" +                     //XXXXXXX terminado
                                    "[4] - Status do robo selecionado\n" +                      //XXXXXXX terminado
                                    "[5] - Mover o robo selecionado\n" +                        //XXXXXXX terminado
                                    "[6] - Escolher outras funcoes do robo selecionado\n" +     //XXXXXXX faltam os comandos
                                    "[7] - Listar robos por tipo ou estado\n" +                 //XXXXXXX terminado
                                    "[8] - Listar mensagens trocadas pelos robos\n" +           //XXXXXXX terminado
                                    "[9] - Selecionar um novo robo\n" +                         //XXXXXXX terminado
                                    "[10] - Finalizar o programa\n" +                           //XXXXXXX terminado
                                    "[11] - Desativar/Ativar delays entre menus\n" +            //XXXXXXX terminado
                                    "Robo Selecionado: " + nomeRobo);


                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        selecao = scanner.nextInt();
                    } else {
                        selecao = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    switch (selecao) {
                        case (1):   // Status do ambiente

                            System.out.println("\nVerificando se há colisões presentes ou robos fora dos limites:");
                            System.out.println("\n1 - Colisões:");
                            ambiente.verificarColisoes();
                            System.out.println("\n2 - Robos fora dos limites:");
                            for (Robo roboaux : robosVivos)
                                try {
                                    ambiente.dentroDosLimites(roboaux.getX(), roboaux.getY(), roboaux.getZ());
                                    System.out.println("O robo " + roboaux.getNome() + " está dentro dos limites");
                                } catch (ForaDosLimitesException e) {
                                    System.out.println("O robo " + roboaux.getNome() + " está fora dos limites (" + roboaux.getX() + "," + roboaux.getY() + "," + roboaux.getZ() + ")");
                                }
                            System.out.println("\nAlém disso, o periodo do ambiente é " + ambiente.getPeriodo());
                            System.out.printf("E há " + robosVivos.size() + " robos vivos\n");
                            System.out.println("\nVerificação do status do ambiente finalizada\n");

                            System.out.println("Digite algo para retornar ao menu principal");
                            scanner.nextLine();

                            break;

                        case (2):   // Vizualizar o mapa do ambiente

                            System.out.println("Vizualização do mapa do ambiente:\n");
                            ambiente.vizualizarAmbiente();

                            System.out.println("Digite algo para retornar ao menu principal");
                            scanner.nextLine();

                            break;


                        case (3):   // Alterar periodo do ambiente
                            System.out.println("\nQual o novo periodo do ambiente? (atual: " + ambiente.getPeriodo() + "):\n" +
                                    "[0] - Cancelar Operação\n" +
                                    "[1] - Dia\n" +
                                    "[2] - Noite");

                            while (scanner.hasNext("\n")) {
                                scanner.skip("\n");
                            }
                            if (scanner.hasNextInt()) {
                                selecao = scanner.nextInt();
                            } else {
                                selecao = -1;
                                scanner.next();
                            }
                            scanner.nextLine();

                            if (selecao < 1 || selecao > 2) {
                                System.out.println("\nOperação cancelada, retornando para o menu principal");
                                Thread.sleep(1000 * d);
                            } else {
                                if (selecao == 1) {
                                    ambiente.mudarTempo("Dia");
                                    System.out.println("Periodo alterado para Dia");
                                } else {
                                    ambiente.mudarTempo("Noite");
                                    System.out.println("Periodo alterado para Noite");
                                }
                            }

                            Thread.sleep(1000 * d);

                            break;

                        case (4):   // Status do robo selecionado

                            System.out.println("\nVerificando o status do robo" + nomeRobo + ":\n" +
                                    robosel.getDescricao());
                            System.out.println("Representação no mapa: " + robosel.getRepresentacao());

                            if (robosel instanceof Sensoreavel) {
                                System.out.printf("Sensores: ");
                                if (!(robosel.getSensores() == null)) {
                                    if (robosel.getSensores().contains(sensorComum25))
                                        System.out.printf("SensorComum25 ");
                                    if (robosel.getSensores().contains(sensorAltitude60XY30))
                                        System.out.printf("SensorAltitude60XY30 ");
                                    if (robosel.getSensores().contains(sensorClasse50))
                                        System.out.printf("SensorClasse50");
                                } else {
                                    System.out.printf("Nenhum");
                                }
                                System.out.println();
                            }

                            System.out.println("\nVerificação do status do robo finalizada\n");

                            System.out.println("Digite algo para retornar ao menu principal");
                            scanner.nextLine();

                            break;

                        case (5):   // Mover o robo selecionado
                            menu = 1;   // Vai para outro menu [proximo loop da main]
                            break;

                        case (6):   // Escolher outras funcoes do robo selecionado
                            menu = 2;   // Vai para outro menu [proximo loop da main]
                            break;

                        case (7):   // Listar robos por tipo ou estado
                            menu = 3;   // Vai para outro menu [proximo loop da main]
                            break;

                        case (8):   // Listar mensagens trocadas pelos robos

                            System.out.println("\nMensagens armazenadas na Central de Comunicação:");
                            CentralComunicacao.exibirMensagens();

                            System.out.println("\nDigite algo para retornar ao menu principal");
                            scanner.nextLine();

                            break;

                        case (9):   // Selecionar um novo robo
                            menu = 4;   // Vai para outro menu [proximo loop da main]
                            break;

                        case (10):   // Finalizar o programa
                            System.out.println("\nFinalizando o programa...");
                            scanner.close();
                            return;

                        case (11):
                            d = d == 1 ? 0 : 1;
                            break;

                        default:    // Comando inválido
                            System.out.println("\nComando inválido, selecione uma opção válida");
                            Thread.sleep(1000 * d);
                            break;

                    }

                    break;

                case (1):    // Mover robo selecionado

                    System.out.println(
                            "\n[O robo " + nomeRobo + " está em (" + robosel.getX() + "," + robosel.getY() + "," + robosel.getZ() + ")]\n" +
                                    "Digite o valor da nova coordenada X do robo" + nomeRobo + "\n(ou '-1' para cancelar a operação) [Limites (0 a " + ambiente.getComprimento() + ")]");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        newpos[0] = scanner.nextInt();
                    } else {
                        newpos[0] = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (newpos[0] == -1) {
                        System.out.println("\nOperação cancelada, retornando para o menu principal");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    if (newpos[0] > ambiente.getComprimento() || newpos[0] < -1) {
                        System.out.println("\nCoordenada fora dos limites do ambiente, cancelando a operação\n");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    System.out.println("\nDigite o valor da nova coordenada Y do robo" + nomeRobo + "\n(ou '-1' para cancelar a operação) [Limites (0 a " + ambiente.getLargura() + ")]");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        newpos[1] = scanner.nextInt();
                    } else {
                        newpos[1] = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (newpos[1] == -1) {
                        System.out.println("\nOperação cancelada, retornando para o menu principal");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    if (newpos[1] > ambiente.getLargura() || newpos[1] < -1) {
                        System.out.println("\nCoordenada fora dos limites do ambiente, cancelando a operação\n");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    if (robosel instanceof RoboTerrestre) {
                        newpos[2] = robosel.getZ();
                        comando = 1;
                        menu = 0;
                        break;
                    }

                    System.out.println("\nDigite o valor da nova coordenada Z do robo" + nomeRobo + "\n(ou '-1' para cancelar a operação) [Limites (0 a " + Math.min(ambiente.getAltura(), ((RoboAereo) robosel).getAltitudeMaxima()) + ")]");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        newpos[2] = scanner.nextInt();
                    } else {
                        newpos[2] = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (newpos[2] == -1) {
                        System.out.println("\nOperação cancelada, retornando para o menu principal");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    if (newpos[2] > Math.min(ambiente.getAltura(), ((RoboAereo) robosel).getAltitudeMaxima()) || newpos[2] < -1) {
                        System.out.println("\nCoordenada fora dos limites do ambiente ou acima da altitude maxima do robo, cancelando a operação\n");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    comando = 1;
                    menu = 0;

                    break;

                case (2):    // Escolher funcao do robo

                    System.out.println("\nEscolha a função do robo selecionado:\n" +
                            "[0] - Cancelar Operação\n" +
                            "[1] - Adicionar Sensor\n" +
                            "[2] - Acionar Sensores\n" +
                            "[3] - Ligar\n" +
                            "[4] - Desligar\n" +
                            "[5] - Enviar mensagem");


                    if (robosel instanceof RoboBombardeiro) {   // Opções especificas do Robo Bombardeiro

                        System.out.println("" +
                                "[6] - Movimento Automatico\n" +
                                "[7] - Carregar Bombas (tem " + ((RoboBombardeiro) robosel).getBombas() + " bombas)\n" +
                                "[8] - Bombardear\n" +
                                "[9] - Bombardeio Automatico");

                        while (scanner.hasNext("\n")) {
                            scanner.skip("\n");
                        }
                        if (scanner.hasNextInt()) {
                            selecao = scanner.nextInt();
                        } else {
                            selecao = -1;
                            scanner.next();
                        }
                        scanner.nextLine();

                        if (selecao == 0) {
                            System.out.println("\nOperação cancelada");
                            Thread.sleep(1000 * d);
                            menu = 0;
                            break;
                        }

                        if (selecao > 9 || selecao < 1) {
                            System.out.println("\nComando inválido, selecione uma opção válida");
                            Thread.sleep(1000 * d);
                            break;
                        }

                        comando = selecao + 1;

                    } else {
                        if (robosel instanceof RoboExplosivo) { // Opções especificas do Robo Explosivo

                            System.out.println("" +
                                    "[6] - Movimento Automatico\n" +
                                    "[7] - Explodir\n" +
                                    "[8] - Explodir Automaticamente");

                            while (scanner.hasNext("\n")) {
                                scanner.skip("\n");
                            }
                            if (scanner.hasNextInt()) {
                                selecao = scanner.nextInt();
                            } else {
                                selecao = -1;
                                scanner.next();
                            }
                            scanner.nextLine();

                            if (selecao == 0) {
                                System.out.println("\nOperação cancelada");
                                Thread.sleep(1000 * d);
                                menu = 0;
                                break;
                            }

                            if (selecao > 8 || selecao < 1) {
                                System.out.println("\nComando inválido, selecione uma opção válida");
                                Thread.sleep(1000 * d);
                                break;
                            }

                            comando = selecao > 6 ? selecao + 4 : selecao + 1;

                        } else {
                            if (robosel instanceof RoboCombustivel) {   // Opções especificas do Robo Combustivel

                                System.out.println("" +
                                        "[6] - Abastecer\n" +
                                        "[7] - Aumentar velocidade maxima (precisa de 100 de combustivel, tem " + ((RoboCombustivel) robosel).getCombustivel() + ")");

                                while (scanner.hasNext("\n")) {
                                    scanner.skip("\n");
                                }
                                if (scanner.hasNextInt()) {
                                    selecao = scanner.nextInt();
                                } else {
                                    selecao = -1;
                                    scanner.next();
                                }
                                scanner.nextLine();

                                if (selecao == 0) {
                                    System.out.println("\nOperação cancelada");
                                    Thread.sleep(1000 * d);
                                    menu = 0;
                                    break;
                                }

                                if (selecao > 7 || selecao < 1) {
                                    System.out.println("\nComando inválido, selecione uma opção válida");
                                    Thread.sleep(1000 * d);
                                    break;
                                }

                                comando = selecao > 5 ? selecao + 7 : selecao + 1;

                            } else {                                    // Opções especificas do Robo Solar

                                System.out.println("" +
                                        "[6] - Movimento Automatico\n" +
                                        "[7] - Carregar bateria\n" +
                                        "[8] - Exploração Automatica");

                                while (scanner.hasNext("\n")) {
                                    scanner.skip("\n");
                                }
                                if (scanner.hasNextInt()) {
                                    selecao = scanner.nextInt();
                                } else {
                                    selecao = -1;
                                    scanner.next();
                                }
                                scanner.nextLine();

                                if (selecao == 0) {
                                    System.out.println("\nOperação cancelada");
                                    Thread.sleep(1000 * d);
                                    menu = 0;
                                    break;
                                }

                                if (selecao > 8 || selecao < 1) {
                                    System.out.println("\nComando inválido, selecione uma opção válida");
                                    Thread.sleep(1000 * d);
                                    break;
                                }

                                comando = selecao > 6 ? selecao + 8 : selecao + 1;

                            }
                        }
                    }

                    menu = -1;

                    break;

                case (3):    // Listar robos por tipo/estado

                    System.out.println("\nEscolha o filtro para a listagem dos robos:\n" +
                            "[0] - Cancelar Operação\n" +
                            "[1] - Ligados\n" +
                            "[2] - Desligados\n" +
                            "[3] - Aereos\n" +
                            "[4] - Terrestres\n" +
                            "[5] - Sensoreaveis\n" +
                            "[6] - Comunicaveis\n" +
                            "[7] - Autonomos\n" +
                            "[8] - Explosivos\n" +
                            "[9] - Energizaveis");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        selecao = scanner.nextInt();
                    } else {
                        selecao = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    switch (selecao) {

                        case (0):
                            System.out.println("\nOperação cancelada");
                            Thread.sleep(1000 * d);
                            menu = 0;
                            break;

                        case (1):
                            System.out.println("\nRobos Ligados:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> ((Robo) entidade2).getEstado() == Estado.LIGADO).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (2):
                            System.out.println("\nRobos Desligados:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> ((Robo) entidade2).getEstado() == Estado.DESLIGADO).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (3):
                            System.out.println("\nRobos Aereos:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof RoboAereo).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (4):
                            System.out.println("\nRobos Terrestres:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof RoboTerrestre).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (5):
                            System.out.println("\nRobos Sensoreaveis:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof Sensoreavel).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (6):
                            System.out.println("\nRobos Comunicaveis:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof Comunicavel).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (7):
                            System.out.println("\nRobos Autonomos:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof Autonomo).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (8):
                            System.out.println("\nRobos Explosivos:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof Explosivos).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        case (9):
                            System.out.println("\nRobos Energizaveis:");
                            for (Robo roboaux : robosVivos.stream().filter(entidade2 -> entidade2 instanceof Energizavel).map(entidade -> (Robo) entidade).toList())
                                System.out.println(roboaux.getNome());

                            menu = 0;
                            break;

                        default:
                            System.out.println("\nComando inválido, selecione uma opção válida");
                            Thread.sleep(1000 * d);
                            break;

                    }

                    if (selecao > 0 && selecao < 10) {
                        System.out.println("\nDigite algo para retornar ao menu principal");
                        scanner.nextLine();

                    }

                    break;

                case (4):

                    System.out.println("\nQual robo será selecionado?\n" +
                            "[0] - Cancelar Operação");
                    int i = 0;
                    for (Robo roboaux : robosVivos) {
                        i++;
                        System.out.println("[" + i + "] - " + roboaux.getNome());
                    }
                    System.out.println("Atual: " + nomeRobo);

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        selecao = scanner.nextInt();
                    } else {
                        selecao = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (selecao == 0) {
                        System.out.println("\nOperação cancelada");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        break;
                    }

                    if (selecao < 1 || selecao > i) {
                        System.out.println("\nComando inválido, selecione uma opção válida");
                        Thread.sleep(1000 * d);
                    } else {
                        robosel = robosVivos.get(selecao - 1);

                        System.out.println("\nRobo " + robosel.getNome() + " selecionado com sucesso");
                        Thread.sleep(1000 * d);
                        menu = 0;
                    }

                    break;

                default:
                    break;

            }


            switch (comando) {

                case (0):
                    break;

                case (1):    // Mover Robo

                    System.out.println("\nExecutando o movimento do robo " + nomeRobo + " para (" + newpos[0] + "," + newpos[1] + "," + newpos[2] + "):");

                    try {
                        robosel.moverPara(newpos[0], newpos[1], newpos[2]);
                        System.out.println("Movimentação realizada com sucesso");
                    } catch (ColisaoException e) {
                        System.out.println(e.getMessage() + "\n\nMovimentação concluida");
                        Thread.sleep(2000 * d);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(2250 * d);
                    }

                    comando = 0;

                    break;

                case (2):    // Adicionar sensor

                    if (!(robosel instanceof Sensoreavel)) {
                        System.out.println("\nO robo selecionado não é Sensoreavel");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;
                    }

                    System.out.println("\nDigite o tipo de sensor que deseja adicionar. Opções:\n" +
                            "[0] - Cancelar operação\n" +
                            "[1] - SensorComum25\n" +
                            "[2] - SensorAltitude60XY30\n" +
                            "[3] - sensorClasse50\n");

                    // Sensor Comum25: Classe sensor base com raio de 25
                    // Sensor Altitude80XY20: Classe sensor altitude com raioZ de 60 e raio 30
                    // Sensor Classe50: Classe sensor classe com raio de 50 (XYZ radial)

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        selecao = scanner.nextInt();
                    } else {
                        selecao = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (selecao > 3 || selecao < 0) {
                        System.out.println("\nComando inválido, selecione uma opção válida");
                        Thread.sleep(1000 * d);
                        break;
                    }

                    switch (selecao) {

                        case (1):
                            robosel.addSensor(sensorComum25);
                            System.out.println("Sensor adicionado com sucesso");
                            break;

                        case (2):
                            robosel.addSensor(sensorAltitude60XY30);
                            System.out.println("Sensor adicionado com sucesso");
                            break;

                        case (3):
                            robosel.addSensor(sensorClasse50);
                            System.out.println("Sensor adicionado com sucesso");
                            break;

                        default:
                            System.out.println("\nOperação cancelada");
                            break;
                    }

                    Thread.sleep(1000 * d);
                    menu = 0;
                    comando = 0;

                    break;

                case (3):    // Acionar sensor

                    if (!(robosel instanceof Sensoreavel)) {
                        System.out.println("\nO robo selecionado não é Sensoreavel");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;
                    }

                    System.out.println("\nAcionando sensores do robo " + nomeRobo + ":");
                    try {
                        robosel.acionarSensores();
                    } catch (RoboDesligadoException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (4):    // Ligar
                    System.out.println();
                    robosel.ligar();
                    Thread.sleep(1000 * d);
                    menu = 0;
                    comando = 0;
                    break;

                case (5):    // Desligar
                    System.out.println();
                    robosel.desligar();
                    Thread.sleep(1000 * d);
                    menu = 0;
                    comando = 0;
                    break;

                case (6):    // Enviar mensagem

                    if (!(robosel instanceof Comunicavel)) {
                        System.out.println("\nO robo selecionado não é Comunicavel");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;
                    }

                    if (robosVivos.size() < 2) {
                        System.out.println("\nNão há outros robos vivos para receber mensagens");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;
                    }

                    System.out.println("\nQual robo receberá a mensagem?\n" +
                            "[0] - Cancelar Operação");

                    ArrayList<Robo> roboOutros = new ArrayList<Robo>();
                    for (Robo roboaux : robosVivos) {
                        if (!roboaux.equals(robosel)) {
                            roboOutros.add(roboaux);
                        }
                    }

                    int i = 0;
                    for (Robo roboaux : roboOutros) {
                        i++;
                        System.out.println("[" + i + "] - " + roboaux.getNome() + " (" + roboaux.getEstado() + ")");
                    }

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        selecao = scanner.nextInt();
                    } else {
                        selecao = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (selecao == 0) {
                        System.out.println("\nOperação cancelada");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;
                    }

                    if (selecao < 1 || selecao > i) {
                        System.out.println("\nComando inválido, cancelando operação");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;

                    }

                    robo = roboOutros.get(selecao - 1);

                    System.out.println("\nRobo " + robo.getNome() + " selecionado com sucesso");

                    System.out.println("\nDigite a mensagem que será enviada:");

                    mensagem = scanner.nextLine();
                    try {
                        robosel.enviarMensagem(robo, mensagem);
                    } catch (ErroComunicacaoException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (7):    // Movimento Automatico

                    System.out.println("\nQual a magnitude (norma) do movimento? (ou '-1' para cancelar a operação)");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextDouble()) {
                        norma = scanner.nextDouble();
                    } else {
                        norma = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    if (norma == -1) {
                        System.out.println("\nOperação cancelada");
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                        break;
                    }

                    if (norma < -1) {
                        System.out.println("\nComando inválido, selecione uma opção válida");
                        Thread.sleep(1000 * d);
                        break;
                    }

                    try {
                        ((Autonomo) robosel).moveAutomatico(norma);
                        System.out.println("\nMovimentação automatica realizada com sucesso");
                        robosel.exibirPosicao();
                    } catch (ColisaoException e) {
                        System.out.println(e.getMessage() + "\n\nMovimentação automatica concluida");
                        Thread.sleep(2000 * d);

                    } catch (ForaDosLimitesException e) {
                        System.out.println("O robo " + nomeRobo + " tentou sair do ambiente durante a movimentação automatica, finalizando a movimentação");
                        Thread.sleep(2000 * d);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(2250 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (8):    // Carregar Bombas

                    System.out.println("\nQuantas bombas deseja carregar? (Bombas: " + ((RoboBombardeiro) robosel).getBombas() + " de " + ((RoboBombardeiro) robosel).getCapacidadeBombas() + ") ('-1' para cancelar a operação)");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextInt()) {
                        selecao = scanner.nextInt();
                    } else {
                        selecao = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    ((RoboBombardeiro) robosel).carregarBombas(selecao);
                    Thread.sleep(1000 * d);
                    menu = 0;
                    comando = 0;
                    break;

                case (9):    // Bombardear

                    System.out.println();
                    try {
                        if (((RoboBombardeiro) robosel).getBombas() > 0)
                            System.out.println("\nIniciando bombardeio...");
                        Thread.sleep(750 * d);
                        ((Explosivos) robosel).explodir();

                    } catch (RoboDesligadoException e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(2250 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (10):   // Bombardeio Automatico

                    System.out.println("\nIniciando bombardeio automatico...");

                    try {
                        robosel.executarTarefa();
                        System.out.println("\nBombardeio Automatico realizado com sucesso");
                    } catch (ColisaoException e) {
                        System.out.println(e.getMessage() + "\n\nBombardeio automatico concluido");
                        Thread.sleep(2000 * d);

                    } catch (ForaDosLimitesException e) {
                        System.out.println("O robo " + nomeRobo + " tentou sair do ambiente durante o bombardeio automatico, finalizando a operação");
                        Thread.sleep(2000 * d);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(2250 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (11):   // Explodir

                    System.out.println("\nExplodindo...");

                    try {
                        Thread.sleep(750 * d);
                        ((Explosivos) robosel).explodir();
                    } catch (RoboDesligadoException e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (12):   // Explodir automaticamente

                    System.out.println("\nIniciando a explosão automatica...\n");

                    try {
                        robosel.executarTarefa();
                    } catch (ColisaoException e) {
                        System.out.println(e.getMessage() + "\n\nExplosão automatica concluido(o robo ainda está vivo)");
                        Thread.sleep(2000 * d);

                    } catch (RoboDesligadoException e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(2250 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (13):   // Abastecer

                    System.out.println("\nPor quanto combustivel o robo será abastecido? (Combustivel: " + ((RoboCombustivel) robosel).getCombustivel() + ") ('-1' para cancelar a operação)");

                    while (scanner.hasNext("\n")) {
                        scanner.skip("\n");
                    }
                    if (scanner.hasNextDouble()) {
                        norma = scanner.nextDouble();
                    } else {
                        norma = -1;
                        scanner.next();
                    }
                    scanner.nextLine();

                    ((Energizavel) robosel).carregar(norma);
                    Thread.sleep(1000 * d);
                    menu = 0;
                    comando = 0;

                    break;

                case (14):   // Aumentar velocidade maxima

                    System.out.println("\nAumentando a velocidade maxima:");
                    try {
                        robosel.executarTarefa();
                    } catch (SemCombustivelException e) {
                        System.out.printf(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(1000 * d);
                        menu = 0;
                        comando = 0;
                    }

                    break;

                case (15):   // Carregar bateria

                    System.out.println("\nUtilizando os paineis solares para carregar a bateria...");
                    Thread.sleep(1000 * d);

                    ((RoboSolar) robosel).carregar();
                    System.out.println("\nO robo carregou " + ((RoboSolar) robosel).getPotenciaPainelSolar() + " de energia e está com " + ((RoboSolar) robosel).getBateria());
                    Thread.sleep(2000 * d);
                    menu = 0;
                    comando = 0;

                    break;

                case (16):   // Exploração automatica

                    System.out.println("\nIniciando a exploração automatica...");

                    try {
                        robosel.executarTarefa();
                        System.out.println("\nExploração automatica realizado com sucesso");
                    } catch (ColisaoException e) {
                        System.out.println(e.getMessage() + "\n\nExploração automatica concluida");
                        Thread.sleep(2000 * d);

                    } catch (ForaDosLimitesException e) {
                        System.out.println("O robo " + nomeRobo + " tentou sair do ambiente durante a exploração automatica, finalizando a operação");
                        Thread.sleep(2000 * d);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        Thread.sleep(2000 * d);
                    } finally {
                        Thread.sleep(2250 * d);
                        menu = 0;
                        comando = 0;
                    }
                    break;

                default:
                    comando = 0;
                    break;

            }

        }

    }
}