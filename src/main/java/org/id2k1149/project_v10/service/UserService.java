package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.model.Role;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.model.Voter;
import org.id2k1149.project_v10.repo.UserRepo;
import org.id2k1149.project_v10.exception.*;
import org.id2k1149.project_v10.repo.VoterRepo;
import org.id2k1149.project_v10.to.UserVotesTo;
import org.id2k1149.project_v10.to.VoterTo;
import org.id2k1149.project_v10.util.VoterUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final VoterRepo voterRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User was not found in DB");
            throw new UsernameNotFoundException("User was not found in DB");
        } else {
            log.info("User was found in DB: {}", username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
                );
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("Find all users in DB");
        return userRepo.findAll();
    }

    public User getUser(Long id) {
        if (userRepo.findById(id).isPresent()) {
            log.info("Find user {} in DB", userRepo.getById(id).getUsername());
            return userRepo.getById(id);
        } else {
            log.error("User with id {} does not exist in DB", id);
            throw new NotFoundException(id + " does not exist");
        }
    }

    public User addUser(User user) {
        Optional<User> optionalUser = Optional.ofNullable(userRepo.findByUsername(user.getUsername()));
        if (optionalUser.isPresent()) {
            log.error("The name {} is already used", user.getUsername());
            throw new BadRequestException("The name " + user.getUsername() + " is already used");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        userRepo.save(user);
        log.info("Add a new user {} to DB", user.getUsername());
        return user;
    }

    @Transactional
    public void updateUser(User user,
                           Long id) {
        User userToUpdate;
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            log.error("User with id {} does not exist in DB", id);
            throw new NotFoundException(
                    "user with id " + id + " does not exist");
        } else {
            userToUpdate = optionalUser.get();
        }

        String newName = user.getUsername();
        if (newName != null && newName.length() > 0 && !Objects.equals(userToUpdate.getUsername(), newName)) {
            userToUpdate.setUsername(newName);
        }

        String newPassword = user.getPassword();
        if (newPassword != null && newPassword.length() > 5) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            userToUpdate.setPassword(encodedPassword);
        }

        Role newRole = user.getRole();
        if (newRole != null) {
            userToUpdate.setRole(newRole);
        }

        userRepo.save(userToUpdate);
        log.info("User {} was updated", user.getUsername());
    }

    public void deleteUser(Long id) {
        if(userRepo.findById(id).isEmpty()) {
            log.error("User with id {} does not exist in DB", id);
            throw new NotFoundException(id + " does not exists");
        }
        userRepo.deleteById(id);
        log.info("User {} was deleted", userRepo.getById(id).getUsername());
    }

    public User findUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        log.info("User with name {} was found in DB", username);
        return userRepo.findByUsername(username);
    }

    public UserVotesTo getUserAllVotes(Long id) {
        User user = getUser(id);
        List<Voter> voterList = voterRepo.findAllByUser(user);
        List<VoterTo> voterToList = VoterUtil.getVoterTo(user, voterList);
        return new UserVotesTo(id, user.getUsername(), voterToList);
    }
}