package pt.ipleiria.estg.ei.p2.blast.modelo;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.Base;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseAr;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.*;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AreaJogavel implements Iteravel, InterativoPosicao {
    private static int NUMERO_LINHAS;
    private static int NUMERO_COLUNAS;
    private static final int BASEAR_ = 0;
    private static final int BASESUP = 1;
    private static final int PORCO__ = 2;
    private static final int FOGUETE = 3;
    private static final int VIDRO__ = 4;
    private static final int MADEIRA = 5;
    private static final int PEDRA__ = 6;
    private static final int BOMBA__ = 7;
    private Random aleatorio = new Random();

    private Base grelha[][];
    private Jogo jogo;

    private static final int[][] NIVEL = {
            {BASEAR_, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASEAR_},
            {BOMBA__, BASESUP, VIDRO__, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP},
            {PORCO__, BASESUP, BASESUP, BASEAR_, BASEAR_, PORCO__, BASESUP, BASESUP, BASESUP},
            {PEDRA__, BASESUP, BASESUP, BASEAR_, BASEAR_, PEDRA__, BASESUP, BASESUP, BASESUP},
            {BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP},
            {BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, MADEIRA, MADEIRA, BASESUP},
            {BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BOMBA__, BASESUP, VIDRO__},
            {BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, FOGUETE, BASESUP, VIDRO__},
            {BASEAR_, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASESUP, BASEAR_}
    };

    public AreaJogavel() {
        carregarNivel();
    }

    public void carregarNivel() {
        NUMERO_LINHAS = NIVEL.length;
        NUMERO_COLUNAS = NIVEL[0].length;
        grelha = new Base[NUMERO_LINHAS][NUMERO_COLUNAS];
        for (int i = 0; i < NUMERO_LINHAS; i++)
            for (int j = 0; j < NUMERO_COLUNAS; j++) {
                grelha[i][j] = NIVEL[i][j] == BASEAR_ ? new BaseAr(this, new Posicao(i, j)) :
                        new BaseSuportadora(this, new Posicao(i, j));
                switch (NIVEL[i][j]) {
                    case PORCO__:
                        criarPorco((BaseSuportadora) grelha[i][j]);
                        break;
                    case FOGUETE:
                        criarFoguete((BaseSuportadora) grelha[i][j]);
                        break;
                    case VIDRO__:
                        criarVidro((BaseSuportadora) grelha[i][j]);
                        break;
                    case MADEIRA:
                        criarMadeira((BaseSuportadora) grelha[i][j]);
                        break;
                    case PEDRA__:
                        criarPedra((BaseSuportadora) grelha[i][j]);
                        break;
                    case BOMBA__:
                        criarBomba((BaseSuportadora) grelha[i][j]);
                        break;
                }
            }
    }

    public void criarBomba(BaseSuportadora baseSuportadora) {
        Bomba bomba = new Bomba(baseSuportadora);
        baseSuportadora.setSuportado(bomba);

        if (jogo != null) {
            jogo.informarCriacaoBomba(bomba, baseSuportadora);
        }
    }

    private void criarPedra(BaseSuportadora baseSuportadora) {
        Pedra pedra = new Pedra(baseSuportadora);
        baseSuportadora.setSuportado(pedra);

        if (jogo != null) {
            jogo.informarCriacaoPedra(pedra, baseSuportadora);
        }
    }

    public int getNumeroLinhas() {
        return grelha.length;
    }

    public int getNumeroColunas() {
        return grelha[0].length;
    }

    public Base getBase(int linha, int coluna) {
        if (linha < 0 || linha >= NUMERO_LINHAS || coluna < 0 || coluna >= NUMERO_COLUNAS)
            return null;
        return grelha[linha][coluna];
    }

    public Base getBase(Posicao posicao) {
        return getBase(posicao.getLinha(), posicao.getColuna());
    }

    @Override
    public void iterar() {
        //manda iterar as bases suportadoras
        for (int linha = 0; linha < NUMERO_LINHAS; linha++) {
            for (int coluna = 0; coluna < NUMERO_COLUNAS; coluna++) {
                Base base = getBase(linha, coluna);
                if (base instanceof BaseSuportadora)
                    ((BaseSuportadora) base).iterar();
            }
        }

        //gera baloes nas bases suportadoras livres da linha de baixo
        for (int coluna = 0; coluna < NUMERO_COLUNAS; coluna++) {
            Base base = getBase(NUMERO_LINHAS - 1, coluna);
            Balao novoBalao;
            while (base.aceita(novoBalao = gerarBalao())) {
                jogo.informarCriacaoBalao(novoBalao, base);
                base.agarrar(novoBalao);
            }
        }
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public boolean interagir(int linha, int coluna) {
        BaseSuportadora baseSuportadora = getBaseSuportadora(linha, coluna);

        if (baseSuportadora == null || !baseSuportadora.podeInteragir())
            return false;

        baseSuportadora.reagirInteracao();
        inverterDirecaoFoguetes();
        return true;
    }

    private void inverterDirecaoFoguetes() {
        for (int linha = 0; linha < NUMERO_LINHAS; linha++) {
            for (int coluna = 0; coluna < NUMERO_COLUNAS; coluna++) {
                Base base = getBase(linha, coluna);
                if (base instanceof BaseSuportadora) {
                    Suportado suportado = ((BaseSuportadora) base).getSuportado();
                    if (suportado instanceof Foguete)
                        ((Foguete) suportado).inverterDirecao();
                }
            }
        }
    }

    public Suportado getSuportadoEm(Posicao posicao) {
        BaseSuportadora baseSuportadora = getBaseSuportadora(posicao.getLinha(), posicao.getColuna());
        return baseSuportadora != null ? baseSuportadora.getSuportado() : null;
    }

    public void criarFoguete(BaseSuportadora baseSuportadora) {
        Foguete foguete = new Foguete(baseSuportadora);
        baseSuportadora.setSuportado(foguete);

        if (jogo != null) {
            jogo.informarCriacaoFoguete(foguete, baseSuportadora);
        }
    }

    private void criarPorco(BaseSuportadora baseSuportadora) {
        Porco porco = new Porco(baseSuportadora);
        baseSuportadora.setSuportado(porco);
        if (jogo != null) {
            jogo.informarCriacaoPorco(porco, baseSuportadora);
        }
    }

    private void criarVidro(BaseSuportadora baseSuportadora) {
        Vidro vidro = new Vidro(baseSuportadora);
        baseSuportadora.setSuportado(vidro);
        if (jogo != null) {
            jogo.informarCriacaoVidro(vidro, baseSuportadora);
        }
    }

    private void criarMadeira(BaseSuportadora baseSuportadora) {
        Madeira madeira = new Madeira(baseSuportadora);
        baseSuportadora.setSuportado(madeira);
        if (jogo != null) {
            jogo.informarCriacaoMadeira(madeira, baseSuportadora);
        }
    }

    private BaseSuportadora getBaseSuportadora(int linha, int coluna) {
        Base base = getBase(linha, coluna);
        if (base instanceof BaseSuportadora) {
            return (BaseSuportadora) base;
        }
        return null;
    }

    private Balao gerarBalao() {
        return new Balao(null, Especie.values()[getValorAleatorio(Especie.values().length)]);
    }

    public int getValorAleatorio(int max) {
        return aleatorio.nextInt(max);
    }

    public List<BaseSuportadora> getBasesSuportadorasDaLinha(int linha) {
        List<BaseSuportadora> bases = new ArrayList<>();

        for (int k = 0; k < NUMERO_COLUNAS; k++) {
            Base aux = getBase(linha, k);
            if (aux instanceof BaseSuportadora)
                bases.add((BaseSuportadora) aux);
        }

        return bases;
    }

    public List<BaseSuportadora> getBasesSuportadorasDaColuna(int coluna) {
        List<BaseSuportadora> bases = new ArrayList<>();

        for (int k = 0; k < NUMERO_LINHAS; k++) {
            Base aux = getBase(k, coluna);
            if (aux instanceof BaseSuportadora)
                bases.add((BaseSuportadora) aux);
        }

        return bases;
    }

    public List<BaseSuportadora> getBasesSuportadorasAdjacentes(Posicao posicao) {
        List<BaseSuportadora> bases = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Posicao p = posicao.seguirVetor(new Posicao(i, j));
                Base aux = getBaseSuportadora(p.getLinha(), p.getColuna());
                if (aux instanceof BaseSuportadora) {
                    bases.add((BaseSuportadora) aux);
                }
            }
        }

        return bases;
    }

    public List<BaseSuportadora> getBasesSuportadorasProximas(Posicao posicao) {
        List<BaseSuportadora> bases = new ArrayList<>();

        bases.addAll(getBasesSuportadorasAdjacentes(posicao));

        Base aux = getBase(posicao.seguirVetor(new Posicao(-2, 0)));
        if (aux instanceof BaseSuportadora) {
            bases.add((BaseSuportadora) aux);
        }
        aux = getBase(posicao.seguirVetor(new Posicao(0, -2)));
        if (aux instanceof BaseSuportadora) {
            bases.add((BaseSuportadora) aux);
        }
        aux = getBase(posicao.seguirVetor(new Posicao(0, 2)));
        if (aux instanceof BaseSuportadora) {
            bases.add((BaseSuportadora) aux);
        }
        aux = getBase(posicao.seguirVetor(new Posicao(2, 0)));
        if (aux instanceof BaseSuportadora) {
            bases.add((BaseSuportadora) aux);
        }

        return bases;
    }
}
