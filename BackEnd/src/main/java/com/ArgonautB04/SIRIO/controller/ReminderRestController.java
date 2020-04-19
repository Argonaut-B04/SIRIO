package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderMailFormat;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.rest.ReminderDTO;
import com.ArgonautB04.SIRIO.rest.ReminderTemplateDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.ReminderMailFormatRestService;
import com.ArgonautB04.SIRIO.services.ReminderRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/Reminder")
public class ReminderRestController {

    @Autowired
    private ReminderRestService reminderRestService;

    @Autowired
    private RekomendasiRestService rekomendasiRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private ReminderMailFormatRestService reminderMailFormatRestService;

    /**
     * Mengambil seluruh reminder yang terhubung dengan rekomendasi spesifik
     *
     * @param rekomendasiDTO data transfer object untuk rekomendasi
     * @return daftar reminder yang terhubung dengan rekomendasi tersebut
     */
    @PostMapping("/getByRekomendasi")
    private BaseResponse<List<ReminderDTO>> getAllReminderUntukRekomendasi(
            @RequestBody RekomendasiDTO rekomendasiDTO,
            Principal principal
    ) {
        Optional<Employee> employeeOptional = employeeRestService.getByUsername(principal.getName());
        Employee employee;

        // Validasi : user berhasil login dengan valid
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak terdaftar dalam Sirio"
            );
        } else {
            employee = employeeOptional.get();
        }
        // Validasi selesai

        // Validasi : role user memperbolehkan pengaturan tenggat waktu
        if (!employee.getRole().getAccessPermissions().getAksesTabelRekomendasi()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak memiliki akses ke pengaturan tenggat waktu"
            );
        }
        // Validasi selesai

        Integer idRekomendasi = rekomendasiDTO.getId();
        Optional<Rekomendasi> rekomendasiOptional = rekomendasiRestService.getOptionalById(idRekomendasi);
        Rekomendasi rekomendasi;

        // Validasi : rekomendasi harus ada dalam basis data
        if (rekomendasiOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Rekomendasi dengan ID " + idRekomendasi + " tidak ditemukan!"
            );
        } else {
            rekomendasi = rekomendasiOptional.get();
        }
        // Validasi selesai

        List<Reminder> result = reminderRestService.getByRekomendasi(rekomendasi);
        List<ReminderDTO> resultDTO = new ArrayList<>();
        for (Reminder reminder : result) {
            ReminderDTO instance = new ReminderDTO();
            instance.setIdPembuat(reminder.getPembuat().getIdEmployee());
            instance.setIdReminder(reminder.getIdReminder());
            instance.setTanggalPengiriman(reminder.getTanggalPengiriman());
            resultDTO.add(instance);
        }

        return new BaseResponse<>(200, "success", resultDTO);
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
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");

        Integer idRekomendasi = rekomendasiDTO.getId();
        Rekomendasi rekomendasi = rekomendasiRestService.validateExistInDatabase(idRekomendasi);

        List<Reminder> oldList = reminderRestService.getByRekomendasi(rekomendasi);
        for (Reminder reminder : oldList) {
            reminderRestService.hapusReminder(reminder.getIdReminder());
        }

        List<ReminderDTO> daftarReminderDTOBaru = new ArrayList<>();

        ReminderMailFormat reminderMailFormatGlobal = reminderMailFormatRestService.getGlobal();

        for (ReminderDTO reminder : rekomendasiDTO.getReminder()) {

            LocalDate tanggalDate = reminder.getTanggalPengiriman();

            Reminder newReminder = reminderRestService.buatReminder(
                    new Reminder(
                            tanggalDate,
                            employee,
                            rekomendasi,
                            reminderMailFormatGlobal
                    )
            );

            reminder.setIdReminder(newReminder.getIdReminder());

            daftarReminderDTOBaru.add(reminder);
        }
        return new BaseResponse<>(200, "success", daftarReminderDTOBaru);
    }

    @GetMapping(value = "/get-template-email/{idReminder}")
    private BaseResponse<ReminderMailFormat> getTemplateByIdReminder(
            @PathVariable("idReminder") int idReminder,
            Principal principal
    ) {
        Optional<Employee> employeeOptional = employeeRestService.getByUsername(principal.getName());
        Employee employee;

        // Validasi : user berhasil login dengan valid
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak terdaftar dalam Sirio"
            );
        } else {
            employee = employeeOptional.get();
        }
        // Validasi selesai

        Optional<Reminder> reminderOptional = reminderRestService.getOptionalById(idReminder);
        Reminder reminder;
        // Validasi : reminder harus ada dalam basis data
        if (reminderOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Reminder dengan ID " + idReminder + " tidak ditemukan!"
            );
        } else {
            reminder = reminderOptional.get();
        }
        // Validasi selesai

        return new BaseResponse<>(200, "success", reminder.getReminderMailFormat());
    }
  
    @PostMapping(value = "/atur-template-email", consumes = {"application/json"})
    private BaseResponse<String> aturEmailFormat(
            @RequestBody ReminderTemplateDTO reminderTemplateDTO,
            Principal principal
    ) {
        Optional<Employee> employeeOptional = employeeRestService.getByUsername(principal.getName());
        Employee employee;

        // Validasi : user berhasil login dengan valid
        if (employeeOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak terdaftar dalam Sirio"
            );
        } else {
            employee = employeeOptional.get();
        }
        // Validasi selesai

        // Validasi : role user memperbolehkan pengaturan tenggat waktu
        if (!employee.getRole().getAccessPermissions().getAksesTabelRekomendasi()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Akun anda tidak memiliki akses ke pengaturan template email"
            );
        }
        // Validasi selesai

        Reminder reminder = null;

        Integer idReminder = reminderTemplateDTO.getIdReminder();
        if (idReminder != null) {
            Optional<Reminder> reminderOptional = reminderRestService.getOptionalById(idReminder);

            // Validasi : reminder harus ada dalam basis data
            if (reminderOptional.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reminder dengan ID " + idReminder + " tidak ditemukan!"
                );
            } else {
                reminder = reminderOptional.get();
            }
            // Validasi selesai
        }


        Integer idEmailTemplate = reminderTemplateDTO.getId();
        String effectArea = reminderTemplateDTO.getEffectArea();
        String subject = reminderTemplateDTO.getSubject();
        String content = reminderTemplateDTO.getContent();

        ReminderMailFormat reminderMailFormat = reminderMailFormatRestService.ambilAtauBuatTemplate(
                new ReminderMailFormat(
                        subject,
                        content
                )
        );

        switch (effectArea) {
            case "global":
                List<Reminder> oldGlobal = reminderRestService.getByReminderMailFormat(
                        reminderMailFormatRestService.getGlobal()
                );
                for (Reminder reminder1 : oldGlobal) {
                    reminder1.setReminderMailFormat(reminderMailFormat);
                    reminderRestService.ubahTemplateReminder(reminder1);
                }
                reminderMailFormatRestService.setGlobal(reminderMailFormat);

                break;
            case "rekomendasi":
                assert reminder != null;
                Rekomendasi rekomendasiTarget = reminder.getRekomendasi();
                List<Reminder> daftarReminder = reminderRestService.getByRekomendasi(rekomendasiTarget);

                for (Reminder reminder1 : daftarReminder) {
                    reminder1.setReminderMailFormat(reminderMailFormat);
                    reminderRestService.ubahTemplateReminder(reminder1);
                }
                break;
            case "reminder":
                assert reminder != null;
                reminder.setReminderMailFormat(reminderMailFormat);
                reminderRestService.ubahTemplateReminder(
                        reminder
                );
                break;
            case "akun":
                List<Rekomendasi> dibuat = rekomendasiRestService.getByPembuat(employee);
                for (Rekomendasi rekomendasi : dibuat) {
                    List<Reminder> reminderRekomendasi = reminderRestService.getByRekomendasi(rekomendasi);
                    for (Reminder reminder1 : reminderRekomendasi) {
                        reminder1.setReminderMailFormat(reminderMailFormat);
                        reminderRestService.ubahTemplateReminder(reminder1);
                    }
                }
                break;
        }

        return new BaseResponse<>(200, "success", "Perubahan template email berhasil tersimpan");
    }
}
