package kz.postkz.AdminCrud.services;

import kz.postkz.AdminCrud.entity.Mall;
import kz.postkz.AdminCrud.repository.MallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MallService {

    @Autowired
    private MallRepository mallRepository;

    public List<Mall> getAllMalls() {
        return mallRepository.findAll();
    }

    public Optional<Mall> getMallById(Long id) {
        return mallRepository.findById(id);
    }

    public Mall createMall(Mall mall) {
        return mallRepository.save(mall);
    }

    public Mall updateMall(Long id, Mall updatedMall) {
        Optional<Mall> existingMallOptional = mallRepository.findById(id);
        if (existingMallOptional.isPresent()) {
            Mall existingMall = existingMallOptional.get();
            existingMall.setName(updatedMall.getName());
            existingMall.setLatitude1(updatedMall.getLatitude1());
            existingMall.setLongitude1(updatedMall.getLatitude2());
            existingMall.setLatitude2(updatedMall.getLatitude2());
            existingMall.setLongitude2(updatedMall.getLongitude2());
            return mallRepository.save(existingMall);
        }
        return null;
    }

    public void deleteMall(Long id) {
        mallRepository.deleteById(id);
    }
}
