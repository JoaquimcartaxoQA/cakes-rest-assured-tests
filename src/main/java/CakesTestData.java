import java.security.PublicKey;
import java.util.Map;

public class CakesTestData {
    public static Map<String, String> validCake() {
        return Map.of(
                "title", "Chocolate Cake",
                "description", "Delicious chocolate cake with ganache"
        );
    }
    public static Map<String, String> cakeWithoutTitle() {
        return Map.of(
                "description", "Cake sem título"
        );
    }
    public static Map<String, String> cakeWithEmptyTitle() {
        return Map.of(
                "title", "",
                "description", "Cake com título vazio"
        );
    }
}
