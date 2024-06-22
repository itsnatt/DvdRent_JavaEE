package controller;

import model.City;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class CityAPIController implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<City> cities;

    @PostConstruct
    public void init() {
        cities = fetchCitiesFromAPI();
    }

    public List<City> getCities() {
        return cities;
    }

    private List<City> fetchCitiesFromAPI() {
        List<City> cities = new ArrayList<>();
        String apiUrl = "http://127.0.0.1:8080/fathan-0.0.1-SNAPSHOT/rest/cities";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonCity = jsonArray.getJSONObject(i);
                        City city = new City();
                        city.setCityId(jsonCity.getInt("cityId"));
                        // You may need to adjust this part based on your City model
                        city.setCity(jsonCity.getString("city"));
                        cities.add(city);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        return cities;
    }
}

