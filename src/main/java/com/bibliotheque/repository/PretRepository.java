package com.bibliotheque.repository;

import com.bibliotheque.model.Client;
import com.bibliotheque.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RepositoryRestResource
//@CrossOrigin(origins = "http://localhost:4200")
@Transactional
public interface PretRepository extends JpaRepository<Pret, Long> {

    public List<Pret> findByClient(Client client);
    public Pret findByDateDebut(Date dateDebut);
    public List<Pret> findByDateDebutLessThanEqual(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date);
}
