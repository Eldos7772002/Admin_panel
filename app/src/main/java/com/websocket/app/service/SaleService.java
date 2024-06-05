package com.websocket.app.service;

import com.websocket.app.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    List<Sale> findAllSales();
    Optional<Sale> findSaleById(Long id);
    Sale saveSale(Sale sale);
    void deleteSale(Long id);
}
