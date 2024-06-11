import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GmailComposeTest {
    
    private WebDriver driver;
    private String baseUrl = "https://mail.google.com";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/driverss/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSendEmailSuccessfully() {
        driver.get(baseUrl);
        loginToGmail("nikhilsrv4@gmail.com", "Qwerty@123");
        composeEmail("banerjee.soudip@gmail.com", "Incubyte", "Automation QA test for Incubyte");
        assertTrue(emailSentSuccessfully());
    }

    @Test
    public void testEmptySubjectField() {
        driver.get(baseUrl);
        loginToGmail("nikhilsrv4@gmail.com", "Qwerty@123");
        composeEmail("banerjee.soudip@gmail.com", "", "Automation QA test for Incubyte");
        assertEquals("Please enter a subject", getErrorMessage());
    }

    @Test
    public void testEmptyBodyField() {
        driver.get(baseUrl);
        loginToGmail("nikhilsrv4@gmail.com", "Qwerty@123");
        composeEmail("banerjee.soudip@gmail.com", "Incubyte", "");
        assertEquals("Please enter a message", getErrorMessage());
    }

    // Implement other test cases similarly...

    private void loginToGmail(String email, String password) {
        WebElement emailInput = driver.findElement(By.id("identifierId"));
        emailInput.sendKeys(email);
        driver.findElement(By.id("identifierNext")).click();
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        driver.findElement(By.id("passwordNext")).click();
    }

    private void composeEmail(String recipient, String subject, String body) {
        driver.findElement(By.cssSelector(".T-I.T-I-KE.L3")).click(); // Compose button
        WebElement toField = driver.findElement(By.name("to"));
        toField.sendKeys(recipient);
        WebElement subjectField = driver.findElement(By.name("subjectbox"));
        subjectField.sendKeys(subject);
        WebElement bodyField = driver.findElement(By.cssSelector("div[class='Am Al editable LW-avf tS-tW']"));
        bodyField.sendKeys(body);
        driver.findElement(By.cssSelector("div[class='T-I J-J5-Ji aoO T-I-atl L3']")).click(); // Send button
    }

    private boolean emailSentSuccessfully() {
        return driver.findElement(By.cssSelector("span[class='bAq']")).isDisplayed();
    }

    private String getErrorMessage() {
        return driver.findElement(By.cssSelector("div[class='Kj-JD-Jl']")).getText();
    }
}
