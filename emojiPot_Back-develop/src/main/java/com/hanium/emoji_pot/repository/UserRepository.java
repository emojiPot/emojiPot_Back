package com.hanium.emoji_pot.repository;


import com.hanium.emoji_pot.domain.user_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //CURD 작업 가능
public interface UserRepository extends JpaRepository<user_table,Long> {
    user_table findByEmail(String email);
    List<user_table> findAll();
}
