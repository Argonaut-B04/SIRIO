package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.repository.RisikoDB;
import com.ArgonautB04.SIRIO.rest.RisikoDTO;
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
public class RisikoRestServiceImpl implements RisikoRestService {

    @Autowired
    private RisikoDB risikoDB;

    @Autowired
    private SOPRestService sopRestService;

    @Override
    public Risiko buatRisiko(Risiko risiko) {
        risiko.setStatus(Risiko.Status.AKTIF);
        return risikoDB.save(risiko);
    }

    @Override
    public Risiko getById(int idRisiko) {
        Optional<Risiko> risiko = risikoDB.findById(idRisiko);
        if (risiko.isPresent()) return risiko.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<Risiko> getAll() {
        return risikoDB.findAllByStatus(Risiko.Status.AKTIF);
    }

    @Override
    public Risiko ubahRisiko(int idRisiko, Risiko risiko) {
        Risiko target = getById(idRisiko);
        target.setChildList(risiko.getChildList());
        target.setSop(risiko.getSop());
        target.setRisikoKategori(risiko.getRisikoKategori());
        target.setParent(risiko.getParent());
        target.setNamaRisiko(risiko.getNamaRisiko());
        target.setKomponen(risiko.getKomponen());
        return risikoDB.save(target);
    }

    @Override
    public void hapusRisiko(int idRisiko) {
        risikoDB.deleteById(idRisiko);
    }

    @Override
    public Risiko nonaktifkanRisiko(int idRisiko) {
        Risiko target = getById(idRisiko);
        target.setStatus(Risiko.Status.NONAKTIF);
        return risikoDB.save(target);
    }

    @Override
    public Risiko aktifkanRisiko(int idRisiko) {
        Risiko target = getById(idRisiko);
        target.setStatus(Risiko.Status.AKTIF);
        return risikoDB.save(target);
    }

    @Override
    public Risiko transformasidto(Risiko risiko, RisikoDTO risikoDTO) {
        risiko.setChildList(risikoDTO.getChild());
        risiko.setKomponen(risikoDTO.getKomponen());
        risiko.setRisikoKategori(risikoDTO.getKategori());
        risiko.setNamaRisiko(risikoDTO.getNama());
        if (risikoDTO.getParent() != null) {
            risiko.setParent(getById(risikoDTO.getId()));
        } else {
            risiko.setParent(null);
        }
        try {
            SOP sop = sopRestService.getById(risikoDTO.getSop());
            risiko.setSop(sop);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "SOP dengan ID " + risikoDTO.getSop() + " tidak ditemukan!"
            );
        }
        return risiko;
    }

    @Override
    public List<Risiko> getByKategori(Integer kategori) {
        return risikoDB.findAllByRisikoKategoriAndStatus(kategori, Risiko.Status.AKTIF);
    }

    @Override
    public boolean isExistInDatabase(Risiko risiko) {
        return risikoDB.findById(risiko.getIdRisiko()).isPresent();
    }

    @Override
    public RisikoDTO ubahHierarki(Risiko risikoAwal, RisikoDTO risikoBaru) {
        risikoAwal.setParent(getById(risikoBaru.getParent()));
        risikoDB.save(risikoAwal);
        return risikoBaru;
    }

    @Override
    public List<Risiko> getParentByKategori(Integer kategori) {
        if (kategori == 2) {
            return getByKategori(1);
        } else if (kategori == 3) {
            return getByKategori(2);
        } else {
            throw new NoSuchElementException("");
        }
    }

    @Override
    public Risiko validateExistById(int idRisiko) {
        Optional<Risiko> target = risikoDB.findById(idRisiko);
        if (target.isPresent()) {
            return target.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "ID Risiko " + idRisiko + " Tidak Ditemukan"
            );
        }
    }
}
