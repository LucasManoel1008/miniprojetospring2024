package br.com.itb.miniprojetospring.model;

import br.com.itb.miniprojetospring.Enums.FiltrosEnums;

public class Filtros {

    private String categoria;
    private FiltrosEnums dataFiltro;
    private double precoMax;
    private String area;

    public Filtros() {
    }

    public Filtros(String categoria, FiltrosEnums dataFiltro, double precoMax, String area) {
        this.categoria = categoria;
        this.dataFiltro = dataFiltro;
        this.precoMax = precoMax;
        this.area = area;
    }

    // Getter's e Setter's

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public FiltrosEnums getDataFiltro() {
        return dataFiltro;
    }

    public void setDataFiltro(FiltrosEnums dataFiltro) {
        this.dataFiltro = dataFiltro;
    }

    public double getPrecoMax() {
        return precoMax;
    }

    public void setPrecoMax(double precoMax) {
        this.precoMax = precoMax;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
