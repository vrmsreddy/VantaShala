package com.vs.rest.api.user;

import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.User;
import com.vs.props.ReadYML;
import com.vs.rest.api.BaseController;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GeetaKrishna on 12/14/2015.
 */

@Slf4j
public abstract class UserController extends BaseController {

    protected IUserService userService;

    @Autowired
    protected ReadYML readYML;

    public UserController(IUserService service) {
        this.userService = service;
    }

    // List Specific type of users
    public Response listUsers(Role role) {
        log.info(" Listing all cooks ");
        List<User> users = userService.listUsers(role);
        return buildResponse(users);
    }

    // List all Users
    public Response listUsers() {
        log.info(" Listing all cooks ");
        List<User> users = userService.listUsers();
        return buildResponse(users);
    }

    public Response findUserBySearchString(String search, Role role) {
        log.info(" Searching User based on: {}", search);
        List<User> users = userService.findUser(search, role);
        log.info("No of Users found: {}", users.size());
        return buildResponse(users);
    }

    public Response findUserBySearchString(String search) {
        log.info(" Searching User based on: {}", search);
        List<User> users = userService.findUser(search);
        log.info("No of Users found: {}", users.size());
        return buildResponse(users);
    }

    public Response getCookByKitchenName(String kitchenName) {
        log.info(" Retrieving User: {}", kitchenName);
        User user = userService.getUserByKitchenName(kitchenName);
        log.info("UserDetails : {}", user);
        return buildResponse(user);
    }

    public Response findUserByFirstName(String name, Role role) {
        log.info(" Searching User by FirstName : {}", name);

        if (role == Role.COOK) {
            List<User> users = userService.getCookByFirstName(name);
            return buildResponse(users);
        } else {
            List<User> users = userService.getCustomerByFirstName(name);
            return buildResponse(users);
        }
    }

    public Response getUserByUserName(String userName) {
        log.info(" Retrieving User: {}", userName);
        User user = userService.getUserByUserName(userName);
        log.info("UserDetails : {}", user);
        return buildResponse(user);
    }

    public Response createUser(User user) {

        log.info("Create User - User Details: {}", user);
        userService.createUser(user);

        return Response.status(200).build();

    }

    public void updateUser(String userName, User user) {
        log.info("Update User: {} - User Details: {}", userName, user);
        userService.updateUser(user);
    }

    public void enableOrDisableUser(String userName, UserStatusEnum userStatusEnum) throws Exception {

        log.info("Disabling User : {}", userName);
        userService.enableOrDisableUser(userName, userStatusEnum);
    }


    public Response addImages(String userName, FileUploadTypeEnum fileUploadTypeEnum, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            throw new Exception("Error: Form must has enctype=multipart/form-data.");
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        //factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // sets maximum size of upload file
        // upload.setFileSizeMax(MAX_FILE_SIZE);

        // sets maximum size of request (include file + form data)
        //upload.setSizeMax(MAX_REQUEST_SIZE);

        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = readYML.getFileUploadProperties(FileUploadTypeEnum.PROFILE_PICTURE, userName);

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // parses the request's content to extract file data
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);

                        // saves the file on disk
                        item.write(storeFile);
                        request.setAttribute("message",
                                "Upload has been done successfully!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Response.status(200).entity(getStatusMap("SUCCESS")).build();
    }

    private void saveFile(InputStream uploadedInputStream, String serverLocation) {

        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();

            uploadedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Response getAllUserCount() {
        long count = userService.getUserCount();
        return Response.status(200).entity(getCountMap(count)).build();
    }

    public Response getUserCount(Role role) {
        long count = userService.getUserCount(role);
        return Response.status(200).entity(getCountMap(count)).build();
    }

    private Map<String, Long> getCountMap(long count) {
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        return map;
    }

    private Map<String, String> getStatusMap(String count) {
        Map<String, String> map = new HashMap<>();
        map.put("status", count);
        return map;
    }
}
