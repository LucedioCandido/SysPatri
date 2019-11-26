package Objetos;

public class Bem {


    private int codBem;
    private String nome;
    private String descricao;
    private int codCategoria;
    private int codLocalizacao;

    public Bem(String nome, String descricao, String categoria, String local){

    }
    public Bem(String nome, String descricao, int codCat, int codLocal){
        this.nome = nome;
        this.descricao = descricao;
        this.codCategoria = codCat;
        this.codLocalizacao = codLocal;
        this.codCategoria = codCat;
    }

    public Bem(int codBem, String nome, String descricao, int codCat, int codLocal){
        this.codBem = codBem;
        this.nome = nome;
        this.descricao = descricao;
        this.codCategoria = codCat;
        this.codLocalizacao = codLocal;
        this.codCategoria = codCat;
    }

    public int getCodBem() {
        return codBem;
    }

    public void setCodBem(int codBem) {
        this.codBem = codBem;
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

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public int getCodLocalizacao() {
        return codLocalizacao;
    }

    public void setCodLocalizacao(int codLocalizacao) {
        this.codLocalizacao = codLocalizacao;
    }
}
