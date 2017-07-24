package pt.ipleiria.estg.ei.p2.blast.modelo.utils;

public class Posicao {
    private int linha;
    private int coluna;

    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public Posicao seguir(Sentido sentido) {
        return seguirVetor(sentido.getPosicao());
    }

    public Posicao seguirVetor(Posicao vetor) {
        return new Posicao(linha + vetor.linha, coluna + vetor.coluna);
    }

}
