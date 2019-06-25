package br.ifms.tsi.iot.model;

public class Comandos {

    private String ventilador;

    private String sala;

    private String quarto;

    private String tv;

    public int getVentilador() {
        if (ventilador.equals("true")) {
            return 10;
        } else {
            return 11;
        }
    }

    public void setVentilador(String ventilador) {
        this.ventilador = ventilador;
    }

    public int getSala() {
        if (sala.equals("true")) {
            return 12;
        } else {
            return 13;
        }
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public int getQuarto() {
        if (quarto.equals("true")) {
            return 14;
        } else {
            return 15;
        }
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public int getTv() {
        if (tv.equals("true")) {
            return 16;
        } else {
            return 17;
        }
    }

    public void setTv(String tv) {
        this.tv = tv;
    }
}
