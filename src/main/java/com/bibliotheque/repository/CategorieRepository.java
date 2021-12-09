package com.bibliotheque.repository;

import com.bibliotheque.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;
import java.util.List;

@RepositoryRestResource
@Transactional
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    public Categorie findByCode(String code);
    public List<Categorie> findByLabelContaining(String label);
}
