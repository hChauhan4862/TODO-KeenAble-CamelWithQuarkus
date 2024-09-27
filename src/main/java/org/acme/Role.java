package org.acme;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends PanacheEntity {

    // hide the id from the API
    @Schema(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String name;
}
