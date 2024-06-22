package api;


import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Country;
import service.CountryService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetCountry {
    @Inject
    private CountryService countryService;

    @GET
    public List<Country> getCountries() {
        return countryService.findAllCountries();
    }

    @GET
    @Path("/{countryId}")
    public Response getCountryById(@PathParam("countryId") int countryId) {
        Country country = countryService.findCountryById(countryId);
        if (country != null) {
            return Response.ok(country).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Country not found ").build();
        }
    }

    @POST
    public Response addCountry(Country country) {
        countryService.addCountry(country);
        return Response.status(Response.Status.CREATED).entity("Country added successfully ").build();
    }

    @PUT
    @Path("/{countryId}")
    public Response updateCountry(@PathParam("countryId") int countryId, Country country) {
        country.setCountryId(countryId);
        countryService.updateCountry(country);
        return Response.ok().entity("Country updated successfully ").build();
    }

    @DELETE
    @Path("/{countryId}")
    public Response deleteCountry(@PathParam("countryId") int countryId) {
        countryService.deleteCountry(countryId);
        return Response.ok().entity("Country deleted successfully ").build();
    }
    
}
