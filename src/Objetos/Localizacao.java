package Objetos;

public class Localizacao {

    private String nome;
    private String descricao;
    private int codLocalizacao;

    public Localizacao() {
    }

    public Localizacao(int codLocalizacao, String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.codLocalizacao = codLocalizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodLocalizacao() {
        return codLocalizacao;
    }

    public void setCodLocalizacao(int codLocalizacao) {
        this.codLocalizacao = codLocalizacao;
    }

}
