package model;

import java.time.LocalDateTime;

public class Usuario {
    private Integer idUsuario;
    private String login;
    private String senhaHash;
    private String papel;
    private boolean ativo;
    private LocalDateTime createdAt;

    public Usuario() {}

    public Usuario(String login, String senhaHash, String papel) {
        this.login = login;
        this.senhaHash = senhaHash;
        this.papel = papel;
        this.ativo = true;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    public String getPapel() { return papel; }
    public void setPapel(String papel) { this.papel = papel; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
