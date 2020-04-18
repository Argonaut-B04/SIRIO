package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.rest.ReminderDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.ReminderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

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
    @PostMapping("/getByRekomendasi")
    private BaseResponse<List<ReminderDTO>> getAllReminderUntukRekomendasi(
            @RequestBody RekomendasiDTO rekomendasiDTO
    ) {
        BaseResponse<List<ReminderDTO>> response = new BaseResponse<>();
        Integer idRekomendasi = rekomendasiDTO.getId();
        try {
            Rekomendasi rekomendasi = rekomendasiRestService.getById(idRekomendasi);
            List<Reminder> result = rekomendasi.getDaftarReminder();
            List<ReminderDTO> resultDTO = new ArrayList<>();
            for (Reminder reminder : result) {
                ReminderDTO instance = new ReminderDTO();
                instance.setIdPembuat(reminder.getPembuat().getIdEmployee());
                instance.setIdReminder(reminder.getIdReminder());
                instance.setTanggalPengiriman(reminder.getTanggalPengiriman());
                resultDTO.add(instance);
            }

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(resultDTO);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        } catch (NullPointerException e) {
            response.setStatus(200);
            response.setMessage("success");
            response.setResult(new ArrayList<>());
        }
        return response;
    }

    /**
     * Konfigurasi reminder secara batch untuk reminder terpilih
     *
     * @param rekomendasiDTO data transfer object untuk rekomendasi yang memuat informasi reminderDTO
     * @return reminder yang telah disimpan
     */
    @PostMapping(value = "/konfigurasi", consumes = {"application/json"})
    private BaseResponse<List<ReminderDTO>> tambahReminder(
            @RequestBody RekomendasiDTO rekomendasiDTO,
            Principal principal
    ) {
        BaseResponse<List<ReminderDTO>> response = new BaseResponse<>();
        Integer idRekomendasi = rekomendasiDTO.getId();
        Optional<Employee> employeeTarget = employeeRestService.getByUsername(principal.getName());
        Employee employee;
        if (employeeTarget.isPresent()) {
            employee = employeeTarget.get();
            if (employee.getRole().getAccessPermissions().getUbahReminder()) {

                try {
                    Rekomendasi rekomendasi = rekomendasiRestService.getById(idRekomendasi);
                    List<Reminder> daftarReminderBaru = new ArrayList<>();
                    List<ReminderDTO> daftarReminderDTOBaru = new ArrayList<>();

                    for (ReminderDTO reminder : rekomendasiDTO.getReminder()) {
                        LocalDate tanggalDate = reminder.getTanggalPengiriman();
                        if (reminderRestService.isExistById(reminder.getIdReminder())) {
                            Reminder reminderTarget = reminderRestService.ubahReminder(
                                    reminder.getIdReminder(),
                                    tanggalDate
                            );
                            ReminderDTO newReminder = new ReminderDTO();
                            newReminder.setIdReminder(reminderTarget.getIdReminder());
                            newReminder.setTanggalPengiriman(reminder.getTanggalPengiriman());

                            daftarReminderDTOBaru.add(newReminder);
                            daftarReminderBaru.add(reminderTarget);
                        } else {
                            Reminder newReminder = new Reminder();
                            newReminder.setPembuat(employee);
                            newReminder.setTanggalPengiriman(tanggalDate);
                            newReminder.setRekomendasi(rekomendasi);
//                            newReminder.setReminderMailFormat();
                            newReminder = reminderRestService.buatReminder(newReminder);

                            reminder.setIdReminder(newReminder.getIdReminder());
                            daftarReminderDTOBaru.add(reminder);
                            daftarReminderBaru.add(newReminder);
                        }
                    }

                    rekomendasi.setDaftarReminder(daftarReminderBaru);
                    rekomendasiRestService.ubahRekomendasi(idRekomendasi, rekomendasi);

                    response.setStatus(200);
                    response.setMessage("success");
                    response.setResult(daftarReminderDTOBaru);

                    return response;
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
                    );
                }
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini!"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio!"
        );
    }
}
