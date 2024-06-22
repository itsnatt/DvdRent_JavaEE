package service;

import model.Film;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Named
@ApplicationScoped
public class FilmService {

    private EntityManager em;

    public FilmService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent");
        this.em = emf.createEntityManager();
    }

    public Film findById(int id) {
        return em.find(Film.class, id);
    }

    // Method to retrieve all films
    public List<Film> findAll() {
        return em.createQuery("SELECT f FROM Film f", Film.class).getResultList();
    }

    // Method to add a new Film
    public void addFilm(Film film) {
        em.getTransaction().begin();
        em.persist(film);
        em.getTransaction().commit();
    }

    // Optionally, if you need to set EntityManager from outside
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
