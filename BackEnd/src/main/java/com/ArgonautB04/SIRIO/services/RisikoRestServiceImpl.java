package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.repository.RisikoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RisikoRestServiceImpl implements RisikoRestService {

    @Autowired
    private RisikoDB risikoDB;

    @Override
    public Risiko buatRisiko(Risiko risiko) {
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
        return risikoDB.findAll();
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
}
