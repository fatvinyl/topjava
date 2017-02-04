package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.User;

import java.net.URI;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController extends AbstractUserController {
    static final String REST_URL = "/rest/admin/users";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() { //будем отдавать лист юзеров в формате JSON. Spring его автоматически закодирует и отдаст браузеру
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) //в теле запроса ы принимаем consumes и отдаем produces объект в формате JSON
    public ResponseEntity<User> createWithLocation(@RequestBody User user) { //ResponseEntity ставим, когда хотим вместе с запросом еще передавать какую-то информацию (например хедеры, код возврата 201)
        User created = super.create(user);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(uriOfNewResource);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created); //создаем Response с телом сreated и со статусом 201 (все хорошо, ресурс создался)
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE) //consumes - в теле запроса у нас объект в формате JSON
    public void update(@RequestBody User user, @PathVariable("id") int id) { //spring возьмет юзера в формате JSON и перегонит в наш бин и сюда вставит. Для этого в User должен быть дефолтный конструктор
        super.update(user, id);
    }

    @Override
    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByMail(@RequestParam("email") String email) { //принимаем параметр email. например /by?email=admin@yandex.ru
        return super.getByMail(email);
    }
}
