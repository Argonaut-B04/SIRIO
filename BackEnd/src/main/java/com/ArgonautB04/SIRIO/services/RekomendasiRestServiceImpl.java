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
    public List<Integer> getAllByMonth() {
        List<Rekomendasi> impl = getAll();
        List<Integer> intImpl = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i=0;i<impl.size();i++) {
            if (impl.get(i).getTenggatWaktu() != null) {
                if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().getMonth())) {
                    count6++;
                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                    count5++;
                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                    count4++;
                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                    count3++;
                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                    count2++;
                } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
                    count1++;
                }
            }
        }
        intImpl.add(count1);
        intImpl.add(count2);
        intImpl.add(count3);
        intImpl.add(count4);
        intImpl.add(count5);
        intImpl.add(count6);
        return intImpl;
    }

    @Override
    public List<Integer> getRekomendasiDiimplementasiByMonth() {
        List<Rekomendasi> impl = getRekomendasiDiimplementasi();
        List<Integer> intImpl = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i=0;i<impl.size();i++) {
            if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().getMonth())) {
                count6++;
            } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                count5++;
            } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                count4++;
            } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                count3++;
            } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                count2++;
            } else if (impl.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
                count1++;
            }
        }
        intImpl.add(count1);
        intImpl.add(count2);
        intImpl.add(count3);
        intImpl.add(count4);
        intImpl.add(count5);
        intImpl.add(count6);
        return intImpl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiDiimplementasi() {
        List<Rekomendasi> impl = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getStatusRekomendasi().getIdStatusRekomendasi() == 7) {
                impl.add(r);
            }
        }
        return impl;
    }

    @Override
    public List<Rekomendasi> getRekomendasiOverdue() {
        List<Rekomendasi> overdue = new ArrayList<>();
        for (Rekomendasi r : getAll()) {
            if (r.getTenggatWaktu() != null && LocalDate.now().isAfter(r.getTenggatWaktu()) && r.getStatusRekomendasi() != null
                    && r.getStatusRekomendasi().getIdStatusRekomendasi() == 6){
                overdue.add(r);
            }
        }
        return overdue;
    }

    @Override
    public List<Integer> getRekomendasiOverdueByMonth() {
        List<Rekomendasi> overdue = getRekomendasiOverdue();
        List<Integer> intOverdue = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i=0;i<overdue.size();i++) {
            if (overdue.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().getMonth())) {
                count6++;
            } else if (overdue.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                count5++;
            } else if (overdue.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                count4++;
            } else if (overdue.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                count3++;
            } else if (overdue.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                count2++;
            } else if (overdue.get(i).getTenggatWaktu().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
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
    public List<Integer> getRekomendasiBelumDiimplementasiByMonth() {
        List<Integer> impl = getRekomendasiDiimplementasiByMonth();
        List<Integer> overdue = getRekomendasiOverdueByMonth();
        List<Integer> rekomendasi = getAllByMonth();
        List<Integer> belumImpl = new ArrayList<>();
        for (int i=0; i<rekomendasi.size();i++) {
            belumImpl.add(rekomendasi.get(i) - impl.get(i) - overdue.get(i));
        }
        return belumImpl;
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
