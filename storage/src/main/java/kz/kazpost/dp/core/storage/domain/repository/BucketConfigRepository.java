package kz.kazpost.dp.core.storage.domain.repository;

import kz.kazpost.dp.core.storage.domain.model.BucketConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketConfigRepository extends JpaRepository<BucketConfig, Long> {
        BucketConfig findByCode(String code);
}

