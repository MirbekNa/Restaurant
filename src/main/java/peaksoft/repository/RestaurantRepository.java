package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

        @Query("select u from Restaurant r join r.users u")
        List<User> getAllUsers();
        boolean existsByName(String name);
}