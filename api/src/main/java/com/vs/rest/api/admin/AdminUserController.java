package com.vs.rest.api.admin;

import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.rest.api.user.UserController;
import com.vs.service.user.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 9/11/2016.
 */
@Component
@Path("/admn")
@Slf4j
@Api(value = "User Administration", description = "Admin User Controller")
public class AdminUserController extends UserController {

    @Autowired
    public AdminUserController(@Qualifier("adminService") IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserByUserName(@PathParam("userName") String userName) {
        return super.getUserByUserName(userName);
    }

    @GET
    @Path("/list/cooks")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCooks() {
        return super.listUsers(Role.COOK);
    }

    @GET
    @Path("/list/customers")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCustomers() {
        return super.listUsers(Role.CUSTOMER);
    }

    @GET
    @Path("/list/users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listAllUsers() {
        return super.listUsers();
    }

    @GET
    @Path("/find/cook/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findCookBySearchString(@PathParam("searchString") String searchString) {
        return super.findUserBySearchString(searchString, Role.COOK);
    }

    @GET
    @Path("/find/customer/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findCustomerBySearchString(@PathParam("searchString") String searchString) {
        return super.findUserBySearchString(searchString, Role.CUSTOMER);
    }


    @GET
    @Path("/find/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findUserBySearchString(@PathParam("searchString") String searchString) {
        return super.findUserBySearchString(searchString);
    }

    @GET
    @Path("cook/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCooksCount() {
        return super.getUserCount(Role.COOK);
    }


    @GET
    @Path("customer/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCustomerByFirstNamme(@PathParam("name") String name) {
        return super.findUserByFirstName(name, Role.CUSTOMER);
    }

    @GET
    @Path("cook/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCookByFirstNamme(@PathParam("name") String name) {
        return super.findUserByFirstName(name, Role.COOK);
    }


    @GET
    @Path("customer/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCustomersCount() {
        return super.getUserCount(Role.CUSTOMER);
    }


    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllUserCount() {
        return super.getAllUserCount();
    }

    @ApiOperation(value = "Disable User", nickname = "enableUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.PUT, path="/{userName}", produces = MediaType.APPLICATION_JSON)

    @PUT
    @Path("/{userName}/disable")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void disableUser(@PathParam("userName") String userName) throws Exception {
        super.enableOrDisableUser(userName, UserStatusEnum.INACTIVE);
    }


    @ApiOperation(value = "Enable User", nickname = "enableUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})

    @PUT
    @Path("/{userName}/enable")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void enableUser(@PathParam("userName") String userName) throws Exception {
        super.enableOrDisableUser(userName, UserStatusEnum.ACTIVE);
    }
}
