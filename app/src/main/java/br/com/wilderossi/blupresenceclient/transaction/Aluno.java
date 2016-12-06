package br.com.wilderossi.blupresenceclient.transaction;

public class Aluno {

    private Long id;
    private String serverId;
    private String url;

    public Aluno() { }

    public Aluno(Long id, String serverId, String url) {
        this.id = id;
        this.serverId = serverId;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}