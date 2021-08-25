package org.id2k1149.dinerVoting.service;

import com.github.javafaker.Faker;
import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.repo.UserRepo;
import org.id2k1149.dinerVoting.repo.VoterRepo;
import org.id2k1149.dinerVoting.to.UserVotesTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.id2k1149.dinerVoting.model.Role.USER;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    private AutoCloseable autoCloseable;

    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private VoterRepo voterRepo;

    public static User getRandomUser() {
        User user = new User();
        user.setId((long) new Random().nextInt(10));
        user.setUsername("testName_" + user.getId());
        user.setPassword(new Faker().internet().password());
        user.setRole(USER);
        return user;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepo, passwordEncoder, voterRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findByUsername() {
        String name = new Faker().name().username();
        userService.findByUsername(name);
        verify(userRepo).findByUsername(name);
    }

    @Test
    void getUsers() {
        userService.getUsers();
        verify(userRepo).findAll();
    }

    @Test
    void getUser() {
        long id = getRandomUser().getId();
        given(userRepo.existsById(id)).willReturn(true);
        userService.getUser(id);
        verify(userRepo).getById(id);
    }

    @Test
    void addUser() {
        User user1 = getRandomUser();
        User user2 = userService.addUser(user1);
        assertThat(user2).isEqualTo(user1);
    }

    @Test
    void updateUser() {
        User user = getRandomUser();
        long id = user.getId();
        given(userRepo.existsById(id)).willReturn(true);
        User userToUpdate = getRandomUser();
        userToUpdate.setId(id);
        doReturn(userToUpdate).when(userRepo).getById(id);
        userService.updateUser(user, id);
        assertThat(userToUpdate.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void deleteUser() {
        long id = getRandomUser().getId();
        given(userRepo.existsById(id)).willReturn(true);
        userService.deleteUser(id);
        verify(userRepo).deleteById(id);
    }

    @Test
    void loadUserByUsername() {
        User user = getRandomUser();
        String name = user.getUsername();
        doReturn(user).when(userRepo).findByUsername(name);
        UserDetails userDetails = userService.loadUserByUsername(name);
        assertThat(userDetails.getUsername()).isEqualTo(name);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void findCurrentUser() {
        User user1 = getRandomUser();
        String name1 = user1.getUsername();
        doReturn(user1).when(userRepo).findByUsername(name1);
        assertThat(user1.getUsername()).isEqualTo(name1);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void getUserAllVotes() {
        User user = getRandomUser();
        long id = user.getId();
        given(userRepo.existsById(id)).willReturn(true);
        List<UserVotesTo> userVotesToList = new ArrayList<>();
        doReturn(userVotesToList).when(voterRepo).findAllByUser(user);
    }
}