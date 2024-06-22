package service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import model.City;

@Named
@ApplicationScoped
public class CityService {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent");
    private EntityManager em = emf.createEntityManager();

    public List<City> findAllCities() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    public void addCity(City city) {
        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();
    }

    public void deleteCity(int cityId) {
        City city = em.find(City.class, cityId);
        if (city != null) {
            em.getTransaction().begin();
            em.remove(city);
            em.getTransaction().commit();
        }
    }

    public void updateCity(City city) {
        em.getTransaction().begin();
        em.merge(city);
        em.getTransaction().commit();
    }

    public City findCityById(int cityId) {
        return em.find(City.class, cityId);
    }
}
