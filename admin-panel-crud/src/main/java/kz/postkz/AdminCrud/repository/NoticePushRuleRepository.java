package kz.postkz.AdminCrud.repository;


import kz.postkz.AdminCrud.entity.NoticePushRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticePushRuleRepository extends JpaRepository<NoticePushRule, Long> {
}
