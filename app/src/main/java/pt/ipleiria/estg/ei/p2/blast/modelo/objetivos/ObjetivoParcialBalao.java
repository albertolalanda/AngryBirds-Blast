package pt.ipleiria.estg.ei.p2.blast.modelo.objetivos;

import pt.ipleiria.estg.ei.p2.blast.modelo.DelegadoEspecie;
import pt.ipleiria.estg.ei.p2.blast.modelo.Especie;
import pt.ipleiria.estg.ei.p2.blast.modelo.Objetivavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.ObjetoComEspecie;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Balao;

public class ObjetivoParcialBalao extends ObjetivoParcial implements ObjetoComEspecie {

    private DelegadoEspecie delegadoEspecie;

    public ObjetivoParcialBalao(Especie especie, int quantidade) {
        super(quantidade);
        this.delegadoEspecie = new DelegadoEspecie(especie);
    }

    @Override
    public Especie getEspecie() {
        return delegadoEspecie.getEspecie();
    }

    @Override
    public boolean hasInfluencia(Objetivavel objetivavel) {
        return (objetivavel instanceof Balao) && ((Balao) objetivavel).getEspecie() == getEspecie();
    }

}
