package com.spring.boot.tdd.springBootTdd.servico;

import com.spring.boot.tdd.springBootTdd.exception.TelefoneNaoEncontradoException;
import com.spring.boot.tdd.springBootTdd.exception.UnicidadeCpfException;
import com.spring.boot.tdd.springBootTdd.exception.UnicidadeTelefoneException;
import com.spring.boot.tdd.springBootTdd.model.Pessoa;
import com.spring.boot.tdd.springBootTdd.model.Telefone;

public interface PessoaService {
    Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

    Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException;
}
