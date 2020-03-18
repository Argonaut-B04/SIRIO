package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.rest.ReminderDTO;
import com.ArgonautB04.SIRIO.rest.Settings;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.ReminderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/Reminder")
public class ReminderRestController {

    @Autowired
    private ReminderRestService reminderRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Mengambil seluruh reminder yang terhubung dengan rekomendasi spesifik
     *
     * @param rekomendasiDTO data transfer object untuk rekomendasi
     * @return daftar reminder yang terhubung dengan rekomendasi tersebut
     */
    @GetMapping("/getListByRekomendasi")
    private BaseResponse<List<Reminder>> getAllReminderUntukRekomendasi(
            @RequestBody RekomendasiDTO rekomendasiDTO
    ) {
        BaseResponse<List<Reminder>> response = new BaseResponse<>();
        Integer idRekomendasi = rekomendasiDTO.getId();
        try {
            Rekomendasi rekomendasi = rekomendasiRestService.getById(idRekomendasi);
            List<Reminder> result = reminderRestService.getByRekomendasi(rekomendasi);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menambah reminder baru untuk rekomendasi spesifik
     *
     * @param rekomendasiDTO data transfer object untuk rekomendasi yang memuat informasi reminderDTO
     * @return reminder yang telah disimpan
     */
    @PostMapping(value = "/tambah", consumes = {"application/json"})
    private BaseResponse<Reminder> tambahReminder(
            @RequestBody RekomendasiDTO rekomendasiDTO
    ) {
        BaseResponse<Reminder> response = new BaseResponse<>();
        Integer idRekomendasi = rekomendasiDTO.getId();
        ReminderDTO reminderDTO = rekomendasiDTO.getReminderDTO();
        try {
            Reminder reminder = new Reminder();

            LocalDate tanggalReminderLocalDate = Settings.stringToLocalDate(reminderDTO.getTanggal());
            reminder.setTanggalPengiriman(tanggalReminderLocalDate);

            Rekomendasi rekomendasi = rekomendasiRestService.getById(idRekomendasi);
            reminder.setRekomendasi(rekomendasi);

            Employee pembuat = employeeRestService.getById(reminderDTO.getIdPembuat());
            reminder.setPembuat(pembuat);

            reminder = reminderRestService.buatReminder(reminder);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(reminder);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Mengubah tanggal reminder
     *
     * @param reminderDTO data transfer object untuk reminder
     * @return reminder yang telah disimpan
     */
    @PutMapping(value = "/ubah", consumes = {"application/json"})
    private BaseResponse<Reminder> ubahTanggalReminder(
            @RequestBody ReminderDTO reminderDTO
    ) {
        BaseResponse<Reminder> response = new BaseResponse<>();
        Integer idReminder = reminderDTO.getId();
        try {
            LocalDate tanggalReminderLocalDate = Settings.stringToLocalDate(reminderDTO.getTanggal());
            Reminder result = reminderRestService.ubahReminder(idReminder, tanggalReminderLocalDate);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Reminder dengan ID " + idReminder + " tidak ditemukan!"
            );
        }
        return response;
    }

    /**
     * Menghapus reminder
     *
     * @param reminderDTO data transfer object untuk reminder
     */
    @DeleteMapping("/hapus")
    private BaseResponse<String> hapusReminder(
            @RequestBody ReminderDTO reminderDTO
    ) {
        BaseResponse<String> response = new BaseResponse<>();
        int idReminder = reminderDTO.getId();
        try {
            reminderRestService.hapusReminder(idReminder);

            response.setStatus(200);
            response.setMessage("success");
            response.setResult("Reminder dengan id " + idReminder + " terhapus!");
        } catch (EmptyResultDataAccessException e) {
            response.setStatus(404);
            response.setMessage("not found");
            response.setResult("Reminder dengan id " + idReminder + " tidak dapat ditemukan");
        }
        return response;
    }
}
