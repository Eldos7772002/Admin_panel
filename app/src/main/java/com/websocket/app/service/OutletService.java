package com.websocket.app.service;


import com.websocket.app.entity.Outlet;
import com.websocket.app.repository.OutletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutletService {

    @Autowired
    private OutletRepository outletRepository;

    public List<Outlet> getAllOutlets() {
        return outletRepository.findAll();
    }

    public Optional<Outlet> getOutletById(Long id) {
        return outletRepository.findById(id);
    }

    public Outlet createOutlet(Outlet outlet) {
        return outletRepository.save(outlet);
    }

    public Outlet updateOutlet(Long id, Outlet updatedOutlet) {
        Optional<Outlet> existingOutletOptional = outletRepository.findById(id);
        if (existingOutletOptional.isPresent()) {
            Outlet existingOutlet = existingOutletOptional.get();
            existingOutlet.setMall(updatedOutlet.getMall());
            existingOutlet.setLongitude1(updatedOutlet.getLongitude1());
            existingOutlet.setLongitude2(updatedOutlet.getLongitude2());
            existingOutlet.setLatitude1(updatedOutlet.getLatitude1());
            existingOutlet.setLatitude2(updatedOutlet.getLatitude2());
            existingOutlet.setCategory(updatedOutlet.getCategory());
            existingOutlet.setName(updatedOutlet.getName());
            return outletRepository.save(existingOutlet);
        }
        return null;
    }

    public void deleteOutlet(Long id) {
        outletRepository.deleteById(id);
    }
}
