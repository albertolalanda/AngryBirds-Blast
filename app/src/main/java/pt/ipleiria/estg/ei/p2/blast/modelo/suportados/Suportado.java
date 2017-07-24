package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.Jogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.ReagenteBonus;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

public abstract class Suportado implements ReagenteBonus {
    protected BaseSuportadora baseSuportadora;

    public Suportado(BaseSuportadora baseSuportadora) {
        this.baseSuportadora = baseSuportadora;
    }

    public BaseSuportadora getBaseSuportadora() {
        return baseSuportadora;
    }

    public void setBaseSuportadora(BaseSuportadora baseSuportadora) {
        this.baseSuportadora = baseSuportadora;
    }

    public void explodir() {
        baseSuportadora.libertarSuportado();
        getJogo().informarExplosao(this);
    }

    protected Jogo getJogo() {
        return baseSuportadora.getAreaJogavel().getJogo();
    }
}
