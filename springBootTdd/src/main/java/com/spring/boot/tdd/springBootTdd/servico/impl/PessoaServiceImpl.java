package com.spring.boot.tdd.springBootTdd.servico.impl;

import com.spring.boot.tdd.springBootTdd.exception.TelefoneNaoEncontradoException;
import com.spring.boot.tdd.springBootTdd.exception.UnicidadeCpfException;
import com.spring.boot.tdd.springBootTdd.model.Pessoa;
import com.spring.boot.tdd.springBootTdd.model.Telefone;
import com.spring.boot.tdd.springBootTdd.repository.PessoaRepository;
import com.spring.boot.tdd.springBootTdd.servico.PessoaService;
import com.spring.boot.tdd.springBootTdd.exception.UnicidadeTelefoneException;

import java.util.List;
import java.util.Optional;

public class PessoaServiceImpl implements PessoaService {
    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
        Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());

        if(optional.isPresent()){
            throw new UnicidadeCpfException();
        }

        final List<Telefone> telefones = pessoa.getTelefones();
        for (Telefone telefone:telefones) {
            optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());

            if(optional.isPresent()){
                throw new UnicidadeTelefoneException();
            }
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
        final Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
        return optional.orElseThrow(() -> new TelefoneNaoEncontradoException());
    }
}
