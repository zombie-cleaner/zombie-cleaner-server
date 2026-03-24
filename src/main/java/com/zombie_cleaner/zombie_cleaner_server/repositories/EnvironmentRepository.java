package com.zombie_cleaner.zombie_cleaner_server.repositories;

import com.zombie_cleaner.zombie_cleaner_server.dtos.environment.responses.EnvironmentDetails;
import com.zombie_cleaner.zombie_cleaner_server.entities.Environment;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnvironmentRepository extends JpaRepository<@NonNull Environment,@NonNull Long> {
    Optional<List<Environment>> getUserEnvironmentsByUserId(Long id);
}
