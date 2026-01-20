package com.fishgo.service;

import com.fishgo.model.Payout;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettlementScheduler {

    private final PayoutService payoutService;

    public SettlementScheduler(PayoutService payoutService) {
        this.payoutService = payoutService;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void autoSchedulePayouts() {
        List<Payout> pending = payoutService.listPending();
        pending.forEach(p -> payoutService.schedule(p.getId()));
        System.out.println("Scheduled " + pending.size() + " payouts");
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void autoPayScheduledPayouts() {
        List<Payout> scheduled = payoutService.listScheduled();
        scheduled.forEach(p -> payoutService.markPaid(p.getId(), "BATCH-" + System.currentTimeMillis()));
        System.out.println("Paid " + scheduled.size() + " payouts");
    }
}
