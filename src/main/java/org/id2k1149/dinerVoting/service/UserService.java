package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.DuplicateNameException;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.model.Role;
import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.repo.UserRepo;
import org.id2k1149.dinerVoting.repo.VoterRepo;
import org.id2k1149.dinerVoting.to.UserVotesTo;
import org.id2k1149.dinerVoting.to.VoterTo;
import org.id2k1149.dinerVoting.util.VoterUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final VoterRepo voterRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
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
        log.info("Find user with username {}", username);
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        log.info("Find all users in DB");
        return userRepo.findAll();
    }

    public User getUser(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " does not exists"));
    }

    @Transactional
    public User addUser(User user) {
        Optional<User> optionalUser = Optional.ofNullable(userRepo.findByUsername(user.getUsername()));
        if (optionalUser.isPresent()) {
            throw new DuplicateNameException("The name " + user.getUsername() + " is already used");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        userRepo.save(user);
        log.info("Add a new user {} with id {} to DB", user.getUsername(), user.getId());
        return user;
    }

    @Transactional
    public void updateUser(User user,
                           Long id) {
        if (userRepo.findById(id).isPresent()) {
            User userToUpdate = userRepo.getById(id);
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setRole(user.getRole() == null ? userToUpdate.getRole() : user.getRole());
            userRepo.save(userToUpdate);
            log.info("User {} was updated", user.getUsername());
        } else {
            throw new NotFoundException("User with id " + id + " does not exists");
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        if (userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
        } else {
            throw new NotFoundException("User with id " + id + " does not exists");
        }
    }

    public User findCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        log.info("Current User with name {} was found in DB", username);
        return userRepo.findByUsername(username);
    }

    public UserVotesTo getUserAllVotes(Long id) {
        if (userRepo.findById(id).isPresent()) {
            List<Voter> voterList = voterRepo.findAllByUser(getUser(id));
            List<VoterTo> voterToList = VoterUtil.getVoterTo(getUser(id), voterList);
            return new UserVotesTo(id, getUser(id).getUsername(), voterToList);
        } else {
            throw new NotFoundException("User with id " + id + " does not exists");
        }
    }
}