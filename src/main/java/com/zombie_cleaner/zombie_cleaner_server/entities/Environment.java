package com.zombie_cleaner.zombie_cleaner_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="environmentName", nullable = false)
    private String environmentName;

    @Column(name="description", nullable = true)
    private String description;

    @Column(name="environmentArn", nullable = false, unique = true)
    private String environmentArn;

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Resource> resources;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}
