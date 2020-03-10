package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.repository.SOPDB;
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
    private SOPDB SOPDB;

    @Override
    public SOP createSOP(SOP sop) {
        return SOPDB.save(sop);
    }

    @Override
    public SOP getById(int idSop) {
        Optional<SOP> sop = SOPDB.findById(idSop);
        if (sop.isPresent()) return sop.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<SOP> getAll() {
        return SOPDB.findAll();
    }

    @Override
    public SOP updateSOP(int idSop, SOP sop) {
        SOP target = getById(idSop);
        target.setPembuat(sop.getPembuat());
        target.setLinkDokumen(sop.getLinkDokumen());
        target.setKategori(sop.getKategori());
        target.setJudul(sop.getJudul());
        return SOPDB.save(target);
    }

    @Override
    public void deleteSOP(int idSop) {
        SOPDB.deleteById(idSop);
    }
}
