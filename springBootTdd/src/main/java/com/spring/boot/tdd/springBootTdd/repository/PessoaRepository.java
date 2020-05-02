package com.spring.boot.tdd.springBootTdd.repository;

import com.spring.boot.tdd.springBootTdd.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByCpf(String cpf);

    @Query("SELECT bean FROM Pessoa bean WHERE 1=1")
    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);
}
