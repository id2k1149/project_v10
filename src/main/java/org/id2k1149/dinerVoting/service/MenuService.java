package org.id2k1149.dinerVoting.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id2k1149.dinerVoting.exception.CounterException;
import org.id2k1149.dinerVoting.exception.MenuException;
import org.id2k1149.dinerVoting.exception.NotFoundException;
import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
import org.id2k1149.dinerVoting.to.DinerTo;
import org.id2k1149.dinerVoting.util.DinerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepo menuRepo;
    private final DinerRepo dinerRepo;
    private final CounterRepo counterRepo;

    public List<Menu> getAllMenu() {
        log.info("Find all info in DB");
        return menuRepo.findAll();
    }

    public Menu getMenu(Long id) {
        if (menuRepo.findById(id).isPresent()) {
            return menuRepo.getById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public Menu addMenu(Menu newMenu, Long dinerId) {
        if (dinerRepo.findById(dinerId).isPresent()) {
            if (menuRepo.findByDateAndDiner(LocalDate.now(), dinerRepo.getById(dinerId)) == null) {
                newMenu.setDiner(dinerRepo.getById(dinerId));
            } else throw new MenuException("Can't add new Menu, need to edit");
            menuRepo.save(newMenu);
            return newMenu;

        } else {
            log.error("Id {} does not exist in DB", dinerId);
            throw new NotFoundException("Id " + dinerId + " does not exists");
        }
    }

    public void updateMenu(Menu menu, Long id) {
        if (menuRepo.findById(id).isPresent()) {
            if (counterRepo.getFirstByDate(menu.getDate()).isEmpty()) {
                Menu menuToUpdate = menuRepo.getById(id);
                menuToUpdate.setDiner(menu.getDiner());
                menuToUpdate.setDate(menu.getDate());
                menuToUpdate.setDishPrice(menu.getDishPrice());
                menuRepo.save(menuToUpdate);
            } else throw new CounterException("Can't edit. There were votes at that day.");
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public void deleteMenu(Long id) {
        if (menuRepo.findById(id).isPresent()) {
            menuRepo.deleteById(id);
        } else {
            log.error("Id {} does not exist in DB", id);
            throw new NotFoundException("Id " + id + " does not exists");
        }
    }

    public List<Menu> getByDate(LocalDate date) {
        return menuRepo.findAllByDate(date);
    }

    public List<Diner> getDinersMenuByDate(LocalDate date) {
        return menuRepo
                .findAllByDate(date)
                .stream()
                .map(Menu::getDiner)
                .collect(Collectors.toList());
    }

    public List<DinerTo> vote() {
        return DinerUtil
                .getDinersTo(getDinersMenuByDate(LocalDate.now()), getByDate(LocalDate.now()));
    }

    public void everyDayUpdate(LocalDate date) {
        menuRepo.findAllByDate(date).forEach(menuRepo::delete);

        List<Diner> allDiners = dinerRepo.findAll();
        List<Diner> diners = new ArrayList<>();
        IntStream.range(0, new Random().nextInt((allDiners.size() - 2) + 1) + 2)
                .map(i -> new Random().nextInt(allDiners.size())).forEach(randomIndex -> {
            Diner randomDiner = allDiners.get(randomIndex);
            allDiners.remove(randomIndex);
            diners.add(randomDiner);
        });
        diners.forEach(answer -> {
            Menu newMenu = new Menu();
            newMenu.setDate(date);
            newMenu.setDiner(answer);
            Map<String, BigDecimal> dishesMap = new HashMap<>();
            IntStream.range(0, new Random().nextInt(4) + 2)
                    .mapToObj(i -> new Faker().food().dish()).forEach(stringInfo -> {
                BigDecimal digitalInfo = BigDecimal.valueOf(Double.parseDouble(new Faker().commerce().price(10, 100)));
                dishesMap.put(stringInfo, digitalInfo);
            });
            newMenu.setDishPrice(dishesMap);
            menuRepo.save(newMenu);
        });
    }
}