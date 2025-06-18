package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int idReserva;
    private Responsavel responsavel;
    private TipoRecurso tipoRecurso;
    private String descricao;
    private int quantidade;
    private LocalDate dataLocacao;
    private LocalTime horarioLocacao;
    private LocalDate dataDevolucao;
    private LocalTime horarioDevolucao;
    private boolean ativo;

    public Reserva() {}

    public Reserva(int idReserva, Responsavel responsavel, TipoRecurso tipoRecurso, String descricao, int quantidade, LocalDate dataLocacao, LocalTime horarioLocacao, LocalDate dataDevolucao, LocalTime horarioDevolucao, boolean ativo) {
        this.setIdReserva(idReserva);
        this.setResponsavel(responsavel);
        this.setTipoRecurso(tipoRecurso);
        this.setDescricao(descricao);
        this.setQuantidade(quantidade);
        this.setDataLocacao(dataLocacao);
        this.setHorarioLocacao(horarioLocacao);
        this.setDataDevolucao(dataDevolucao);
        this.setHorarioDevolucao(horarioDevolucao);
        this.setAtivo(ativo);
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public TipoRecurso getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(TipoRecurso tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
