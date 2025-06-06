package lab03;

import lab03.obstaculos.Obstaculo;
import lab03.obstaculos.TipoObstaculo;
import lab03.robos.Robo;
import lab03.robos.RoboAereo;
import lab03.robos.robos_aereos.RoboBombardeiro;
import lab03.robos.robos_aereos.RoboExplosivo;
import lab03.robos.robos_terrestres.RoboCombustivel;
import lab03.robos.robos_terrestres.RoboSolar;
import lab03.sensores.Sensor;
import lab03.sensores.SensorAltitude;
import lab03.sensores.SensorClasse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        Ambiente ambiente = new Ambiente(100, 100, 100);

        Robo robo1 = new RoboBombardeiro(ambiente, "teste1", "norte", 0, 0, 0, 120, 10);
        Robo robo2 = new RoboExplosivo(ambiente, "teste2", "sul", 0, 0, 0, 100, 5);
        Robo robo3 = new RoboCombustivel(ambiente, "teste3", "leste", 0, 0, 50, 100);
        Robo robo4 = new RoboSolar(ambiente, "teste4", "oeste", 0, 0, 80, 20);

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

        // TESTES: --------------------------------

        System.out.printf("\n------- INICIO DOS TESTES: ---------\n");

        // Movimento novo de robos terrestres: (vel. maxima era unicimensional --> radial):

        System.out.printf("MOVIMENTO DO ROBO TERRESTRE (radial com vel. max):\n");
        System.out.printf("Tenta mover acima da vel. maxima e depois abaixo da vel. maxima\n");

        robo4.exibirPosicao();
        robo4.mover(70,40);
        robo4.mover(70,30);
        robo4.exibirPosicao();
        robo4.setPosX(0);
        robo4.setPosY(0);


        // Robo explosivo novo (explosao unidimensional --> radial):

        System.out.printf("\nEXPLOSAO RADIAL:\n");
        System.out.printf("Robo explosivo consegue atingir apenas um robo em seu raio de 5\n");

        Robo robo5 = new RoboExplosivo(ambiente, "teste5", "sul", 40, 40, 10, 100, 5);
        Robo robo6 = new RoboExplosivo(ambiente, "teste6", "sul", 43, 43, 13, 100, 5);
        Robo robo7 = new RoboExplosivo(ambiente, "teste7", "sul", 43, 43, 12, 100, 5);
        robo5.exibirPosicao();
        robo6.exibirPosicao();
        robo7.exibirPosicao();

        ((RoboExplosivo)robo5).explodir();


        // Colisão com obstaculos para movimento XY:

        System.out.printf("\nCOLISAO ENTRE ROBOS E OBSTACULOS EM XY\n");

        System.out.printf("\nColisao no canto do obstaculo:\n");
        robo4.mover(7,7);

        System.out.printf("\nColisao no lado inferior do obstaculo:\n");
        robo4.setPosX(6);
        robo4.setPosY(0);
        robo4.exibirPosicao();
        robo4.mover(0,10);

        System.out.printf("\nColisao no lado superior do obstaculo:\n");
        robo4.setPosX(6);
        robo4.setPosY(10);
        robo4.exibirPosicao();
        robo4.mover(0,-10);

        System.out.printf("\nColisao no lado esquerdo do obstaculo:\n");
        robo4.setPosX(0);
        robo4.setPosY(6);
        robo4.exibirPosicao();
        robo4.mover(10,0);

        System.out.printf("\nColisao no lado direito do obstaculo:\n");
        robo4.setPosX(10);
        robo4.setPosY(6);
        robo4.exibirPosicao();
        robo4.mover(-10,0);

        robo4.setPosX(0);
        robo4.setPosY(0);

        // Colisão com obstaculos em subir/descer:

        System.out.printf("\nCOLISAO COM OBSTACULOS EM SUBIR/DESCER\n");

        System.out.printf("\nRobo descendo em um obstaculo:\n");
        robo1.setPosX(6);
        robo1.setPosY(6);
        robo1.setAltitude(10);
        robo1.exibirPosicao();
        ((RoboAereo)robo1).descer(10);

        System.out.printf("\nRobo subindo em um obstaculo:\n");

        Obstaculo pedra2 = new Obstaculo(5,5,7,7,15,TipoObstaculo.ROCHA);
        ambiente.adicionarObstaculo(pedra2);

        robo1.setPosX(6);
        robo1.setPosY(6);
        robo1.setAltitude(10);
        robo1.exibirPosicao();
        ((RoboAereo)robo1).subir(10);

        pedra2 = null;

        // Sensor basico e altitude:

        System.out.printf("\nSENSOR BASICO, SENSOR DE ALTITUDE E DE CLASSE\n");

        robo1.setPosX(1);
        robo1.setPosY(1);

        robo6.setPosX(1);
        robo6.setPosY(1);
        robo6.setAltitude(0);
        robo6.addSensor(sensorComum20);
        robo6.addSensor(sensorAltitude40XY10);
        robo6.addSensor(sensorClasse50);

        System.out.printf("\nHá 3 robos em (0,0,0), um em (1,1,14) e o robo com sensores em (1,1,0)\n");

        robo6.monitorar();

        robo1.setPosX(0);
        robo1.setPosY(0);
        robo1.setAltitude(0);

        robo6.setAltitude(50);
        ((RoboExplosivo)robo6).explodir();


        System.out.printf("\n------FIM DOS TESTES------------\n");

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

                        robo.mover(deltaX, deltaY);

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
                    ambiente.detectarColisoes();
                    break;
                case 8:
                    System.out.println("Finalizando o programa...");
                    scanner.close();
                    return;
                default:
                    System.out.printf("Opção invalida\n");
            }
        }
    }
}
