package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.RencanaPemeriksaan;
import com.ArgonautB04.SIRIO.repository.RencanaPemeriksaanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RencanaPemeriksaanRestServiceImpl implements RencanaPemeriksaanRestService {

    @Autowired
    private RencanaPemeriksaanRepo rencanaPemeriksaanRepo;

    @Override
    public RencanaPemeriksaan createRencanaPemeriksaan(RencanaPemeriksaan rencanaPemeriksaan) {
        return rencanaPemeriksaanRepo.save(rencanaPemeriksaan);
    }

    @Override
    public RencanaPemeriksaan getById(int id_rencana_pemeriksaan) {
        Optional<RencanaPemeriksaan> rencanaPemeriksaan = rencanaPemeriksaanRepo.findById(id_rencana_pemeriksaan);
        if (rencanaPemeriksaan.isPresent()) return rencanaPemeriksaan.get();
        else throw new NoSuchElementException();
    }

    @Override
    public RencanaPemeriksaan updateRencanaPemeriksaan(int id_rencana_pemeriksaan, RencanaPemeriksaan rencanaPemeriksaan) {
        RencanaPemeriksaan target = getById(id_rencana_pemeriksaan);
        target.setLink_majelis(rencanaPemeriksaan.getLink_majelis());
        target.setStatus(rencanaPemeriksaan.getStatus());
        target.setPembuat(rencanaPemeriksaan.getPembuat());
        target.setNama_rencana(rencanaPemeriksaan.getNama_rencana());
        return rencanaPemeriksaanRepo.save(target);
    }

    @Override
    public void deleteRencanaPemeriksaan(int id_rencana_pemeriksaan) {
        rencanaPemeriksaanRepo.deleteById(id_rencana_pemeriksaan);
    }
}
