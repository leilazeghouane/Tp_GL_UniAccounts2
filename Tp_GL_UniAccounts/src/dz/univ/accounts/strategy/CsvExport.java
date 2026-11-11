package dz.univ.accounts.strategy;

import dz.univ.accounts.model.StudentAccount;
import java.util.List;

/**
 * ============================================================================
 * Design Pattern: Strategy Concrete Implementation
 * Strategy 1: CsvExport
 * Role: تصدير الحسابات بصيغة CSV (سطر عناوين ثم سطر لكل حساب: id,name,level,status).
 * ============================================================================
 */
public class CsvExport implements ExportStrategy {

    @Override
    public String getFormatName() {
        return "CSV";
    }

    @Override
    public String export(List<StudentAccount> accounts) {
        StringBuilder sb = new StringBuilder();
        // سطر العناوين (CSV Header)
        sb.append("id,name,level,status\n");

        if (accounts != null) {
            for (StudentAccount acc : accounts) {
                sb.append(escapeCsv(acc.getId())).append(",")
                  .append(escapeCsv(acc.getFullName())).append(",")
                  .append(escapeCsv(acc.getLevel())).append(",")
                  .append(escapeCsv(acc.getStatusName())).append("\n");
            }
        }
        return sb.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
