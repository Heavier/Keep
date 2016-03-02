package com.example.dam.keep.pojo;

public class Keep {

    private long id;
    private String contenido;
    private String estado;

    public Keep() {
    }

    public Keep(long id, String contenido, String estado) {
        this.id = id;
        this.contenido = contenido;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keep keep = (Keep) o;

        return id == keep.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Keep{" +
                "id=" + id +
                ", contenido='" + contenido + '\'' +
                ", estado=" + estado +
                '}';
    }
}
