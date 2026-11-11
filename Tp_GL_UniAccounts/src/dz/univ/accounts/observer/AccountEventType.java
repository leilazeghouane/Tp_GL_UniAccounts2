package dz.univ.accounts.observer;

/**
 * أنواع الأحداث التي تطرأ على حساب الطالب
 */
public enum AccountEventType {
    CREATED,
    ACTIVATED,
    SUSPENDED,
    CLOSED,
    IMPORTED
}
