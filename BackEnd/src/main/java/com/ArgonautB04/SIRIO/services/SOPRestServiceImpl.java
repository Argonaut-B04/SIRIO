package com.argonautb04.sirio.services;

import com.argonautb04.sirio.model.SOP;
import com.argonautb04.sirio.repository.SOPDB;
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
public class SOPRestServiceImpl implements SOPRestService {

    @Autowired
    private SOPDB SOPDB;

    @Override
    public SOP buatSOP(SOP sop) {
        return SOPDB.save(sop);
    }

    @Override
    public SOP getById(int idSop) {
        Optional<SOP> sop = SOPDB.findById(idSop);
        if (sop.isPresent())
            return sop.get();
        else
            throw new NoSuchElementException();
    }

    @Override
    public Optional<SOP> getByJudul(String judul) {
        return SOPDB.findByJudul(judul);
    }

    @Override
    public SOP validateExistById(int idSOP) {

        Optional<SOP> sop = SOPDB.findById(idSOP);
        if (sop.isPresent()) {
            return sop.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SOP dengan ID " + idSOP + " tidak ditemukan!");
        }
    }

    @Override
    public SOP nonaktifkanSOP(int idSOP) {
        SOP sop = getById(idSOP);
        sop.setStatus(SOP.Status.NONAKTIF);
        return sop;
    }

    @Override
    public List<SOP> getAll() {
        return SOPDB.findAllByStatus(SOP.Status.AKTIF);
    }

    @Override
    public SOP ubahSOP(int idSop, SOP sop) {
        SOP target = getById(idSop);
        target.setPembuat(sop.getPembuat());
        target.setLinkDokumen(sop.getLinkDokumen());
        target.setKategori(sop.getKategori());
        target.setJudul(sop.getJudul());
        return SOPDB.save(target);
    }

    @Override
    public void hapusSOP(int idSop) {
        SOPDB.deleteById(idSop);
    }
}
