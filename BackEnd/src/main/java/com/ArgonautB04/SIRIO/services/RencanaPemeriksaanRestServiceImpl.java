package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.repository.RencanaPemeriksaanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public RencanaPemeriksaan createRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan) {
        return rencanaPemeriksaanDB.save(rencanaPemeriksaan);
    }

    @Override
    public RencanaPemeriksaan getById(int idRencanaPemeriksaan) {
        Optional<RencanaPemeriksaan> rencanaPemeriksaan = rencanaPemeriksaanDB.findById(idRencanaPemeriksaan);
        if (rencanaPemeriksaan.isPresent()) return rencanaPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<RencanaPemeriksaan> getAll() {
        return rencanaPemeriksaanDB.findAll();
    }

    @Override
    public RencanaPemeriksaan updateRencanaPemeriksaan(int idRencanaPemeriksaan, RencanaPemeriksaan rencanaPemeriksaan) {
        RencanaPemeriksaan target = getById(idRencanaPemeriksaan);
        target.setLinkMajelis(rencanaPemeriksaan.getLinkMajelis());
        target.setStatus(rencanaPemeriksaan.getStatus());
        target.setPembuat(rencanaPemeriksaan.getPembuat());
        target.setNamaRencana(rencanaPemeriksaan.getNamaRencana());
        return rencanaPemeriksaanDB.save(target);
    }

    @Override
    public void deleteRencanaPemeriksaan(int idRencanaPemeriksaan) {
        rencanaPemeriksaanDB.deleteById(idRencanaPemeriksaan);
    }
}
