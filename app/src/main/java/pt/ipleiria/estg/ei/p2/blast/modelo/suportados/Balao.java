package pt.ipleiria.estg.ei.p2.blast.modelo.suportados;

import pt.ipleiria.estg.ei.p2.blast.modelo.*;
import pt.ipleiria.estg.ei.p2.blast.modelo.bases.BaseSuportadora;

import java.util.ArrayList;
import java.util.List;

public class Balao extends SuportadoAgrupavel implements Objetivavel, ObjetoComEspecie {
    private DelegadoEspecie delegadoEspecie;

    public Balao(BaseSuportadora baseSuportadora, Especie especie) {
        super(baseSuportadora);
        this.delegadoEspecie = new DelegadoEspecie(especie);
    }

    public Especie getEspecie() {
        return delegadoEspecie.getEspecie();
    }

    @Override
    public void reagirInteracao() {
        List<Balao> grupo = baseSuportadora.getGrupoFormado();
        int tamanhoGrupo = grupo.size();

        if (tamanhoGrupo > 1) {
            List<BaseSuportadora> basesOndaChoque = new ArrayList<>();
            for (Balao balao : grupo) {
                balao.explodir();
                basesOndaChoque.add(balao.getBaseSuportadora());
            }
            getJogo().incrementarPontuacao(calcularPontuacao(tamanhoGrupo));

            espalharOndaChoque(basesOndaChoque);

            if (tamanhoGrupo > 4) {
                if (tamanhoGrupo >= 7){
                    getBaseSuportadora().criarBomba();
                } else {
                    getBaseSuportadora().criarFoguete();
                }
            }
        }
    }

    @Override
    public void explodir() {
        super.explodir();
        getJogo().influenciarObjetivoDoJogo(this);
    }

    @Override
    public boolean agrupaCom(SuportadoAgrupavel suportadoAgrupavel) {
        return suportadoAgrupavel instanceof Balao &&
                ((Balao) suportadoAgrupavel).getEspecie() == getEspecie();
    }

    @Override
    public boolean podeInteragir() {
        return getAgrupaveisVizinhos().size() > 0;
    }

    @Override
    public void reagirBonus() {
        explodir();
        getJogo().incrementarPontuacao(calcularPontuacao(1));
    }

    private void espalharOndaChoque(List<BaseSuportadora> basesOndaChoque) {
        List<SensivelOndaChoque> sensiveis = new ArrayList<>();

        for (BaseSuportadora base : basesOndaChoque) {

            for (SensivelOndaChoque sensivel : base.getVizinhosSensiveisOndaChoque()) {
                if (!sensiveis.contains(sensivel) && !basesOndaChoque.contains(sensivel))
                    sensiveis.add(sensivel);
            }
        }

        for (SensivelOndaChoque sensivel : sensiveis) {
            sensivel.receberOndaChoque();
        }
    }

    private int calcularPontuacao(int tamanhoGrupo) {
        return (int) Math.pow(2, tamanhoGrupo) * 10;
    }
}
