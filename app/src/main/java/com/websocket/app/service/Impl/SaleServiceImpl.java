package com.websocket.app.service.Impl;

import com.websocket.app.entity.Sale;
import com.websocket.app.repository.SaleRepository;
import com.websocket.app.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> findSaleById(Long id) {
        return saleRepository.findById(id);
    }

    @Override
    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}
