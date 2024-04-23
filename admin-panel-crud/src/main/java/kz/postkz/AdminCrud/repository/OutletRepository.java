package kz.postkz.AdminCrud.repository;

import kz.postkz.AdminCrud.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutletRepository extends JpaRepository<Outlet, Long> {
}