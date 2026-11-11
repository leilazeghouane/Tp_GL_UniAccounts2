package dz.univ.accounts.state;

import dz.univ.accounts.model.StudentAccount;

/**
 * ============================================================================
 * Design Pattern: State (نمط الحالة - سلوكي Behavioral)
 * Role: واجهة تحدد السلوكيات المعتمدة على حالة الحساب.
 * 
 * المشكلة التي يحلها:
 * سلوك الحساب يتغير حسب حالته (دورة الحياة: PENDING, ACTIVE, SUSPENDED, CLOSED).
 * بدون هذا النمط، سنحتاج شروطاً معقدة (if-else / switch) مكررة في كل دالة تفحص
 * الحالة الحالية، مما يجعل الكود هشاً وصعب الصيانة والتوسيع.
 * ============================================================================
 */
public interface AccountState {
    
    /**
     * اسم الحالة (PENDING, ACTIVE, SUSPENDED, CLOSED)
     */
    String getStateName();

    /**
     * محاولة تسجيل الدخول
     */
    boolean login(StudentAccount account);

    /**
     * تفعيل الحساب
     */
    void activate(StudentAccount account);

    /**
     * تعليق الحساب
     */
    void suspend(StudentAccount account);

    /**
     * إغلاق الحساب (عند التخرج)
     */
    void close(StudentAccount account);
}
