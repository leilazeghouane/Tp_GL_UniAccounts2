package dz.univ.accounts.decorator;

import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Decorator Concrete Implementation
 * Decorator 2: RestaurantDecorator
 * Role: يضيف خدمة الإطعام والمطعم الجامعي للوجبات اليومية المدعومة.
 * ============================================================================
 */
public class RestaurantDecorator extends ServiceDecorator {

    private static final double RESTAURANT_FEE = 1200.0; // 1200 DZD

    public RestaurantDecorator(StudentService service) {
        super(service);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + [Restaurant Service]";
    }

    @Override
    public double getMonthlyCost() {
        return super.getMonthlyCost() + RESTAURANT_FEE;
    }

    @Override
    public List<String> getPrivileges() {
        List<String> privileges = super.getPrivileges();
        privileges.add("Daily Subsidized Lunch & Dinner at University Restaurant");
        privileges.add("Campus Cafeteria Discount Voucher (15%)");
        return privileges;
    }
}
