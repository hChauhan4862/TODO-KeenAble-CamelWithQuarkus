package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student extends PanacheEntity {
    
    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public int age;

    @Column(nullable = false)
    public String course;

    @Column(nullable = false)
    public String email;

    // Getters and setters if needed
}
