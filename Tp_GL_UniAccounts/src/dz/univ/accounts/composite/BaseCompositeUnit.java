package dz.univ.accounts.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseCompositeUnit implements UniversityComponent {

    private final String name;
    private final String unitType;
    private final List<UniversityComponent> children = new ArrayList<>();

    public BaseCompositeUnit(String unitType, String name) {
        this.unitType = unitType;
        this.name = name;
    }

    public void add(UniversityComponent component) {
        if (component != null && !children.contains(component)) {
            children.add(component);
        }
    }

    public void remove(UniversityComponent component) {
        children.remove(component);
    }

    public List<UniversityComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUnitType() {
        return unitType;
    }

    @Override
    public int countAccounts() {
        int total = 0;
        for (UniversityComponent child : children) {
            total += child.countAccounts();
        }
        return total;
    }

    @Override
    public void printHierarchy(String indent) {
        System.out.printf("%s+ [%s] %s (Count: %d)%n", indent, unitType, name, countAccounts());
        for (UniversityComponent child : children) {
            child.printHierarchy(indent + "   |");
        }
    }
}
