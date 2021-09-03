package com.example.pmsu2021.Model;

import java.util.Date;

public class Akcija {

    private String nazivAkcije;
    private Date datumOd;
    private Date datumDo;
    private String imeProdavca;
    private String opis;
    private int popust;
    private String objectId;

    public String getNazivAkcije() {
        return nazivAkcije;
    }

    public void setNazivAkcije(String nazivAkcije) {
        this.nazivAkcije = nazivAkcije;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    public String getImeProdavca() {
        return imeProdavca;
    }

    public void setImeProdavca(String imeProdavca) {
        this.imeProdavca = imeProdavca;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
