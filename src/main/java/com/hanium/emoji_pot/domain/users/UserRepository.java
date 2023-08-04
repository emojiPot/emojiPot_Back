package com.hanium.emoji_pot.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndIsDeleted(Long userId, Boolean isDeleted);
    Optional<User> findByUsernameAndIsDeleted(String username, Boolean isDeleted);
    Optional<User> findByEmailAndIsDeleted(String email, Boolean isDeleted);

}
