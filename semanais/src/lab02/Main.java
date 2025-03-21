package lab02;

import lab02.robos.Robo;
import lab02.robos.RoboAereo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Ambiente ambiente = new Ambiente(10,10,10);

        Robo Robson = new Robo("Robson",10,10,"Sul",107);
        Robo Robson2 = new Robo("Robson",10,10,"Sul",10);

        Robo Robson3 = new RoboAereo("Robson",10,10,"Sul",10,100,10);

        ambiente.adicionarRobo(Robson);
        ambiente.adicionarRobo(Robson2);
        ambiente.adicionarRobo(Robson3);

        int RobHash = Robson.hashCode();

        Class sus = Robson.getClass();


        Class sus2 = Robson2.getClass();



        List listaRobos = ambiente.getListaRobos();

        System.out.println(sus);
        System.out.println(sus2);

        System.out.println( listaRobos );
        ambiente.removerRobo(Robson);
//      System.out.println( sus==sus2 );

        System.out.println( listaRobos );


    }




}
