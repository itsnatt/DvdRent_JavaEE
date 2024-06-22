package api;

import model.Address;
import model.City;
import service.AddressService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import service.CityService;


@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class AddressApi {

	

    @Inject
    private AddressService addressService;
	@Inject
	private CityService cityService;

    @GET
    public List<Address> getAllAddresses() {
        return addressService.findAll();
    }
    
    @GET
    @Path("/{addressId}")
    public Response getAddressById(@PathParam("addressId") int addressId) {
    	Address address = addressService.findAddressById(addressId);
        if (address != null) {
            return Response.ok(address).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("address not found").build();
        }
    }
    
   

    @POST
    public Response addAddress(Address address) {
    	address.setCity(cityService.findCityById(address.getCity().getCityId()));
        addressService.addAddress(address);
        return Response.status(Response.Status.CREATED).entity("Address added successfully").build();
    }

    @PUT
    @Path("/{addressId}")
    public Response updateAddress(@PathParam("addressId") int addressId, Address address) {
        Address existingAddress = addressService.findAddressById(addressId);
        if (existingAddress == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Address not found").build();
        }
        address.setAddressId(addressId);
        addressService.edit(address);
        return Response.status(Response.Status.OK).entity("Address updated successfully").build();
    }

    @DELETE
    @Path("/{addressId}")
    public Response deleteAddress(@PathParam("addressId") int addressId) {
        Address address = addressService.findAddressById(addressId);
        if (address == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Address not found").build();
        }
        addressService.delete(addressId);
        return Response.status(Response.Status.OK).entity("Address deleted successfully").build();
    }
}