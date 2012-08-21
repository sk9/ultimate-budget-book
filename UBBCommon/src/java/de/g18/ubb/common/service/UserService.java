package de.g18.ubb.common.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.g18.ubb.common.domain.User;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserService {

    public static final String RESTFUL_SERVICE_NAME = "UserService";
    public static final String AUTHENTIFICATION_TEST_PATH = RESTFUL_SERVICE_NAME + "/isAuthenticated";

    @GET
    List<User> getAll();

    @PUT
    User saveAndLoad(User aEntity);

    @GET
    @Path("isAuthenticated")
    boolean isAuthenticated();
}
