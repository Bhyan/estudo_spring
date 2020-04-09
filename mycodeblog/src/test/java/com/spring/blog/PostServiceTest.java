package com.spring.blog;

import com.spring.blog.exception.PostException;
import com.spring.blog.model.Post;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.service.BlogService;
import com.spring.blog.service.serviceImpl.BlogServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PostServiceTest {

    private static final String TITULO = "Post teste";
    private static final String AUTOR = "Autor teste";
    private static final String TEXTO = "Texto teste";
    private static final Long ID = 1L;

    private BlogService blogService;

    @MockBean
    private BlogRepository blogRepository;

    private Post post;

    @Before
    public void setUp() throws Exception {
        blogService = new BlogServiceImpl(blogRepository);

        post = new Post(TITULO, AUTOR, TEXTO);
    }

    @Test
    public void salvarPost() throws Exception {
        blogRepository.save(post);

        Mockito.verify(blogRepository).save(post);
    }

    @Test(expected = Exception.class)
    public void testeSalvarPostSemTituloException() throws Exception {
        post.setTitulo(null);
        Mockito.when(blogRepository.save(post)).thenThrow(Exception.class);

        blogRepository.save(post);
    }

    @Test(expected = Exception.class)
    public void testeSalvarPostSemTextoException() throws Exception {
        post.setTexto(null);
        Mockito.when(blogRepository.save(post)).thenThrow(Exception.class);

        blogRepository.save(post);
    }

    @Test(expected = Exception.class)
    public void testeSalvarPostSemAutorException() throws Exception {
        post.setAutor(null);
        Mockito.when(blogRepository.save(post)).thenThrow(Exception.class);

        blogRepository.save(post);
    }

    @Test
    public void testeBuscaPostPorId() throws Exception {
        Mockito.when(blogRepository.findById(ID)).thenReturn(Optional.of(post));

        Post postTeste = blogService.findById(ID);

        Mockito.verify(blogRepository).findById(ID);

        assertThat(postTeste).isNotNull();
        assertThat(postTeste.getAutor()).isEqualTo(AUTOR);
        assertThat(postTeste.getTexto()).isEqualTo(TEXTO);
        assertThat(postTeste.getTitulo()).isEqualTo(TITULO);
    }

}
