package kz.kazpost.dp.core.storage.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bucket_config")
@Data
public class BucketConfig {

    @Id
    @SequenceGenerator(name = "bucketConfigSeq", sequenceName = "seq_bucket_config_id", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "bucketConfigSeq")
    private Long id;

    @Column(nullable = false, length = 255)
    private String code;

    @Column(nullable = false, length = 255)
    private String value;
}
