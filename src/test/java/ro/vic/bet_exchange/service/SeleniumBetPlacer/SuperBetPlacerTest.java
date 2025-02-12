package ro.vic.bet_exchange.service.SeleniumBetPlacer;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SuperBetPlacerTest {

    private static WebDriver driver;

    @Autowired
    private SuperBetPlacer superBetPlacer;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();  // Automatically download and setup ChromeDriver
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() {
        superBetPlacer.setDriver(driver);
        superBetPlacer.initializeWebDriver(); // Ensure driver is initialized
        boolean result = superBetPlacer.login();
        assertTrue(result);
    }
}
