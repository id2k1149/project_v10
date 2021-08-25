package org.id2k1149.dinerVoting.component;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.id2k1149.dinerVoting.model.*;
import org.id2k1149.dinerVoting.repo.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

import static org.id2k1149.dinerVoting.model.Role.ADMIN;
import static org.id2k1149.dinerVoting.model.Role.USER;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UserRepo userRepo;
    private final DinerRepo dinerRepo;
    private final MenuRepo menuRepo;
    private final CounterRepo counterRepo;
    private final VoterRepo voterRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (Optional.ofNullable(userRepo
                .findByUsername("admin"))
                .isEmpty())
            populateDB();
    }

    public void populateDB() {
        addUsers();
        addDiners();
        addMenu();
        addVotes();
    }

    private void addVotes() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Diner diner = dinerRepo.findAll().get(new Random().nextInt(2));
                Optional<Counter> optionalCounter = counterRepo.findByDateAndDiner(LocalDate.now().minusDays(2 - i), diner);
                Counter counter = (optionalCounter.isEmpty() ? new Counter() : optionalCounter.get());
                counter.setDate(LocalDate.now().minusDays(2 - i));
                counter.setDiner(diner);
                counter.setVotes(optionalCounter.isEmpty() ? 1 : 2);
                counterRepo.save(counter);
                Voter voter = new Voter();
                voter.setDate(LocalDate.now().minusDays(2 - i));
                voter.setUser(userRepo.findAll().get(j));
                voter.setDiner(diner);
                voterRepo.save(voter);
            }
        }
    }

    private void addMenu() {
        IntStream.range(0, 3)
                .mapToObj(i -> LocalDate.now().minusDays(2 - i))
                .forEach(localDate -> IntStream.range(0, 2)
                        .mapToObj(j -> dinerRepo.findAll().get(j))
                        .forEach(diner -> {
                            Map<String, BigDecimal> dishes = new HashMap<>();
                            IntStream.range(0, 2)
                                    .mapToObj(k -> new Faker().food().dish())
                                    .forEach(stringInfo -> {
                                        BigDecimal digitalInfo = BigDecimal
                                                .valueOf(Double.parseDouble(new Faker().commerce().price(10, 100)));
                                        dishes.put(stringInfo, digitalInfo);
                                    });
                            Menu menu = new Menu();
                            menu.setDate(localDate);
                            menu.setDiner(diner);
                            menu.setDishPrice(dishes);
                            menuRepo.save(menu);
                        }));
    }

    private void addDiners() {
        IntStream.range(0, 2)
                .mapToObj(i -> new Diner(new Faker().company().name()))
                .forEach(dinerRepo::save);
    }

    public void addUsers() {
        IntStream.range(0, 2).forEach(i -> {
            User user = new User();
            user.setUsername(i == 0 ? "admin" : "user");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(i == 0 ? ADMIN : USER);
            userRepo.save(user);
        });
    }
}