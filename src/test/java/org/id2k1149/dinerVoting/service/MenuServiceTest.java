package org.id2k1149.dinerVoting.service;

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
    @Mock
    private DinerService dinerService;

    public static Menu getRandomInfo() {
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
        long id = getRandomInfo().getId();
        given(menuRepo.existsById(id)).willReturn(true);
        menuService.getMenu(id);
        verify(menuRepo).getById(id);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void addMenu() {
        Menu menu1 = getRandomInfo();
        long id = menu1.getDiner().getId();
        given(dinerRepo.existsById(id)).willReturn(true);
        Menu menu2 = menuService.addMenu(menu1, id);
        assertThat(menu2).isEqualTo(menu1);
    }

    @Test
    void updateMenu() {
        Menu menu = getRandomInfo();
        System.out.println("menu ->" + menu);
        long id = menu.getId();
        given(menuRepo.existsById(id)).willReturn(true);
        Menu menuToUpdate = getRandomInfo();
        menuToUpdate.setId(id);
        doReturn(menuToUpdate).when(menuRepo).getById(id);
        menuService.updateMenu(menu, id);
        assertThat(menuToUpdate.getDate()).isEqualTo(menu.getDate());
    }

    @Test
    void deleteMenu() {
        long id = getRandomInfo().getId();
        given(menuRepo.existsById(id)).willReturn(true);
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