package com.fishgo.repo;

import com.fishgo.model.Payout;
import com.fishgo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PayoutRepository extends JpaRepository<Payout, Long> {
    List<Payout> findByVendorAndStatus(User vendor, String status);
    List<Payout> findByStatus(String status);
    boolean existsByOrder_Id(Long orderId);
}
