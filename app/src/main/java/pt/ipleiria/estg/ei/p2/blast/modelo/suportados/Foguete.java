package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Direcao;

public class Foguete extends SuportadoAgrupavelBonus {

    private Direcao direcao;

    public Foguete(BaseSuportadora baseSuportadora) {
        super(baseSuportadora);
        direcao = Direcao.values()[baseSuportadora.getAreaJogavel().getValorAleatorio(Direcao.values().length)];
    }

    @Override
    protected void aplicarDestruicaoDupla() {
        getJogo().informarCombinacaoFoguetesLancados(this);
        destruirColuna(baseSuportadora.getPosicao().getColuna());
        destruirLinha(baseSuportadora.getPosicao().getLinha());
    }

    @Override
    protected void aplicarDestruicaoSimples() {
        int posicao = direcao == Direcao.VERTICAL ? baseSuportadora.getPosicao().getColuna() : baseSuportadora.getPosicao().getLinha();

        getJogo().informarFogueteLancado(this);
        if (direcao == Direcao.VERTICAL)
            destruirColuna(posicao);
        else
            destruirLinha(posicao);
    }

    public Direcao getDirecao() {
        return direcao;
    }


    private void destruirLinha(int linha) {
        expandirExplosao(baseSuportadora.getAreaJogavel().getBasesSuportadorasDaLinha(linha));
    }

    private void destruirColuna(int coluna) {
        expandirExplosao(baseSuportadora.getAreaJogavel().getBasesSuportadorasDaColuna(coluna));
    }

    public void inverterDirecao() {
        direcao = direcao.perpendicular();
        getJogo().informarMudancaDirecaoFoguete(this);
    }

}
