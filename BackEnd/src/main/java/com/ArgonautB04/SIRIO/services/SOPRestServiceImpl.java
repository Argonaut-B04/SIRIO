package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.repository.SOPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class SOPRestServiceImpl implements SOPRestService {

    @Autowired
    private SOPRepo sopRepo;

    @Override
    public SOP createSOP(SOP sop) {
        return sopRepo.save(sop);
    }

    @Override
    public SOP getById(int id_sop) {
        Optional<SOP> sop = sopRepo.findById(id_sop);
        if (sop.isPresent()) return sop.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<SOP> getAll() {
        return sopRepo.findAll();
    }

    @Override
    public SOP updateSOP(int id_sop, SOP sop) {
        SOP target = getById(id_sop);
        target.setPembuat(sop.getPembuat());
        target.setLink_dokumen(sop.getLink_dokumen());
        target.setKategori(sop.getKategori());
        target.setJudul(sop.getJudul());
        return sopRepo.save(target);
    }

    @Override
    public void deleteSOP(int id_sop) {
        sopRepo.deleteById(id_sop);
    }
}
