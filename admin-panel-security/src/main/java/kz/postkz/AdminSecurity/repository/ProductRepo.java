package kz.postkz.AdminSecurity.repository;

import kz.postkz.AdminSecurity.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
