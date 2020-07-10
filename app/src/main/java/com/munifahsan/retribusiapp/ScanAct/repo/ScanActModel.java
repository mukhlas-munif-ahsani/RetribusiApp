package com.munifahsan.retribusiapp.ScanAct.repo;

public class ScanActModel {
    private int saldo;
    private int pajak_1;
    private String nama;

    public ScanActModel() {
    }

    public ScanActModel(int saldo, int pajak_1, String nama) {
        this.saldo = saldo;
        this.pajak_1 = pajak_1;
        this.nama = nama;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getPajak_1() {
        return pajak_1;
    }

    public String getNama() {
        return nama;
    }
}
