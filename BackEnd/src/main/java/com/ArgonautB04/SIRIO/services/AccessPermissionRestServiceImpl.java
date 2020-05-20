package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.AccessPermissions;
import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.repository.AccessPermissionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessPermissionRestServiceImpl implements AccessPermissionRestService {

    @Autowired
    private AccessPermissionDB accessPermissionDB;

    @Override
    public AccessPermissions getPermission(Role role) {
        return accessPermissionDB.findByRole(role);
    }

    @Override
    public Boolean roleHasPermission(Role role, String requestedAccess) {
        AccessPermissions accessPermissions = getPermission(role);
        switch (requestedAccess) {
            case "akses risk level":
                return accessPermissions.getAksesRiskLevel();
            case "konfigurasi risk level":
                return accessPermissions.getUbahRiskLevel();
            case "akses risk rating":
                return accessPermissions.getAksesRiskRating();
            case "konfigurasi risk rating":
                return accessPermissions.getUbahRiskRating();
            case "akses tabel rekomendasi":
                return accessPermissions.getAksesTabelRekomendasi();
            case "detail rekomendasi":
                return accessPermissions.getAksesRekomendasi();
            case "detail bukti pelaksanaan":
                return accessPermissions.getAksesBuktiPelaksanaan();
            case "tambah bukti pelaksanaan":
                return accessPermissions.getAksesTambahBuktiPelaksanaan();
            case "ubah bukti pelaksanaan":
                return accessPermissions.getAksesUbahBuktiPelaksanaan();
            case "persetujuan bukti pelaksanaan":
                return accessPermissions.getAksesPersetujuanBuktiPelaksanaan();
            case "dashboard kantor cabang":
                return  accessPermissions.getAksesDashboardKC();
            default:
                return false;
        }
    }
}
