package dz.univ.accounts.observer;

public class EmailNotifier implements AccountObserver {

    @Override
    public void onAccountEvent(AccountEvent event) {
        String studentEmail = event.getAccount().getEmail();
        String studentName = event.getAccount().getFullName();
        String eventType = event.getType().name();

        System.out.println("  [EMAIL DISPATCH]");
        System.out.println("     To      : " + studentName + " <" + studentEmail + ">");
        System.out.println("     Subject : UniAccounts Notice: Your status is now " + eventType);
        System.out.println("     Body    : Dear " + studentName + ", " + event.getDescription());
        System.out.println("     -----------------------------------------------------------------");
    }
}
