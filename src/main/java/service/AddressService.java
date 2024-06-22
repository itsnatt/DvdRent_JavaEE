package service;

import model.Address;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

import controller.AddressController;

import java.util.List;

@Named
@ApplicationScoped
public class AddressService {

	static Logger logger = Logger.getLogger(AddressService.class);
	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent");
	private EntityManager em = emf.createEntityManager();

	public List<Address> findAll() {
//		return em.createQuery("SELECT a FROM Address a", Address.class).getResultList();
	return em.createNamedQuery("Address.findAll",Address.class).getResultList();
	
	
	
	}
	
	  public Address findAddressById(int addressId) {
	        return em.find(Address.class, addressId);
	    }

	public void addAddress(Address address) {
		logger.info("addAddress  - ");
		em.getTransaction().begin();
		em.persist(address); // New address
		em.getTransaction().commit();
	}

	public void delete(int addressId) {
		Address address = em.find(Address.class, addressId);
		if (address != null) {
			logger.info("delete  - ");

			em.getTransaction().begin();
			em.remove(address);
			em.getTransaction().commit();
		}
	}

	public void edit(Address address) {
		logger.info("edit - ");
		em.getTransaction().begin();
		em.merge(address); // Existing address
		em.getTransaction().commit();
	}
}