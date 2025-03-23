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
        RoboBombardeiro Bombardeiro = new RoboBombardeiro("Bombardeiro", 25, 25, "Leste", 20, 20, 20, 5);
        RoboExplosivo Explosivo = new RoboExplosivo("Explosivo", 50, 25, "Oeste", 3, 2, 8, 10);

        ambiente.adicionarRobo(Robson);
        ambiente.adicionarRobo(Terrestre);
        ambiente.adicionarRobo(Combustivel);
        ambiente.adicionarRobo(Solar);
        ambiente.adicionarRobo(Aereo);
        ambiente.adicionarRobo(Bombardeiro);
        ambiente.adicionarRobo(Explosivo);

        System.out.printf("\n------- Posição de todos os robos:\n\n");

        for (Robo robo : ambiente.getListaRobos())
            robo.exibirPosicao();


        System.out.printf("\n------- Metodo identifica obstaculos de todos os robos:\n\n");

        for (Robo robo : ambiente.getListaRobos()) {
            System.out.printf("%s:\n", robo.getNome());
            robo.identificarObstaculos(ambiente);
            System.out.println();
        }


        System.out.printf("\n------- Move os robos terrestres:\n\n");

        System.out.println("\nPosições iniciais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboTerrestre) {
                robo.exibirPosicao();
                ((RoboTerrestre) robo).mover(2, 5, ambiente);
                ((RoboTerrestre) robo).mover(2, -1, ambiente);

            }
        System.out.println("\nPosições finais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboTerrestre)
                robo.exibirPosicao();


        System.out.printf("\n------- Move os robos terrestres acima de algumas velocidades maximas:\n\n");

        System.out.println("\nPosições iniciais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboTerrestre)
                robo.exibirPosicao();

        System.out.println();

        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboTerrestre)
                ((RoboTerrestre) robo).mover(15, 22, ambiente);


        System.out.println("\nPosições finais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboTerrestre)
                robo.exibirPosicao();


        System.out.printf("\n------- Move o robo Combustivel após o metodo abastecer:\n\n");

        System.out.println("\nPosição inicial:");
        Combustivel.exibirPosicao();

        Combustivel.abastecer(50);

        System.out.println("\nPosição final:");
        Combustivel.mover(10, 10, ambiente);
        Combustivel.exibirPosicao();


        System.out.printf("\n------- Move o robo Solar durante o dia, carrega a bateria, tenta mover durante a noite e tenta carregar:\n\n");

        System.out.printf("Bateria: %d\n", Solar.getBateria());
        Solar.carregar(ambiente);
        System.out.printf("Bateria após carregamento: %d\n", Solar.getBateria());

        ambiente.mudarTempo("Noite");

        System.out.println("\nPosição inicial:");
        Solar.exibirPosicao();

        System.out.println("\nPosição após primeiro movimento:");
        Solar.mover(30, 19, ambiente);
        Solar.exibirPosicao();

        System.out.println("\nPosição final:");
        Solar.mover(5, 5, ambiente);
        Solar.exibirPosicao();

        Solar.carregar(ambiente);


        System.out.printf("\n------- Sobe os robos aereos e tenta ir alem de algumas alturas maximas:\n\n");

        System.out.println("\nPosições iniciais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboAereo) {
                robo.exibirPosicao();
                ((RoboAereo) robo).subir(10, ambiente);
            }

        System.out.println("\nPosições finais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboAereo)
                robo.exibirPosicao();


        System.out.printf("\n------- Desce os robos aereos:\n\n");

        System.out.println("\nPosições iniciais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboAereo) {
                robo.exibirPosicao();
                ((RoboAereo) robo).descer(-5, ambiente);
                ((RoboAereo) robo).descer(2, ambiente);
            }

        System.out.println("\nPosições intermediarias:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboAereo)
                robo.exibirPosicao();

        System.out.println("\nPosições finais:");
        for (Robo robo : ambiente.getListaRobos())
            if (robo instanceof RoboAereo) {
                ((RoboAereo) robo).descer(7, ambiente);
                robo.exibirPosicao();
            }


        System.out.printf("\n------- Posições de todos os robos antes de remoção por metodos:\n\n");

        for (Robo robo : ambiente.getListaRobos())
            robo.exibirPosicao();


        System.out.printf("\n------- Robo explosivo vai ate o robo terrestre e explode na altura maxima:\n\n");

        System.out.println("\nPosição inicial:");
        Explosivo.exibirPosicao();
        Explosivo.mover(Terrestre.getPosX() - Explosivo.getPosX(), Terrestre.getPosY() - Explosivo.getPosY(), ambiente);
        Explosivo.subir(8, ambiente);

        System.out.println("\nPosições final:");
        Explosivo.exibirPosicao();

        System.out.println();

        Explosivo.explodir(ambiente);


        System.out.printf("\n------- Robo bobardeiro tenta bombardear sem bombas, carrega bombas e tenta novamente:\n\n");

        Bombardeiro.bombardear(ambiente);
        Bombardeiro.carregarBombas(1);
        Bombardeiro.bombardear(ambiente);


        System.out.printf("\n------- Robo bobardeiro vai bombardear o robo Solar no chão:\n\n");

        System.out.println("\nPosições iniciais:");
        Solar.exibirPosicao();
        Bombardeiro.exibirPosicao();

        Bombardeiro.carregarBombas(1);
        Bombardeiro.mover(Solar.getPosX() - Bombardeiro.getPosX(), Solar.getPosY() - Bombardeiro.getPosY(), ambiente);
        Bombardeiro.descer(Bombardeiro.getAltitude(), ambiente);

        System.out.println("\nPosição final:");
        Bombardeiro.exibirPosicao();

        Bombardeiro.bombardear(ambiente);


        System.out.printf("\n------- Posições de todos os robos após as remoções:\n\n");

        for (Robo robo : ambiente.getListaRobos())
            robo.exibirPosicao();


    }


}
