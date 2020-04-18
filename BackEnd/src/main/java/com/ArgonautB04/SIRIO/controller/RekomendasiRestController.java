package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.rest.Settings;
import com.ArgonautB04.SIRIO.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/Rekomendasi")
public class RekomendasiRestController {

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private TugasPemeriksaanRestService tugasPemeriksaanRestService;

    @Autowired
    private HasilPemeriksaanRestService hasilPemeriksaanRestService;

    @Autowired
    private KomponenPemeriksaanRestService komponenPemeriksaanRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

    /**
     * Mengambil seluruh rekomendasi yang terhubung dengan user yang sedang login
     *
     * @return daftar rekomendasi yang terhubung dengan pembuat tersebut
     */
    @GetMapping("/getAll")
    private BaseResponse<List<RekomendasiDTO>> getAllRekomendasiUntukLoggedInUser(Principal principal) {
        BaseResponse<List<RekomendasiDTO>> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesTabelRekomendasi()) {
                try {
                    List<Rekomendasi> result;
                    if (pengelola.getRole().getNamaRole().equals("Branch Manager")) {
                        KantorCabang kantorCabang = kantorCabangRestService.getByPemilik(pengelola);
                        List<TugasPemeriksaan> daftarTugasPemeriksaan = tugasPemeriksaanRestService
                                .getByKantorCabang(kantorCabang);
                        List<HasilPemeriksaan> daftarHasilPemeriksaan = hasilPemeriksaanRestService
                                .getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
                        List<KomponenPemeriksaan> daftarKomponenPemeriksaan = komponenPemeriksaanRestService
                                .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
                        result = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);
                    } else if (pengelola.getRole().getNamaRole().equals("Super QA Officer Operational Risk")
                            | pengelola.getRole().getNamaRole().equals("Manajer Operational Risk")) {
                        result = rekomendasiRestService.getAll();
                    } else {
                        result = rekomendasiRestService.getByPembuat(pengelola);
                    }

                    List<RekomendasiDTO> resultDTO = new ArrayList<>();
                    for (Rekomendasi rekomendasi : result) {
                        RekomendasiDTO rekomendasiDTO = new RekomendasiDTO();
                        rekomendasiDTO.setId(rekomendasi.getIdRekomendasi());
                        rekomendasiDTO.setKeterangan(rekomendasi.getKeterangan());

                        Date tenggatWaktu = rekomendasi.getTenggatWaktu();
                        if (tenggatWaktu != null) {
                            String tenggatWaktuString = tenggatWaktu.toString();
                            String tenggatWaktuFinal = tenggatWaktuString.substring(0, 10);
                            rekomendasiDTO.setTenggatWaktu(tenggatWaktuFinal);

                            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                            Date dateobj = new Date();
                            String mulai = df.format(dateobj);
                            String tanggalMulai = mulai.substring(0, 2);
                            String tanggalSelesai = tenggatWaktuString.substring(8, 10);
                            int tanggalMulaiFinal = Integer.parseInt(tanggalMulai);
                            int tanggalSelesaiFinal = Integer.parseInt(tanggalSelesai);
                            int durasi = (tanggalSelesaiFinal - tanggalMulaiFinal) + 1;
                            if (durasi < 0) {
                                durasi = 0;
                            }
                            String durasiString = Integer.toString(durasi);
                            String durasiFinal = durasiString + " Hari";
                            rekomendasiDTO.setDurasi(durasiFinal);
                        }

                        rekomendasiDTO.setStatus(rekomendasi.getStatusRekomendasi().getNamaStatus());

                        List<BuktiPelaksanaan> buktiList = buktiPelaksanaanRestService.getByDaftarRekomendasi(result);
                        for (BuktiPelaksanaan buktiPelaksanaan : buktiList) {
                            if (buktiPelaksanaan.getRekomendasi().equals(rekomendasi)) {
                                rekomendasiDTO.setStatusBukti(buktiPelaksanaan.getStatusBuktiPelaksanaan().getNamaStatus());
                            }
                        }
                        rekomendasiDTO.setNamaKantorCabang(rekomendasi.getKomponenPemeriksaan().getHasilPemeriksaan()
                                .getTugasPemeriksaan().getKantorCabang().getNamaKantor());
                        resultDTO.add(rekomendasiDTO);
                    }
                    response.setStatus(200);
                    response.setMessage("success");
                    response.setResult(resultDTO);
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Akun anda tidak terdaftar atau tidak ditemukan!");
                }
                return response;
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }

    /**
     * Mengambil seluruh rekomendasi yang terhubung dengan suatu kantor cabang
     *
     * @param idKantor identifier kantor cabang
     * @return daftar rekomendasi yang terhubung dengan kantor cabang
     */
    @GetMapping("/getAllByKantorCabang/{idKantor}")
    private BaseResponse<List<Rekomendasi>> getAllRekomendasiUntukKantorCabang(@PathVariable("idKantor") int idKantor) {
        BaseResponse<List<Rekomendasi>> response = new BaseResponse<>();
        try {
            KantorCabang kantorCabang = kantorCabangRestService.getById(idKantor);
            List<TugasPemeriksaan> daftarTugasPemeriksaan = tugasPemeriksaanRestService.getByKantorCabang(kantorCabang);
            List<HasilPemeriksaan> daftarHasilPemeriksaan = hasilPemeriksaanRestService
                    .getByDaftarTugasPemeriksaan(daftarTugasPemeriksaan);
            List<KomponenPemeriksaan> daftarKomponenPemeriksaan = komponenPemeriksaanRestService
                    .getByDaftarHasilPemeriksaan(daftarHasilPemeriksaan);
            List<Rekomendasi> result = rekomendasiRestService.getByDaftarKomponenPemeriksaan(daftarKomponenPemeriksaan);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Kantor Cabang dengan ID " + idKantor + " tidak ditemukan!");
        }
        return response;
    }

    /**
     * Mengubah tenggat waktu untuk rekomendasi spesifik.
     *
     * @return objek rekomendasi yang telah diubah
     */
    @PostMapping("/tenggatWaktu")
    private BaseResponse<Rekomendasi> ubahTenggatWaktu(
            @RequestBody RekomendasiDTO rekomendasiDTO,
            Principal principal
    ) {
        Optional<Employee> employeeOptional = employeeRestService.getByUsername(principal.getName());
        Employee employee;

        // Validasi : user berhasil login dengan valid
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak terdaftar dalam Sirio"
            );
        } else {
            employee = employeeOptional.get();
        }
        // Validasi selesai

        // Validasi : role user memperbolehkan pengaturan tenggat waktu
        if (!employee.getRole().getAccessPermissions().getAturTenggatWaktu()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak memiliki akses ke pengaturan tenggat waktu"
            );
        }
        // Validasi selesai

        Integer idRekomendasi = rekomendasiDTO.getId();
        Optional<Rekomendasi> rekomendasiOptional = rekomendasiRestService.getOptionalById(idRekomendasi);
        Rekomendasi rekomendasi;

        // Validasi : rekomendasi harus ada dalam basis data
        if (rekomendasiOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        } else {
            rekomendasi = rekomendasiOptional.get();
        }
        // Validasi selesai

        Date tenggatWaktuBaru = Settings.convertToDateViaInstant(rekomendasiDTO.getTenggatWaktuDate());

        // Validasi : Tanggal tenggat waktu harus melebihi tanggal hari ini
        if (tenggatWaktuBaru.compareTo(new Date()) < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pastikan tenggat waktu yang anda masukan belum terlewat"
            );
        }
        // Validasi selesai

        // Validasi : Status rekomendasi harus memungkinkan pengubahan tenggat waktu
        if (!rekomendasi.getStatusRekomendasi().isDapatSetTenggatWaktu()) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED,
                    "Tenggat waktu rekomendasi belum dapat diatur!"
            );
        }
        // Validasi selesai

        Rekomendasi result = rekomendasiRestService.ubahTenggatWaktu(rekomendasi, tenggatWaktuBaru);

        return new BaseResponse<>(200, "success", result);
    }
}