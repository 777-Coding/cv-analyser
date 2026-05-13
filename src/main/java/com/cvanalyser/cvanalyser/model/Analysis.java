package com.cvanalyser.cvanalyser.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "analyses")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    @Column(columnDefinition = "TEXT")
    private String skills;

    @Column(columnDefinition = "TEXT")
    private String improvements;

    @Column(name = "match_score")
    private Integer matchScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}