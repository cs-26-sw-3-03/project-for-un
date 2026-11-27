import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class TestOrderPage {
    @Test
    void canCreatePage() {
        Settings.GetSettingsFromSettingsfile();
        CustomTheme.ReadThemesFromThemefile();
        assertNotNull(new CustomerOrderPage());
    }
}