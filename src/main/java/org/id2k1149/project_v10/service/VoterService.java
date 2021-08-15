package org.id2k1149.project_v10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.project_v10.exception.NotFoundException;
import org.id2k1149.project_v10.model.Answer;
import org.id2k1149.project_v10.model.Counter;
import org.id2k1149.project_v10.model.User;
import org.id2k1149.project_v10.model.Voter;
import org.id2k1149.project_v10.repo.CounterRepo;
import org.id2k1149.project_v10.repo.VoterRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VoterService {
    private final VoterRepo voterRepo;
    private final CounterRepo counterRepo;
    private final UserService userService;

    public List<Voter> getVoters() {
        return voterRepo.findAll();
    }

    public Voter getVoter(Long id) {
        if (voterRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        return voterRepo.getById(id);
    }

    public void addVoter(Voter newVoter) {
        voterRepo.save(newVoter);
    }

    public void updateVoter(Long id, Voter voter) {
        if (voterRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exist");
        }
        Voter voterToUpdate = voterRepo.findById(id).get();
        voterToUpdate.setAnswer(voter.getAnswer());
        voterToUpdate.setDate(voter.getDate());
        voterToUpdate.setUser(voter.getUser());
        voterRepo.save(voterToUpdate);
    }

    public void deleteVoter(Long id) {
        if (voterRepo.findById(id).isEmpty()) {
            throw new NotFoundException(id + " does not exists");
        }
        voterRepo.deleteById(id);
    }

    public void checkVoter(Answer newAnswer) {
        User user = userService.findUser();
        Voter voter = new Voter();
        voter.setUser(user);
        Optional<Voter> optionalVoter = checkUser();
        if (optionalVoter.isPresent()) {
            voter = optionalVoter.get();
            Answer voterAnswer = voter.getAnswer();
            Counter oldCounter = counterRepo
                    .findByDateAndAnswer(LocalDate.now(), voterAnswer)
                    .get();
            int votes = oldCounter.getVotes() - 1;
            oldCounter.setVotes(votes);
            counterRepo.save(oldCounter);
        }
        voter.setAnswer(newAnswer);
        voterRepo.save(voter);
    }

    public Optional<Voter> checkUser() {
        User user = userService.findUser();
        Voter voter = new Voter();
        voter.setUser(user);
        return voterRepo.findByUserAndDate(user, LocalDate.now());
    }
}