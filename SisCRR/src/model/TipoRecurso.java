package model;

public class TipoRecurso {
    private int IdTipoRecurso;
    private String TipoRecurso;
    private String Descricao;
    private boolean Ativo;

    public TipoRecurso() {
    }

    public TipoRecurso(String tipoRecurso, String descricao, boolean ativo) {
        TipoRecurso = tipoRecurso;
        Descricao = descricao;
        Ativo = ativo;
    }

    public int getIdTipoRecurso() {
        return IdTipoRecurso;
    }

    public void setIdTipoRecurso(int idTipoRecurso) {
        IdTipoRecurso = idTipoRecurso;
    }

    public String getTipoRecurso() {
        return TipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        TipoRecurso = tipoRecurso;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public boolean isAtivo() {
        return Ativo;
    }

    public void setAtivo(boolean ativo) {
        Ativo = ativo;
    }
}
