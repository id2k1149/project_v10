package org.id2k1149.project_v10.repo;

import com.github.javafaker.Faker;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Info;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Rollback(false)
public class InfoRepoTest {

    private final Faker faker = new Faker();

    @Autowired
    private InfoRepo infoRepo;

    @Autowired
    private AnswerRepo answerRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createInfo() {
    }

    @Test
    public void findByDate() {
        LocalDate today = LocalDate.now();
        List<Info> optionalInfo = infoRepo.getByDate(today);
    }

    @Test
    public void getRandomInfo() {
        // today info check
        LocalDate today = LocalDate.now();
//        LocalDate today = LocalDate.now().minusDays(2);

        List<Info> optionalInfo = infoRepo.getByDate(today);


        if (optionalInfo.size() > 0) {
            return;
        } else {
            Random random = new Random();

            List<Answer> allAnswers = answerRepo.findAll();
            int max = allAnswers.size();
            int min = 2;
            int numberOfElements = random.nextInt((max - min) + 1) + min;

            List<Answer> todayAnswers = new ArrayList<>();
            for (int i = 0; i < numberOfElements; i++) {
                int randomIndex = random.nextInt(allAnswers.size());
                Answer randomAnswer = allAnswers.get(randomIndex);
                allAnswers.remove(randomIndex);
                todayAnswers.add(randomAnswer);
            }

            for (Answer todayAnswer : todayAnswers) {
                Info newInfo = new Info();
                newInfo.setDate(today);
                newInfo.setAnswer(todayAnswer);

                max = 5;
                min = 2;
                numberOfElements = random.nextInt((max - min) + 1) + min;
                Map<String, BigDecimal> descriptionMap = new HashMap<>();
                for (int i = 0; i < numberOfElements; i++) {
                    String stringInfo = faker.food().dish();
                    BigDecimal digitalInfo = BigDecimal.valueOf(Double.valueOf(faker.commerce().price(10, 100)));
                    descriptionMap.put(stringInfo, digitalInfo);
                }
                newInfo.setDetails(descriptionMap);
                infoRepo.save(newInfo);
            }
        }
    }





    @Test
    public void deleteAllInfo() {
        infoRepo.deleteAll();
    }




        /*
        Map<String, BigDecimal> descriptionMap = new HashMap<>();
        List<Description> allDescriptions = descriptionRepository.findAll();
        List<String> items = new ArrayList<>();
        for (Description allDescription : allDescriptions) {
            items.add(allDescription.getItem());
        }

        Random random = new Random();
        int max = 5;
        int min = 2;
        int numberOfElements = random.nextInt((max - min) + 1) + min;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = random.nextInt(items.size());
            String randomItem = items.get(randomIndex);
            items.remove(randomIndex);

            int maxPrice = 10000;
            int minPrice = 100;
            int r = random.nextInt((maxPrice - minPrice) + 1) + minPrice;

            BigDecimal digitalInfo = BigDecimal.valueOf(r / 100.);

            descriptionMap.put(randomItem, digitalInfo);
        }

        for (Map.Entry<String, BigDecimal> pair : descriptionMap.entrySet()) {
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }


        List<Answer> allAnswers = answerRepository.findAll();
        max = allAnswers.size();
        min = 1;
        int randomInt = random.nextInt((max - min) + 1) + min;
        Answer randomAnswer = answerRepository.findById(4L).get();

         */





//        List<String> allAnswers = new ArrayList<>();
//        for (int i = 0; i < answers.size(); i++) {
//            String answer = answers.get(i).getAnswerTitle();
//            items.add(i, answer);
//        }
//
//
//        max = allAnswers.size();
//        min = 2;
//        numberOfElements = random.nextInt((max - min) + 1) + min;
//
//        List<String> todayAnswers = new ArrayList<>();
//
//        for (int i = 0; i < numberOfElements; i++) {
//            int randomIndex = random.nextInt(allAnswers.size());
//            String randomAnswer = allAnswers.get(randomIndex);
//            allAnswers.remove(randomIndex);
//            todayAnswers.add(randomAnswer);








    }

//    Random random = new Random();
//    int leftLimit = 97; // letter 'a'
//    int rightLimit = 122; // letter 'z'
//    int targetStringLength = 6;
//
//
//    String testName = random.ints(leftLimit, rightLimit + 1)
//            .limit(targetStringLength)
//            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//            .toString();

//
//    @Test
//    public void createNewQuestionWithOneAnswer() {
//        Answer answerDiner1 = entityManager.find(Answer.class, 3L);
//        Question newQuestion = new Question();
//        newQuestion.addAnswer(answerDiner1);
//
//        questionRepository.save(newQuestion);
//    }
//
//    @Test
//    public void createNewQuestionWithTwoAnswers() {
//        Answer answerDiner1 = entityManager.find(Answer.class, 3L);
//        Answer answerDiner2 = entityManager.find(Answer.class, 1L);
//        Question newQuestion = new Question();
//        newQuestion.addAnswer(answerDiner1);
//        newQuestion.addAnswer(answerDiner2);
//
//        questionRepository.save(newQuestion);
//    }
//
//    @Test
//    public void addAnswerToQuestion() {
//        Question question = questionRepository.findById(4L).get();
//        Answer answerDiner3 = entityManager.find(Answer.class, 1L);
//        question.addAnswer(answerDiner3);
//    }
//
//    @Test
//    public void removeAnswerFromQuestion() {
//        Question question = questionRepository.findById(6L).get();
//        Answer answer = answerRepository.getById(3L);
//        question.removeAnswer(answer);
//    }
//
//    @Test
//    public void createNewQuestionWithNewAnswer() {
//        Answer answer = new Answer("Diner #4");
//        Question question = new Question();
//        question.addAnswer(answer);
//        questionRepository.save(question);
//    }
//
//    @Test
//    public void getQuestion() {
//        Question question = questionRepository.findById(4L).get();
//        System.out.println(question.getAnswers());
//    }
//
//    @Test
//    public void deleteQuestion() {
//        questionRepository.deleteById(9L);
//    }


