package io.tapack.satisfy.rest.fake;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/xml")
public class RestXmlService {

    private static UserStorage userStorage = new UserStorage();

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/all-users")
    public List<User> getAllUsers() {
        return userStorage.getUsers();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/single-user")
    public User getUserById(@QueryParam("id") int id) {
        return userStorage.findUserById(id);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/single-user")
    public void deleteUserById(@QueryParam("id") int id) {
        userStorage.deleteUserById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/single-user")
    public void addUser(@FormParam("id") int id,
                        @FormParam("firstName") String firstName,
                        @FormParam("lastName") String lastName,
                        @FormParam("email") String email) {
        userStorage.addUser(id, firstName, lastName, email);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/single-user")
    public void updateUserById(@QueryParam("id") long id,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName) {
        userStorage.updateUserById(id, firstName, lastName);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/single-user-by-xml")
    public void updateUserById(User user) {
        userStorage.updateUserById(user.getId(), user.getFirstName(), user.getLastName());
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/single-user-by-xml")
    public User addUser(User user, @HeaderParam("token") String token, @HeaderParam("super-token") String superToken) {
        if ("{1a2b3c49d8e76f23f4d65a87}".equals(superToken)
                && "12345678".equals(token) && user != null) {
            userStorage.addUser(user);
        }
        return user;
    }

}