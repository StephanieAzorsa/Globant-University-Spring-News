package com.eggExercise.news.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class News implements Serializable {

    @Id
    @SequenceGenerator(
            name = "news_sequence",
            sequenceName = "news_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "news_sequence"
    )
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_date", nullable = false, updatable = false)
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "modified_date")
//    @LastModifiedDate
//    private Date modifiedDate;


    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = null;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }
}
