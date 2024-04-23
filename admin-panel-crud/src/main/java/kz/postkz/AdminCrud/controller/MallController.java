package kz.postkz.AdminCrud.controller;


import kz.postkz.AdminCrud.entity.Mall;
import kz.postkz.AdminCrud.services.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/malls")
public class MallController {

    @Autowired
    private MallService mallService;

    @GetMapping
    public ResponseEntity<List<Mall>> getAllMalls() {
        List<Mall> malls = mallService.getAllMalls();
        return new ResponseEntity<>(malls, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mall> getMallById(@PathVariable("id") Long id) {
        Optional<Mall> mall = mallService.getMallById(id);
        return mall.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Mall> createMall(@RequestBody Mall mall) {
        Mall createdMall = mallService.createMall(mall);
        return new ResponseEntity<>(createdMall, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mall> updateMall(@PathVariable("id") Long id, @RequestBody Mall updatedMall) {
        Mall mall = mallService.updateMall(id, updatedMall);
        return mall != null ? new ResponseEntity<>(mall, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMall(@PathVariable("id") Long id) {
        mallService.deleteMall(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}