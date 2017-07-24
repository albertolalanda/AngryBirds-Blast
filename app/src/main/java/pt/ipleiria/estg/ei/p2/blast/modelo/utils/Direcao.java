package pt.ipleiria.estg.ei.p2.blast.modelo.utils;

public enum Direcao {
    HORIZONTAL, VERTICAL;

    public Direcao perpendicular() {
        if (this == HORIZONTAL)
            return VERTICAL;
        return HORIZONTAL;
    }
}
