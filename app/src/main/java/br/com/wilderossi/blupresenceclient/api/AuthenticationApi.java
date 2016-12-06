package br.com.wilderossi.blupresenceclient.api;

import br.com.wilderossi.blupresenceclient.vo.AlunoVO;
import br.com.wilderossi.blupresenceclient.vo.LoginVO;

public class AuthenticationApi extends BaseApi<AlunoVO>{

    private static final String RELATIVE_URL = "/studentAuthentication";

    private final LoginVO loginVO;

    public AuthenticationApi(String baseUrl, LoginVO loginVO) {
        super(baseUrl);
        this.loginVO = loginVO;
    }

    @Override
    protected String getRelativeUrl() {
        return RELATIVE_URL;
    }

    @Override
    protected AlunoVO doInBackground(Void... params) {
        return rest.postForObject(getUrl(), loginVO, AlunoVO.class);
    }
}
