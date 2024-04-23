package kz.postkz.AdminCrud.controller;


import kz.postkz.AdminCrud.entity.NoticePushRule;
import kz.postkz.AdminCrud.services.NoticePushRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notice-push-rules")
public class NoticePushRuleController {

    @Autowired
    private NoticePushRuleService service;

    @GetMapping
    public List<NoticePushRule> getAllNoticePushRules() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticePushRule> getNoticePushRuleById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public NoticePushRule createNoticePushRule(@RequestBody NoticePushRule noticePushRule) {
        return service.createNoticePushRule(noticePushRule);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoticePushRule(@PathVariable Long id) {
        return service.findById(id)
                .map(rule -> {
                    service.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
