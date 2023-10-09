package com.chris.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 80)
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(name="last_name")
    private String lastName;

    @NotEmpty
    @Email
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

}
