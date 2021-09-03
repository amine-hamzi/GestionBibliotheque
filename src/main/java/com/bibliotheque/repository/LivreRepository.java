package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public interface LivreRepository extends JpaRepository<Livre, Long> {
    public Livre findByIsbn(String isbn);
    public List<Livre> findByTitleContaining(String titre);
}
