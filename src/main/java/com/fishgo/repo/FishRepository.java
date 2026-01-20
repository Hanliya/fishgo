package com.fishgo.repo;

import com.fishgo.model.Fish;
import com.fishgo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FishRepository extends JpaRepository<Fish, Long> {
    List<Fish> findByStatus(String status);
    List<Fish> findByVendor(User vendor);
}
