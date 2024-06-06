/**
 * Această clasă conține teste unitare pentru clasele ContactComponent și AdminContactComponent.
 */
public class UnitTests {

    /**
     * Metoda principală execută teste unitare.
     * @param args Argumentele liniei de comandă (neutilizate).
     */
    public static void main(String[] args) {
        testContactComponent();
        testAdminContactComponent();
    }

    /**
     * Test unitar pentru clasa ContactComponent.
     */
    public static void testContactComponent() {
        // Crearea unui nou obiect ContactComponent
        ContactComponent contact = new ContactComponent(1, "John Doe", "1234567890", "john@example.com", "123 Main St", 1);

        // Modificarea informațiilor de contact
        contact.setContactName("Jane Doe");
        contact.setContactNumber("0987654321");
        contact.setEmail("jane@example.com");
        contact.setAddress("456 Elm St");

        // Testarea metodelor set și get
        System.out.println("Test Componenta Contact:");
        System.out.println("Nume așteptat: Jane Doe, Nume actual: " + contact.getContactName());
        System.out.println("Număr așteptat: 0987654321, Număr actual: " + contact.getContactNumber());
        System.out.println("Email așteptat: jane@example.com, Email actual: " + contact.getEmail());
        System.out.println("Adresă așteptată: 456 Elm St, Adresă actuală: " + contact.getAddress());
    }

    /**
     * Test unitar pentru clasa AdminContactComponent.
     */
    public static void testAdminContactComponent() {
        // Crearea unui nou obiect AdminContactComponent
        AdminContactComponent adminContact = new AdminContactComponent(1, "John Doe", "1234567890", "john@example.com", "123 Main St", 1);

        // Modificarea informațiilor de contact pentru admin
        adminContact.setContactName("Jane Doe");
        adminContact.setContactNumber("0987654321");
        adminContact.setEmail("jane@example.com");
        adminContact.setAddress("456 Elm St");

        // Testarea metodelor set și get
        System.out.println("Test Componenta Contact Admin:");
        System.out.println("Nume așteptat: Jane Doe, Nume actual: " + adminContact.getContactName());
        System.out.println("Număr așteptat: 0987654321, Număr actual: " + adminContact.getContactNumber());
        System.out.println("Email așteptat: jane@example.com, Email actual: " + adminContact.getEmail());
        System.out.println("Adresă așteptată: 456 Elm St, Adresă actuală: " + adminContact.getAddress());
    }
}
