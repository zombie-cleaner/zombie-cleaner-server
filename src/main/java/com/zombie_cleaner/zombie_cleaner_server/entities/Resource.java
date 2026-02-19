package com.zombie_cleaner.zombie_cleaner_server.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
    @JoinColumn(name = "id" , nullable = false)
    private Environment environment;

}
