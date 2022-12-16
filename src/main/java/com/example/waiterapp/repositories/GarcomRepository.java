package com.example.waiterapp.repositories;

import com.example.waiterapp.models.Garcom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarcomRepository extends JpaRepository<Garcom, Long> {
    public Optional<Garcom> findByCpf(String cpf);
}
