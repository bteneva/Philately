package Philately.user.repository;


import Philately.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

//    @Query(Select u from user where username = 'username' or email = 'email')
//    Optional<User> findByEmailAndUsername(@Size(min=3, max=20, message = "username length must be between 3 and 30 charakters") String username, @Email String email);

    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Query("SELECT u from User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    User getUsersById(UUID userId);

    //Optional<User> findByUsername(@NotNull @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters") String username);
}
