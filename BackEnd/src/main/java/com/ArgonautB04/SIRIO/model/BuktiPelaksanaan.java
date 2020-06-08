package com.argonautb04.sirio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
public class BuktiPelaksanaan implements Serializable {

    /**
     * Maximum size of varchar in database.
     */
    private final int maxVAR = 255;

    /**
     * Half size of maximum varchar.
     */
    private final int halfVAR = 125;

    /**
     * ID for Bukti Pelaksanaan Object.
     */
    @Id
    private int idBuktiPelaksanaan;

    /**
     * Attachment.
     */
    @NotNull
    @Size(max = maxVAR)
    @Column(nullable = false)
    private String lampiran;

    /**
     * Details.
     */
    @NotNull
    @Size(max = halfVAR)
    @Column(nullable = false)
    private String keterangan;

    /**
     * Feedback.
     */
    @Size(max = halfVAR)
    @Column
    private String feedback;

    /**
     * Approval Date.
     */
    @DateTimeFormat
    private LocalDate tanggalPersetujuan;

    /**
     * Status.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status", referencedColumnName = "idStatusBukti", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private StatusBuktiPelaksanaan statusBuktiPelaksanaan;

    /**
     * Recommendation object.
     */
    @OneToOne
    @MapsId
    @JsonIgnore
    private Rekomendasi rekomendasi;

    /**
     * Person in task Employee object.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pemeriksa", referencedColumnName = "idEmployee")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pemeriksa;

    /**
     * Person in charge Employee object.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pembuat", referencedColumnName = "idEmployee", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Employee pembuat;

    /**
     * Getter for Bukti Pelaksaan id.
     *
     * @return id of this object.
     */
    public int getIdBuktiPelaksanaan() {
        return idBuktiPelaksanaan;
    }

    /**
     * Setter for bukti pelaksanaan object.
     *
     * @param id for this object.
     */
    public void setIdBuktiPelaksanaan(final int id) {
        this.idBuktiPelaksanaan = id;
    }

    /**
     * Getter for attachment.
     *
     * @return lampiran attachment.
     */
    public String getLampiran() {
        return lampiran;
    }

    /**
     * Setter for attachment.
     *
     * @param attachment attachment
     */
    public void setLampiran(final String attachment) {
        this.lampiran = attachment;
    }

    /**
     * Getter for details.
     *
     * @return keterangan details.
     */
    public String getKeterangan() {
        return keterangan;
    }

    /**
     * Setter for details.
     *
     * @param details of this object
     */
    public void setKeterangan(final String details) {
        this.keterangan = details;
    }

    /**
     * Getter for feedback.
     *
     * @return feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Setter for feedback.
     *
     * @param feed
     */
    public void setFeedback(final String feed) {
        this.feedback = feed;
    }

    /**
     * Getter for status.
     *
     * @return status
     */
    public StatusBuktiPelaksanaan getStatusBuktiPelaksanaan() {
        return statusBuktiPelaksanaan;
    }

    /**
     * Setter for status.
     *
     * @param status
     */
    public void setStatusBuktiPelaksanaan(final StatusBuktiPelaksanaan status) {
        this.statusBuktiPelaksanaan = status;
    }

    /**
     * Getter for Recommendation object.
     *
     * @return Recommendation object
     */
    public Rekomendasi getRekomendasi() {
        return rekomendasi;
    }

    /**
     * Setter for Recommendation Object.
     *
     * @param recommendation object
     */
    public void setRekomendasi(final Rekomendasi recommendation) {
        this.rekomendasi = recommendation;
    }

    /**
     * Getter for person in task.
     *
     * @return person in task
     */
    public Employee getPemeriksa() {
        return pemeriksa;
    }

    /**
     * Setter for person in task.
     *
     * @param pit person in task
     */
    public void setPemeriksa(final Employee pit) {
        this.pemeriksa = pit;
    }

    /**
     * Getter for person in charge.
     *
     * @return pembuat person in charge
     */
    public Employee getPembuat() {
        return pembuat;
    }

    /**
     * Setter for person in change.
     *
     * @param pic person in charge
     */
    public void setPembuat(final Employee pic) {
        this.pembuat = pic;
    }

    /**
     * Getter for approval date.
     *
     * @return approval date
     */
    public LocalDate getTanggalPersetujuan() {
        return tanggalPersetujuan;
    }

    /**
     * Setter for approval date.
     *
     * @param approvalDate
     */
    public void setTanggalPersetujuan(final LocalDate approvalDate) {
        this.tanggalPersetujuan = approvalDate;
    }
}
