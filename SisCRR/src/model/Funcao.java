package model;

public class Funcao {
    private int IdFuncao;
    private String TipoFuncao;
    private String Permissao;
    private boolean Ativo;

    public Funcao() {
    }

    public Funcao(String tipoFuncao, String permissao, boolean ativo) {
        TipoFuncao = tipoFuncao;
        Permissao = permissao;
        Ativo = ativo;
    }

    public int getIdFuncao() {
        return IdFuncao;
    }

    public void setIdFuncao(int idFuncao) {
        IdFuncao = idFuncao;
    }

    public String getTipoFuncao() {
        return TipoFuncao;
    }

    public void setTipoFuncao(String tipoFuncao) {
        TipoFuncao = tipoFuncao;
    }

    public String getPermissao() {
        return Permissao;
    }

    public void setPermissao(String permissao) {
        Permissao = permissao;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }
}
