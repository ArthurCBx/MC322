package lab01;

public class Main {
    public static void main(String[] args) {
        Ambiente ambiente = new Ambiente(10, 10);
        Robo robo = new Robo("Robson",6,2);

        System.out.println("O nome deste robo é: " + robo.getNome());
        robo.exibirPosicao();
        robo.mover(5, 5);
        robo.exibirPosicao();

        System.out.println(ambiente.dentroDosLimites(5, 5));
        System.out.println(ambiente.dentroDosLimites(10, 10));
        System.out.println(ambiente.dentroDosLimites(-1, 10));
        System.out.println(ambiente.dentroDosLimites(10, -1));

    }
}
