package service;

import model.Customer;
import model.Staff;
// Import model.Staff

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Named
@ApplicationScoped
public class AuthenticationService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent");
    private EntityManager em = emf.createEntityManager();

    @Transactional
    public Object authenticate(String email, String password) {
        // First, try to authenticate as Customer
        TypedQuery<Customer> customerQuery = em.createQuery(
                "SELECT c FROM Customer c WHERE c.email = :email AND c.email = :password", Customer.class);
        customerQuery.setParameter("email", email);
        customerQuery.setParameter("password", email);

        try {
            return customerQuery.getSingleResult();
        } catch (Exception e) {
            // If no Customer is found, try to authenticate as Staff
            TypedQuery<Staff> staffQuery = em.createQuery(
                    "SELECT s FROM Staff s WHERE s.email = :email AND s.password = :password", Staff.class);
            staffQuery.setParameter("email", email);
            staffQuery.setParameter("password", password);

            try {
                return staffQuery.getSingleResult();
            } catch (Exception ex) {
                // Log error or handle it as needed
                return null;
            }
        }
    }

    // Other methods if needed
}
