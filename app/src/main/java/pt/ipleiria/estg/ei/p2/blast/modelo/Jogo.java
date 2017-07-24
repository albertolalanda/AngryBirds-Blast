package pt.ipleiria.estg.ei.p2.blast.modelo;

import pt.ipleiria.estg.ei.p2.blast.modelo.bases.Base;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoJogo;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoParcialBalao;
import pt.ipleiria.estg.ei.p2.blast.modelo.objetivos.ObjetivoParcialPorco;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.*;

import java.util.ArrayList;
import java.util.List;

public class Jogo extends ObjetoComAreaJogavel implements Iteravel, InterativoPosicao {
    private EstadoJogo estadoJogo;
    private int pontuacao;
    private int numeroMovimentosRestantes;
    private ObjetivoJogo objetivoJogo;
    private List<OuvinteJogo> ouvintes;

    public Jogo() {
        super(new AreaJogavel());
        areaJogavel.setJogo(this);
        ouvintes = new ArrayList<>();
        reiniciar();
    }

    public void reiniciar() {
        estadoJogo = EstadoJogo.A_DECORRER;
        pontuacao = 0;
        numeroMovimentosRestantes = 10;
        objetivoJogo = new ObjetivoJogo();
        objetivoJogo.adicionar(new ObjetivoParcialBalao(Especie.STELLA, 4));
        objetivoJogo.adicionar(new ObjetivoParcialPorco(2));
        areaJogavel.carregarNivel();
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void incrementarPontuacao(int valor) {
        pontuacao += valor;
    }

    public int getNumeroMovimentosRestantes() {
        return numeroMovimentosRestantes;
    }

    private void decrementarNumeroMovimentosRestantes() {
        numeroMovimentosRestantes--;
    }

    public EstadoJogo getEstadoJogo() {
        return estadoJogo;
    }

    @Override
    public void iterar() {
        areaJogavel.iterar();
    }

    @Override
    public boolean interagir(int linha, int coluna) {
        if (estadoJogo != EstadoJogo.A_DECORRER)
            return false;

        boolean jogadaValida = areaJogavel.interagir(linha, coluna);
        if (jogadaValida) {
            decrementarNumeroMovimentosRestantes();
            if (objetivoJogo.isConcluido()) {
                if (estadoJogo == EstadoJogo.A_DECORRER) {
                    estadoJogo = EstadoJogo.CONCLUIDO_VITORIA;
                    informarObjetivosAtingidos();
                }
            } else if (numeroMovimentosRestantes == 0) {
                estadoJogo = EstadoJogo.CONCLUIDO_DERROTA;
                informarMovimentosEsgotados();
            }
        }
        return jogadaValida;
    }

    public void influenciarObjetivoDoJogo(Objetivavel objetivavel) {
        objetivoJogo.influenciar(objetivavel);
    }

    public ObjetivoJogo getObjetivoJogo() {
        return objetivoJogo;
    }

    private void informarMovimentosEsgotados() {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.movimentosEsgotados();
        }
    }

    private void informarObjetivosAtingidos() {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.objetivosConcluidos();
        }
    }

    public void informarExplosao(Suportado suportado) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.suportadoExplodiu(suportado);
        }
    }

    public void informarMovimentoSuportadoAgrupavel(SuportadoAgrupavel<?> suportadoAgrupavel, BaseSuportadora baseOrigem, BaseSuportadora baseDestino) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.suportadoAgrupavelMovimentou(suportadoAgrupavel, baseOrigem, baseDestino);
        }
    }

    public void informarCriacaoBalao(Balao novoSuportado, Base baseInsercao) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.balaoCriado(novoSuportado, baseInsercao);
        }
    }

    public void informarDestruicaoParcial(SuportadoSensivelOndaChoqueComForca suportado, float percentagemRestante) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.suportadoDestruidoParcialmente(suportado, percentagemRestante);
        }
    }

    public void informarFogueteLancado(Foguete foguete) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.fogueteLancado(foguete);
        }
    }

    public void informarCombinacaoFoguetesLancados(Foguete foguete) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.combinacaoFoguetesLancados(foguete);
        }
    }

    public void informarMudancaDirecaoFoguete(Foguete foguete) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.fogueteMudaDirecao(foguete);
        }
    }

    public void informarCriacaoPorco(Porco porco, BaseSuportadora baseSuportadora) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.porcoCriado(porco, baseSuportadora);
        }
    }

    public void informarCriacaoVidro(Vidro vidro, BaseSuportadora baseSuportadora) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.vidroCriado(vidro, baseSuportadora);
        }
    }

    public void informarCriacaoMadeira(Madeira madeira, BaseSuportadora baseSuportadora) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.madeiraCriada(madeira, baseSuportadora);
        }
    }

    public void informarCriacaoFoguete(Foguete foguete, BaseSuportadora baseSuportadora) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.fogueteCriado(foguete, baseSuportadora);
        }
    }

    public void adicionarOuvinte(OuvinteJogo ouvinte) {
        ouvintes.add(ouvinte);
    }

    public void removerOuvinte(OuvinteJogo ouvinte) {
        ouvintes.remove(ouvinte);
    }

    public void informarCriacaoPedra(Pedra pedra, BaseSuportadora baseSuportadora) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.pedraCriada(pedra, baseSuportadora);
        }
    }

    public void informarBombaAtivada(Bomba bomba) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.bombaAtivada(bomba);
        }
    }

    public void informarCombinacaoBombasAtivada(Bomba bomba) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.combinacaoBombasAtivadas(bomba);
        }
    }

    public void informarCriacaoBomba(Bomba bomba, BaseSuportadora baseSuportadora) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.bombaCriada(bomba, baseSuportadora);
        }
    }

    public void informarCombinacaoBombaseFoguetes(SuportadoAgrupavelBonus suportadoAgrupavelBonus) {
        for (OuvinteJogo ouvinte : ouvintes) {
            ouvinte.combinacaoBombaFogueteAtivada(suportadoAgrupavelBonus);
        }
    }

}
