package br.com.letscode.cookbook.domain;

import br.com.letscode.cookbook.enums.TipoRendimento;

public class Rendimento {
    private int minimo;
    private int maximo;
    private TipoRendimento tipo;

    public Rendimento(int minimo, int maximo, TipoRendimento tipo) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.tipo = tipo;
    }

    public Rendimento(int quantidade, TipoRendimento tipo) {
        maximo = minimo = quantidade;
        this.tipo = tipo;
    }

    public int getMinimo() {
        return minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public TipoRendimento getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "br.com.letscode.cookbook.domain.Rendimento{" +
                "minimo=" + minimo +
                ", maximo=" + maximo +
                ", tipo=" + tipo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rendimento that = (Rendimento) o;

        if (minimo != that.minimo) return false;
        if (maximo != that.maximo) return false;
        return tipo == that.tipo;
    }

    @Override
    public int hashCode() {
        int result = minimo;
        result = 31 * result + maximo;
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }
}
