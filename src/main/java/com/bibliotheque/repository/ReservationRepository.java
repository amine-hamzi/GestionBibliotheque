package com.bibliotheque.repository;

import com.bibliotheque.model.Client;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.format.annotation.DateTimeFormat;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RepositoryRestController
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public List<Reservation> findByClient(Client client);
    public List<Reservation> findByLivre(Livre livre);
    public List<Reservation> findByDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date);

}