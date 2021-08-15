package org.id2k1149.project_v10.restController;

import lombok.RequiredArgsConstructor;
import org.id2k1149.project_v10.model.Info;
import org.id2k1149.project_v10.service.InfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/info")
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    @GetMapping
    public List<Info> getAllInfo() {
        return infoService.getAllInfo();
    }

    @GetMapping(path = "{id}")
    public Info getInfo(@PathVariable Long id) {
        return infoService.getInfo(id);
    }

    @PostMapping(value = "{answerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addInfo(@RequestBody Info newInfo, @PathVariable Long answerId) {
        infoService.addInfo(newInfo, answerId);
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInfo(
            @RequestBody Info Info,
            @PathVariable Long id
    ) {
        infoService.updateInfo(Info, id);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInfo(@PathVariable Long id) {
        infoService.deleteInfo(id);
    }

    @GetMapping(path = "/today")
    public List<Info> getTodayInfo() {
        return infoService.getByDate(LocalDate.now());
    }
}