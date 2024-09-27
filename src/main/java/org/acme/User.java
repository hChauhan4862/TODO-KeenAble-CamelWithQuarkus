package org.acme;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String password;

    @Column(name = "createdat", nullable = false)
    public LocalDateTime createdat;

    @Column(name = "username", nullable = false, length = 50)
    public String username;

    @Column(name = "first_name", length = 50)
    public String firstName;

    @Column(name = "last_name", length = 50)
    public String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    public Role role;

    @Column(nullable = false)
    public String passwordhash;
}
