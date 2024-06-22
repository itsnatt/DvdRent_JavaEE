package controller;

import model.Address;
import service.AddressService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.jboss.logging.Logger;
import model.City;
import service.CityService;

@Named("addressController")
@ViewScoped
public class AddressController implements Serializable {

	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(AddressController.class);

	@Inject
	private AddressService addressService;
	@Inject
	private CityService cityService;

	private Address newAddress = new Address();
	private List<Address> addresses;
	private Address selectedAddress;
	private int cityId;
	private int adressid;

	@PostConstruct
	public void init() {
		addresses = addressService.findAll();
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public Address getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(Address newAddress) {
		this.newAddress = newAddress;
	}

	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCityId() {
		return cityId;
	}

	public int getAdressid() {
		return adressid;
	}

	public void setAdressid(int adressid) {
		this.adressid = adressid;
	}

	public void prepareAdd() {
		logger.info("prepareAdd - ");
		newAddress = new Address();
	}

	public void addAddress() {
		logger.info("addAddress - " + newAddress.getAddress() + newAddress.getDistrict() + newAddress.getPostalCode()
				+ newAddress.getCity());

		newAddress.setCity(cityService.findCityById(cityId));
		addressService.addAddress(newAddress);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Address added successfully."));
		newAddress = new Address();
		addresses = addressService.findAll();

	}

	// ;
	public void prepareEdit(Address address) {
		logger.info("prepareEdit - " + address);
		newAddress = address;
		cityId = newAddress.getCity().getCityId();
	}

	public void editAddress() {
		logger.info("editAddress - ");
		newAddress.setCity(cityService.findCityById(cityId));
		addressService.edit(newAddress);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Address edited successfully."));
		addresses = addressService.findAll();
	}

	public void prepareDelete(Address address) {
		logger.info("prepareDelete - ");
		newAddress = address;
	}

	public void deleteAddress() {
		logger.info("rowdelet - " + newAddress.getAddressId());
		addressService.delete(newAddress.getAddressId());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Address deleted successfully."));
		addresses = addressService.findAll();

	}

}