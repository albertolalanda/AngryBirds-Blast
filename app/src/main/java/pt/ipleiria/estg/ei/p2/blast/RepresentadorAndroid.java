package pt.ipleiria.estg.ei.p2.blast;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

import java.util.List;

import pt.ipleiria.estg.dei.gridcomponent.CellRepresentation;
import pt.ipleiria.estg.dei.gridcomponent.GridComponent;
import pt.ipleiria.estg.dei.gridcomponent.SingleImageCellRepresentation;
import pt.ipleiria.estg.dei.gridcomponent.SlideCellRepresentation;
import pt.ipleiria.estg.dei.gridcomponent.TextCellRepresentation;
import pt.ipleiria.estg.dei.gridcomponent.TremblingCellRepresentation;
import pt.ipleiria.estg.ei.p2.blast.modelo.AreaJogavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.Jogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.OuvinteJogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.Base;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoParcial;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoParcialBalao;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Balao;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Bomba;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Foguete;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Madeira;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Pedra;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Porco;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Suportado;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.SuportadoAgrupavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.SuportadoAgrupavelBonus;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.SuportadoSensivelOndaChoqueComForca;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Vidro;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Direcao;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;

public class RepresentadorAndroid implements OuvinteJogo {

    public static final int JOGAR_NOVAMENTE = 1;
    private Jogo jogo;
    private Activity context;
    private GridComponent componente;
    private GridComponent info;
    private int numeroColunasInfo;
    private static final int TEMPO_ANIMACAO = 300;

    public RepresentadorAndroid(Jogo jogo, Activity context, GridComponent componente, GridComponent info) {
        this.jogo = jogo;
        this.context = context;
        this.componente = componente;
        this.info = info;

        jogo.adicionarOuvinte(this);
        //configurações componente
        //colocar o background transparente
        componente.setGridBackground(0);
        componente.setShowGridLines(false);
        componente.setNumberOfRows(jogo.getAreaJogavel().getNumeroLinhas());
        componente.setNumberOfColumns(jogo.getAreaJogavel().getNumeroColunas());

        reiniciar();
    }

    public void reiniciar() {
        limparComponente(componente);
        limparComponente(info);
        componente.enableEvents();

        //definir o tempo entre as iterações do motor (como no jogo atual não há
        //nenhum elemento que necessite "contar" o tempo, o valor do parâmetro
        //pode ser 1 segundo = 1000ms)
        componente.startIterations(1000);

        representarAreaJogavel();

        //configurações info
        info.setNumberOfRows(2);
        numeroColunasInfo = jogo.getObjetivoJogo().getNumeroObjetivosParciais() + 2;
        info.setNumberOfColumns(numeroColunasInfo);
        info.setShowGridLines(false);
        //representa os objetivos de jogo
        for (int i = 0; i < jogo.getObjetivoJogo().getNumeroObjetivosParciais(); i++) {
            ObjetivoParcial objetivoParcial = jogo.getObjetivoJogo().getObjetivoParcial(i);
            if (objetivoParcial instanceof ObjetivoParcialBalao) {
                info.add(0, i, new SingleImageCellRepresentation(context,
                        ((ObjetivoParcialBalao) objetivoParcial).getEspecie().toString() + ".png"));
            } else {
                info.add(0, i, new SingleImageCellRepresentation(context, "Porco2.png"));
            }
        }

        info.add(0, numeroColunasInfo - 2, new TextCellRepresentation(context, "MOVES"));
        info.add(0, numeroColunasInfo - 1, new TextCellRepresentation(context, "SCORE"));

        representarInfo();

    }

    private void limparComponente(GridComponent gridComponent) {
        gridComponent.reinitialize();
    }

    public void representarInfo() {
        representarObjetivoJogo();
        representarNumeroMovimentosRestantes();
        representarPontuacao();

    }

    private void representarPontuacao() {
        representarTexto(numeroColunasInfo - 1, String.valueOf(jogo.getPontuacao()));
    }

