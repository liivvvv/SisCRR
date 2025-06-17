package model;

public class Responsavel {
    private int IdResponsavel;
    private int IdFuncao;
    private String Nome;
    private String Cracha;
    private long Telefone;
    private boolean Ativo;

    public Responsavel() {
    }

    public Responsavel(int idFuncao, String nome, String cracha, long telefone, boolean ativo) {
        IdFuncao = idFuncao;
        Nome = nome;
        Cracha = cracha;
        Telefone = telefone;
        Ativo = ativo;
    }

    public int getIdResponsavel() {
        return IdResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        IdResponsavel = idResponsavel;
    }

    public int getIdFuncao() {
        return IdFuncao;
    }

    public void setIdFuncao(int idFuncao) {
        IdFuncao = idFuncao;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCracha() {
        return Cracha;
    }

    public void setCracha(String cracha) {
        Cracha = cracha;
    }

    public long getTelefone() {
        return Telefone;
    }

    public void setTelefone(long telefone) {
        Telefone = telefone;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }
}
