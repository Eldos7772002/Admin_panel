package kz.postkz.AdminCrud.services;

import kz.postkz.AdminCrud.dto.NoticePushRuleDTO;
import kz.postkz.AdminCrud.entity.MassPushNotification;
import kz.postkz.AdminCrud.entity.NoticePushRule;
import kz.postkz.AdminCrud.repository.NoticePushRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticePushRuleService {

    @Autowired
    private NoticePushRuleRepository repository;

    public List<NoticePushRule> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
    public Optional<NoticePushRule> findById(Long id) {
        return repository.findById(id);
    }

    public NoticePushRule createNoticePushRule(NoticePushRule dto) {
//        NoticePushRule noticePushRule = new NoticePushRule();
//        noticePushRule.setCode(dto.getCode());
//        noticePushRule.setPriority(dto.getPriority());
//        noticePushRule.setName(dto.getName());
//        noticePushRule.setDescription(dto.getDescription());
//        noticePushRule.setSmsOnly(dto.isSmsOnly());
//        noticePushRule.setSmsOn(dto.isSmsOn());
//        noticePushRule.setCount(dto.getCount());
//        noticePushRule.setPeriodic(dto.getPeriodic());
        return repository.save(dto);
    }


    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}