package lab05.entidade.obstaculos;

import lab05.entidade.Entidade;
import lab05.entidade.TipoEntidade;

/* Criando classe Obstaculo com atributos especificados no enunciado.
   Coordenadas não são final, pois consideram animais ou nuvens que podem se mover.
*/
public class Obstaculo implements Entidade {

    private int posX1;
    private int posY1;
    private int posX2;
    private int posY2;
    private int base;   // Altitude onde o obstaculo começa
    private int altura; // Obstaculo ocupa base + altura
    private final TipoObstaculo tipo;
    private final TipoEntidade tipoEntidade = TipoEntidade.OBSTACULO;

    // Construtores para obstaculo:

    public Obstaculo(int posX1, int posY1, int posX2, int posY2, int base, int altura, TipoObstaculo tipo) {
        this.posX1 = Math.abs(Math.min(posX1,posX2));
        this.posY1 = Math.abs(Math.min(posY1,posY2));

        this.posX2 = Math.abs(Math.max(posX1,posX2));
        this.posY2 = Math.abs(Math.max(posY1,posY2));

        this.altura = Math.abs(altura);
        this.base = Math.abs(base);
        this.tipo = tipo;
    }

    // Sobrecarga de construtor porque obstáculos possuem altura padrão, então não precisa ser especificada
    public Obstaculo(int posX1, int posY1, int posX2, int posY2, int base, TipoObstaculo tipo) {
        this.posX1 = Math.abs(Math.min(posX1,posX2));
        this.posY1 = Math.abs(Math.min(posY1,posY2));

        this.posX2 = Math.abs(Math.max(posX1,posX2));
        this.posY2 = Math.abs(Math.max(posY1,posY2));

        this.altura = tipo.getAltura();
        this.base = Math.abs(base);
        this.tipo = tipo;
    }

    // Getters e Setters:

    public int getPosX1() {
        return posX1;
    }

    public int getPosY1() {
        return posY1;
    }

    public int getPosX2() {
        return posX2;
    }

    public int getPosY2() {
        return posY2;
    }

    public int getX(){
        return (int) ((posX1 + posX2) / 2);
    }

    public int getY(){
        return  (int) ((posY1 + posY2) / 2);
    }

    public int getZ(){
        return (int) ((base + altura) / 2);
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public String getDescricao() {
        return "Obstáculo do tipo " + tipo.getNome() + " com base em " + base + " e altura de " + altura + "\n" +
                "Ele está ocupando de (" + posX1 + "," + posY1 + ") até (" + posX2 + "," + posY2 + ")\n";
    }

    public char getRepresentacao() {
        return 'O';
    }

    public TipoObstaculo getTipoObstaculo() {
        return tipo;
    }

    public int getAltura() {
        return altura;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setPosX1(int posX1) {
        this.posX1 = posX1;
    }

    public void setPosY1(int posY1) {
        this.posY1 = posY1;
    }

    public void setPosX2(int posX2) {
        this.posX2 = posX2;
    }

    public void setPosY2(int posY2) {
        this.posY2 = posY2;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    // Metodo para verificar se um ponto está dentro do obstáculo
    public boolean contemPonto(double x, double y, double z) {
        // Verifica se o ponto (x,y,z) está dentro do obstáculo
        return (x >= posX1 && x <= posX2 && y >= posY1 && y <= posY2 && z >= base && z <= (base + altura));
    }

}
