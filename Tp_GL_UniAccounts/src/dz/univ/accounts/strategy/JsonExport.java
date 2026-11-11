package dz.univ.accounts.strategy;

import dz.univ.accounts.model.StudentAccount;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Strategy Concrete Implementation
 * Strategy 2: JsonExport
 * Role: تصدير الحسابات بصيغة مصفوفة JSON منسقة (JSON Array of Objects).
 * ============================================================================
 */
public class JsonExport implements ExportStrategy {

    @Override
    public String getFormatName() {
        return "JSON";
    }

    @Override
    public String export(List<StudentAccount> accounts) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        if (accounts != null && !accounts.isEmpty()) {
            for (int i = 0; i < accounts.size(); i++) {
                StudentAccount acc = accounts.get(i);
                sb.append("  {\n");
                sb.append("    \"id\": \"").append(escapeJson(acc.getId())).append("\",\n");
                sb.append("    \"name\": \"").append(escapeJson(acc.getFullName())).append("\",\n");
                sb.append("    \"email\": \"").append(escapeJson(acc.getEmail())).append("\",\n");
                sb.append("    \"level\": \"").append(escapeJson(acc.getLevel())).append("\",\n");
                sb.append("    \"status\": \"").append(escapeJson(acc.getStatusName())).append("\",\n");
                sb.append("    \"major\": \"").append(escapeJson(acc.getMajor())).append("\",\n");
                sb.append("    \"scholarship\": ").append(acc.isScholarship()).append("\n");
                sb.append("  }");

                if (i < accounts.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\b", "\\b")
                   .replace("\f", "\\f")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
