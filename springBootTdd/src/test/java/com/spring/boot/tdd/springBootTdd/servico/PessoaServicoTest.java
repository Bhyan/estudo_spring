package com.spring.boot.tdd.springBootTdd.servico;

import com.spring.boot.tdd.springBootTdd.exception.TelefoneNaoEncontradoException;
import com.spring.boot.tdd.springBootTdd.exception.UnicidadeCpfException;
import com.spring.boot.tdd.springBootTdd.exception.UnicidadeTelefoneException;
import com.spring.boot.tdd.springBootTdd.model.Pessoa;
import com.spring.boot.tdd.springBootTdd.model.Telefone;
import com.spring.boot.tdd.springBootTdd.repository.PessoaRepository;
import com.spring.boot.tdd.springBootTdd.servico.impl.PessoaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PessoaServicoTest {

    private static final String NOME = "Teste";
    private static final String CPF = "12345678910";
    private static final String NUMERO = "123456789";
    private static final String DDD = "55";

    @MockBean
    private PessoaRepository pessoaRepository;

    private PessoaService pessoaService;

    private Pessoa pessoa;

    private Telefone telefone;

    @Before
    public void setUp() {
        pessoaService = new PessoaServiceImpl(pessoaRepository);

        telefone = new Telefone();
        telefone.setDdd(DDD);
        telefone.setNumero(NUMERO);

        pessoa = new Pessoa();
        pessoa.setNome(NOME);
        pessoa.setCpf(CPF);
        pessoa.setTelefones(Arrays.asList(telefone));
    }

    @Test
    public void deveSalvarPessoaRepository() throws Exception {
        pessoaService.salvar(pessoa);

        Mockito.verify(pessoaRepository).save(pessoa);
    }

    @Test(expected = UnicidadeCpfException.class)
    public void naoDeveSalvarDuasPessoasMesmoCPF() throws Exception {
        Mockito.when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

        pessoaService.salvar(pessoa);
    }

    @Test(expected = UnicidadeTelefoneException.class)
    public void naoDeveSalvarDuasPessoasMesmoTelefone() throws Exception {
        Mockito.when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

        pessoaService.salvar(pessoa);
    }

    @Test(expected = TelefoneNaoEncontradoException.class)
    public void deveRetornarExcecaoNaoExistePessoaDddTelefone() throws Exception {
        pessoaService.buscarPorTelefone(telefone);
    }

    @Test
    public void deveProcurarPessoaPeloDddTelefone() throws Exception {
        Mockito.when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
        Pessoa pessoaTeste = pessoaService.buscarPorTelefone(telefone);

        Mockito.verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

        assertThat(pessoaTeste).isNotNull();
        assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
        assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
    }
}