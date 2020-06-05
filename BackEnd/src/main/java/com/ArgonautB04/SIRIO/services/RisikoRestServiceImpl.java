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
import java.util.ArrayList;
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
    public Risiko buatRisiko(final Risiko risiko) {
        risiko.setStatus(Risiko.Status.AKTIF);
        return risikoDB.save(risiko);
    }

    @Override
    public Risiko getById(final int idRisiko) {
        Optional<Risiko> risiko = risikoDB.findById(idRisiko);
        if (risiko.isPresent()) {
            return risiko.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Risiko> getAll() {
        return risikoDB.findAllByStatus(Risiko.Status.AKTIF);
    }

    @Override
    public Risiko ubahRisiko(final int idRisiko,
                             final Risiko risiko) {
        Risiko target = getById(idRisiko);
        List<Risiko> empty = new ArrayList<>();
        if (target.getRisikoKategori().
                equals(risiko.getRisikoKategori())) {
            for (Risiko r : risiko.getChildList()) {
                r.setParent(null);
            }
            target.setChildList(empty);
            target.setParent(null);
        } else {
            target.setChildList(risiko.getChildList());
            target.setParent(risiko.getParent());
        }
        target.setSop(risiko.getSop());
        target.setNamaRisiko(risiko.getNamaRisiko());
        target.setRisikoKategori(risiko.getRisikoKategori());
        if (risiko.getRisikoKategori() == 3) {
            if (risiko.getDetailUraian() != null) {
                target.setDetailUraian(risiko.getDetailUraian());
            }
            if (risiko.getDeskripsi() != null) {
                target.setDeskripsi(risiko.getDeskripsi());
            }
            if (risiko.getMetodologi() != null) {
                target.setMetodologi(risiko.getMetodologi());
            }
            if (risiko.getKetentuanSampel() != null) {
                target.setKetentuanSampel(risiko.getKetentuanSampel());
            }
        } else {
            target.setDeskripsi(null);
            target.setMetodologi(null);
            target.setKetentuanSampel(null);
            target.setDetailUraian(null);
        }
        return risikoDB.save(target);
    }

    @Override
    public void hapusRisiko(final int idRisiko) {
        Risiko risk = getById(idRisiko);
        if (risk.getChildList().isEmpty()) {
            risikoDB.delete(risk);
        } else {
            for (Risiko risiko : risk.getChildList()) {
                risiko.setParent(null);
            }
            risikoDB.delete(risk);
        }
    }

    @Override
    public Risiko nonaktifkanRisiko(final int idRisiko) {
        Risiko target = getById(idRisiko);
        target.setStatus(Risiko.Status.NONAKTIF);
        return risikoDB.save(target);
    }

    @Override
    public Risiko transformasidto(final Risiko risiko,
                                  final RisikoDTO risikoDTO) {
        if (risikoDTO.getKategori() == 3) {
            if (risikoDTO.getDetailUraian() != null) {
                risiko.setDetailUraian(risikoDTO.getDetailUraian());
            }
            if (risikoDTO.getDeskripsi() != null) {
                risiko.setDeskripsi(risikoDTO.getDeskripsi());
            }
            if (risikoDTO.getMetodologi() != null) {
                risiko.setMetodologi(risikoDTO.getMetodologi());
            }
            if (risikoDTO.getKetentuanSampel() != null) {
                risiko.setKetentuanSampel(risikoDTO.getKetentuanSampel());
            }
        } else {
            risiko.setDetailUraian(null);
            risiko.setKetentuanSampel(null);
            risiko.setMetodologi(null);
            risiko.setDeskripsi(null);
        }
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
                    HttpStatus.NOT_FOUND,
                    "SOP dengan ID " + risikoDTO.getSop() + " tidak ditemukan!"
            );
        }
        return risiko;
    }

    @Override
    public List<Risiko> getByKategori(final Integer kategori) {
        return risikoDB.findAllByRisikoKategoriAndStatus(
                kategori, Risiko.Status.AKTIF);
    }

    @Override
    public boolean isExistInDatabase(final Risiko risiko) {
        return risikoDB.findById(risiko.getIdRisiko()).isPresent();
    }

    @Override
    public RisikoDTO ubahHierarki(final Risiko risikoAwal,
                                  final RisikoDTO risikoBaru) {
        risikoAwal.setParent(getById(risikoBaru.getParent()));
        risikoDB.save(risikoAwal);
        return risikoBaru;
    }
//
//    @Override
//    public List<Risiko> getParentByKategori(Integer kategori) {
//        if (kategori == 2) {
//            return getByKategori(1);
//        } else if (kategori == 3) {
//            return getByKategori(2);
//        } else {
//            throw new NoSuchElementException("");
//        }
//    }

    @Override
    public Risiko validateExistById(final int idRisiko) {
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
