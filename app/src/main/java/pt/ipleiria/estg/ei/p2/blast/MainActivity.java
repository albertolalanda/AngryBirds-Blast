package pt.ipleiria.estg.ei.p2.blast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pt.ipleiria.estg.dei.gridcomponent.GridComponent;
import pt.ipleiria.estg.dei.gridcomponent.GridPanelEventHandler;
import pt.ipleiria.estg.ei.p2.blast.modelo.Jogo;

public class MainActivity extends AppCompatActivity implements GridPanelEventHandler {
    private Jogo jogo;
    private GridComponent gridComponent;
    private RepresentadorAndroid representadorAndroid;
    private GridComponent gridComponentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridComponent = (GridComponent) findViewById(R.id.gridAreaJogavel);
        gridComponentInfo = (GridComponent) findViewById(R.id.gridInfo);

        iniciarJogo();


    }

    private void iniciarJogo() {
        //criar ou reiniciar o jogo
        if (jogo == null) {
            jogo = new Jogo();

            //definir o representador em android
            //além do jogo é necessário passar o contexto e o grid
            representadorAndroid = new RepresentadorAndroid(jogo, this, gridComponent, gridComponentInfo);
        } else {
            jogo.reiniciar();
            representadorAndroid.reiniciar();
        }

        //iterar pela 1ª vez para criar balões
        jogo.iterar();

        //definir esta classe como sendo o ouvinte dos eventos do componente
        gridComponent.setEventHandler(this);

        // coordenar os repaints e outros eventos com a grelha de cima
        gridComponent.coordinateWith(gridComponentInfo);
    }

    @Override
    public void pressed(int linha, int coluna) {

    }

    @Override
    public void released(int linha, int coluna) {
        //vamos assumir que apenas interessa saber onde se levantou o dedo
        if (jogo.interagir(linha, coluna)) {
            jogo.iterar();
            representadorAndroid.representarInfo();
        }
    }

    @Override
    public void dragged(int linha, int coluna) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RepresentadorAndroid.JOGAR_NOVAMENTE) {
            if (resultCode == RESULT_OK) {
                iniciarJogo();
            } else {
                finish();
            }
        }

    }
}
