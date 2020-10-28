package com.qeema.authorizationserver.repository;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.qeema.authorizationserver.model.security.User;

@Transactional
public interface UserDetailRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String name);


  @Query("select count(u) from User u")
  public int getUserCount();

}


