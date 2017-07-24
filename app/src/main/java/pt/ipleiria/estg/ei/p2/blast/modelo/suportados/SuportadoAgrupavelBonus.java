package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by catarina on 25/04/2017.
 */
public abstract class SuportadoAgrupavelBonus extends SuportadoAgrupavel {
    public SuportadoAgrupavelBonus(BaseSuportadora baseSuportadora) {
        super(baseSuportadora);
    }

    @Override
    public boolean podeInteragir() {
        return true;
    }

    @Override
    public void reagirInteracao() {
        List<SuportadoAgrupavelBonus> grupo = getBaseSuportadora().getGrupoFormado();

        for (SuportadoAgrupavelBonus v : grupo)
            v.explodir();

        if (grupo.size() == 1) {// só o próprio
            aplicarDestruicaoSimples();
        } else {
            if (!isBonusCombinado(grupo)) {
                aplicarDestruicaoDupla();
            } else {
                aplicarDestruicaoCombinada();
            }
        }

    }

    private void aplicarDestruicaoCombinada() {
        getJogo().informarCombinacaoBombaseFoguetes(this);
        List<BaseSuportadora> bases = new LinkedList<>();

        int linha = baseSuportadora.getPosicao().getLinha();
        for (int i = linha-1 ; i <= linha+1; i++) {
            adicionaCasoNaoExista(bases, baseSuportadora.getAreaJogavel().getBasesSuportadorasDaLinha(i));
        }

        int coluna = baseSuportadora.getPosicao().getColuna();
        for (int i = coluna-1 ; i <= coluna+1; i++) {
            adicionaCasoNaoExista(bases, baseSuportadora.getAreaJogavel().getBasesSuportadorasDaColuna(i));
        }

        for (BaseSuportadora base : bases) {
            base.reagirBonus();
        }
    }

    private void adicionaCasoNaoExista(List<BaseSuportadora> bases, List<BaseSuportadora> basesSuportadorasNovas) {
        for (BaseSuportadora baseSuportadora: basesSuportadorasNovas){
            if (!bases.contains(baseSuportadora))
                bases.add(baseSuportadora);
        }

    }

    private boolean isBonusCombinado(List<SuportadoAgrupavelBonus> grupo) {
        for(SuportadoAgrupavelBonus v : grupo){
            if (!v.getClass().getName().equals(this.getClass().getName())){
               return true;
            }
        }
        return false;
    }

    protected abstract void aplicarDestruicaoDupla();

    protected abstract void aplicarDestruicaoSimples();

    @Override
    public void reagirBonus() {
        explodir();
        aplicarDestruicaoSimples();
    }

    @Override
    public boolean agrupaCom(SuportadoAgrupavel suportado) {
        return suportado instanceof SuportadoAgrupavelBonus;
    }

    protected void expandirExplosao(List<BaseSuportadora> bases) {
        for (BaseSuportadora base : bases) {
            base.reagirBonus();
        }
    }

}
