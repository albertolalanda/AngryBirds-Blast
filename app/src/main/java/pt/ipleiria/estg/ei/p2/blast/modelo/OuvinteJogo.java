package pt.ipleiria.estg.ei.p2.blast.modelo;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.Base;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.*;

public interface OuvinteJogo {
    void balaoCriado(Balao balao, Base baseInsercao);

    void suportadoExplodiu(Suportado suportado);

    void suportadoAgrupavelMovimentou(SuportadoAgrupavel<?> suportadoAgrupavel, BaseSuportadora origem, BaseSuportadora destino);

    void objetivosConcluidos();

    void movimentosEsgotados();

    void suportadoDestruidoParcialmente(SuportadoSensivelOndaChoqueComForca suportado, float percentagemRestante);

    void fogueteLancado(Foguete foguete);

    void combinacaoFoguetesLancados(Foguete foguete);

    void fogueteMudaDirecao(Foguete foguete);

    void porcoCriado(Porco porco, BaseSuportadora baseSuportadora);

    void fogueteCriado(Foguete foguete, BaseSuportadora baseSuportadora);

    void vidroCriado(Vidro vidro, BaseSuportadora baseSuportadora);

    void madeiraCriada(Madeira madeira, BaseSuportadora baseSuportadora);

    void pedraCriada(Pedra pedra, BaseSuportadora baseSuportadora);

    void bombaAtivada(Bomba bomba);

    void combinacaoBombasAtivadas(Bomba bomba);

    void bombaCriada(Bomba bomba, BaseSuportadora baseSuportadora);

    void combinacaoBombaFogueteAtivada(SuportadoAgrupavelBonus suportadoAgrupavelBonus);
}
