package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.repository.RekomendasiDB;
import com.ArgonautB04.SIRIO.repository.StatusRekomendasiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RekomendasiRestServiceImpl implements RekomendasiRestService {

    @Autowired
    private RekomendasiDB rekomendasiDB;

    @Autowired
    private StatusRekomendasiRestService statusRekomendasiRestService;

    @Override
    public Rekomendasi buatRekomendasi(Rekomendasi rekomendasi) {
        return rekomendasiDB.save(rekomendasi);
    }

    @Override
    public Rekomendasi getById(int idRekomendasi) {
        Optional<Rekomendasi> rekomendasi = getOptionalById(idRekomendasi);
        if (rekomendasi.isPresent()) return rekomendasi.get();
        else throw new NoSuchElementException();
    }

    @Override
    public Optional<Rekomendasi> getOptionalById(int idRekomendasi) {
        return rekomendasiDB.findById(idRekomendasi);
    }

    @Override
    public List<Rekomendasi> getAll() {
        return rekomendasiDB.findAll();
    }

    @Override
    public List<Rekomendasi> getByPembuat(int idQa) {
        List<Rekomendasi> list = new ArrayList<>();
        for (Rekomendasi r: getAll()) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getPelaksana().getIdEmployee() == idQa) {
                list.add(r);
            }
        }
        return list;
    }

//    @Override
//    public List<Integer> getAllByMonth() {
//        List<Rekomendasi> impl = getAll();
//        List<Integer> intImpl = new ArrayList<>();
//        int count6 = 0;
//        int count5 = 0;
//        int count4 = 0;
//        int count3 = 0;
//        int count2 = 0;
//        int count1 = 0;
//        for (int i=0;i<impl.size();i++) {
//            if (impl.get(i).getTenggatWaktu() != null) {
//                if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().getMonth())) {
//                    count6++;
//                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
//                    count5++;
//                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
//                    count4++;
//                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
//                    count3++;
//                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
//                    count2++;
//                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
//                    count1++;
//                }
//            }
//        }
//        intImpl.add(count1);
//        intImpl.add(count2);
//        intImpl.add(count3);
//        intImpl.add(count4);
//        intImpl.add(count5);
//        intImpl.add(count6);
//        return intImpl;
//    }

    @Override
    public List<Rekomendasi> getRekomendasiDiimplementasi() {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getBuktiPelaksanaan() != null && r.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getIdStatusBukti()
                    == 2) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiDiimplementasiByPembuat(int idQa) {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getRekomendasiDiimplementasi()) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getPelaksana().getIdEmployee() == idQa) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiOverdue() {
        List<Rekomendasi> overdue = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getTenggatWaktu() != null && r.getStatusRekomendasi().getIdStatusRekomendasi() == 6
                    && r.getTenggatWaktu().isBefore(LocalDate.now())){
                overdue.add(r);
                System.out.println("overdue" + r.getIdRekomendasi());
            }
        }
        return overdue;
    }

    @Override
    public List<Rekomendasi> getRekomendasiOverdueByPembuat(int idQa) {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getRekomendasiOverdue()) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getPelaksana().getIdEmployee() == idQa) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<Integer> getRekomendasiByMonth(List<Rekomendasi> rekomendasiList) {
        List<Integer> intOverdue = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i=0;i<rekomendasiList.size();i++) {
            if (rekomendasiList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai().getMonth().equals(LocalDate.now().getMonth())) {
                count6++;
                //tanggal mulai dari tugas
            } else if (rekomendasiList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                count5++;
            } else if (rekomendasiList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                count4++;
            } else if (rekomendasiList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                count3++;
            } else if (rekomendasiList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                count2++;
            } else if (rekomendasiList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
                count1++;
            }
        }
        intOverdue.add(count1);
        intOverdue.add(count2);
        intOverdue.add(count3);
        intOverdue.add(count4);
        intOverdue.add(count5);
        intOverdue.add(count6);
        return intOverdue;
    }

    @Override
    public List<Rekomendasi> getRekomendasiBelumDiimplementasi() {
        List<Rekomendasi> belumImpl = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getStatusRekomendasi().getIdStatusRekomendasi() == 6 && r.getBuktiPelaksanaan() == null
                    && r.getTenggatWaktu().isAfter(LocalDate.now())) {
                belumImpl.add(r);
            } else if (r.getStatusRekomendasi().getIdStatusRekomendasi() == 6 && r.getBuktiPelaksanaan() != null
                    && r.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().getIdStatusBukti() != 2  && r.getTenggatWaktu().isAfter(LocalDate.now())) {
                belumImpl.add(r);
            }
        }
        return belumImpl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiBelumDiimplementasiByPembuat(int idQa) {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getRekomendasiBelumDiimplementasi()) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getPelaksana().getIdEmployee() == idQa) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<String> getListMonth() {
        List<String> months = new ArrayList<>(6);
        for (int i=5;i>=0;i--) {
            months.add(LocalDate.now().minusMonths(i).getMonth() + "\n" + LocalDate.now().minusMonths(i).getYear());
        }
        return months;
    }

    @Override
    public void ubahRekomendasi(int idRekomendasi, Rekomendasi rekomendasi) {
        Rekomendasi target = getById(idRekomendasi);
        target.setKeterangan(rekomendasi.getKeterangan());
        target.setTenggatWaktu(rekomendasi.getTenggatWaktu());
        target.setStatusRekomendasi(rekomendasi.getStatusRekomendasi());
        target.setPembuat(rekomendasi.getPembuat());
        target.setKomponenPemeriksaan(rekomendasi.getKomponenPemeriksaan());
        target.setBuktiPelaksanaan(rekomendasi.getBuktiPelaksanaan());
        rekomendasiDB.save(target);
    }

    @Override
    public void hapusRekomendasi(int idRekomendasi) {
        rekomendasiDB.deleteById(idRekomendasi);
    }

    @Override
    public List<Rekomendasi> getByPembuat(Employee pembuat) {
        return rekomendasiDB.findAllByPembuat(pembuat);
    }

    @Override
    public Rekomendasi validateExistInById(int idRekomendasi) {
        Optional<Rekomendasi> rekomendasiOptional = getOptionalById(idRekomendasi);
        if (rekomendasiOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        } else {
            return rekomendasiOptional.get();
        }
    }

    @Override
    public void validateDateInputMoreThanToday(LocalDate localDate) {
        if (localDate.compareTo(LocalDate.now()) < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Pastikan tenggat waktu yang anda masukan belum terlewat"
            );
        }
    }

    @Override
    public void validateDeadlineCanBeSet(Rekomendasi rekomendasi) {
        if (!rekomendasi.getStatusRekomendasi().isDapatSetTenggatWaktu()) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED,
                    "Tenggat waktu rekomendasi belum dapat diatur!"
            );
        }
    }

    @Override
    public Rekomendasi buatAtauSimpanPerubahanRekomendasi(Rekomendasi rekomendasi, boolean tenggatWaktuTerubah) {
        if (tenggatWaktuTerubah) {
            rekomendasi.setStatusRekomendasi(
                    statusRekomendasiRestService.getByNamaStatus("Menunggu Pelaksanaan")
            );
        }
        return rekomendasiDB.save(rekomendasi);
    }

    @Override
    public List<Rekomendasi> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return rekomendasiDB.findAllByKomponenPemeriksaan(komponenPemeriksaan);
    }

    @Override
    public List<Rekomendasi> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList) {
        return rekomendasiDB.findAllByKomponenPemeriksaanIn(komponenPemeriksaanList);
    }
}
