package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.KomponenPemeriksaan;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.repository.RekomendasiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    public List<Rekomendasi> getAll(
            final LocalDate tanggalAwal, final LocalDate tanggalAkhir) {
        List<Rekomendasi> getByTanggal = new ArrayList<>();
        for (Rekomendasi r: getAll()) {
            try {
                if ((r.getKomponenPemeriksaan().
                        getHasilPemeriksaan().getTugasPemeriksaan()
                .getTanggalMulai().isAfter(tanggalAwal)
                        && r.getKomponenPemeriksaan().getHasilPemeriksaan().
                                getTugasPemeriksaan()
                                .getTanggalMulai().isBefore(tanggalAkhir))
                        || r.getKomponenPemeriksaan().getHasilPemeriksaan().
                        getTugasPemeriksaan()
                        .getTanggalMulai().isEqual(tanggalAwal)
                        || r.getKomponenPemeriksaan().getHasilPemeriksaan().
                        getTugasPemeriksaan()
                        .getTanggalMulai().isEqual(tanggalAkhir)
//                || (r.getKomponenPemeriksaan().getHasilPemeriksaan().
//                getTugasPemeriksaan()
//                        .getTanggalSelesai().isAfter(tanggalAwal) &&
//                        r.getKomponenPemeriksaan().getHasilPemeriksaan().
//                        getTugasPemeriksaan()
//                                .getTanggalSelesai().isBefore(tanggalAkhir))
                ) {
                    getByTanggal.add(r);
                }
            } catch (NullPointerException | NoSuchElementException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rekomendasi tidak ada!"
                );
            }
        }
        return getByTanggal;

    }

    @Override
    public List<Rekomendasi> getByPembuat(final int idQa) {
        List<Rekomendasi> list = new ArrayList<>();
        for (Rekomendasi r: getAll()) {
            try {
                if (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                        getTugasPemeriksaan().getPelaksana().
                        getIdEmployee() == idQa) {
                    list.add(r);
                }
            } catch (NullPointerException | NoSuchElementException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rekomendasi tidak memiliki pelaksana!"
                );
            }
        }
        return list;
    }

    @Override
    public List<Rekomendasi> getByPembuat(
            final int idQa, final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<Rekomendasi> list = new ArrayList<>();
        for (Rekomendasi r: getAll(tanggalAwal, tanggalAkhir)) {
            try {
                if (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                        getTugasPemeriksaan().getPelaksana().
                        getIdEmployee() == idQa) {
                    list.add(r);
                }
            } catch (NullPointerException | NoSuchElementException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rekomendasi tidak memiliki pelaksana!"
                );
            }
        }
        return list;
    }

    @Override
    public List<Rekomendasi> getRekomendasiDiimplementasi(
            final LocalDate tanggalAwal, final LocalDate tanggalAkhir) {
        List<Rekomendasi> impl = new ArrayList<>();
        if (tanggalAwal != null) {
            for (Rekomendasi r : getAll(tanggalAwal, tanggalAkhir)) {
                if (r.getBuktiPelaksanaan() != null
                        && r.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().
                        getIdStatusBukti() == 2) {
                    impl.add(r);
                }
            }
        } else {
            for (Rekomendasi r : getAll()) {
                if (r.getBuktiPelaksanaan() != null
                        && r.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().
                        getIdStatusBukti()
                        == 2) {
                    impl.add(r);
                }
            }
        }
        return impl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiDiimplementasiByPembuat(
            final int idQa, final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getRekomendasiDiimplementasi(
                tanggalAwal, tanggalAkhir)) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                    getTugasPemeriksaan().getPelaksana().getIdEmployee()
                    == idQa) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiOverdue(
            final LocalDate tanggalAwal, final LocalDate tanggalAkhir) {
        List<Rekomendasi> overdue = new ArrayList<>();
        if (tanggalAwal != null) {
            for (Rekomendasi r : getAll(tanggalAwal, tanggalAkhir)) {
                if (r.getTenggatWaktu() != null
                        && r.getStatusRekomendasi().getIdStatusRekomendasi()
                        == 6
                        && r.getTenggatWaktu().isBefore(LocalDate.now())) {
                    overdue.add(r);
                }
                else if (r.getTenggatWaktu() != null
                        && r.getBuktiPelaksanaan().
                        getTanggalPersetujuan() != null
                        && r.getTenggatWaktu().isBefore(
                        r.getBuktiPelaksanaan().
                        getTanggalPersetujuan())) {
                    overdue.add(r);
                }
            }
        } else {
            for (Rekomendasi r : getAll()) {
                if (r.getTenggatWaktu() != null && r.getStatusRekomendasi().
                        getIdStatusRekomendasi() == 6
                        && r.getTenggatWaktu().isBefore(LocalDate.now())) {
                    overdue.add(r);
                }
            }
        }
        return overdue;
    }

    @Override
    public List<Rekomendasi> getRekomendasiOverdueByPembuat(
            final int idQa, final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getRekomendasiOverdue(tanggalAwal, tanggalAkhir)) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                    getTugasPemeriksaan().getPelaksana().
                    getIdEmployee() == idQa) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<Integer> getRekomendasiByMonth(
            final List<Rekomendasi> rekomendasiList,
            final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<Integer> intOverdue = new ArrayList<>();
        List<String> months = getListMonth(tanggalAwal, tanggalAkhir);
        if (tanggalAwal != null) {
            int count = 0;
            List<Integer> bulan = Arrays.asList(new Integer[months.size()]);
            String bulan1;
            String bulan2 = null;
            String tahun1;
            String tahun2 = null;
            for (int i = 0; i < rekomendasiList.size(); i++) {
                bulan1 = String.valueOf(rekomendasiList.get(i).
                        getKomponenPemeriksaan().getHasilPemeriksaan()
                        .getTugasPemeriksaan().getTanggalMulai().getMonth());
                tahun1 = String.valueOf(rekomendasiList.get(i).
                        getKomponenPemeriksaan().getHasilPemeriksaan()
                        .getTugasPemeriksaan().getTanggalMulai().getYear());
                for (int j = 0; j < months.size(); j++) {
                    List<String> temp = Arrays.asList(months.get(j).
                            split(" "));
                    bulan2 = temp.get(0);
                    tahun2 = temp.get(1);
                    if (bulan1.equals(bulan2) && tahun1.equals(tahun2)) {
                        bulan.set(j, ++count);
                    } else {
                        bulan.set(j, 0);
                    }
                }
            }
            intOverdue = bulan;
        } else {
            int count6 = 0;
            int count5 = 0;
            int count4 = 0;
            int count3 = 0;
            int count2 = 0;
            int count1 = 0;
            for (int i = 0; i < rekomendasiList.size(); i++) {
                try {
                    if (rekomendasiList.get(i).getKomponenPemeriksaan().
                            getHasilPemeriksaan().getTugasPemeriksaan().
                            getTanggalMulai().getMonth().equals(
                                    LocalDate.now().getMonth())) {
                        count6++;
                    } else if (rekomendasiList.get(i).getKomponenPemeriksaan().
                            getHasilPemeriksaan().getTugasPemeriksaan().
                            getTanggalMulai().getMonth().equals(
                            LocalDate.now().minusMonths(1).getMonth())) {
                        count5++;
                    } else if (rekomendasiList.get(i).getKomponenPemeriksaan().
                            getHasilPemeriksaan().getTugasPemeriksaan().
                            getTanggalMulai().getMonth().equals(
                            LocalDate.now().minusMonths(2).getMonth())) {
                        count4++;
                    } else if (rekomendasiList.get(i).getKomponenPemeriksaan().
                            getHasilPemeriksaan().getTugasPemeriksaan().
                            getTanggalMulai().getMonth().equals(
                            LocalDate.now().minusMonths(3).getMonth())) {
                        count3++;
                    } else if (rekomendasiList.get(i).getKomponenPemeriksaan().
                            getHasilPemeriksaan().getTugasPemeriksaan().
                            getTanggalMulai().getMonth().equals(
                            LocalDate.now().minusMonths(4).getMonth())) {
                        count2++;
                    } else if (rekomendasiList.get(i).getKomponenPemeriksaan().
                            getHasilPemeriksaan().getTugasPemeriksaan().
                            getTanggalMulai().getMonth().equals(
                            LocalDate.now().minusMonths(5).getMonth())) {
                        count1++;
                    }
                } catch (NullPointerException | NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Rekomendasi tidak memiliki komponen pemeriksaan!"
                    );
                }
            }
            intOverdue.add(count1);
            intOverdue.add(count2);
            intOverdue.add(count3);
            intOverdue.add(count4);
            intOverdue.add(count5);
            intOverdue.add(count6);
        }
        return intOverdue;
    }

    @Override
    public List<Rekomendasi> getRekomendasiBelumDiimplementasi(
            final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<Rekomendasi> belumImpl = new ArrayList<>();
        if (tanggalAwal != null) {
            for (Rekomendasi r : getAll(tanggalAwal, tanggalAkhir)) {
                if (r.getStatusRekomendasi().getIdStatusRekomendasi()
                        == 6 && r.getBuktiPelaksanaan() == null
                        && r.getTenggatWaktu().isAfter(LocalDate.now())) {
                    belumImpl.add(r);
                } else if (r.getStatusRekomendasi().getIdStatusRekomendasi()
                        == 6 && r.getBuktiPelaksanaan() != null
                        && r.getBuktiPelaksanaan().getStatusBuktiPelaksanaan().
                        getIdStatusBukti() != 2 && r.getTenggatWaktu().
                        isAfter(LocalDate.now())) {
                    belumImpl.add(r);
                }
            }
        } else {
            for (Rekomendasi r : getAll()) {
                if (r.getStatusRekomendasi().getIdStatusRekomendasi() == 6
                        && r.getBuktiPelaksanaan() == null
                        && r.getTenggatWaktu().isAfter(LocalDate.now())) {
                    belumImpl.add(r);
                } else if (r.getStatusRekomendasi().
                        getIdStatusRekomendasi() == 6
                        && r.getBuktiPelaksanaan() != null
                        && r.getBuktiPelaksanaan().
                        getStatusBuktiPelaksanaan().
                        getIdStatusBukti() != 2 && r.getTenggatWaktu().
                        isAfter(LocalDate.now())) {
                    belumImpl.add(r);
                }
            }
        }
        return belumImpl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiBelumDiimplementasiByPembuat(
            final int idQa, final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getRekomendasiBelumDiimplementasi(
                tanggalAwal, tanggalAkhir)) {
            if (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                getTugasPemeriksaan().getPelaksana().getIdEmployee() == idQa) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<String> getListMonth(
            final LocalDate tanggalAwal,
            final LocalDate tanggalAkhir) {
        List<String> months = new ArrayList<>();
        if (tanggalAwal != null) {
            int monthsBetween = (int) ChronoUnit.MONTHS.between(
                    tanggalAwal.withDayOfMonth(1),
                    tanggalAkhir.withDayOfMonth(1));
            for (int i = monthsBetween; i >= 0; i--) {
                months.add(tanggalAkhir.minusMonths(i).getMonth()
                        + " " + tanggalAkhir.minusMonths(i).getYear());
            }
        } else {
            for (int i = 5; i >= 0; i--) {
                months.add(LocalDate.now().minusMonths(i).getMonth()
                        + " " + LocalDate.now().minusMonths(i).getYear());
            }
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
