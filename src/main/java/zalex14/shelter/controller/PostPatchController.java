package zalex14.shelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zalex14.shelter.entity.shelter.Handler;

@RestController
@RequestMapping("/apost")
public class PostPatchController {

    @PostMapping
    public ResponseEntity<Handler> create(@RequestBody Handler handler) {
        System.out.println("c " + handler.getId() + " " + handler.getTelegramId() + " " + handler.getTelegramUserName());
        return handler.getId() != null & handler.getTelegramUserName() != null
                ? ResponseEntity.ok().body(handler)
                : ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<Handler> update(@RequestBody Handler handler) {
        System.out.println("u " + handler.getId() + " " + handler.getTelegramId() + " " + handler.getTelegramUserName());
        return handler.getId() != null & handler.getTelegramUserName() != null
                ? ResponseEntity.ok().body(handler)
                : ResponseEntity.notFound().build();
    }
}
