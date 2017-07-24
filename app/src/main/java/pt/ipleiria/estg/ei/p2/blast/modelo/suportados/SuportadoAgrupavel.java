package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.Interativo;
import pt.ipleiria.estg.ei.p2.blast.modelo.Iteravel;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Sentido;

import java.util.ArrayList;
import java.util.List;

public abstract class SuportadoAgrupavel<T extends SuportadoAgrupavel> extends Suportado
        implements Iteravel, Interativo {

    public SuportadoAgrupavel(BaseSuportadora baseSuportadora) {
        super(baseSuportadora);
    }

    @Override
    public void iterar() {
        if (baseSuportadora.permiteSubirSuportadoAgrupavel()) {
            baseSuportadora.fazerSuportadoAgrupavelSubir();
        }
    }

    public abstract boolean agrupaCom(SuportadoAgrupavel suportado);

    public List<T> getAgrupaveisVizinhos() {
        List<T> vizinhos = new ArrayList<>();
        for (Sentido sentido : Sentido.values()) {
            Suportado suportado = baseSuportadora.getSuportadoA(sentido);
            if (suportado instanceof SuportadoAgrupavel && agrupaCom((SuportadoAgrupavel) suportado)) {
                vizinhos.add((T) suportado);
            }
        }
        return vizinhos;
    }

}
