package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

/**
 * Created by catarina on 24/04/2017.
 */
public class Bomba extends SuportadoAgrupavelBonus {

    public Bomba(BaseSuportadora baseSuportadora) {
        super(baseSuportadora);
    }

    @Override
    protected void aplicarDestruicaoSimples() {
        getJogo().informarBombaAtivada(this);
        expandirExplosao(baseSuportadora.getAreaJogavel().getBasesSuportadorasAdjacentes(getBaseSuportadora().getPosicao()));
    }

    @Override
    protected void aplicarDestruicaoDupla() {
        getJogo().informarCombinacaoBombasAtivada(this);
        expandirExplosao(baseSuportadora.getAreaJogavel().getBasesSuportadorasProximas(getBaseSuportadora().getPosicao()));
    }

}
