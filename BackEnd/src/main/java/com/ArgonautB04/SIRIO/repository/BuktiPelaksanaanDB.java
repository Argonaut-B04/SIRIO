package com.ArgonautB04.SIRIO.repository;

import com.ArgonautB04.SIRIO.model.BuktiPelaksanaan;
import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BuktiPelaksanaanDB
        extends JpaRepository<BuktiPelaksanaan, Integer> {

    /**
     * Get all Bukti Pelaksanaan object by Person in Charge.
     *
     * @param pembuat person in charge Employee object
     * @return Bukti Pelaksaan object
     */
    List<BuktiPelaksanaan> findAllByPembuat(Employee pembuat);

    /**
     * Getter for all Bukti Pelaksanaan which has Recommendation in list.
     *
     * @param rekomendasi Recommendation object
     * @return Bukti Pelaksanaan object
     */
    List<BuktiPelaksanaan> findAllByRekomendasiIn(
            Collection<Rekomendasi> rekomendasi
    );

    /**
     * Get Bukti Pelaksanaan bound to Recommendation.
     *
     * @param rekomendasi Recommendation object
     * @return Bukti Pelaksanaan object
     */
    BuktiPelaksanaan findByRekomendasi(Rekomendasi rekomendasi);
}
