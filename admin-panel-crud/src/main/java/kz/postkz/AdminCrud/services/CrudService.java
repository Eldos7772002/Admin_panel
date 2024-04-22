package kz.postkz.AdminCrud.services;


import kz.postkz.AdminCrud.dto.MassPushNotificationDTO;
import kz.postkz.AdminCrud.entity.MassPushNotification;
import kz.postkz.AdminCrud.repository.MassPushNotificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

@Service
@Slf4j
@AllArgsConstructor
public class CrudService {

    @Autowired
    private MassPushNotificationRepository repository;

//    public List<MassPushNotification> findAll() {
//        return repository.findAll();
//    }
    public List<MassPushNotification> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public MassPushNotification findById(Long id) {
        return repository.findById(Math.toIntExact(id)).orElse(null);
    }




    public MassPushNotification save(MassPushNotification notificationDTO) {
        return repository.save(notificationDTO);
    }

    public void deleteById(Long id) {
        repository.deleteById(Math.toIntExact(id));
    }
}
