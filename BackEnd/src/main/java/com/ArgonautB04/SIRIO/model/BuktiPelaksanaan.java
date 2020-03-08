package com.ArgonautB04.SIRIO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "Bukti_Pelaksanaan")
public class BuktiPelaksanaan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_bukti_pelaksanaan;

    @Size(max = 255)
    @Column
    private String lampiran;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String keterangan;

    @NotNull
    @Size(max = 125)
    @Column(nullable = false)
    private String feedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "id_status_bukti", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusBuktiPelaksanaan status_bukti_pelaksanaan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rekomendasi", referencedColumnName = "id_rekomendasi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Rekomendasi rekomendasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemeriksa", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pemeriksa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "id_employee", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Employee pembuat;

    public int getId_bukti_pelaksanaan() {
        return id_bukti_pelaksanaan;
    }

    public void setId_bukti_pelaksanaan(int id_bukti_pelaksanaan) {
        this.id_bukti_pelaksanaan = id_bukti_pelaksanaan;
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

    public StatusBuktiPelaksanaan getStatus_bukti_pelaksanaan() {
        return status_bukti_pelaksanaan;
    }

    public void setStatus_bukti_pelaksanaan(StatusBuktiPelaksanaan status_bukti_pelaksanaan) {
        this.status_bukti_pelaksanaan = status_bukti_pelaksanaan;
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