    private void representarNumeroMovimentosRestantes() {
        representarTexto(numeroColunasInfo - 2, String.valueOf(jogo.getNumeroMovimentosRestantes()));
    }

    private void representarObjetivoJogo() {
        for (int i = 0; i < jogo.getObjetivoJogo().getNumeroObjetivosParciais(); i++) {
            ObjetivoParcial objetivoParcial = jogo.getObjetivoJogo().getObjetivoParcial(i);
            representarTexto(i, String.valueOf(objetivoParcial.getQuantidade()));
        }
    }


    private void representarTexto(int pos, String texto) {
        CellRepresentation representacaoAntiga = getRepresentacaoEm(info, new Posicao(1, pos));
        TextCellRepresentation representacaoNova = new TextCellRepresentation(context, texto, TextCellRepresentation.HorizontalAlignment.CENTER, TextCellRepresentation.VerticalAlignment.CENTER, Color.CYAN, Color.BLACK, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 15);
        if (representacaoAntiga == null)
            info.add(1, pos, representacaoNova);
        else
            info.replace(1, pos, representacaoAntiga, representacaoNova);

    }

    private void representarAreaJogavel() {
        AreaJogavel areaJogavel = jogo.getAreaJogavel();
        for (int linha = 0; linha < areaJogavel.getNumeroLinhas(); linha++) {
            for (int coluna = 0; coluna < areaJogavel.getNumeroColunas(); coluna++) {
                representarBase(areaJogavel.getBase(linha, coluna));
            }
        }
    }

    private void representarBase(Base base) {
        if (base instanceof BaseSuportadora) {
            //vamos assumir que as bases são representadas no layer 0
            //os suportados serão representados no layer 1
            componente.setCurrentLayer(0);
            colocarEm(base.getPosicao(), "BaseSuportadora.png");
            representarSuportado(base.getPosicao(), ((BaseSuportadora) base).getSuportado());
        }
    }

    private void representarSuportado(Posicao posicao, Suportado suportado) {
        if (suportado == null)
            return;

        componente.setCurrentLayer(1);
        if (suportado instanceof Balao) {
            colocarEm(posicao, ((Balao) suportado).getEspecie().toString() + ".png");
        } else if (suportado instanceof Porco) {
            colocarEm(posicao, "Porco" + ((Porco) suportado).getForca() + ".png");
        } else if (suportado instanceof Madeira) {
            colocarEm(posicao, "Madeira" + ((Madeira) suportado).getForca() + ".png");
        } else if (suportado instanceof Vidro) {
            colocarEm(posicao, "Vidro.png");
        } else if (suportado instanceof Foguete) {
            colocarEm(posicao, "Foguete" + ((Foguete) suportado).getDirecao().toString() + ".png");
        } else if (suportado instanceof Pedra) {
            colocarEm(posicao, "Pedra" + ((Pedra) suportado).getForca() + ".png");
        } else if (suportado instanceof Bomba) {
            colocarEm(posicao, "Bomba.png");
        }
    }

    private void colocarEm(Posicao posicao, String imagem) {
        CellRepresentation representacaoAntiga = getRepresentacaoEm(componente, posicao);
        SingleImageCellRepresentation representacaoNova = new SingleImageCellRepresentation(context, imagem);
        if (representacaoAntiga == null)
            componente.add(posicao.getLinha(), posicao.getColuna(),
                    representacaoNova);
        else
            componente.replace(posicao.getLinha(), posicao.getColuna(),
                    representacaoAntiga, new SingleImageCellRepresentation(context, imagem));
    }

    @Override
    public void balaoCriado(Balao balao, Base baseInsercao) {
        componente.setCurrentLayer(1);
        Posicao pos = new Posicao(jogo.getAreaJogavel().getNumeroLinhas(), baseInsercao.getPosicao().getColuna());
        colocarEm(pos, balao.getEspecie().toString() + ".png");
    }

    @Override
    public void suportadoExplodiu(Suportado suportado) {
        Posicao posicao = suportado.getBaseSuportadora().getPosicao();
        componente.setCurrentLayer(1);
        componente.clear(posicao.getLinha(), posicao.getColuna());
    }

