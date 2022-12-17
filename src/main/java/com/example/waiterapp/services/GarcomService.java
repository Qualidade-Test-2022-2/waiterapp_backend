package com.example.waiterapp.services;

import com.example.waiterapp.dto.GarcomDTO;
import com.example.waiterapp.exceptions.ObjectNotFoundException;
import com.example.waiterapp.models.Garcom;
import com.example.waiterapp.repositories.GarcomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GarcomService {

    private GarcomRepository garcomRepository;

    @Autowired
    public GarcomService(GarcomRepository garcomRepository) {
        this.garcomRepository = garcomRepository;
    }

    public boolean isWaiterAuthorized(Garcom garcom, String candidatePassword, String cpf) {
        var isValid = BCrypt.checkpw(candidatePassword, garcom.getPassword());
        var isCPFCorrect = Objects.equals(garcom.getCpf(), cpf);
        return isValid && isCPFCorrect;
    }

    public Garcom transformarDTO(GarcomDTO garcomDTO){
        Garcom garcom = new Garcom(garcomDTO.getId(), garcomDTO.getNome(), garcomDTO.getDataCriacao(), garcomDTO.getCpf());
        garcom.setPedidos(garcomDTO.getPedidos());
        garcom.setPassword(BCrypt.hashpw(garcomDTO.getPassword(), BCrypt.gensalt()));
        return garcom;
    }

    public List<Garcom> listaGarcons(){
        return garcomRepository.findAll();
    }

    public Garcom retornaGarcomById(Long idGarcom) throws ObjectNotFoundException {
        Optional<Garcom> garcom = garcomRepository.findById(idGarcom);
        return garcom.orElseThrow(() -> new ObjectNotFoundException("Objeto nao encontrado! ID: " + idGarcom + ", Tipo: " + Garcom.class.getName()));
    }

    public Garcom insereGarcom(Garcom garcom){
        garcom.setId(null);
        garcom.setDataCriacao(LocalDateTime.now());
        return garcomRepository.save(garcom);
    }

    public Garcom atualizaGarcom(Garcom garcom) throws ObjectNotFoundException{
        retornaGarcomById(garcom.getId());
        return garcomRepository.save(garcom);
    }

    public void apagaGarcom(Long idGarcom) throws DataIntegrityViolationException, ObjectNotFoundException{
        retornaGarcomById(idGarcom);
        try{
            garcomRepository.deleteById(idGarcom);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(("Não é possível excluir esse garcom"));
        }
    }

    public Garcom retornaGarcomByCpf(String cpf) {
        return garcomRepository.findByCpf(cpf).orElse(null);
    }
}
