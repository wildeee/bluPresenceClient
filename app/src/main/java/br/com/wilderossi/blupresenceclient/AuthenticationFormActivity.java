package br.com.wilderossi.blupresenceclient;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.wilderossi.blupresenceclient.api.AuthenticationApi;
import br.com.wilderossi.blupresenceclient.components.LoaderDialog;
import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;
import br.com.wilderossi.blupresenceclient.vo.AlunoVO;
import br.com.wilderossi.blupresenceclient.vo.LoginVO;

public class AuthenticationFormActivity extends BaseActivity {

    private String urlInstituicao;

    @Override
    public int getActivity() {
        return R.layout.authentication_form;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urlInstituicao = SingletonHelper.urlInstituicao;

        TextView txtInstituicaoUrl = (TextView) findViewById(R.id.txtInstituicaoUrl);
        txtInstituicaoUrl.setText(urlInstituicao);
    }


    public void onClickAutenticar(View view) {
        EditText txtLogin = (EditText) findViewById(R.id.txtLogin);
        EditText txtSenha = (EditText) findViewById(R.id.txtSenha);
        String login = txtLogin.getText().toString();
        String senha = txtSenha.getText().toString();

        LoginVO loginVO = new LoginVO(login, senha);

        final LoaderDialog loader = new LoaderDialog(this);

        AuthenticationApi service = new AuthenticationApi(urlInstituicao, loginVO){
            @Override
            protected void onPostExecute(AlunoVO alunoVO) {
                loader.cancel();
                SingletonHelper.serverIdAluno = alunoVO.getId();
                AuthenticationFormActivity.this.finish();
            }
        };

        loader.show();
        service.execute();
    }

    @Override
    public void finish() {
        super.finish();
        SingletonHelper.threadAwaits = Boolean.FALSE;
    }
}
