package com.example.pmsu2021.Model;

public class PorudzbinaFinal {

    private String kupac;
    private String prodavac;
    private String adresaKupca;
    private Boolean dostavljeno;
    private int ocena;
    private String komentar;
    private Boolean anonimanKomentar;
    private Boolean arhiviranKomentar;
    private String sifraPorudzbine;
    private Double ukupnaCena;
    private String objectId;
    private String created;

    public String getProdavac() {
        return prodavac;
    }

    public void setProdavac(String prodavac) {
        this.prodavac = prodavac;
    }

    public String getKupac() {
        return kupac;
    }

    public void setKupac(String kupac) {
        this.kupac = kupac;
    }

    public String getAdresaKupca() {
        return adresaKupca;
    }

    public void setAdresaKupca(String adresaKupca) {
        this.adresaKupca = adresaKupca;
    }

    public Boolean getDostavljeno() {
        return dostavljeno;
    }

    public void setDostavljeno(Boolean dostavljeno) {
        this.dostavljeno = dostavljeno;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Boolean getAnonimanKomentar() {
        return anonimanKomentar;
    }

    public void setAnonimanKomentar(Boolean anonimanKomentar) {
        this.anonimanKomentar = anonimanKomentar;
    }

    public Boolean getArhiviranKomentar() {
        return arhiviranKomentar;
    }

    public void setArhiviranKomentar(Boolean arhiviranKomentar) {
        this.arhiviranKomentar = arhiviranKomentar;
    }

    public String getSifraPorudzbine() {
        return sifraPorudzbine;
    }

    public void setSifraPorudzbine(String sifraPorudzbine) {
        this.sifraPorudzbine = sifraPorudzbine;
    }

    public Double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(Double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
