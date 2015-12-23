package com.vs.service.repo;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public interface UserRepository extends MongoRepository<User, String>{

    public List<User> findByFirstName(String firstName);
    public List<User> findByFirstNameAndRole(String firstName, Role role);
    public List<User> findByLastNameAndRole(String firstName, Role role);
    public List<User> findByLastName(String lastName);
    public User findByKitchenName(String kitchenName);
    public int countByKitchenName(String name);
    public int countByLastName(String name);
    public int countByFirstName(String name);
    public int countByLastNameAndRole(String name, Role role);
    public int countByFirstNameAndRole(String name, Role role);
    public int countByRole(String roleName);
    public List<User> findByRole(String roleName);
    public boolean exists(String kitchenName);
    public User findByUserName(String userName);
}
