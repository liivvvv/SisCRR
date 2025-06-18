package model;

public class Funcao {
    private int idFuncao;
    private String tipoFuncao;
    private String permissao;
    private boolean ativo;

    public Funcao() {}

    public Funcao(int idFuncao, String tipoFuncao, String permissao, boolean ativo) {
        this.setIdFuncao(idFuncao);
        this.setTipoFuncao(tipoFuncao);
        this.setPermissao(permissao);
        this.setAtivo(ativo);
    }

    public int getIdFuncao() {
        return idFuncao;
    }

    public void setIdFuncao(int idFuncao) {
        this.idFuncao = idFuncao;
    }

    public String getTipoFuncao() {
        return tipoFuncao;
    }

    public void setTipoFuncao(String tipoFuncao) {
        this.tipoFuncao = tipoFuncao;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return this.tipoFuncao;
    }
}
