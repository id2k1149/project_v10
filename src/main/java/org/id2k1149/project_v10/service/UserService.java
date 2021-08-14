package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.model.Role;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.repo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User was not found in DB");
            throw new UsernameNotFoundException("User was not found in DB");
        } else {
            log.info("User was found in DB: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Role role = user.getRole();
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
                );
    }

    public User saveUser(User user) {
        log.info("Saving new user {} to DB", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User getUser(String username) {
        log.info("Finding user {} in DB", username);
        return userRepo.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("Finding all users in DB ");
        return userRepo.findAll();
    }
}