    @Override
    public void suportadoAgrupavelMovimentou(SuportadoAgrupavel<?> suportadoAgrupavel, BaseSuportadora origem, BaseSuportadora destino) {
        componente.setCurrentLayer(1);
        Posicao posicaoDestino = destino.getPosicao();
        Posicao posicaoOrigem = null;

        if (origem == null) { // Balão recentemente criado
            posicaoOrigem = new Posicao(jogo.getAreaJogavel().getNumeroLinhas(), destino.getPosicao().getColuna());
        } else {
            posicaoOrigem = origem.getPosicao();
        }

        CellRepresentation representacaoAntiga = getRepresentacaoEm(componente, posicaoOrigem);
        if (representacaoAntiga != null) {
            componente.moveItem(posicaoOrigem.getLinha(), posicaoOrigem.getColuna(),
                    posicaoDestino.getLinha(), posicaoDestino.getColuna(), TEMPO_ANIMACAO, representacaoAntiga);
        }
    }

    private CellRepresentation getRepresentacaoEm(GridComponent componente, Posicao posicao) {
        List<CellRepresentation> representacoes = componente.get(posicao.getLinha(), posicao.getColuna());
        if (representacoes.size() > 0)
            return representacoes.get(0);
        return null;
    }

    @Override
    public void objetivosConcluidos() {
        Intent intent = ResultadoActivity.createIntent(context, context.getString(R.string.txtConcluidoSucessoText));
        context.startActivityForResult(intent, JOGAR_NOVAMENTE);
    }

    @Override
    public void movimentosEsgotados() {
        Intent intent = ResultadoActivity.createIntent(context, context.getString(R.string.txtGameOverText));
        context.startActivityForResult(intent, JOGAR_NOVAMENTE);
    }

    @Override
    public void suportadoDestruidoParcialmente(SuportadoSensivelOndaChoqueComForca suportado, float percentagemRestante) {
        representarSuportado(suportado.getBaseSuportadora().getPosicao(), suportado);
    }

    @Override
    public void fogueteLancado(Foguete foguete) {
        if (foguete.getDirecao() == Direcao.HORIZONTAL) {
            Posicao destino = new Posicao(foguete.getBaseSuportadora().getPosicao().getLinha(), jogo.getAreaJogavel().getNumeroColunas() - 1);
            Posicao origem = new Posicao(destino.getLinha(), 0);
            animar(origem, destino, TEMPO_ANIMACAO, "FogueteHORIZONTAL.png", 0);

        } else {
            Posicao destino = new Posicao(0, foguete.getBaseSuportadora().getPosicao().getColuna());
            Posicao origem = new Posicao(jogo.getAreaJogavel().getNumeroLinhas() - 1, destino.getColuna());
            animar(origem, destino, TEMPO_ANIMACAO, "FogueteVERTICAL.png", 0);
        }
    }

    private void animar(Posicao origem, final Posicao destino, int tempo, String imagem, int deltaTremer) {
        componente.setCurrentLayer(2);
        CellRepresentation representacaoInterna = new SingleImageCellRepresentation(context, imagem);
        if (deltaTremer > 0) {
            representacaoInterna = new TremblingCellRepresentation(representacaoInterna, deltaTremer, deltaTremer);
        }
        CellRepresentation rep = new SlideCellRepresentation(componente, origem.getColuna(), origem.getLinha(),
                destino.getColuna(), destino.getLinha(), tempo, representacaoInterna);
        componente.add(destino.getLinha(), destino.getColuna(), rep);
        componente.disableEvents();
        rep.addEndHandler(new CellRepresentation.EndHandler() {
            @Override
            public void representationEnded(CellRepresentation cell) {
                componente.setCurrentLayer(2);
                componente.clear(destino.getLinha(), destino.getColuna());
                componente.enableEvents();
            }
        });

    }

    private void animar(final Posicao destino, int tempo, String imagem, int deltaTremer) {
        animar(destino, destino, tempo, imagem, deltaTremer);
    }

