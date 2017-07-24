package pt.ipleiria.estg.ei.p2.blast;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    private static final String MENSAGEM = "pt.ipleiria.estg.ei.p2.blast.abburbano.MENSAGEM";
    private TextView txtMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_resultado);

        txtMensagem = (TextView) findViewById(R.id.txtMensagem);

        Intent intent = getIntent();
        String mensagem = intent.getStringExtra(MENSAGEM);
        txtMensagem.setText(mensagem);
    }

    public void onClickNao(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickSim(View view) {
        setResult(RESULT_OK);
        finish();
    }

    public static Intent createIntent(Context context, String mensagem) {
        return new Intent(context, ResultadoActivity.class).putExtra(MENSAGEM, mensagem);
    }
}
