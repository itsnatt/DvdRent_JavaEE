package service;

import model.Staff;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Named
@ApplicationScoped
public class StaffService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("dvdrent"); // Sesuaikan dengan nama unit persistensi Anda
    private EntityManager em = emf.createEntityManager();

    public List<Staff> findAll() {
        return em.createQuery("SELECT s FROM Staff s", Staff.class).getResultList();
    }

    public void saveOrUpdate(Staff staff) {
        em.getTransaction().begin();
        if (staff.getStaffId() == 0 || em.find(Staff.class, staff.getStaffId()) == null) {
            em.persist(staff); // Staff baru
        } else {
            em.merge(staff); // Staff yang sudah ada
        }
        em.getTransaction().commit();
    }

    public void delete(int staffId) {
        Staff staff = em.find(Staff.class, staffId);
        if (staff != null) {
            em.getTransaction().begin();
            em.remove(staff);
            em.getTransaction().commit();
        }
    }
}
