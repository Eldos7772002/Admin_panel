package kz.postkz.AdminCrud.controller;

import kz.postkz.AdminCrud.entity.MassPushNotification;
import kz.postkz.AdminCrud.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class MassPushNotificationController {

    @Autowired
    private CrudService service;

    @GetMapping
    public List<MassPushNotification> getAllNotifications() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MassPushNotification> getNotificationById(@PathVariable Long id) {
        MassPushNotification notification = service.findById(id);
        return notification != null ? ResponseEntity.ok(notification) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public MassPushNotification createNotification(@RequestBody MassPushNotification notification) {
        return service.save(notification);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
