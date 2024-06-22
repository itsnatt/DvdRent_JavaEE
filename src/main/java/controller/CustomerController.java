package controller;

import model.Customer;
import service.AddressService;
import service.CustomerService;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import java.io.Serializable;
import java.util.List;

@Named("customerController")
@ViewScoped
public class CustomerController implements Serializable {
	static Logger logger = Logger.getLogger(CustomerController.class);
    private static final long serialVersionUID = 1L;

    @Inject
    private CustomerService customerService;

    private List<Customer> customers;
    private Customer newCustomer = new Customer();
    private Customer selectedCustomer;

    @PostConstruct
    public void init() {
        customers = customerService.findAll();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public void addCustomer() {
        customerService.addCustomer(newCustomer);
        newCustomer = new Customer(); // Clear the form after adding
    }

    public void prepareEdit(Customer customer) {
		logger.info("prepareEdit  - ");
        selectedCustomer = customer;
    }

    public void editCustomer() {
        customerService.edit(selectedCustomer);
    }

    public void prepareDelete(Customer customer) {
        selectedCustomer = customer;
    }

    public void deleteCustomer() {
        customerService.delete(selectedCustomer.getCustomerId());
        customers.remove(selectedCustomer);
        selectedCustomer = null; // Clear the selected customer after deletion
    }
}
