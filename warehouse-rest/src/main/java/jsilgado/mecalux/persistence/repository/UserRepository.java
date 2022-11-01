package jsilgado.mecalux.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import jsilgado.mecalux.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    public User findByUsername(String username);

}
