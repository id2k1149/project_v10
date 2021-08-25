package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Counter;
import org.id2k1149.dinerVoting.model.User;
import org.id2k1149.dinerVoting.model.Voter;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.VoterRepo;
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
        log.info("Find all voters in DB");
        return voterRepo.findAll();
    }

    public Voter getVoter(Long id) {
        if (voterRepo.findById(id).isPresent()) {
            return voterRepo.getById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public Voter addVoter(Voter newVoter) {
        voterRepo.save(newVoter);
        return newVoter;
    }

    public void updateVoter(Long id, Voter voter) {
        if (voterRepo.findById(id).isPresent()) {
            Voter voterToUpdate = voterRepo.getById(id);
            voterToUpdate.setDiner(voter.getDiner());
            voterToUpdate.setDate(voter.getDate());
            voterToUpdate.setUser(voter.getUser());
            voterRepo.save(voterToUpdate);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void deleteVoter(Long id) {
        if (voterRepo.findById(id).isPresent()) {
            voterRepo.deleteById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void checkVoter(Diner newDiner) {
        User user = userService.findCurrentUser();
        Voter voter = new Voter();
        voter.setUser(user);
        Optional<Voter> optionalVoter = checkUser();
        if (optionalVoter.isPresent()) {
            voter = optionalVoter.get();
            Diner voterDiner = voter.getDiner();
            Counter oldCounter = counterRepo
                    .findByDateAndDiner(LocalDate.now(), voterDiner)
                    .get();
            int votes = oldCounter.getVotes() - 1;
            oldCounter.setVotes(votes);
            counterRepo.save(oldCounter);
        }
        voter.setDiner(newDiner);
        voterRepo.save(voter);
    }

    public Optional<Voter> checkUser() {
        User user = userService.findCurrentUser();
        Voter voter = new Voter();
        voter.setUser(user);
        return voterRepo.findByUserAndDate(user, LocalDate.now());
    }
}