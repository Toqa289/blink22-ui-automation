import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
public class task {
    WebDriver driver;
    WebDriverWait wait;
    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @BeforeMethod
    public void openBlogPage() {
        driver.get("https://blink22.com/blog");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='rcc-confirm-button']"))).click();
            System.out.println("Accepted cookies");
        } catch (Exception e) {
            System.out.println("The cookies have already been accepted.");
        }
    }
  
    @Test(priority = 1)
    public void testExtractPlaceholders() {
            WebElement nameField = driver.findElement(By.xpath("//*[@id='fullname']"));
            WebElement emailField = driver.findElement(By.xpath("//*[@id='email']"));
            String namePlaceholder = nameField.getAttribute("placeholder");
            String emailPlaceholder = emailField.getAttribute("placeholder");
            System.out.println("Name Placeholder: " + namePlaceholder);
            System.out.println("Email Placeholder: " + emailPlaceholder);
            Assert.assertNotNull(namePlaceholder, "Name placeholder not found");
            Assert.assertNotNull(emailPlaceholder, "Email placeholder not found");
    }

    @Test(priority = 2)
    public void testMissingFieldValidation() {
            driver.findElement(By.xpath("//*[@id='_form_5_submit']")).click();

            WebElement nameError = driver.findElement(By.xpath("//*[contains(text(), 'This field is required.')]"));
            WebElement emailError = driver.findElement(By.xpath("//*[contains(text(), 'This field is required.')]"));

            System.out.println("Name Error: " + nameError.getText());
            System.out.println("Email Error: " + emailError.getText());

            Assert.assertTrue(nameError.isDisplayed(), "Name error message not displayed");
            Assert.assertTrue(emailError.isDisplayed(), "Email error message not displayed");
    }

    @Test(priority = 3)
    public void testInvalidEmailFormat() {
            WebElement nameField = driver.findElement(By.xpath("//*[@id='fullname']"));
            WebElement emailField = driver.findElement(By.xpath("//*[@id='email']"));

            nameField.clear();
            emailField.clear();
            nameField.sendKeys("Toqa");
            emailField.sendKeys("invalid-email");
            driver.findElement(By.xpath("//*[@id='_form_5_submit']")).click();
            WebElement invalidEmailError = driver.findElement(By.xpath("//*[contains(text(), 'Enter a valid email address.')]"));
            System.out.println("Invalid Email Error: " + invalidEmailError.getText());
            Assert.assertTrue(invalidEmailError.isDisplayed(), "Invalid email error message not displayed");
    }

    @Test(priority = 4)
    public void testValidSubmission() {
            WebElement nameField = driver.findElement(By.xpath("//*[@id='fullname']"));
            WebElement emailField = driver.findElement(By.xpath("//*[@id='email']"));
            nameField.clear();
            emailField.clear();
            nameField.sendKeys("Toqa Test");
            emailField.sendKeys("toqa24@gmail.com");
            driver.findElement(By.xpath("//*[@id='_form_5_submit']")).click();
            WebElement thanksMessage = driver.findElement(By.xpath("//*[contains(text(), 'Thanks for signing up!')]"));
            System.out.println("Success Message: " + thanksMessage.getText());
            Assert.assertTrue(thanksMessage.isDisplayed(), "Thank You message not displayed");
    }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}