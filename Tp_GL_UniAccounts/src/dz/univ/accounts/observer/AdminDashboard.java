package dz.univ.accounts.observer;

import java.util.EnumMap;
import java.util.Map;

public class AdminDashboard implements AccountObserver {

    private final Map<AccountEventType, Integer> eventCounters = new EnumMap<>(AccountEventType.class);

    public AdminDashboard() {
        for (AccountEventType type : AccountEventType.values()) {
            eventCounters.put(type, 0);
        }
    }

    @Override
    public synchronized void onAccountEvent(AccountEvent event) {
        int currentCount = eventCounters.getOrDefault(event.getType(), 0) + 1;
        eventCounters.put(event.getType(), currentCount);

        System.out.println("  [DASHBOARD UPDATE]");
        System.out.printf("     New Event: %s | Total %s events: %d%n",
                event.getType(), event.getType(), currentCount);
    }

    public synchronized void printDashboardSummary() {
        System.out.println("--------------------------------------------------");
        System.out.println("            ADMIN DASHBOARD STATISTICS            ");
        System.out.println("--------------------------------------------------");
        int total = 0;
        for (Map.Entry<AccountEventType, Integer> entry : eventCounters.entrySet()) {
            System.out.printf("  %-15s : %d%n", entry.getKey(), entry.getValue());
            total += entry.getValue();
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("  %-15s : %d%n", "TOTAL EVENTS", total);
        System.out.println("--------------------------------------------------");
    }

    public synchronized int getCount(AccountEventType type) {
        return eventCounters.getOrDefault(type, 0);
    }
}
