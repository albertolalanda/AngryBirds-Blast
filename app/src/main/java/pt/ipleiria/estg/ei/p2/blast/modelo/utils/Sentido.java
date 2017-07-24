package pt.ipleiria.estg.ei.p2.blast.modelo.utils;

public enum Sentido {
    NORTE(-1, 0), OESTE(0, -1), SUL(1, 0), ESTE(0, 1);

    private Posicao posicao;

    Sentido(int deltaLinha, int deltaColuna) {
        posicao = new Posicao(deltaLinha, deltaColuna);
    }

    public Posicao getPosicao() {
        return posicao;
    }
}
