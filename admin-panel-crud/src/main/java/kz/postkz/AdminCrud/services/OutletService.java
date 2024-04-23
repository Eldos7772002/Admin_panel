package kz.postkz.AdminCrud.services;

import kz.postkz.AdminCrud.entity.Outlet;
import kz.postkz.AdminCrud.repository.OutletRepository;
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
            existingOutlet.setLatitude(updatedOutlet.getLatitude());
            existingOutlet.setLongitude(updatedOutlet.getLongitude());
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
