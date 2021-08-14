package org.id2k1149.project_v10.service;

import org.id2k1149.project_v10.model.Role;
import org.id2k1149.project_v10.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
