package dz.univ.accounts.repository;

import dz.univ.accounts.model.StudentAccount;
import dz.univ.accounts.observer.AccountEvent;
import dz.univ.accounts.observer.AccountEventType;
import dz.univ.accounts.observer.AccountObserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ============================================================================
 * Design Pattern: Singleton (نمط المفرد / النمط الوحيد)
 * Role: مستودع مركزي وحيد لحفظ وإدارة جميع حسابات الطلبة.
 * 
 * المشكلة التي يحلها:
 * يجب أن يوجد في البرنامج مستودع واحد يحفظ الحسابات. لو أنشأ كل جزء من البرنامج
 * مستودعه الخاص لضاعت الحسابات بين النسخ ولحدث تكرار في أرقام التسجيل (ID Conflicts).
 * 
 * ماذا يحدث لو لم نستعمله؟
 * عدم اتساق البيانات، فقدان الحسابات بين الشاشات والأجزاء المختلفة، وتعذر
 * فرض شرط فرادة رقم التسجيل (Unique ID).
 * ============================================================================
 */
public class AccountRepository {

    // المتغير الثابت الوحيد (Thread-safe Singleton using Bill Pugh idiom)
    private static class InstanceHolder {
        private static final AccountRepository INSTANCE = new AccountRepository();
    }

    // خريطة لتخزين الحسابات بالمعرف (ID) لضمان السرعة والفرادة
    private final Map<String, StudentAccount> accountsMap;

    // قائمة المراقبين (العمل الثاني: نمط Observer)
    private final List<AccountObserver> observers;

    // مُنشئ خاص لمنع الإنشاء الخارجي
    private AccountRepository() {
        this.accountsMap = new LinkedHashMap<>();
        this.observers = new ArrayList<>();
        AuditLogger.getInstance().log("REPO_INIT", "Central AccountRepository initialized.");
    }

    /**
     * نقطة الوصول العامة الوحيدة للمستودع
     */
    public static AccountRepository getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // ========================================================================
    // عمليات إدارة المراقبين (Observer Pattern Management)
    // ========================================================================
    public synchronized void registerObserver(AccountObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public synchronized void unregisterObserver(AccountObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(AccountEvent event) {
        for (AccountObserver observer : observers) {
            try {
                observer.onAccountEvent(event);
            } catch (Exception e) {
                System.err.println("Error notifying observer: " + e.getMessage());
            }
        }
    }

    // ========================================================================
    // عمليات المستودع CRUD Operations
    // ========================================================================

    /**
     * إضافة حساب جديد مع التحقق من عدم تكرار الـ ID
     */
    public synchronized void addAccount(StudentAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Cannot add a null account.");
        }
        if (accountsMap.containsKey(account.getId())) {
            throw new IllegalStateException("Duplicate Account ID: An account with ID '" + account.getId() + "' already exists!");
        }

        accountsMap.put(account.getId(), account);
        AuditLogger.getInstance().log("ACCOUNT_CREATED", "Account '" + account.getId() + "' (" + account.getFullName() + ") added to repository.");

        // إشعار المراقبين بإنشاء الحساب
        notifyObservers(new AccountEvent(AccountEventType.CREATED, account, "Account created successfully."));
    }

    /**
     * استيراد حساب (يستخدم في نمط Adapter)
     */
    public synchronized void importAccount(StudentAccount account) {
        if (account == null) return;
        if (accountsMap.containsKey(account.getId())) {
            System.out.println("[WARN] Account '" + account.getId() + "' already exists. Skipping import.");
            return;
        }

        accountsMap.put(account.getId(), account);
        AuditLogger.getInstance().log("ACCOUNT_IMPORTED", "Legacy account '" + account.getId() + "' imported successfully.");

        // إشعار المراقبين بالاستيراد
        notifyObservers(new AccountEvent(AccountEventType.IMPORTED, account, "Account imported from legacy system."));
    }

    /**
     * البحث عن حساب بالرقم التعريفي
     */
    public synchronized Optional<StudentAccount> findById(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(accountsMap.get(id));
    }

    /**
     * الحصول على جميع الحسابات المسجلة
     */
    public synchronized List<StudentAccount> getAllAccounts() {
        return Collections.unmodifiableList(new ArrayList<>(accountsMap.values()));
    }

    /**
     * عدد الحسابات في المستودع
     */
    public synchronized int count() {
        return accountsMap.size();
    }

    /**
     * تفعيل حساب
     */
    public synchronized void activateAccount(String id) {
        findById(id).ifPresentOrElse(account -> {
            account.activate();
            notifyObservers(new AccountEvent(AccountEventType.ACTIVATED, account, "Account has been activated."));
        }, () -> System.out.println("[ERROR] Cannot activate: Account '" + id + "' not found."));
    }

    /**
     * تعليق حساب
     */
    public synchronized void suspendAccount(String id) {
        findById(id).ifPresentOrElse(account -> {
            account.suspend();
            notifyObservers(new AccountEvent(AccountEventType.SUSPENDED, account, "Account has been suspended."));
        }, () -> System.out.println("[ERROR] Cannot suspend: Account '" + id + "' not found."));
    }

    /**
     * إغلاق حساب عند التخرج
     */
    public synchronized void closeAccount(String id) {
        findById(id).ifPresentOrElse(account -> {
            account.close();
            notifyObservers(new AccountEvent(AccountEventType.CLOSED, account, "Account closed upon graduation."));
        }, () -> System.out.println("[ERROR] Cannot close: Account '" + id + "' not found."));
    }

    /**
     * تفريغ المستودع (مفيد للاختبارات)
     */
    public synchronized void clear() {
        accountsMap.clear();
        AuditLogger.getInstance().log("REPO_CLEAR", "All accounts cleared from repository.");
    }
}
