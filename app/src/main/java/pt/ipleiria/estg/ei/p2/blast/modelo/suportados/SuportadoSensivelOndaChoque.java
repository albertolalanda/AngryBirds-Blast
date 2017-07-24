package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.SensivelOndaChoque;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

public abstract class SuportadoSensivelOndaChoque extends Suportado implements SensivelOndaChoque {

    public SuportadoSensivelOndaChoque(BaseSuportadora baseSuportadora) {
        super(baseSuportadora);
    }

}
