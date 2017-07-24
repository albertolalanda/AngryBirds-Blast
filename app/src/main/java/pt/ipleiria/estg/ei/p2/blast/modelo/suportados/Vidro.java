package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

public class Vidro extends SuportadoSensivelOndaChoque {

    public Vidro(BaseSuportadora baseSuportadora) {
        super(baseSuportadora);
    }

    @Override
    public void explodir() {
        super.explodir();
        getJogo().incrementarPontuacao(50);
    }

    @Override
    public void receberOndaChoque() {
        explodir();
    }

    @Override
    public void reagirBonus() {
        explodir();
    }
}
