package pt.ipleiria.estg.ei.p2.blast.modelo.objetivos;

import pt.ipleiria.estg.ei.p2.blast.modelo.Objetivavel;

public abstract class Objetivo {
    public abstract void influenciar(Objetivavel balao);
    public abstract boolean isConcluido();
}
