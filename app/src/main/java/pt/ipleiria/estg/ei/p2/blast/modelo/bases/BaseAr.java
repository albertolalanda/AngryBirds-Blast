package pt.ipleiria.estg.ei.p2.blast.modelo.bases;

import pt.ipleiria.estg.ei.p2.blast.modelo.AreaJogavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.SuportadoAgrupavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Sentido;

public class BaseAr extends Base {

    public BaseAr(AreaJogavel grelha, Posicao posicao) {
        super(grelha, posicao);
    }

    @Override
    public boolean aceita(SuportadoAgrupavel<?> suportadoAgrupavel) {
        Base baseAcima = areaJogavel.getBase(posicao.seguir(Sentido.NORTE));
        return baseAcima != null && baseAcima.aceita(suportadoAgrupavel);
    }

    @Override
    public void agarrar(SuportadoAgrupavel<?> suportadoAgrupavel) {
        if (!aceita(suportadoAgrupavel)) {
            return; // Devia lançar excepção
        }
        Base baseAcima = areaJogavel.getBase(posicao.seguir(Sentido.NORTE));
        baseAcima.agarrar(suportadoAgrupavel);
    }


}
