package service;

import model.Actor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Named
@ApplicationScoped
public class ActorService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent"); // Adjust the persistence unit name as needed
    private EntityManager em = emf.createEntityManager();

    
    public List<Actor> findAll() {
        return em.createQuery("SELECT a FROM Actor a", Actor.class).getResultList();
    }

    public void saveOrUpdate(Actor actor) {
        em.getTransaction().begin();
        if (actor.getActorId() == 0 || em.find(Actor.class, actor.getActorId()) == null) {
            em.persist(actor); // New actor
        } else {
            em.merge(actor); // Existing actor
        }
        em.getTransaction().commit();
    }

    public void delete(int actorId) {
        Actor actor = em.find(Actor.class, actorId);
        if (actor != null) {
            em.getTransaction().begin();
            em.remove(actor);
            em.getTransaction().commit();
        }
    }    
}
