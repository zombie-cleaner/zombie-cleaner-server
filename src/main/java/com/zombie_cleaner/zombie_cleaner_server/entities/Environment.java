package com.zombie_cleaner.zombie_cleaner_server.entities;

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

    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL)
    private List<Resource> resources;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
