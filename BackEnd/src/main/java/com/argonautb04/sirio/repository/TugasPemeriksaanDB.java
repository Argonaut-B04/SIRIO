package com.argonautb04.sirio.repository;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.KantorCabang;
import com.argonautb04.sirio.model.RencanaPemeriksaan;
import com.argonautb04.sirio.model.TugasPemeriksaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface TugasPemeriksaanDB extends JpaRepository<TugasPemeriksaan, Integer> {
    List<TugasPemeriksaan> findAllByPelaksana(Employee pelaksana);

    List<TugasPemeriksaan> findAllByKantorCabang(KantorCabang kantorCabang);

    List<TugasPemeriksaan> findAllByKantorCabangIn(Collection<KantorCabang> kantorCabang);

    List<TugasPemeriksaan> findAllByRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan);

    List<TugasPemeriksaan> findByTanggalSelesaiIsGreaterThanAndTanggalMulaiIsLessThan(@NotNull LocalDate tanggalMulai,
                                                                                      @NotNull LocalDate tanggalSelesai);

    List<TugasPemeriksaan> findAllByKantorCabangInAndTanggalSelesaiBetween(Collection<KantorCabang> kantorCabang,
                                                                           @NotNull LocalDate tanggalMulai, @NotNull LocalDate tanggalSelesai);

    List<TugasPemeriksaan> findAllByKantorCabangAndTanggalSelesaiBetween(KantorCabang kantorCabang,
                                                                         @NotNull LocalDate tanggalMulai, @NotNull LocalDate tanggalSelesai);
}
