package br.com.letscode.cookbook.domain;

import br.com.letscode.cookbook.enums.TipoMedida;

public class Ingrediente {
    private String nome;
    private double quantidade;
    private TipoMedida tipo;

    public Ingrediente(String nome, double quantidade, TipoMedida tipo) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public TipoMedida getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "br.com.letscode.cookbook.domain.Ingrediente{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", tipo=" + tipo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingrediente that = (Ingrediente) o;

        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
}
