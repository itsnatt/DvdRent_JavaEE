package service;

import model.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

@Named
@ApplicationScoped
public class CustomerService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent");
    private final EntityManager em = emf.createEntityManager();

    public List<Customer> findAll() {
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
        return query.getResultList();
    }

    public void addCustomer(Customer customer) {
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }

    public void delete(int customerId) {
        Customer customer = em.find(Customer.class, customerId);
        if (customer != null) {
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
        }
    }

    public void edit(Customer customer) {
        em.getTransaction().begin();
        em.merge(customer);
        em.getTransaction().commit();
    }
}
