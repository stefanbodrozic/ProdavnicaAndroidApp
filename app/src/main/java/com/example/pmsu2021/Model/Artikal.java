package com.example.pmsu2021.Model;

public class Artikal {

    private double cena;
    private double cenaNaPopustu;
    private String nazivAkcije;
    private String nazivArtikla;
    private String opis;
    private String imeProdavca;
    private String objectId;
    private String fileColumn;

    public double getCenaNaPopustu() {
        return cenaNaPopustu;
    }

    public void setCenaNaPopustu(double cenaNaPopustu) {
        this.cenaNaPopustu = cenaNaPopustu;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getNazivAkcije() {
        return nazivAkcije;
    }

    public void setNazivAkcije(String nazivAkcije) {
        this.nazivAkcije = nazivAkcije;
    }

    public String getNazivArtikla() {
        return nazivArtikla;
    }

    public void setNazivArtikla(String nazivArtikla) {
        this.nazivArtikla = nazivArtikla;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getImeProdavca() {
        return imeProdavca;
    }

    public void setImeProdavca(String imeProdavca) {
        this.imeProdavca = imeProdavca;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFileColumn() {
        return fileColumn;
    }

    public void setFileColumn(String fileColumn) {
        this.fileColumn = fileColumn;
    }
}
