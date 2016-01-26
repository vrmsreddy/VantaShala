package com.vs.repository;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByFirstName(String firstName);

    public List<User> findByFirstNameAndRole(String firstName, Role role);

    public List<User> findByLastNameAndRole(String firstName, Role role);

    public List<User> findByLastName(String lastName);

    public int countByLastName(String name);

    public int countByFirstName(String name);

    public int countByLastNameAndRole(String name, Role role);

    public int countByFirstNameAndRole(String name, Role role);

    public int countByRole(Role role);

    public List<User> findByRole(String roleName);

    public boolean exists(String userName);

    public List<User> findByUserName(String userName);

    public List<User> findByLastNameOrFirstNameOrUserNameAndRole(String lname, String fname, String uname, Role role);
}
