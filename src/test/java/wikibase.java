
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.time.Duration;

public class wikibase {
    protected WebDriver driver;

    private String downloadDirectory;
    protected final String wikipediaURL = "https://www.wikipedia.org/";
    protected final String RELATIVE_RESOURCE_PATH = "/src/test/resources/";
    protected WebDriverWait wait;
    protected static final int MAX_WAIT = 30;



    @BeforeMethod
    public void setup() {

        // Set the desired download directory
        downloadDirectory = getFullPath(RELATIVE_RESOURCE_PATH);

        // Set up ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();
        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("download.default_directory", downloadDirectory);
        chromeOptions.setExperimentalOption("prefs", prefs);
        driver = new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
        wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT));

    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
    protected String getFullPath(String relativePath) {
        String projectRoot = System.getProperty("user.dir");
        return Paths.get(projectRoot, relativePath).toString();

    }
}
