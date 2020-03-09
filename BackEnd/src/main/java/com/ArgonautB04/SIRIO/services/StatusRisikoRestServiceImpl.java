package com.ArgonautB04.SIRIO.services;

import com.ArgonautB04.SIRIO.model.StatusRisiko;
import com.ArgonautB04.SIRIO.repository.StatusRisikoRepo;
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
    private StatusRisikoRepo statusRisikoRepo;

    @Override
    public StatusRisiko createStatusRisiko(StatusRisiko statusRisiko) {
        return statusRisikoRepo.save(statusRisiko);
    }

    @Override
    public StatusRisiko getById(int id_status) {
        Optional<StatusRisiko> statusRisiko = statusRisikoRepo.findById(id_status);
        if (statusRisiko.isPresent()) return statusRisiko.get();
        else throw new NoSuchElementException();
    }

    @Override
    public List<StatusRisiko> getAll() {
        return statusRisikoRepo.findAll();
    }
}
