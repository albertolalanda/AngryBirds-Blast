package pt.ipleiria.estg.ei.p2.blast.modelo.bases;

import pt.ipleiria.estg.ei.p2.blast.modelo.AreaJogavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.ObjetoComAreaJogavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.SuportadoAgrupavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;

public abstract class Base extends ObjetoComAreaJogavel {
    protected Posicao posicao;

    public Base(AreaJogavel areaJogavel, Posicao posicao) {
        super(areaJogavel);
        this.posicao = posicao;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public abstract boolean aceita(SuportadoAgrupavel<?> suportadoAgrupavel);

    public abstract void agarrar(SuportadoAgrupavel<?> suportadoAgrupavel);
}
