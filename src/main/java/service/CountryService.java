package service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import controller.CountryController;
import model.Country;

@Named
@ApplicationScoped
public class CountryService {
    private final EntityManagerFactory em_FACTORY = Persistence.createEntityManagerFactory("dvdrent");
	static Logger logger = Logger.getLogger(CountryService.class);

    public List<Country> findAllCountries() {
        EntityManager entityManager = em_FACTORY.createEntityManager();
        try {
            TypedQuery<Country> query = entityManager.createQuery("SELECT c FROM Country c", Country.class);
            return query.getResultList();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    public Country findCountryById(int countryId) {
    	  logger.info("Searching for country with ID: " + countryId);
        EntityManager entityManager = em_FACTORY.createEntityManager();
        Country country = entityManager.find(Country.class, countryId);
        if (country != null) {
        	  logger.info("Country found: " + country.getCountry());
        } else {
        	  logger.info("Country not found with ID: " + countryId);
        }
        entityManager.close();
        return country;
    }


    public void addCountry(Country country) {
        EntityManager entityManager = em_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(country);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    

    
    public void updateCountry(Country country) {
        EntityManager entityManager = em_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(country); // Menggunakan merge untuk menyimpan perubahan pada entitas yang ada
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    
    public void deleteCountry(Country country) {
        EntityManager entityManager = em_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            // Merge the entity to ensure it's in the persistence context before removal
            Country managedCountry = entityManager.merge(country);
            entityManager.remove(managedCountry);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    
    @Transactional // Anotasi ini diperlukan agar operasi hapus berjalan dalam transaksi
    public void deleteCountry(int countryId) {
        EntityManager entityManager = em_FACTORY.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Country country = entityManager.find(Country.class, countryId);
            if (country != null) {
                entityManager.remove(country); // Menghapus negara
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    
    

    // Metode lain seperti updateCountry, deleteCountry, dll. juga dapat ditambahkan di sini
}
