package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.*;
import com.ArgonautB04.SIRIO.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class TemuanRisikoRestServiceImpl implements TemuanRisikoRestService {

    @Autowired
    private TemuanRisikoDB temuanRisikoDB;

    @Autowired
    private KomponenPemeriksaanDB komponenPemeriksaanDB;

    @Autowired
    private HasilPemeriksaanDB hasilPemeriksaanDB;

    @Autowired
    private TugasPemeriksaanDB tugasPemeriksaanDB;

    @Autowired
    private StatusHasilPemeriksaanDB statusHasilPemeriksaanDB;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Override
    public TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko) {
        return temuanRisikoDB.save(temuanRisiko);
    }

    @Override
    public TemuanRisiko getById(int idTemuanRisiko) {
        Optional<TemuanRisiko> temuanRisiko = temuanRisikoDB.findById(idTemuanRisiko);
        if (temuanRisiko.isPresent()) return temuanRisiko.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<TemuanRisiko> getAll() {
        return temuanRisikoDB.findAll();
    }

    @Override
    public List<TemuanRisiko> getByPembuat(int idQa) {
        Employee emp = employeeRestService.getById(idQa);
        return temuanRisikoDB.findAllByPembuat(emp);
    }

    @Override
    public List<Integer> getAllByMonth() {
        List<TemuanRisiko> impl = getAll();
        List<Integer> intImpl = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i=0;i<impl.size();i++) {
            if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().getMonth())) {
                count6++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                count5++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                count4++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                count3++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                count2++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
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
    public List<Integer> getByPembuatByMonth(int idQa) {
        List<TemuanRisiko> impl = getByPembuat(idQa);
        List<Integer> intImpl = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i=0;i<impl.size();i++) {
            if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().getMonth())) {
                count6++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                count5++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                count4++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                count3++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                count2++;
            } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                    .getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
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
    public List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return temuanRisikoDB.findAllByKomponenPemeriksaan(komponenPemeriksaan);
    }

    @Override
    public List<TemuanRisiko> getHistoriTemuanRisikoKantorCabang(TugasPemeriksaan tugasPemeriksaan, Risiko risiko) {
        StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanDB.findById(5).get();
        List<TugasPemeriksaan> tugasPemeriksaans = tugasPemeriksaanDB.findAllByKantorCabang(
                tugasPemeriksaan.getKantorCabang());
        List<HasilPemeriksaan> hasilPemeriksaans = hasilPemeriksaanDB.findAllByTugasPemeriksaanInAndStatusHasilPemeriksaan(
                tugasPemeriksaans, statusHasilPemeriksaan);
        List<KomponenPemeriksaan> komponenPemeriksaans = komponenPemeriksaanDB.findAllByHasilPemeriksaanInAndRisiko(hasilPemeriksaans, risiko);

        return temuanRisikoDB.findAllByKomponenPemeriksaanIn(komponenPemeriksaans);
    }

    @Override
    public TemuanRisiko ubahTemuanRisiko(int idTemuanRisiko, TemuanRisiko temuanRisiko) {
        TemuanRisiko target = getById(idTemuanRisiko);
        target.setPembuat(temuanRisiko.getPembuat());
        target.setKomponenPemeriksaan(temuanRisiko.getKomponenPemeriksaan());
        target.setKeterangan(temuanRisiko.getKeterangan());
        return temuanRisikoDB.save(target);
    }

    @Override
    public void hapusTemuanRisiko(int idTemuanRisiko) {
        temuanRisikoDB.deleteById(idTemuanRisiko);
    }
}
