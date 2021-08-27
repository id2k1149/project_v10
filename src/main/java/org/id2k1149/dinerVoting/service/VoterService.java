package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.model.Diner;
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
@RequiredArgsConstructor
@Slf4j
public class VoterService {
    private final VoterRepo voterRepo;
    private final UserService userService;

    public Voter addVoter(Voter newVoter) {
        voterRepo.save(newVoter);
        return newVoter;
    }

    @Transactional
    public void deleteVoter(Long id) {
        if (voterRepo.findById(id).isPresent()) {
            voterRepo.deleteById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void saveVoterForDiner(Diner newDiner) {
        Voter voter = new Voter();
        User user = userService.findCurrentUser();
        voter.setUser(user);
        voter.setDiner(newDiner);
        voterRepo.save(voter);
    }

    public Optional<Voter> getVoterByUserAndDate() {
        return voterRepo.findByUserAndDate(userService.findCurrentUser(), LocalDate.now());
    }

    public boolean userVotedToday() {
        return voterRepo.findByUserAndDate(userService.findCurrentUser(), LocalDate.now()).isPresent();
    }
}