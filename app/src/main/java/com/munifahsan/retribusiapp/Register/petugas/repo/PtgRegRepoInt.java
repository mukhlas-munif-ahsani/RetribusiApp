package com.munifahsan.retribusiapp.Register.petugas.repo;

public interface PtgRegRepoInt {
    void checkToken(String token, String nama, String email, String alamat, String lokasi, String nohp, String level, String pass);
}
