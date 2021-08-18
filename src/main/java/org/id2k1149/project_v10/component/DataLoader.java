package org.id2k1149.project_v10.component;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.id2k1149.project_v10.model.*;
import org.id2k1149.project_v10.repo.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

import static org.id2k1149.project_v10.model.Role.ADMIN;
import static org.id2k1149.project_v10.model.Role.USER;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final UserRepo userRepo;
    private final AnswerRepo answerRepo;
    private final InfoRepo infoRepo;
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
        addAnswers();
        addInfo();
        addVotes();
    }

    private void addVotes() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Answer answer = answerRepo.findAll().get(new Random().nextInt(2));
                Optional<Counter> optionalCounter = counterRepo.findByDateAndAnswer(LocalDate.now().minusDays(2 - i), answer);
                Counter counter = (optionalCounter.isEmpty() ? new Counter() : optionalCounter.get());
                counter.setDate(LocalDate.now().minusDays(2 - i));
                counter.setAnswer(answer);
                counter.setVotes(optionalCounter.isEmpty() ? 1 : 2);
                counterRepo.save(counter);
                Voter voter = new Voter();
                voter.setDate(LocalDate.now().minusDays(2 - i));
                voter.setUser(userRepo.findAll().get(j));
                voter.setAnswer(answer);
                voterRepo.save(voter);
            }
        }
    }

    private void addInfo() {
        IntStream.range(0, 2)
                .mapToObj(i -> LocalDate.now().minusDays(2 - i))
                .forEach(localDate -> IntStream.range(0, 2)
                        .mapToObj(j -> answerRepo.findAll().get(j))
                        .forEach(answer -> {
                            Map<String, BigDecimal> descriptionMap = new HashMap<>();
                            IntStream.range(0, 2)
                                    .mapToObj(k -> new Faker().food().dish())
                                    .forEach(stringInfo -> {
                                        BigDecimal digitalInfo = BigDecimal
                                                .valueOf(Double.parseDouble(new Faker().commerce().price(10, 100)));
                                        descriptionMap.put(stringInfo, digitalInfo);
                                    });
                            Info info = new Info();
                            info.setDate(localDate);
                            info.setAnswer(answer);
                            info.setDetails(descriptionMap);
                            infoRepo.save(info);
                        }));
    }

    private void addAnswers() {
        IntStream.range(0, 2)
                .mapToObj(i -> new Answer(new Faker().company().name()))
                .forEach(answerRepo::save);
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