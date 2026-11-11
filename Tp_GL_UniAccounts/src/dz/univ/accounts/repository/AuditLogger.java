package dz.univ.accounts.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Singleton (نمط المفرد / النمط الوحيد)
 * Role: Logger مركزي وحيد لتسجيل جميع عمليات التدقيق (Audit Log) في النظام.
 * 
 * المشكلة التي يحلها:
 * لو قام كل جزء من البرنامج بإنشاء Logger خاص به، لتفرقت السجلات في أماكن متعددة
 * وتضاربت عمليات الكتابة، مما يجعل تتبع العمليات الأمنية والتدقيق مستحيلاً.
 * 
 * ماذا يحدث لو لم نستعمله؟
 * تعدد نسخ المسجل، فقدان التسلسل الزمني للأحداث، وخطر حدوث تضارب في الوصول للملفات.
 * ============================================================================
 */
public class AuditLogger {

    // المتغير الثابت الوحيد (Thread-safe Singleton using Bill Pugh / Initialization-on-demand Holder)
    private static class InstanceHolder {
        private static final AuditLogger INSTANCE = new AuditLogger();
    }

    private final List<String> memoryLogs;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // المُنشئ خاص (private) لمنع إنشاء نسخ جديدة من الخارج
    private AuditLogger() {
        this.memoryLogs = new ArrayList<>();
        log("AUDIT_SYSTEM_INIT", "System audit logger initialized successfully.");
    }

    /**
     * نقطة الوصول العامة الوحيدة للكائن
     */
    public static AuditLogger getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * تسجيل عملية تدقيق جديدة
     */
    public synchronized void log(String action, String details) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = String.format("[%s] [%s] %s", timestamp, action, details);
        memoryLogs.add(logEntry);
        System.out.println("[AUDIT LOG] " + logEntry);
    }

    /**
     * الحصول على جميع السجلات المسجلة (نسخة غير قابلة للتعديل للحفاظ على النزاهة)
     */
    public synchronized List<String> getLogs() {
        return Collections.unmodifiableList(new ArrayList<>(memoryLogs));
    }

    /**
     * تفريغ السجلات وحفظها في ملف (اختياري)
     */
    public synchronized void exportLogsToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            for (String entry : memoryLogs) {
                writer.println(entry);
            }
        } catch (IOException e) {
            System.err.println("Error saving audit logs: " + e.getMessage());
        }
    }
}
