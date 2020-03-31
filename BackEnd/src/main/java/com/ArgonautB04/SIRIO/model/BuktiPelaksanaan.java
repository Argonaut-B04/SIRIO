package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table
public class BuktiPelaksanaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBuktiPelaksanaan;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String lampiran;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan;

    @Size(max = 125)
    @Column
    private String feedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusBukti", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusBuktiPelaksanaan statusBuktiPelaksanaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rekomendasi", referencedColumnName = "idRekomendasi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Rekomendasi rekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemeriksa", referencedColumnName = "idEmployee")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pemeriksa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    public int getIdBuktiPelaksanaan() {
        return idBuktiPelaksanaan;
    }

    public void setIdBuktiPelaksanaan(int idBuktiPelaksanaan) {
        this.idBuktiPelaksanaan = idBuktiPelaksanaan;
    }

    public String getLampiran() {
        return lampiran;
    }

    public void setLampiran(String lampiran) {
        this.lampiran = lampiran;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public StatusBuktiPelaksanaan getStatusBuktiPelaksanaan() {
        return statusBuktiPelaksanaan;
    }

    public void setStatusBuktiPelaksanaan(StatusBuktiPelaksanaan statusBuktiPelaksanaan) {
        this.statusBuktiPelaksanaan = statusBuktiPelaksanaan;
    }

    public Rekomendasi getRekomendasi() {
        return rekomendasi;
    }

    public void setRekomendasi(Rekomendasi rekomendasi) {
        this.rekomendasi = rekomendasi;
    }

    public Employee getPemeriksa() {
        return pemeriksa;
    }

    public void setPemeriksa(Employee pemeriksa) {
        this.pemeriksa = pemeriksa;
    }

    public Employee getPembuat() {
        return pembuat;
    }

    public void setPembuat(Employee pembuat) {
        this.pembuat = pembuat;
    }
}
