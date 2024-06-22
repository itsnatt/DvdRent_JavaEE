package controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Address;
import model.City;
import model.Country;

@Named("addressAPIController")
@ViewScoped
public class AddressAPIController implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(AddressAPIController.class);

    private List<Address> addresses;

    @PostConstruct
    public void init() {
        fetchAddressesFromAPI();
    }

    private void fetchAddressesFromAPI() {
        addresses = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("http://127.0.0.1:8080/fathan-0.0.1-SNAPSHOT/rest/addresses");
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String jsonResponse = EntityUtils.toString(entity);
                    addresses = parseAddresses(jsonResponse);
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            logger.error("Error fetching addresses from API: " + e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("Error closing HttpClient: " + e.getMessage());
            }
        }
    }

    private List<Address> parseAddresses(String jsonResponse) {
        List<Address> addresses = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Address address = new Address();
                address.setAddressId(jsonObject.getInt("addressId"));
                address.setAddress(jsonObject.getString("address"));
                address.setDistrict(jsonObject.getString("district"));
                address.setPostalCode(jsonObject.getString("postalCode"));
                address.setPhone(jsonObject.getString("phone"));

                JSONObject cityJson = jsonObject.getJSONObject("city");
                City city = new City();
                city.setCityId(cityJson.getInt("cityId"));
                city.setCity(cityJson.getString("city"));

                JSONObject countryJson = cityJson.getJSONObject("country");
                Country country = new Country();
                country.setCountryId(countryJson.getInt("countryId"));
                country.setCountry(countryJson.getString("country"));

                city.setCountry(country);
                address.setCity(city);

                addresses.add(address);
            }
        } catch (JSONException e) {
            logger.error("Error parsing JSON response: " + e.getMessage());
        }
        return addresses;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    // Your existing code

    public void addAddress(Address newAddress) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost("http://127.0.0.1:8080/fathan-0.0.1-SNAPSHOT/rest/addresses");
            
            // Log the data that will be posted
            logger.info("Data to be posted: " + newAddress.toJSON());
            
            StringEntity params = new StringEntity(newAddress.toJSON());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            CloseableHttpResponse response = httpClient.execute(request);
            try {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Address added successfully."));
                    // Fetch addresses again after adding a new one
                    fetchAddressesFromAPI();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to add address."));
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            logger.error("Error adding address: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to add address."));
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("Error closing HttpClient: " + e.getMessage());
            }
        }
    }


    // Add other methods for CRUD operations using HttpClient

}
