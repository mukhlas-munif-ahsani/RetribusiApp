package com.munifahsan.retribusiapp.History;

public class HistoryModel {
    String created_at;
    String jenis;
    String pedagang_id;
    String petugas_id;
    String nama_pedagang;
    int potongan;
    int sisa_saldo;

    public HistoryModel() {
    }

    public HistoryModel(String created_at, String jenis, String pedagang_id, String petugas_id, String nama_pedagang, int potongan, int sisa_saldo) {
        this.created_at = created_at;
        this.jenis = jenis;
        this.pedagang_id = pedagang_id;
        this.petugas_id = petugas_id;
        this.nama_pedagang = nama_pedagang;
        this.potongan = potongan;
        this.sisa_saldo = sisa_saldo;
    }

    public String getNama_pedagang() {
        return nama_pedagang;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getJenis() {
        return jenis;
    }

    public String getPedagang_id() {
        return pedagang_id;
    }

    public String getPetugas_id() {
        return petugas_id;
    }

    public int getPotongan() {
        return potongan;
    }

    public int getSisa_saldo() {
        return sisa_saldo;
    }
}
