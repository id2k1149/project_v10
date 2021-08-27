package org.id2k1149.dinerVoting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.DuplicateNameException;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
import org.id2k1149.dinerVoting.to.DinerTo;
import org.id2k1149.dinerVoting.to.MenuTo;
import org.id2k1149.dinerVoting.util.MenuUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DinerService {
    private final DinerRepo dinerRepo;
    private final MenuRepo menuRepo;

    public List<Diner> getAllDiners() {
        log.info("Find all diners in DB");
        return dinerRepo.findAll();
    }

    public Diner getDiner(Long id) {
        if (dinerRepo.findById(id).isPresent()) {
            return dinerRepo.getById(id);
        } else {
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    @Transactional
    public Diner addDiner(Diner newDiner) {
        Optional<Diner> optionalDiner = Optional.ofNullable(dinerRepo.findDinerByTitle(newDiner.getTitle()));
        if (optionalDiner.isPresent()) {
            throw new DuplicateNameException("The name " + newDiner.getTitle() + " is already used");
        }
        dinerRepo.save(newDiner);
        return newDiner;
    }

    @Transactional
    public void updateDiner(Diner diner,
                            Long id) {
        if (dinerRepo.findById(id).isPresent()) {
            Diner dinerToUpdate = dinerRepo.getById(id);
            if (diner.getTitle() != null) {
                dinerToUpdate.setTitle(diner.getTitle());
                dinerRepo.save(dinerToUpdate);
            } else {
                throw new NotFoundException("Id " + id + " does not exists");
            }
        }
    }

    @Transactional
    public void deleteDiner(Long id) {
        if (dinerRepo.findById(id).isPresent()) {
            dinerRepo.deleteById(id);
        } else {
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public DinerTo getMenuHistoryForDiner(Long id) {
        List<Menu> menuList = menuRepo.findAllByDiner(getDiner(id));
        List<MenuTo> menuToList = MenuUtil.getMenuTo(getDiner(id), menuList);
        return new DinerTo(id, getDiner(id).getTitle(), menuToList);
    }
}