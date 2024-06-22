package api;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import model.City;
import model.Country;
import service.CityService;
import service.CountryService;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CityAPI {
    @Inject
    private CityService cityService;
    @Inject
	private CountryService countryService;

    @GET
    public List<City> getCities() {
        return cityService.findAllCities();
    }

    @GET
    @Path("/{cityId}")
    public Response getCityById(@PathParam("cityId") int cityId) {
        City city = cityService.findCityById(cityId);
        if (city != null) {
            return Response.ok(city).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("City not found").build();
        }
    }

    @POST
    public Response addCity(City city) {
		Country country = countryService.findCountryById(city.getCountry().getCountryId());
		city.setCountry(country);
        cityService.addCity(city);
        return Response.status(Response.Status.CREATED).entity("City added successfully").build();
    }

    @PUT
    @Path("/{cityId}")
    public Response updateCity(@PathParam("cityId") int cityId, City city) {
        city.setCityId(cityId);
        cityService.updateCity(city);
        return Response.ok().entity("City updated successfully").build();
    }

    @DELETE
    @Path("/{cityId}")
    public Response deleteCity(@PathParam("cityId") int cityId) {
        cityService.deleteCity(cityId);
        return Response.ok().entity("City deleted successfully").build();
    }
}
