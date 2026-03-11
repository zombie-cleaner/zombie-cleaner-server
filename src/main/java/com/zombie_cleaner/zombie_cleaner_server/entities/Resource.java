package com.zombie_cleaner.zombie_cleaner_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resourceName", nullable = false, unique = false)
    private String resourceName;

    @Column(name ="resourceType", nullable = false, unique = false)
    private String resourceType;

    @Column(name = "resourceNameAws", nullable = false, unique = false)
    private String resourceNameAws;

    @Column(name = "resourceIdentifierAws", nullable = false , unique = true)
    private String resourceIdentifierAws;

    @ManyToOne
    @JoinColumn(name = "environment_id" , nullable = false)
    @JsonBackReference
    private Environment environment;

}
