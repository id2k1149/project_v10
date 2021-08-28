package org.id2k1149.dinerVoting.service;

import org.id2k1149.dinerVoting.model.Diner;
import org.id2k1149.dinerVoting.model.Menu;
import org.id2k1149.dinerVoting.repo.DinerRepo;
import org.id2k1149.dinerVoting.repo.CounterRepo;
import org.id2k1149.dinerVoting.repo.MenuRepo;
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

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.id2k1149.dinerVoting.service.DinerServiceTest.getRandomDiner;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    private MenuService menuService;
    private AutoCloseable autoCloseable;

    @Mock
    private MenuRepo menuRepo;
    @Mock
    private DinerRepo dinerRepo;
    @Mock
    private CounterRepo counterRepo;

    public static Menu getRandomMenu() {
        Menu menu = new Menu();
        menu.setId((long) new Random().nextInt(10));
        menu.setDate(LocalDate.now().minusDays(menu.getId()));
        menu.setDiner(getRandomDiner());
        return menu;
    }

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        menuService = new MenuService(menuRepo, dinerRepo, counterRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllMenu() {
        menuService.getAllMenu();
        verify(menuRepo).findAll();
    }

    @Test
    void getMenu() {
        Menu menu = getRandomMenu();
        long id = menu.getId();
        doReturn(Optional.of(menu)).when(menuRepo).findById(id);
        Menu testMenu = menuService.getMenu(id);
        assertThat(testMenu).isEqualTo(menu);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void addMenu() {
        Menu menu1 = getRandomMenu();
        Diner diner = menu1.getDiner();
        long id = diner.getId();
        given(dinerRepo.findById(id)).willReturn(Optional.of(diner));
        Menu menu2 = menuService.addMenu(menu1, id);
        assertThat(menu2).isEqualTo(menu1);
    }

    @Test
    void updateMenu() {
        Menu menu = getRandomMenu();
        long id = menu.getId();
        given(menuRepo.findById(id)).willReturn(Optional.of(menu));
        Menu menuToUpdate = getRandomMenu();
        menuToUpdate.setId(id);
        doReturn(menuToUpdate).when(menuRepo).getById(id);
        menuService.updateMenu(menu, id);
        assertThat(menuToUpdate.getDate()).isEqualTo(menu.getDate());
    }

    @Test
    void deleteMenu() {
        Menu menu = getRandomMenu();
        long id = menu.getId();
        given(menuRepo.findById(id)).willReturn(Optional.of(menu));
        menuService.deleteMenu(id);
        verify(menuRepo).deleteById(id);
    }

    @Test
    void getMenuByDate() {
        LocalDate localDate = LocalDate.now();
        menuService.getByDate(localDate);
        verify(menuRepo).findAllByDate(localDate);
    }

    @Test
    void getDinersMenuByDate() {
        LocalDate localDate = LocalDate.now();
        menuService.getDinersMenuByDate(localDate);
        verify(menuRepo).findAllByDate(localDate);
    }
}