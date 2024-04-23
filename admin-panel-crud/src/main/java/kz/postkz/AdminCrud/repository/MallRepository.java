package kz.postkz.AdminCrud.repository;


import kz.postkz.AdminCrud.entity.Mall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MallRepository extends JpaRepository<Mall, Long> {
}