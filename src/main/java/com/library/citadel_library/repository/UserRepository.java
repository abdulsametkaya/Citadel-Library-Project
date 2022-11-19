package com.library.citadel_library.repository;

import com.library.citadel_library.domain.User;
import com.library.citadel_library.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("Select u from User u where u.isActive=true")
    List<User> findAll();
    @Query("select u from User u where u.id=:id and u.isActive=true ")
   Optional<User> findById(Long id);

    @Query("select new com.library.citadel_library.dto.UserDTO(user) from User user where user.isActive=true and (user.firstName= :query or user.lastName= :query or user.email= :query or user.phone= :query or :query is null)")
    Page<UserDTO> findUsersQueryOptionalSearchWithPage(@Param("query") Optional<String> query, Pageable pageable);


}


