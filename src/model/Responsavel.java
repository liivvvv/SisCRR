package model;

public class Responsavel {
    private int idResponsavel;
    private Funcao funcao;
    private String nome;
    private String cracha;
    private String telefone;
    private boolean ativo;

    public Responsavel() {}

    public Responsavel(int idResponsavel, Funcao funcao, String nome, String cracha, String telefone, boolean ativo) {
       this.setIdResponsavel(idResponsavel);
       this.setFuncao(funcao);
       this.setNome(nome);
       this.setCracha(cracha);
       this.setTelefone(telefone);
       this.setAtivo(ativo);
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCracha() {
        return cracha;
    }

    public void setCracha(String cracha) {
        this.cracha = cracha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
