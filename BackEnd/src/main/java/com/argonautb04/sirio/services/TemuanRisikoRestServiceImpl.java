package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.HasilPemeriksaan;
import com.argonautb04.sirio.model.KomponenPemeriksaan;
import com.argonautb04.sirio.model.Risiko;
import com.argonautb04.sirio.model.StatusHasilPemeriksaan;
import com.argonautb04.sirio.model.TemuanRisiko;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import com.argonautb04.sirio.repository.HasilPemeriksaanDB;
import com.argonautb04.sirio.repository.KomponenPemeriksaanDB;
import com.argonautb04.sirio.repository.StatusHasilPemeriksaanDB;
import com.argonautb04.sirio.repository.TemuanRisikoDB;
import com.argonautb04.sirio.repository.TugasPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Override
    public TemuanRisiko buatTemuanRisiko(TemuanRisiko temuanRisiko) {
        return temuanRisikoDB.save(temuanRisiko);
    }

    @Override
    public TemuanRisiko getById(int idTemuanRisiko) {
        Optional<TemuanRisiko> temuanRisiko = temuanRisikoDB.findById(idTemuanRisiko);
        if (temuanRisiko.isPresent())
            return temuanRisiko.get();
        else
            throw new NoSuchElementException();
    }

    @Override
    public List<TemuanRisiko> getAll() {
        return temuanRisikoDB.findAll();
    }

    @Override
    public List<Integer> getTemuanPerMonth(List<TemuanRisiko> temuanRisikoList) {
        List<Integer> perMonth = new ArrayList<>();
        int count6 = 0;
        int count5 = 0;
        int count4 = 0;
        int count3 = 0;
        int count2 = 0;
        int count1 = 0;
        for (int i = 0; i < temuanRisikoList.size(); i++) {
            if (temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                    .getTanggalMulai().getMonth().equals(LocalDate.now().getMonth())) {
                count6++;
            } else if (temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                    .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                count5++;
            } else if (temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                    .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                count4++;
            } else if (temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                    .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                count3++;
            } else if (temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                    .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                count2++;
            } else if (temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                    .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
                count1++;
            }
        }
        perMonth.add(count1);
        perMonth.add(count2);
        perMonth.add(count3);
        perMonth.add(count4);
        perMonth.add(count5);
        perMonth.add(count6);
        return perMonth;
    }

    @Override
    public List<Integer> getByPembuatByMonth(final int idQa, final LocalDate tanggalAwal,
                                             final LocalDate tanggalAkhir) {
        List<TemuanRisiko> impl = new ArrayList<>();
        List<Integer> intImpl = new ArrayList<>();
        List<String> months = rekomendasiRestService.getListMonth(tanggalAwal, tanggalAkhir);
        if (tanggalAwal != null) {
            impl = getByPembuat(idQa, tanggalAwal, tanggalAkhir);
            List<Integer> bulan = Arrays.asList(new Integer[months.size()]);
            int count = 0;
            String bulan1;
            String bulan2 = null;
            String tahun1;
            String tahun2 = null;
            for (int i = 0; i < impl.size(); i++) {
                bulan1 = String.valueOf(impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth());
                tahun1 = String.valueOf(impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getYear());
                for (int j = 0; j < months.size(); j++) {
                    List<String> temp = Arrays.asList(months.get(j).split(" "));
                    bulan2 = temp.get(0);
                    tahun2 = temp.get(1);
                    if (bulan1.equals(bulan2) && tahun1.equals(tahun2)) {
                        bulan.set(j, ++count);
                    } else {
                        bulan.set(j, 0);
                    }
                }
            }
            intImpl = bulan;
        } else {
            impl = getByPembuat(idQa);
            int count6 = 0;
            int count5 = 0;
            int count4 = 0;
            int count3 = 0;
            int count2 = 0;
            int count1 = 0;
            for (int i = 0; i < impl.size(); i++) {
                if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .getMonth().equals(LocalDate.now().getMonth())) {
                    count6++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                    count5++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                    count4++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                    count3++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                    count2++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
                    count1++;
                }
            }
            intImpl.add(count1);
            intImpl.add(count2);
            intImpl.add(count3);
            intImpl.add(count4);
            intImpl.add(count5);
            intImpl.add(count6);
        }
        return intImpl;
    }

    @Override
    public List<TemuanRisiko> getAll(final LocalDate tanggalAwal, final LocalDate tanggalAkhir) {
        List<TemuanRisiko> getByTanggal = new ArrayList<>();
        for (TemuanRisiko r : getAll()) {
            try {
                if ((r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isAfter(tanggalAwal)
                        && r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isBefore(tanggalAkhir))
                        || r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isEqual(tanggalAwal)
                        || r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isEqual(tanggalAkhir)
                    // || (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                    // getTugasPemeriksaan()
                    // .getTanggalSelesai().isAfter(tanggalAwal) &&
                    // r.getKomponenPemeriksaan().getHasilPemeriksaan().
                    // getTugasPemeriksaan()
                    // .getTanggalSelesai().isBefore(tanggalAkhir))
                ) {
                    getByTanggal.add(r);
                }
            } catch (NullPointerException | NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Temuan risiko tidak ada!");
            }
        }
        return getByTanggal;

    }

    @Override
    public List<Integer> getAllByMonth(final LocalDate tanggalAwal, final LocalDate tanggalAkhir) {
        List<TemuanRisiko> impl = new ArrayList<>();
        List<Integer> intImpl = new ArrayList<>();
        List<String> months = rekomendasiRestService.getListMonth(tanggalAwal, tanggalAkhir);
        if (tanggalAwal != null) {
            impl = getAll(tanggalAwal, tanggalAkhir);
            List<Integer> bulan = Arrays.asList(new Integer[months.size()]);
            int count = 0;
            String bulan1;
            String bulan2 = null;
            String tahun1;
            String tahun2 = null;
            for (int i = 0; i < impl.size(); i++) {
                bulan1 = String.valueOf(impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth());
                tahun1 = String.valueOf(impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getYear());
                for (int j = 0; j < months.size(); j++) {
                    List<String> temp = Arrays.asList(months.get(j).split(" "));
                    bulan2 = temp.get(0);
                    tahun2 = temp.get(1);
                    if (bulan1.equals(bulan2) && tahun1.equals(tahun2)) {
                        bulan.set(j, ++count);
                    } else {
                        bulan.set(j, 0);
                    }
                }
            }
            intImpl = bulan;
        } else {
            impl = getAll();
            int count6 = 0;
            int count5 = 0;
            int count4 = 0;
            int count3 = 0;
            int count2 = 0;
            int count1 = 0;
            for (int i = 0; i < impl.size(); i++) {
                if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .getMonth().equals(LocalDate.now().getMonth())) {
                    count6++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(1).getMonth())) {
                    count5++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(2).getMonth())) {
                    count4++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(3).getMonth())) {
                    count3++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(4).getMonth())) {
                    count2++;
                } else if (impl.get(i).getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan()
                        .getTanggalMulai().getMonth().equals(LocalDate.now().minusMonths(5).getMonth())) {
                    count1++;
                }
            }
            intImpl.add(count1);
            intImpl.add(count2);
            intImpl.add(count3);
            intImpl.add(count4);
            intImpl.add(count5);
            intImpl.add(count6);
        }
        return intImpl;
    }

    @Override
    public List<Integer> getTemuanPerMonthFiltered(List<TemuanRisiko> temuanRisikoList, LocalDate tanggalAwal,
                                                   LocalDate tanggalAkhir) {
        List<Integer> perMonth = new ArrayList<>();
        List<String> months = rekomendasiRestService.getListMonth(tanggalAwal, tanggalAkhir);
        if (tanggalAwal != null) {
            List<Integer> bulan = Arrays.asList(new Integer[months.size()]);
            int count = 0;
            String bulan1;
            String bulan2 = null;
            String tahun1;
            String tahun2 = null;
            for (int i = 0; i < temuanRisikoList.size(); i++) {
                bulan1 = String.valueOf(temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan()
                        .getTugasPemeriksaan().getTanggalMulai().getMonth());
                tahun1 = String.valueOf(temuanRisikoList.get(i).getKomponenPemeriksaan().getHasilPemeriksaan()
                        .getTugasPemeriksaan().getTanggalMulai().getYear());
                for (int j = 0; j < months.size(); j++) {
                    List<String> temp = Arrays.asList(months.get(j).split(" "));
                    bulan2 = temp.get(0);
                    tahun2 = temp.get(1);
                    if (bulan1.equals(bulan2) && tahun1.equals(tahun2)) {
                        bulan.set(j, ++count);
                    } else {
                        bulan.set(j, 0);
                    }
                }
            }
            perMonth = bulan;
        }
        return perMonth;
    }

    @Override
    public List<TemuanRisiko> getByPembuat(final int idQa, final LocalDate tanggalAwal, final LocalDate tanggalAkhir) {
        List<TemuanRisiko> getByTanggal = new ArrayList<>();
        for (TemuanRisiko r : getByPembuat(idQa)) {
            try {
                if ((r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isAfter(tanggalAwal)
                        && r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isBefore(tanggalAkhir))
                        || r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isEqual(tanggalAwal)
                        || r.getKomponenPemeriksaan().getHasilPemeriksaan().getTugasPemeriksaan().getTanggalMulai()
                        .isEqual(tanggalAkhir)
                    // || (r.getKomponenPemeriksaan().getHasilPemeriksaan().
                    // getTugasPemeriksaan()
                    // .getTanggalSelesai().isAfter(tanggalAwal) &&
                    // r.getKomponenPemeriksaan().getHasilPemeriksaan().
                    // getTugasPemeriksaan()
                    // .getTanggalSelesai().isBefore(tanggalAkhir))
                ) {
                    getByTanggal.add(r);
                }
            } catch (NullPointerException | NoSuchElementException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Temuan risiko tidak memiliki pelaksana!");
            }
        }
        return getByTanggal;
    }

    @Override
    public List<TemuanRisiko> getByPembuat(final int idQa) {
        Employee emp = employeeRestService.getById(idQa);
        List<TemuanRisiko> temuan = temuanRisikoDB.findAllByPembuat(emp);
        return temuan;
    }

    @Override
    public List<TemuanRisiko> getByKomponenPemeriksaan(KomponenPemeriksaan komponenPemeriksaan) {
        return temuanRisikoDB.findAllByKomponenPemeriksaan(komponenPemeriksaan);
    }

    @Override
    public List<TemuanRisiko> getByDaftarKomponenPemeriksaan(List<KomponenPemeriksaan> komponenPemeriksaanList) {
        return temuanRisikoDB.findAllByKomponenPemeriksaanIn(komponenPemeriksaanList);
    }

    @Override
    public List<TemuanRisiko> getHistoriTemuanRisikoKantorCabang(TugasPemeriksaan tugasPemeriksaan, Risiko risiko) {
        StatusHasilPemeriksaan statusHasilPemeriksaan = statusHasilPemeriksaanDB.findById(5).get();
        List<TugasPemeriksaan> tugasPemeriksaans = tugasPemeriksaanDB
                .findAllByKantorCabang(tugasPemeriksaan.getKantorCabang());
        List<HasilPemeriksaan> hasilPemeriksaans = hasilPemeriksaanDB
                .findAllByTugasPemeriksaanInAndStatusHasilPemeriksaan(tugasPemeriksaans, statusHasilPemeriksaan);
        List<KomponenPemeriksaan> komponenPemeriksaans = komponenPemeriksaanDB
                .findAllByHasilPemeriksaanInAndRisiko(hasilPemeriksaans, risiko);

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
