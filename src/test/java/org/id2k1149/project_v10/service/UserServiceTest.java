package org.id2k1149.project_v10.service;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.BadRequestException;
import org.id2k1149.project_v10.exception.NotFoundException;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.repo.UserRepo;
import org.id2k1149.project_v10.repo.VoterRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.id2k1149.project_v10.model.Role.ADMIN;
import static org.id2k1149.project_v10.model.Role.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DataJpaTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final Faker faker = new Faker();

    @Mock
    private UserRepo userRepo;
    @Autowired
    private VoterRepo voterRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService testUserService;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        testUserService = new UserService(userRepo, bCryptPasswordEncoder, voterRepo);
        int bound = new Random().nextInt(4) + 2;
        IntStream.range(0, bound).mapToObj(i -> new User()).forEach(testUser -> {
            testUser.setUsername(new Faker().name().username());
            testUser.setPassword(new Faker().internet().password());
            testUserService.addUser(testUser);
        });
    }

//    @AfterEach
//    void tearDown() {
//        userRepo.deleteAll();
//    }

    @Test
    void getUsers() {
        //given
        testUserService.getUsers();
        //then
        verify(userRepo).findAll();
    }

    @Test
    void getUser() {
        //given
        System.out.println(testUserService.getUsers().size());

        //when
//        User user = testUserService.getUser(testUser2.getId());

        //then
//        assertThat(testUserService.getUser(testUser2.getId())).isEqualTo(testUser1);
    }

    @Test
    void addUser() {
        //given
        User testUser1 = new User();
        String testName1 = faker.name().username();
        testUser1.setUsername(testName1);
        String testPassword1 = faker.internet().password();
        testUser1.setPassword(testPassword1);

        //when
        testUserService.addUser(testUser1);

        //then
        ArgumentCaptor<User> testUserArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(testUserArgumentCaptor.capture());
        User captorUser = testUserArgumentCaptor.getValue();

    }

    @Test
    void nameIsTaken() {
        //given
        User testUser1 = new User();
        String testName1 = faker.name().username();
        testUser1.setUsername(testName1);
        String testPassword1 = faker.internet().password();
        testUser1.setPassword(testPassword1);
        testUser1.setId(1L);
        testUserService.addUser(testUser1);


        User newUser = new User();
        newUser.setUsername(testName1);
        String testPassword2 = faker.internet().password();
        newUser.setPassword(testPassword2);
        newUser.setId(2L);
        testUserService.addUser(newUser);

//        given(testRepository.usernameExists(anyString()))
//                .willReturn(true);

        //when
        //then
        assertThatThrownBy(() -> testUserService.addUser(newUser))
                .isInstanceOf(BadRequestException.class);

        verify(userRepo, never()).save(newUser);

    }

    @Test
    void updateUser() {

    }

    @Test
    void deleteUser() {
        // given
        long id = 10;
        given(userRepo.existsById(id))
                .willReturn(true);
        // when
        testUserService.deleteUser(id);

        // then
        verify(userRepo).deleteById(id);
    }

    @Test
    void cantDeleteIfUserNotFound() {
        // given
        long id = 10;
        given(userRepo.existsById(id))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> testUserService.deleteUser(id))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User with id " + id + " does not exists");

        verify(userRepo, never()).deleteById(any());
    }

}