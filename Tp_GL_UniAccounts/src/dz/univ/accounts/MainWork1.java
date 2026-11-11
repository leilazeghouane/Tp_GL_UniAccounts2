package dz.univ.accounts;

import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.repository.AccountRepository;
import dz.univ.accounts.repository.AuditLogger;

import java.util.Arrays;

/**
 * TP 1 - Creational Design Patterns
 * Universite Hamma Lakhdar - El Oued
 * Departement d'Informatique
 */
public class MainWork1 {

    public static void main(String[] args) {

        // 1. Singleton Pattern Verification
        System.out.println("\n[1] Singleton Pattern:");
        AccountRepository repo1 = AccountRepository.getInstance();
        AccountRepository repo2 = AccountRepository.getInstance();

        AuditLogger logger1 = AuditLogger.getInstance();
        AuditLogger logger2 = AuditLogger.getInstance();

        System.out.println("repo1 == repo2     : " + (repo1 == repo2));
        System.out.println("logger1 == logger2 : " + (logger1 == logger2));

        // 2. Builder Pattern Verification
        System.out.println("\n[2] Builder Pattern:");
        StudentAccount student1 = new StudentAccount.Builder("20240001", "Med Adel Kouidi", "medadel.kouidi@univ-eloued.dz")
                .phone("0550112233")
                .level("L3")
                .major("Informatique (Genie Logiciel)")
                .scholarship(true)
                .courses(Arrays.asList("Design Patterns", "Databases", "Operating Systems"))
                .build();

        System.out.println("Created: " + student1);
        repo1.addAccount(student1);

        System.out.println("\nTesting validations:");
        try {
            new StudentAccount.Builder("20240002", "Bad Email Student", "invalid_email.dz").build();
        } catch (IllegalArgumentException e) {
            System.out.println("Caught error (Email): " + e.getMessage());
        }

        try {
            new StudentAccount.Builder("20240003", "Wrong Level Student", "test@univ-eloued.dz")
                    .level("Doctorate")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Caught error (Level): " + e.getMessage());
        }

        try {
            StudentAccount dup = new StudentAccount.Builder("20240001", "Duplicate Test", "dup@univ-eloued.dz").build();
            repo1.addAccount(dup);
        } catch (IllegalStateException e) {
            System.out.println("Caught error (Duplicate ID): " + e.getMessage());
        }

        // 3. Prototype Pattern Verification
        System.out.println("\n[3] Prototype Pattern:");
        StudentAccount m1IsTemplate = new StudentAccount.Builder("TEMPLATE_M1_IS", "Template Batch IS", "template@univ-eloued.dz")
                .level("M1")
                .major("Informatique (Systemes d'Information)")
                .courses(Arrays.asList("Data Warehousing", "Advanced Databases", "ERP Systems", "Information Security"))
                .build();

        System.out.println("Template: " + m1IsTemplate);

        StudentAccount studentOualid = m1IsTemplate.cloneWithNewIdentity("20241001", "Oualid Laroum", "oualid.laroum@univ-eloued.dz");
        studentOualid.setPhone("0661223344");
        studentOualid.setScholarship(true);

        StudentAccount studentFarah = m1IsTemplate.cloneWithNewIdentity("20241002", "Farah Kouidi", "farah.kouidi@univ-eloued.dz");
        studentFarah.setPhone("0770556677");

        repo1.addAccount(studentOualid);
        repo1.addAccount(studentFarah);

        System.out.println("Cloned 1: " + studentOualid);
        System.out.println("Cloned 2: " + studentFarah);

        studentOualid.addCourse("Cloud Computing");
        System.out.println("Template courses unchanged? " + (m1IsTemplate.getCourses().size() == 4));

        // Summary
        System.out.println("\n--- Repository Accounts (" + repo1.count() + ") ---");
        repo1.getAllAccounts().forEach(acc -> System.out.println(" - " + acc));

        System.out.println("\n--- Audit Logs ---");
        AuditLogger.getInstance().getLogs().forEach(System.out::println);
    }
}
