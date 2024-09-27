package org.acme;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity {

    @Column(nullable = false)
    public LocalDateTime createdat;

    @Column(nullable = false, length = 1000)
    public String description;

    @Column(nullable = false)
    public LocalDateTime duedate;

    @Column(nullable = false)
    public String status;

    @Column(nullable = false)
    public String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;
}
