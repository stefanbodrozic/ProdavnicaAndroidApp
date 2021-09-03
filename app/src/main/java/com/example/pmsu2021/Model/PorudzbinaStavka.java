package com.example.pmsu2021.Model;

public class PorudzbinaStavka {

    private String sifra;
    private String artikalNaziv;
    private int kolicina;
    private String kupac;
    private Double cenaArtikla;
    private String prodavac;
    private String ownerId;

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }


    public String getArtikalNaziv() {
        return artikalNaziv;
    }

    public void setArtikalNaziv(String artikalNaziv) {
        this.artikalNaziv = artikalNaziv;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Double getCenaArtikla() {
        return cenaArtikla;
    }

    public void setCenaArtikla(Double cenaArtikla) {
        this.cenaArtikla = cenaArtikla;
    }


    public String getKupac() {
        return kupac;
    }

    public void setKupac(String kupac) {
        this.kupac = kupac;
    }


    public String getProdavac() {
        return prodavac;
    }

    public void setProdavac(String prodavac) {
        this.prodavac = prodavac;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
