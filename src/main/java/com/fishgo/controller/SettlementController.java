package com.fishgo.controller;

import com.fishgo.model.Payout;
import com.fishgo.service.PayoutService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/settlements")
@CrossOrigin(origins = "http://localhost:3000")
public class SettlementController {

    private final PayoutService payoutService;

    public SettlementController(PayoutService ps) {
        this.payoutService = ps;
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Payout> pending() { return payoutService.listPending(); }

    @GetMapping("/scheduled")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Payout> scheduled() { return payoutService.listScheduled(); }

    @PostMapping("/create/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Payout createForOrder(@PathVariable Long orderId) {
        return payoutService.createPayoutForOrder(orderId);
    }

    @PostMapping("/{payoutId}/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public Payout schedule(@PathVariable Long payoutId) {
        return payoutService.schedule(payoutId);
    }

    @PostMapping("/{payoutId}/pay")
    @PreAuthorize("hasRole('ADMIN')")
    public Payout pay(@PathVariable Long payoutId,
                      @RequestParam(required = false) String settlementRef) {
        return payoutService.markPaid(payoutId, settlementRef);
    }
}
