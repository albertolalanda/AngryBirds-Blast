package pt.ipleiria.estg.ei.p2.blast.modelo;

public class DelegadoEspecie implements ObjetoComEspecie {
    private Especie especie;

    public DelegadoEspecie(Especie especie) {
        this.especie = especie;
    }

    @Override
    public Especie getEspecie() {
        return especie;
    }
}
