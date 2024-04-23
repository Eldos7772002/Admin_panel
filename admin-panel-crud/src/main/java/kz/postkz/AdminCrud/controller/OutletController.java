package kz.postkz.AdminCrud.controller;
import kz.postkz.AdminCrud.entity.Outlet;
import kz.postkz.AdminCrud.services.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/outlets")
public class OutletController {

    @Autowired
    private OutletService outletService;

    @GetMapping
    public ResponseEntity<List<Outlet>> getAllOutlets() {
        List<Outlet> outlets = outletService.getAllOutlets();
        return new ResponseEntity<>(outlets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Outlet> getOutletById(@PathVariable("id") Long id) {
        Optional<Outlet> outlet = outletService.getOutletById(id);
        return outlet.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Outlet> createOutlet(@RequestBody Outlet outlet) {
        Outlet createdOutlet = outletService.createOutlet(outlet);
        return new ResponseEntity<>(createdOutlet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Outlet> updateOutlet(@PathVariable("id") Long id, @RequestBody Outlet updatedOutlet) {
        Outlet outlet = outletService.updateOutlet(id, updatedOutlet);
        return outlet != null ? new ResponseEntity<>(outlet, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOutlet(@PathVariable("id") Long id) {
        outletService.deleteOutlet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
