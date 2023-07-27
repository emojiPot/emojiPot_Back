package com.hanium.emoji_pot.domain.Member.repository;


import com.hanium.emoji_pot.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //CURD 작업 가능
public interface UserRepository extends JpaRepository<Member,Long> {

    //null 값 방지 위해 Optional 사용
    Member findByEmail(String email);

    List<Member> findAll();

    Member findByUsername(String username);

}
