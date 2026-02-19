package com.zombie_cleaner.zombie_cleaner_server.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="environmentName", nullable = false)
    private String environmentName;

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL)
    private List<Resource> resources;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;
}
