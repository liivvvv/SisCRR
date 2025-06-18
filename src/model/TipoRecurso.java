package model;

public class TipoRecurso {
    private int idTipoRecurso;
    private String tipoRecurso;
    private String descricao;
    private boolean ativo;

    public TipoRecurso() {}

    public TipoRecurso(int idTipoRecurso, String tipoRecurso, String descricao, boolean ativo) {
        this.setIdTipoRecurso(idTipoRecurso);
        this.setTipoRecurso(tipoRecurso);
        this.setDescricao(descricao);
        this.setAtivo(ativo);
    }

    public int getIdTipoRecurso() {
        return idTipoRecurso;
    }

    public void setIdTipoRecurso(int idTipoRecurso) {
        this.idTipoRecurso = idTipoRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return this.tipoRecurso;
    }
}
