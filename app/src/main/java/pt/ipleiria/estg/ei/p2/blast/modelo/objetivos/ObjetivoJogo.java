package pt.ipleiria.estg.ei.p2.blast.modelo.objetivos;

import pt.ipleiria.estg.ei.p2.blast.modelo.Objetivavel;

import java.util.ArrayList;
import java.util.List;

public class ObjetivoJogo extends Objetivo {
    private List<ObjetivoParcial> parciais;

    public ObjetivoJogo() {
        parciais = new ArrayList<>();
    }

    public void adicionar(ObjetivoParcial parcial) {
        parciais.add(parcial);
    }

    @Override
    public void influenciar(Objetivavel objetivavel) {
        for (ObjetivoParcial objetivoParcial : parciais) {
            if (objetivoParcial.hasInfluencia(objetivavel)) {
                objetivoParcial.influenciar(objetivavel);
                return;
            }
        }
    }

    public int getNumeroObjetivosParciais() {
        return parciais.size();
    }

    public ObjetivoParcial getObjetivoParcial(int index) {
        return parciais.get(index);
    }

    @Override
    public boolean isConcluido() {
        for (ObjetivoParcial op : parciais) {
            if (!op.isConcluido()) {
                return false;
            }
        }
        return true;
    }
}
