package com.munifahsan.retribusiapp.HomePetugas.repo;

import com.google.firebase.firestore.DocumentId;

public class HomePtgModel {
    @DocumentId
    private String id;

    private int saldo;
    private String created_at;
    private String updated_at;

    private String alamat;
    private String email;
    private String level;
    private String nama;
    private String nohp_number;
    private String token_id;

    public HomePtgModel() {}

    public HomePtgModel(String id, int saldo, String created_at, String updated_at, String alamat, String email, String level, String nama, String nohp_number, String token_id) {
        this.id = id;
        this.saldo = saldo;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.alamat = alamat;
        this.email = email;
        this.level = level;
        this.nama = nama;
        this.nohp_number = nohp_number;
        this.token_id = token_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp_number() {
        return nohp_number;
    }

    public void setNohp_number(String nohp_number) {
        this.nohp_number = nohp_number;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
