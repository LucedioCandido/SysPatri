package Objetos;

public class Categoria {
    private String nome;
    private String descricao;
    private int codCategoria;

    public Categoria() {

    }

    public Categoria(int codCategoria, String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.codCategoria = codCategoria;
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
}
