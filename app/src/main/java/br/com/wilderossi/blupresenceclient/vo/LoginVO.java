package br.com.wilderossi.blupresenceclient.vo;

public class LoginVO {

    private final String login;
    private final String senha;

    public LoginVO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
