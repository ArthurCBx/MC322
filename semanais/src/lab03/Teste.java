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
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Teste {

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


        while (true) {
            String nomes = robosVivos.stream()
                    .map(Robo::getNome)
                    .collect(Collectors.joining(", "));
            if (nomes.isEmpty()) {
                System.out.println("Todos os robôs foram destruídos. Finalizando o programa...");
                return;
            }

            System.out.println("Digite o comando que será realizado:\n" +
                    "[1] - Adicionar Sensor em um robô\n" +
                    "[2] - Comandar um robo para monitorar seu arredor\n" +
                    "[3] - Comandar um robo para mover-se\n" +
                    "[4] - Usar habilidade específica da classe de robô\n" +
                    "[5] - Finalizar o programa");
            int comando = scanner.nextInt();
            scanner.nextLine();

            switch (comando) {
                case 1:
                    System.out.printf("Digite o nome do robô que receberá o sensor. Opções: %s\n",nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) {
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


                        System.out.println("Digite o tipo de sensor que deseja adicionar. Opções:\n" +
                                "[1] - SensorComum20\n" +
                                "[2] - SensorComum50\n" +
                                "[3] - SensorAltitude80XY20\n" +
                                "[4] - SensorAltitude40XY10\n" +
                                "[5] - SensorClasse50\n" +
                                "[6] - SensorClasse100");
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
                        }
                        System.out.printf("Sensor adicionado ao robô %s com sucesso\n", robo.getNome());
                        break;
                    }

                case 2:
                    System.out.printf("Digite o nome do robô que irá monitorar seu arredor. Opções: %s\n", nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) {
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

                case 3:
                    System.out.printf("Digite o nome do robô que irá se mover. Opções: %s\n",nomes);
                    nomeRobo = scanner.nextLine();

                    robo = null;
                    switch (nomeRobo) {
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
                        System.out.printf("Digite o valor de deltaX que deseja mover o robô %s: ", robo.getNome());
                        int deltaX = scanner.nextInt();
                        System.out.printf("Digite o valor de deltaY que deseja mover o robô %s: ", robo.getNome());
                        int deltaY = scanner.nextInt();

                        if (robo instanceof RoboBombardeiro || robo instanceof RoboExplosivo) {
                            System.out.printf("Digite o valor de deltaZ que deseja mover o robô %s: ", robo.getNome());
                            int deltaZ = scanner.nextInt();
                            robo.mover(deltaX, deltaY);
                            if (deltaZ >= 0) {
                                ((RoboAereo) robo).subir(deltaZ);
                            } else {
                                ((RoboAereo) robo).descer(Math.abs(deltaZ));
                            }
                        } else {
                            robo.mover(deltaX, deltaY);
                        }
                        System.out.printf("(%d,%d,%d)\n",robo.getPosX(),robo.getPosY(),robo.getAltitude());
                    }
                    break;
                case 4:
                    System.out.printf("Digite o nome do robô que irá usar a habilidade específica. Opções: %s\n",nomes);
                    nomeRobo = scanner.nextLine();
                    robo = null;
                    switch (nomeRobo) {
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
                            case "teste1":
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

                            case "teste2":
                                System.out.printf("O robô %s está prestes a explodir...\n", robo.getNome());
                                Thread.sleep(2000);
                                List<Robo> robosAtingidos = ((RoboExplosivo) robo).explodir();
                                for (Robo roboAtingido : robosAtingidos) {
                                    robosVivos.remove(roboAtingido);
                                }
                                break;

                            case "teste3":
                                System.out.printf("Digite a quantidade de gasolina que deseja abastecer o robô %s: ", robo.getNome());
                                int gasolina = scanner.nextInt();
                                ((RoboCombustivel) robo).abastecer(gasolina);
                                break;

                            case "teste4":
                                System.out.printf("O robô %s está prestes a carregar %.2f energia pelo painel solar\n", robo.getNome(), ((RoboSolar) robo).getPotenciaPainelSolar());
                                ((RoboSolar) robo).carregar();
                                break;
                        }
                    }
                    break;
                case 5:
                    System.out.println("Finalizando o programa...");
                    scanner.close();
                    return;
            }
        }
    }
}
