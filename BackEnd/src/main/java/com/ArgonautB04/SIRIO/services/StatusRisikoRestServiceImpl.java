package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRisiko;
import com.ArgonautB04.SIRIO.repository.StatusRisikoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class StatusRisikoRestServiceImpl implements StatusRisikoRestService {

    @Autowired
    private StatusRisikoDB statusRisikoDB;

    @Override
    public StatusRisiko buatStatusRisiko(StatusRisiko statusRisiko) {
        return statusRisikoDB.save(statusRisiko);
    }

    @Override
    public StatusRisiko getById(int idStatus) {
        Optional<StatusRisiko> statusRisiko = statusRisikoDB.findById(idStatus);
        if (statusRisiko.isPresent()) return statusRisiko.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<StatusRisiko> getAll() {
        return statusRisikoDB.findAll();
    }
}
