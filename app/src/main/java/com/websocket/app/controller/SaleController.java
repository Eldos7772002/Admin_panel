package com.websocket.app.controller;

import com.websocket.app.entity.Sale;
import com.websocket.app.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping
    public List<Sale> getAllSales() {
        return saleService.findAllSales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable Long id) {
        Optional<Sale> sale = saleService.findSaleById(id);
        return sale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        return saleService.saveSale(sale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable Long id, @RequestBody Sale saleDetails) {
        Optional<Sale> saleOptional = saleService.findSaleById(id);
        if (saleOptional.isPresent()) {
            Sale sale = saleOptional.get();
            sale.setTitle(saleDetails.getTitle());
            sale.setDescription(saleDetails.getDescription());
            sale.setStartTime(saleDetails.getStartTime());
            sale.setEndTime(saleDetails.getEndTime());
            sale.setOutlet(saleDetails.getOutlet());
            sale.setImageUrl(saleDetails.getImageUrl());
            Sale updatedSale = saleService.saveSale(sale);
            return ResponseEntity.ok(updatedSale);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        Optional<Sale> sale = saleService.findSaleById(id);
        if (sale.isPresent()) {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
