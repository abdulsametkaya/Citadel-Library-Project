package com.library.citadel_library.repository;

import com.library.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findById(Long userId);

    Boolean existsByEmail(String email);


   /* @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName, u.phone=:phone, u.email=:email," +
            "u.address=:address,u.birthDate=:birthDate, u.resetPasswordCode=:resetPasswordCode WHERE u.id=:id")
    void update(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName,
                @Param("phone") String phone, @Param("email") String email, @Param("address") String address,
                @Param("birthDate") Date birthDate, @Param("resetPasswordCode") String resetPasswordCode);
*/
}

