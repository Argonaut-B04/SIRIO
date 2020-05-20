package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.Rekomendasi;
import com.ArgonautB04.SIRIO.model.Reminder;
import com.ArgonautB04.SIRIO.model.ReminderTemplate;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.RekomendasiDTO;
import com.ArgonautB04.SIRIO.rest.ReminderDTO;
import com.ArgonautB04.SIRIO.rest.ReminderTemplateDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RekomendasiRestService;
import com.ArgonautB04.SIRIO.services.ReminderRestService;
import com.ArgonautB04.SIRIO.services.ReminderTemplateRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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
    private ReminderTemplateRestService reminderTemplateRestService;

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
    private BaseResponse<List<Reminder>> tambahReminder(
            @RequestBody RekomendasiDTO rekomendasiDTO,
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "tabel rekomendasi");

        Integer idRekomendasi = rekomendasiDTO.getId();
        Rekomendasi rekomendasi = rekomendasiRestService.validateExistInById(idRekomendasi);

        ReminderTemplate reminderTemplateToAssign = rekomendasi.getReminderTemplatePilihan();
        if (reminderTemplateToAssign == null) {
            reminderTemplateToAssign = employee.getReminderTemplatePilihan();
        }
        if (reminderTemplateToAssign == null) {
            reminderTemplateToAssign = reminderTemplateRestService.getGlobal();
        }

        List<Reminder> oldList = reminderRestService.getByRekomendasi(rekomendasi);
        List<Reminder> daftarReminderBaru = new ArrayList<>();

        // Cari daftar reminder yang lama tapi diubah berdasarkan id dan nama pembuat
        List<ReminderDTO> daftarReminderDTOBaru = rekomendasiDTO.getReminder();

        for (ReminderDTO calonReminderBaru : daftarReminderDTOBaru) {
            Optional<Reminder> telahAda = reminderRestService.isExistByIdReminderDanPembuat(calonReminderBaru.getIdReminder(), rekomendasi, employee);
            Reminder hasil;
            if (telahAda.isPresent()) {
                // Reminder akan diperbarui
                hasil = telahAda.get();
                hasil.setTanggalPengiriman(
                        calonReminderBaru.getTanggalPengiriman()
                );
                reminderRestService.simpanReminder(hasil);
            } else {
                // Reminder akan dibuat baru
                hasil = reminderRestService.buatReminder(
                        new Reminder(
                                calonReminderBaru.getTanggalPengiriman(),
                                employee,
                                rekomendasi,
                                reminderTemplateToAssign
                        )
                );
            }
            daftarReminderBaru.add(hasil);
        }

        // hapus seluruh reminder lama yang tidak ada di daftar reminder baru
        for (Reminder reminderLama : oldList) {
            boolean ada = false;
            for (Reminder reminderBaru : daftarReminderBaru) {
                if (reminderLama.getIdReminder() == reminderBaru.getIdReminder() ) {
                    ada = true;
                    break;
                }
            }
            if (!ada) {
                reminderRestService.hapusReminder(reminderLama.getIdReminder());
            }
        }

        return new BaseResponse<>(200, "success", reminderRestService.getByRekomendasi(rekomendasi));
    }

    @GetMapping(value = "/get-template-reminder/{idReminder}")
    private BaseResponse<ReminderTemplate> getTemplateByIdReminder(
            @PathVariable("idReminder") int idReminder,
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        Reminder reminder = reminderRestService.validateExistById(idReminder);
        return new BaseResponse<>(200, "success", reminder.getReminderTemplate());
    }

    @GetMapping(value = "/get-template-reminder-global")
    private BaseResponse<ReminderTemplate> getTemplateGlobal(
            Principal principal
    ) {
        employeeRestService.validateEmployeeExistByPrincipal(principal);
        ReminderTemplate reminderTemplate = reminderTemplateRestService.getGlobal();
        return new BaseResponse<>(200, "success", reminderTemplate);
    }

    @PostMapping(value = "/atur-template-reminder", consumes = {"application/json"})
    private BaseResponse<String> aturReminderTemplate(
            @RequestBody ReminderTemplateDTO reminderTemplateDTO,
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);

        String effectArea = reminderTemplateDTO.getEffectArea();
        String subject = reminderTemplateDTO.getSubject();
        String content = reminderTemplateDTO.getContent();

        Reminder reminder = null;
        if (reminderTemplateDTO.getIdReminder() != null) {
            reminder = reminderRestService.validateExistById(reminderTemplateDTO.getIdReminder());
        }

        ReminderTemplate reminderTemplate = reminderTemplateRestService.ambilAtauBuatTemplate(
                new ReminderTemplate(
                        subject,
                        content
                )
        );

        switch (effectArea) {
            case "global":
                if (reminder != null) {
                    reminder.setReminderTemplate(reminderTemplate);
                    reminderRestService.ubahTemplateReminder(
                            reminder
                    );
                }
                reminderTemplateRestService.setGlobal(reminderTemplate);
                break;
            case "rekomendasi":
                assert reminder != null;
                Rekomendasi rekomendasiTarget = reminder.getRekomendasi();
                List<Reminder> daftarReminder = reminderRestService.getByRekomendasi(rekomendasiTarget);

                for (Reminder reminder1 : daftarReminder) {
                    reminder1.setReminderTemplate(reminderTemplate);
                    reminderRestService.ubahTemplateReminder(reminder1);
                }

                rekomendasiTarget.setReminderTemplatePilihan(reminderTemplate);
                rekomendasiRestService.buatAtauSimpanPerubahanRekomendasi(rekomendasiTarget, false);
                break;
            case "reminder":
                assert reminder != null;
                reminder.setReminderTemplate(reminderTemplate);
                reminderRestService.ubahTemplateReminder(
                        reminder
                );
                break;
            case "akun":
                if (reminder != null) {
                    reminder.setReminderTemplate(reminderTemplate);
                    reminderRestService.ubahTemplateReminder(
                            reminder
                    );
                }
                employee.setReminderTemplatePilihan(reminderTemplate);
                employeeRestService.simpanPerubahan(employee);
                break;
        }

        return new BaseResponse<>(200, "success", "Perubahan template email berhasil tersimpan");
    }
}
