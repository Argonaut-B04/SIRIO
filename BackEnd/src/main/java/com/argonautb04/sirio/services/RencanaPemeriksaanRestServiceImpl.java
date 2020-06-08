package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.RencanaPemeriksaan;
import com.argonautb04.sirio.repository.RencanaPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RencanaPemeriksaanRestServiceImpl implements RencanaPemeriksaanRestService {

    @Autowired
    private RencanaPemeriksaanDB rencanaPemeriksaanDB;

    @Override
    public RencanaPemeriksaan buatRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan) {
        return rencanaPemeriksaanDB.save(rencanaPemeriksaan);
    }

    @Override
    public RencanaPemeriksaan getById(int idRencanaPemeriksaan) {
        Optional<RencanaPemeriksaan> rencanaPemeriksaan = rencanaPemeriksaanDB.findById(idRencanaPemeriksaan);
        if (rencanaPemeriksaan.isPresent())
            return rencanaPemeriksaan.get();
        else
            throw new NoSuchElementException();
    }

    @Override
    public List<RencanaPemeriksaan> getByPembuat(Employee pembuat) {
        return rencanaPemeriksaanDB.findAllByPembuat(pembuat);
    }

    @Override
    public Optional<RencanaPemeriksaan> getByNama(String nama) {
        return rencanaPemeriksaanDB.findByNamaRencana(nama);
    }

    @Override
    public RencanaPemeriksaan validateExistById(int idRencanaPemeriksaan) {
        Optional<RencanaPemeriksaan> target = rencanaPemeriksaanDB.findById(idRencanaPemeriksaan);
        if (target.isPresent()) {
            return target.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Rencana dengan ID " + idRencanaPemeriksaan + " tidak ditemukan!");
        }
    }

    @Override
    public List<RencanaPemeriksaan> getAll() {
        return rencanaPemeriksaanDB.findAll();
    }

    @Override
    public RencanaPemeriksaan ubahRencanaPemeriksaan(int idRencanaPemeriksaan, RencanaPemeriksaan rencanaPemeriksaan) {
        RencanaPemeriksaan target = getById(idRencanaPemeriksaan);
        target.setLinkMajelis(rencanaPemeriksaan.getLinkMajelis());
        target.setStatus(rencanaPemeriksaan.getStatus());
        target.setPembuat(rencanaPemeriksaan.getPembuat());
        target.setNamaRencana(rencanaPemeriksaan.getNamaRencana());
        return rencanaPemeriksaanDB.save(target);
    }

    @Override
    public void hapusRencanaPemeriksaan(int idRencanaPemeriksaan) {
        rencanaPemeriksaanDB.deleteById(idRencanaPemeriksaan);
    }
}
