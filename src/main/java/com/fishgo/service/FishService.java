package com.fishgo.service;

import com.fishgo.model.Fish;
import com.fishgo.model.User;
import com.fishgo.repo.FishRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FishService {
    private final FishRepository repo;

    public FishService(FishRepository repo) {
        this.repo = repo;
    }

    public Fish addFish(Fish fish, User vendor) {
        fish.setVendor(vendor);
        fish.setStatus("AVAILABLE");
        return repo.save(fish);
    }

    public List<Fish> listAvailable() {
        return repo.findByStatus("AVAILABLE");
    }

    public List<Fish> listByVendor(User vendor) {
        return repo.findByVendor(vendor);
    }

    public Fish updateStatus(Long id, String status) {
        Fish f = repo.findById(id).orElseThrow();
        f.setStatus(status);
        return repo.save(f);
    }
}
