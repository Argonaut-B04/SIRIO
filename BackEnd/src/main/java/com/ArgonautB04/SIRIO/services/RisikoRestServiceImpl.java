package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.Risiko;
import com.ArgonautB04.SIRIO.repository.RisikoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class RisikoRestServiceImpl implements RisikoRestService {

    @Autowired
    private RisikoRepo risikoRepo;

    @Override
    public Risiko createRisiko(Risiko risiko) {
        return risikoRepo.save(risiko);
    }

    @Override
    public Risiko getById(int id_risiko) {
        Optional<Risiko> risiko = risikoRepo.findById(id_risiko);
        if (risiko.isPresent()) {
            return risiko.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Risiko updateRisiko(int id_risiko, Risiko risiko) {
        Risiko target = getById(id_risiko);
        target.setChildList(risiko.getChildList());
        target.setStatus_risiko(risiko.getStatus_risiko());
        target.setSop(risiko.getSop());
        target.setRisiko_kategori(risiko.getRisiko_kategori());
        target.setParent(risiko.getParent());
        target.setNama_risiko(risiko.getNama_risiko());
        target.setIndikator(risiko.getIndikator());
        return risikoRepo.save(target);
    }

    @Override
    public void deleteRisiko(int id_risiko) {
        risikoRepo.deleteById(id_risiko);
    }
}
