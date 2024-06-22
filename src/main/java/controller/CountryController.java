package controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Country;
import service.CountryService;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;



@Named
@SessionScoped
public class CountryController implements Serializable {
    private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger(CountryController.class);

   //add
    @Inject
    private CountryService countryService;

    private List<Country> countries;
    private Country newCountry = new Country();
    
    //find county
    private int countryIdTo; 
    public int getCountryIdTo() {
        return countryIdTo;
    }
    public void setCountryIdTo(int countryIdTo) {
        this.countryIdTo = countryIdTo;
    }
    
    private Country fetchedCountry; 
    public Country getFetchedCountry() {
        return fetchedCountry;
    }
    public void setFetchedCountry(Country fetchedCountry) {
        this.fetchedCountry = fetchedCountry;
    }
    
    public void fetchCountryById() {
        logger.info("Fetching country with ID: " + countryIdTo);
        fetchedCountry = countryService.findCountryById(countryIdTo);
        if (fetchedCountry != null) {
        	  logger.info("Country found: " + fetchedCountry.getCountry());
        } else {
        	  logger.info("Country not found with ID: " + countryIdTo);
        }
    }
    

    public void saveEditedCountry() {
        try {
            countryService.updateCountry(fetchedCountry);
            fetchedCountry = null; 
            countries = countryService.findAllCountries();
            countryIdTo = 0;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukses", "Negara berhasil diperbarui"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal", "Gagal memperbarui negara: " + e.getMessage()));
        }
    }

 
    
    public void deleteCountryById() {
        try {
            countryService.deleteCountry(countryIdTo);
            fetchedCountry = null; 
            countries = countryService.findAllCountries();
            countryIdTo = 0;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukses", "Negara berhasil dihapus"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal", "Gagal menghapus negara: " + e.getMessage()));
        }
    }
    //
    
    
    @PostConstruct
    public void init() {
        countries = countryService.findAllCountries();
    }

    public List<Country> getCountries() {
        logger.info("gostt");
        return countries;

    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Country getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(Country newCountry) {
        this.newCountry = newCountry;
    }

    public Country findCountryById(int countryId) {
        return countryService.findCountryById(countryId);
    }

    public void addCountry() {
        countryService.addCountry(newCountry);
        countries = countryService.findAllCountries(); // Refresh daftar negara
        newCountry = new Country(); // Clear form input setelah menambahkan
    }
    public void rfshs()
    {
        countries = countryService.findAllCountries(); // Refresh daftar negara
    }
    

}
