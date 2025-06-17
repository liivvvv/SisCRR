package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    private int idRecurso;
    private int idResponsavel;
    private int idTipoRecurso;
    private int quantidade;
    private String Descricao;
    private LocalDate dataLocacao;
    private LocalTime horarioLocacao;
    private LocalDate dataDevolucao;
    private LocalTime horarioDevolucao;
    private boolean ativo;


    public Reserva() {
    }

    public Reserva(int idResponsavel, int idTipoRecurso, int quantidade, String descricao, LocalDate dataLocacao, LocalTime horarioLocacao, LocalDate dataDevolucao, LocalTime horarioDevolucao, boolean ativo) {
        this.idResponsavel = idResponsavel;
        this.idTipoRecurso = idTipoRecurso;
        this.quantidade = quantidade;
        Descricao = descricao;
        this.dataLocacao = dataLocacao;
        this.horarioLocacao = horarioLocacao;
        this.dataDevolucao = dataDevolucao;
        this.horarioDevolucao = horarioDevolucao;
        this.ativo = ativo;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public int getIdTipoRecurso() {
        return idTipoRecurso;
    }

    public void setIdTipoRecurso(int idTipoRecurso) {
        this.idTipoRecurso = idTipoRecurso;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalTime getHorarioLocacao() {
        return horarioLocacao;
    }

    public void setHorarioLocacao(LocalTime horarioLocacao) {
        this.horarioLocacao = horarioLocacao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public LocalTime getHorarioDevolucao() {
        return horarioDevolucao;
    }

    public void setHorarioDevolucao(LocalTime horarioDevolucao) {
        this.horarioDevolucao = horarioDevolucao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
