package pt.ipleiria.estg.ei.p2.blast.modelo;


public enum Especie {
    RED, CHUCK, BOMB, MATILDA, TERENCE, STELLA;

    public char getInicial() {
        return this.toString().charAt(0);
    }
}
