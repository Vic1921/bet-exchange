package ro.vic.bet_exchange.service.SeleniumBetPlacer;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.vic.bet_exchange.model.Match;
import ro.vic.bet_exchange.service.BetPlacer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SuperBetPlacer implements BetPlacer {

    private static Logger logger = LoggerFactory.getLogger(SuperBetPlacer.class);

    private WebDriver driver;
    private boolean loggedIn = false;

    @Value("${superbet.username}")
    public String username;

    @Value("${superbet.password}")
    public String password;

    public void initializeWebDriver() {
        logger.info("Initializing WebDriver");
        try {
            if (driver == null) {
                logger.info("Using system-installed ChromeDriver");
                //System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
                WebDriverManager.chromedriver().setup(); // Automatically sets up ChromeDriver

                ChromeOptions options = new ChromeOptions();
                //options.addArguments("--headless");
                //options.addArguments("--disable-gpu");
                //options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--window-size=2300,1352");

                // Disable pop-ups and GDPR cookie banners
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--disable-features=SameSiteByDefaultCookies,CookiesWithoutSameSiteMustBeSecure");


                driver = new ChromeDriver(options);

                logger.info("WebDriver initialized successfully");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver", e);
        }
    }

    @PreDestroy
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


    public boolean login() {
        if (loggedIn) {
            return true;
        }

        initializeWebDriver();

        try {
            driver.get("https://superbet.ro/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Check for the cookie consent popup and dismiss it
            try {
                WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(), 'Accept')]")  // Adjust the text if needed
                ));
                acceptCookiesButton.click();
                logger.info("Dismissed cookie popup.");
            } catch (Exception e) {
                logger.info("No cookie popup found or already dismissed.");
            }

            // Click on login button with xpath
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div[1]/div/div/div[3]/button[1]")));
            loginButton.click();
            logger.info("Clicked on login button.");

            // Locate and enter username
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input.input__field")
            ));
            usernameField.sendKeys(username);
            logger.info("Entered username.");

            // Locate and enter password
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@type='password']")
            ));
            passwordField.sendKeys(password);
            logger.info("Entered password.");

            // Click login button
            WebElement loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'e2e-login-submit')]")
            ));
            loginSubmitButton.click();
            logger.info("Clicked on login submit button.");

            /*

            // Enter username and password
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/form/div[1]/div")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div[2]/div[1]/form/div[2]/div/input")));
            WebElement loginSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"login-modal-submit\"]")));
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginSubmitButton.click();*/

            TimeUnit.SECONDS.sleep(10);  // Wait for login to process
            loggedIn = true;
            return true;
        } catch (Exception e) {
            //log.error("Login failed", e);
            return false;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public boolean placeBet(Match match, double stake) {
        // Use Selenium WebDriver to navigate and place the bet.
        // Handle delays, element locators, and exceptions.
        return true;
    }
}
