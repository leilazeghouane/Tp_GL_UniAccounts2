package dz.univ.accounts.model;

import dz.univ.accounts.state.AccountState;
import dz.univ.accounts.state.PendingState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * StudentAccount entity.
 * Implements Builder, Prototype, and State patterns.
 */
public class StudentAccount implements Cloneable {

    private static final Set<String> VALID_LEVELS = new HashSet<>(
            Arrays.asList("L1", "L2", "L3", "M1", "M2")
    );

    // Required fields
    private String id;
    private String fullName;
    private String email;

    // Optional fields
    private String phone;
    private String level;
    private String major;
    private boolean scholarship;
    private List<String> courses;

    // State Pattern: Account lifecycle state
    private AccountState currentState;

    // Private constructor for Builder
    private StudentAccount(Builder builder) {
        this.id = builder.id;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.level = builder.level;
        this.major = builder.major;
        this.scholarship = builder.scholarship;
        this.courses = new ArrayList<>(builder.courses);
        this.currentState = (builder.initialState != null) ? builder.initialState : new PendingState();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be empty.");
        }
        this.id = id.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format (must contain '@').");
        }
        this.email = email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        if (level != null && !VALID_LEVELS.contains(level.toUpperCase())) {
            throw new IllegalArgumentException("Allowed levels: " + VALID_LEVELS);
        }
        this.level = (level != null) ? level.toUpperCase() : "L1";
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public boolean isScholarship() {
        return scholarship;
    }

    public void setScholarship(boolean scholarship) {
        this.scholarship = scholarship;
    }

    public List<String> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setCourses(List<String> courses) {
        this.courses = (courses != null) ? new ArrayList<>(courses) : new ArrayList<>();
    }

    public void addCourse(String course) {
        if (course != null && !course.trim().isEmpty() && !this.courses.contains(course.trim())) {
            this.courses.add(course.trim());
        }
    }

    // State Pattern methods
    public AccountState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(AccountState currentState) {
        this.currentState = currentState;
    }

    public String getStatusName() {
        return currentState != null ? currentState.getStateName() : "PENDING";
    }

    public boolean login() {
        return currentState.login(this);
    }

    public void activate() {
        currentState.activate(this);
    }

    public void suspend() {
        currentState.suspend(this);
    }

    public void close() {
        currentState.close(this);
    }

    // Prototype Pattern: Deep cloning for batch templates
    @Override
    public StudentAccount clone() {
        try {
            StudentAccount cloned = (StudentAccount) super.clone();
            cloned.courses = new ArrayList<>(this.courses);
            cloned.currentState = new PendingState();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning error", e);
        }
    }

    public StudentAccount cloneWithNewIdentity(String newId, String newFullName, String newEmail) {
        StudentAccount copy = this.clone();
        copy.setId(newId);
        copy.setFullName(newFullName);
        copy.setEmail(newEmail);
        return copy;
    }

    @Override
    public String toString() {
        return String.format(
                "StudentAccount[id='%s', name='%s', email='%s', level='%s', major='%s', phone='%s', scholarship=%s, status=%s, courses=%s]",
                id, fullName, email, level, major, (phone != null ? phone : "N/A"), scholarship, getStatusName(), courses
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentAccount that = (StudentAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Builder Pattern
    public static class Builder {
        private final String id;
        private final String fullName;
        private final String email;

        private String phone = null;
        private String level = "L1";
        private String major = "General";
        private boolean scholarship = false;
        private List<String> courses = new ArrayList<>();
        private AccountState initialState = null;

        public Builder(String id, String fullName, String email) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Builder: id is required.");
            }
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("Builder: fullName is required.");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Builder: email must contain '@'.");
            }
            this.id = id.trim();
            this.fullName = fullName.trim();
            this.email = email.trim();
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder level(String level) {
            if (level != null) {
                String upper = level.trim().toUpperCase();
                if (!VALID_LEVELS.contains(upper)) {
                    throw new IllegalArgumentException("Builder: invalid level. Allowed: " + VALID_LEVELS);
                }
                this.level = upper;
            }
            return this;
        }

        public Builder major(String major) {
            this.major = major;
            return this;
        }

        public Builder scholarship(boolean scholarship) {
            this.scholarship = scholarship;
            return this;
        }

        public Builder courses(List<String> courses) {
            if (courses != null) {
                this.courses = new ArrayList<>(courses);
            }
            return this;
        }

        public Builder addCourse(String course) {
            if (course != null && !course.trim().isEmpty()) {
                this.courses.add(course.trim());
            }
            return this;
        }

        public Builder initialState(AccountState state) {
            this.initialState = state;
            return this;
        }

        public StudentAccount build() {
            return new StudentAccount(this);
        }
    }
}
