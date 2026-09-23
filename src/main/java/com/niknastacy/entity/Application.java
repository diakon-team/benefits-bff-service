package com.niknastacy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    private String id;

    @Column(name="user_id", nullable=false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ApplicationStatus status;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;
}
