package com.jeesun.thymelte.repository;

import com.jeesun.thymelte.domain.UserDomain;
import org.eclipse.jetty.server.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDomainRepository extends JpaRepository<UserDomain, String> {
    UserDomain findById(Long id);
    UserDomain findByUsername(String username);
    UserDomain findByEmail(String email);

    @Query(value = "SELECT a.* FROM users a WHERE NOT EXISTS (SELECT * FROM authorities b WHERE a.id=b.user_id AND (b.authority = 'ROLE_ADMIN' OR b.authority = 'ROLE_SU')) OFFSET :offset LIMIT :limit", nativeQuery = true)
    List<UserDomain> findAllByAuthority(@Param("offset") Integer offset, @Param("limit") Integer limit);

    @Query(value = "SELECT COUNT(a.*) FROM users a WHERE NOT EXISTS (SELECT * FROM authorities b WHERE a.id=b.user_id AND (b.authority = 'ROLE_ADMIN' OR b.authority = 'ROLE_SU'))", nativeQuery = true)
    int countAllByAuthority();

    UserDomain findByUsernameOrEmail(String username, String email);

    Page<UserDomain> findAll(Pageable pageable);
}
