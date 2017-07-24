package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

public abstract class SuportadoSensivelOndaChoqueComForca extends SuportadoSensivelOndaChoque {
    protected int forca;
    protected final int forcaMaxima;
    protected int multiplicadorPontuacao;

    public SuportadoSensivelOndaChoqueComForca(BaseSuportadora baseSuportadora, int forca, int multiplicadorPontuacao) {
        super(baseSuportadora);
        this.forca = forca;
        this.forcaMaxima = forca;
        this.multiplicadorPontuacao = multiplicadorPontuacao;
    }

    public int getForca() {
        return forca;
    }

    @Override
    public void receberOndaChoque() {
        diminuirForca();
    }

    @Override
    public void reagirBonus() {
        diminuirForca();
    }

    protected void diminuirForca() {
        forca--;
        if (forca > 0) {
            getJogo().informarDestruicaoParcial(this, (float) forca / forcaMaxima);
        } else {
            explodir();
        }
        getJogo().incrementarPontuacao((forcaMaxima - forca) * multiplicadorPontuacao);
    }

}
