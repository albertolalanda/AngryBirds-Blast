package pt.ipleiria.estg.ei.p2.blast.modelo.bases;

import pt.ipleiria.estg.ei.p2.blast.modelo.*;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.Suportado;
import pt.ipleiria.estg.ei.p2.blast.modelo.suportados.SuportadoAgrupavel;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Posicao;
import pt.ipleiria.estg.ei.p2.blast.modelo.utils.Sentido;

import java.util.ArrayList;
import java.util.List;

public class BaseSuportadora extends Base implements SensivelOndaChoque, Iteravel,
        ReagenteBonus, Interativo {

    private Suportado suportado;

    public BaseSuportadora(AreaJogavel areaJogavel, Posicao posicao) {
        super(areaJogavel, posicao);
    }

    @Override
    public void iterar() {
        if (suportado instanceof SuportadoAgrupavel) {
            ((SuportadoAgrupavel) suportado).iterar();
        }
    }

    @Override
    public boolean aceita(SuportadoAgrupavel<?> suportadoAgrupavel) {
        return isVazia();
    }

    @Override
    public void agarrar(SuportadoAgrupavel<?> suportadoAgrupavel) {
        this.suportado = suportadoAgrupavel;
        if (permiteSubirSuportadoAgrupavel()) {
            fazerSuportadoAgrupavelSubir();
        } else {
            getJogo().informarMovimentoSuportadoAgrupavel(suportadoAgrupavel, suportadoAgrupavel.getBaseSuportadora(), this);
            this.suportado.setBaseSuportadora(this);
        }
    }

    public Suportado getSuportado() {
        return suportado;
    }

    public void setSuportado(Suportado suportado) {
        this.suportado = suportado;
    }

    public boolean permiteSubirSuportadoAgrupavel() {
        if (suportado == null)
            return false;

        Base baseAcima = areaJogavel.getBase(posicao.seguir(Sentido.NORTE));
        if (baseAcima == null) {
            return false;
        }

        return baseAcima.aceita((SuportadoAgrupavel<?>) suportado);
    }

    public void fazerSuportadoAgrupavelSubir() {
        if (!permiteSubirSuportadoAgrupavel())
            return; // Devia lançar uma excepção

        Base baseAcima = areaJogavel.getBase(posicao.seguir(Sentido.NORTE));
        baseAcima.agarrar((SuportadoAgrupavel<?>) suportado);
        suportado = null;
    }

    public boolean isVazia() {
        return suportado == null;
    }

    public void libertarSuportado() {
        suportado = null;
    }

    @Override
    public void reagirInteracao() {
        if (podeInteragir()) {
            ((SuportadoAgrupavel) suportado).reagirInteracao();
        }
    }

    public Suportado getSuportadoA(Sentido sentido) {
        return areaJogavel.getSuportadoEm(posicao.seguir(sentido));
    }

    public <T extends SuportadoAgrupavel> List<T> getGrupoFormado() {
        List<T> porAnalisar = new ArrayList<>();
        List<T> analisados = new ArrayList<>();

        porAnalisar.add((T) suportado);

        while (!porAnalisar.isEmpty()) {
            T aAnalisar = porAnalisar.remove(0);
            if (analisados.contains(aAnalisar)) {
                continue;
            }
            analisados.add(aAnalisar);
            porAnalisar.addAll(aAnalisar.getAgrupaveisVizinhos());
        }
        return analisados;
    }

    @Override
    public boolean podeInteragir() {
        return suportado instanceof SuportadoAgrupavel && ((SuportadoAgrupavel) suportado).podeInteragir();
    }

    @Override
    public void receberOndaChoque() {
        if (suportado instanceof SensivelOndaChoque) {
            ((SensivelOndaChoque) suportado).receberOndaChoque();
        }
    }

    @Override
    public void reagirBonus() {
        if (suportado != null) {
            suportado.reagirBonus();
        }
    }

    public List<SensivelOndaChoque> getVizinhosSensiveisOndaChoque() {
        List<SensivelOndaChoque> resultado = new ArrayList<>();
        for (Sentido sentido : Sentido.values()) {
            Base base = areaJogavel.getBase(posicao.seguir(sentido));
            if (base instanceof SensivelOndaChoque)
                resultado.add((SensivelOndaChoque) base);
        }

        return resultado;
    }

    public void criarFoguete() {
        getAreaJogavel().criarFoguete(this);
    }

    protected Jogo getJogo() {
        return areaJogavel.getJogo();
    }

    public void criarBomba() {
        getAreaJogavel().criarBomba(this);
    }
}
