package controller;

import model.Address;
import model.City;
import service.CityService;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import service.CountryService;
import model.Country;

import java.io.Serializable;
import java.util.List;

@Named("cityController")
@ViewScoped
public class CityController implements Serializable {
	static Logger logger = Logger.getLogger(CityController.class);

	private static final long serialVersionUID = 1L;

	@Inject
	private CityService cityService;
	@Inject
	private CountryService countryService;
	private List<City> cities;
	private City newCity = new City();
	private int selectedCityId;
	private int countryId;

	@PostConstruct
	public void init() {
		cities = cityService.findAllCities();
	}

	public List<City> getCities() {
		return cities;
	}

	public City getNewCity() {
		return newCity;
	}

	public void setNewCity(City newCity) {
		this.newCity = newCity;
	}

	public int getSelectedCityId() {
		return selectedCityId;
	}

	public void setSelectedCityId(int selectedCityId) {
		this.selectedCityId = selectedCityId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public void prepareAdd() {
		logger.info("prepareAdd - ");
		newCity = new City();
	}

	public void addCity() {
		logger.info("addAddress -  countryid :" + countryId + " City: " + newCity.getCity() + "Country : ");

		Country country = countryService.findCountryById(countryId);
		newCity.setCountry(country);
		cityService.addCity(newCity);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "City added successfully."));
		newCity = new City();
		cities = cityService.findAllCities();
	}

	public void prepareDelete(City city) {
		logger.info("prepareDelete - ");
		newCity = city;
	}

	public void deleteCity() {
		logger.info("rowdelet - " + newCity.getCityId());
		cityService.deleteCity(newCity.getCityId());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "City deleted successfully."));
		cities = cityService.findAllCities();

	}

	public void prepareEdit(City city) {
		newCity = city;
		countryId = newCity.getCountry().getCountryId();
		logger.info("prepareEdit - " + newCity.getCity() + " x " + newCity.getCountry().getCountry() + " x " + countryId);
		

	}

	public void updateCity() {
		logger.info("updateCity - ");
		Country country = countryService.findCountryById(countryId);
		newCity.setCountry(country);
		cityService.updateCity(newCity);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "City updated successfully."));
		cities = cityService.findAllCities();
	}
	
	
	
	
}
