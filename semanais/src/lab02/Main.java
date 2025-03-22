package lab02;

import lab02.robos.Robo;
import lab02.robos.RoboTerrestre;
import lab02.robos.RoboAereo;
import lab02.robos.robos_aereos.RoboBombardeiro;
import lab02.robos.robos_aereos.RoboExplosivo;
import lab02.robos.robos_terrestres.RoboCombustivel;
import lab02.robos.robos_terrestres.RoboSolar;


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Ambiente ambiente = new Ambiente(100, 50, 25);

        Robo Robson = new Robo("Robson", 10, -10, "Norte", 10);
        RoboTerrestre Terrestre = new RoboTerrestre("Terrestre", -20, 10, "Sul", 20, 10);
        RoboCombustivel Combustivel = new RoboCombustivel("Combustivel", 30, 50, "Leste", 5, 15, 8);
        RoboSolar Solar = new RoboSolar("Solar", 500, 500, "Oeste", 50, 30, 50);
        RoboAereo Aereo = new RoboAereo("Aereo", 0, 0, "Norte", 25, 50, 10);
        RoboExplosivo Explosivo = new RoboExplosivo("Explosivo", 50, 25, "Oeste", 3, 2, 8, 5);
        RoboBombardeiro Bombardeiro = new RoboBombardeiro("Bombardeiro", 25, 25, "Leste", 20, 20, 20, 5);

        ambiente.adicionarRobo(Robson);
        ambiente.adicionarRobo(Terrestre);
        ambiente.adicionarRobo(Combustivel);
        ambiente.adicionarRobo(Solar);
        ambiente.adicionarRobo(Aereo);
        ambiente.adicionarRobo(Explosivo);
        ambiente.adicionarRobo(Bombardeiro);

        for (int i = 0; i < 20; i++) {
            System.out.printf("\n%d - %d - %d - %d - %d - %d\n",i,i,i,i,i,i);
            for (Robo robo : ambiente.getListaRobos()) {
                switch (i) {

                    case 0:
                        robo.exibirPosicao();
                        break;

                    case 1:
                        System.out.printf("%s:\n",robo.getNome());
                        robo.identificarObstaculos(ambiente);
                        System.out.println();
                        break;

                    case 2:
                        if (robo instanceof RoboTerrestre) {
                            ((RoboTerrestre) robo).mover(2, 5, ambiente);
                            ((RoboTerrestre) robo).mover(2, -1, ambiente);
                        }
                        break;

                    case 3:
                        if (robo instanceof RoboTerrestre)
                            robo.exibirPosicao();

                        break;

                    case 4:
                        if (robo instanceof RoboTerrestre)
                            ((RoboTerrestre) robo).mover(15,22,ambiente);

                        break;

                    case 5:
                        if (robo instanceof RoboTerrestre)
                            robo.exibirPosicao();

                        break;

                    case 6:
                        if (robo instanceof RoboCombustivel) {
                            ((RoboCombustivel) robo).abastecer(50);
                            ((RoboCombustivel) robo).mover(10, 10, ambiente);
                            robo.exibirPosicao();
                        }

                        break;

                    case 7:
                        if (robo instanceof RoboSolar) {
                            ((RoboSolar) robo).carregar(ambiente);
                            ambiente.mudarTempo("Noite");
                            ((RoboSolar) robo).mover(30,19,ambiente);
                            robo.exibirPosicao();
                            ((RoboSolar) robo).mover(5,5,ambiente);
                            robo.exibirPosicao();
                            ((RoboSolar) robo).carregar(ambiente);
                            }

                        break;

                    case 8:
                        if (robo instanceof RoboAereo){
                            robo.exibirPosicao();
                            ((RoboAereo) robo).subir(10,ambiente);
                            }

                        break;

                    case 9:
                        if (robo instanceof RoboAereo)
                            robo.exibirPosicao();

                        break;

                    case 10:
                        if (robo instanceof RoboAereo) {
                            ((RoboAereo) robo).descer(-5, ambiente);
                            ((RoboAereo) robo).descer(2,ambiente);
                        }
                        break;

                    case 11:
                        if (robo instanceof RoboAereo)
                            robo.exibirPosicao();

                        break;

                    case 12:
                        if (robo instanceof RoboAereo)
                            ((RoboAereo) robo).descer(5,ambiente);

                        break;

                    case 13:
                        if (robo instanceof RoboAereo)
                            robo.exibirPosicao();

                        break;

                    case 14:
                        if (robo instanceof RoboExplosivo)
                            ((RoboExplosivo) robo).explodir(ambiente);

                        break;


                }
            }

        }

    }


}
