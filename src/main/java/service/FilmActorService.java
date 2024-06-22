package service;

import model.FilmActor;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Named
@ApplicationScoped
public class FilmActorService {

    private EntityManager em;

    public FilmActorService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent");
        this.em = emf.createEntityManager();
    }

    public List<FilmActor> findByActorId(short actorId) {
        return em.createQuery("SELECT fa FROM FilmActor fa WHERE fa.id.actorId = :actorId", FilmActor.class)
                .setParameter("actorId", actorId)
                .getResultList();
    }

    public List<Short> findFilmIdsByActorId(short actorId) {
        return em.createQuery("SELECT fa.id.filmId FROM FilmActor fa WHERE fa.id.actorId = :actorId", Short.class)
                .setParameter("actorId", actorId)
                .getResultList();
    }

    public void addFilmActor(FilmActor filmActor) {
        em.getTransaction().begin();
        em.persist(filmActor);
        em.getTransaction().commit();
    }
    
}