    @Override
    public void combinacaoFoguetesLancados(Foguete foguete) {
        Posicao destino = new Posicao(foguete.getBaseSuportadora().getPosicao().getLinha(), jogo.getAreaJogavel().getNumeroColunas() - 1);
        Posicao origem = new Posicao(destino.getLinha(), 0);
        animar(origem, destino, TEMPO_ANIMACAO, "FogueteHORIZONTAL.png", 0);

        destino = new Posicao(0, foguete.getBaseSuportadora().getPosicao().getColuna());
        origem = new Posicao(jogo.getAreaJogavel().getNumeroLinhas() - 1, destino.getColuna());
        animar(origem, destino, TEMPO_ANIMACAO, "FogueteVERTICAL.png", 0);
    }

    @Override
    public void fogueteMudaDirecao(Foguete foguete) {
        componente.setCurrentLayer(1);
        colocarEm(foguete.getBaseSuportadora().getPosicao(), "Foguete" + foguete.getDirecao().toString() + ".png");
    }

    @Override
    public void porcoCriado(Porco porco, BaseSuportadora baseSuportadora) {

    }

    @Override
    public void fogueteCriado(Foguete foguete, BaseSuportadora baseSuportadora) {
        componente.setCurrentLayer(1);
        colocarEm(baseSuportadora.getPosicao(), "Foguete" + foguete.getDirecao().toString() + ".png");
    }

    @Override
    public void vidroCriado(Vidro vidro, BaseSuportadora baseSuportadora) {

    }

    @Override
    public void madeiraCriada(Madeira madeira, BaseSuportadora baseSuportadora) {

    }

    @Override
    public void pedraCriada(Pedra pedra, BaseSuportadora baseSuportadora) {

    }

    @Override
    public void bombaAtivada(Bomba bomba) {
        List<BaseSuportadora> basesSuportadoras = jogo.getAreaJogavel().getBasesSuportadorasAdjacentes(bomba.getBaseSuportadora().getPosicao());
        for (BaseSuportadora baseSuportadora: basesSuportadoras) {
            animar(baseSuportadora.getPosicao(), TEMPO_ANIMACAO, "Explosao.png", 2);
        }
    }

    @Override
    public void combinacaoBombasAtivadas(Bomba bomba) {
        List<BaseSuportadora> basesSuportadoras = jogo.getAreaJogavel().getBasesSuportadorasProximas(bomba.getBaseSuportadora().getPosicao());
        for (BaseSuportadora baseSuportadora: basesSuportadoras) {
            animar(baseSuportadora.getPosicao(), TEMPO_ANIMACAO, "Explosao.png", 2);
        }
    }

    @Override
    public void bombaCriada(Bomba bomba, BaseSuportadora baseSuportadora) {
        componente.setCurrentLayer(1);
        colocarEm(baseSuportadora.getPosicao(), "Bomba.png");
    }

    @Override
    public void combinacaoBombaFogueteAtivada(SuportadoAgrupavelBonus suportadoAgrupavelBonus) {
        Posicao posicao = suportadoAgrupavelBonus.getBaseSuportadora().getPosicao();
        Posicao novaPosicao;
        for (int i = -1; i <=1 ; i++) {
            novaPosicao = posicao.seguirVetor(new Posicao(i, 0));
            Posicao destino = new Posicao(novaPosicao.getLinha(), jogo.getAreaJogavel().getNumeroColunas() - 1);
            Posicao origem = new Posicao(novaPosicao.getLinha(), 0);
            animar(origem, destino, TEMPO_ANIMACAO, "FogueteHORIZONTAL.png", 0);

            novaPosicao = posicao.seguirVetor(new Posicao(0, i));
            destino = new Posicao(0, novaPosicao.getColuna());
            origem = new Posicao(jogo.getAreaJogavel().getNumeroLinhas() - 1, destino.getColuna());
            animar(origem, destino, TEMPO_ANIMACAO, "FogueteVERTICAL.png", 0);
        }
    }

}
