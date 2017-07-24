package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.Objetivavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

public class Porco extends SuportadoSensivelOndaChoqueComForca implements Objetivavel {

    public Porco(BaseSuportadora base) {
        super(base, 2, 100);
    }

    @Override
    public void explodir() {
        super.explodir();
        getJogo().influenciarObjetivoDoJogo(this);
    }

}
