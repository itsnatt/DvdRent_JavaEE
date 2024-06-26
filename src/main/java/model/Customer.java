package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "customer", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
})
public class Customer implements java.io.Serializable {

    private int customerId;
    private Address address;
    private short storeId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activebool;
    private Date createDate;
    private Date lastUpdate;
    private Integer active;
    private Set<Rental> rentals = new HashSet<Rental>(0);
    private Set<Payment> payments = new HashSet<Payment>(0);

    public Customer() {
    }

    public Customer(int customerId, Address address, short storeId, String firstName, String lastName,
            boolean activebool, Date createDate) {
        this.customerId = customerId;
        this.address = address;
        this.storeId = storeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.activebool = activebool;
        this.createDate = createDate;
    }

    public Customer(int customerId, Address address, short storeId, String firstName, String lastName, String email,
            boolean activebool, Date createDate, Date lastUpdate, Integer active, Set<Rental> rentals,
            Set<Payment> payments) {
        this.customerId = customerId;
        this.address = address;
        this.storeId = storeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activebool = activebool;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.active = active;
        this.rentals = rentals;
        this.payments = payments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", unique = true, nullable = false)
    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "store_id", nullable = false)
    public short getStoreId() {
        return this.storeId;
    }

    public void setStoreId(short storeId) {
        this.storeId = storeId;
    }

    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "activebool", nullable = false)
    public boolean isActivebool() {
        return this.activebool;
    }

    public void setActivebool(boolean activebool) {
        this.activebool = activebool;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Column(name = "active")
    public Integer getActive() {
        return this.active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<Rental> getRentals() {
        return this.rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @PrePersist
    @PreUpdate
    private void updateTimestamp() {
        lastUpdate = new Date();
    }
}
