import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class YandexTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH-mm-ss");

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);
    }

    @Test
    public void loginTest() throws IOException {
        driver.get("https://mail.yandex.com/");

        takeScreenshot();

        WebElement inputButton = driver.findElement(By.xpath("//a[contains(@class,'HeadBanner-Button-Enter')]"));
        inputButton.click();

        WebElement loginInput = driver.findElement(By.xpath("//input[@id='passp-field-login']"));
        loginInput.sendKeys(ResourceProperties.getCredProperty("login"));

        WebElement loginButton = driver.findElement(By.xpath("//button[@id='passp:sign-in']"));
        loginButton.click();

        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='passp-field-passwd']"));
        passwordInput.sendKeys(ResourceProperties.getCredProperty("password"));

        WebElement loginSecondButton = driver.findElement(By.xpath("//button[@id='passp:sign-in']"));
        loginSecondButton.click();

        Assertions.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class,'user-account_left-name')]/span[@class='user-account__name']"))).getText(),
                ResourceProperties.getCredProperty("login"), "Login failed");
    }

    private void takeScreenshot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./" + getClass().getName() + "_" + formatter.format( new Date()) + ".png"));
    }

    @AfterEach
    public void closeUp() {
        driver.quit();
    }
}
