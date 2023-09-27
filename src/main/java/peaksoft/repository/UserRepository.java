package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



   Optional <User> getUserByEmail(String email);

   boolean existsByEmail(String email);

   @Query("select count(u) from User u where u.id=1")
   int countAllUsers();

   @Query("select sum(c.priceAverage) from User u join u.cheques c where u.id=:id")
   int averageSum(Long id);
}