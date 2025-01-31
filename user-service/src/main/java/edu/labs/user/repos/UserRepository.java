package edu.labs.user.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.labs.user.entities.User;



@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserName(String userName);
}
