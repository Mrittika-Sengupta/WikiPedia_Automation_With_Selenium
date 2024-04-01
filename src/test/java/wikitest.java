import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class wikitest extends wikibase{
    @Test
    public void wikitest() {
        driver.get(wikipediaURL);
        WebElement element = driver.findElement(By.className("central-textlogo"));
        Assert.assertTrue(element.isDisplayed(), "Main page is not displayed.");
        System.out.println("Main page is displayed");
        WebElement searchInput = driver.findElement(By.name("search"));
        searchInput.sendKeys("Albert Einstein");
        searchInput.sendKeys(Keys.RETURN);
        wait.until(ExpectedConditions.titleContains("Albert Einstein - Wikipedia"));

        WebElement toolMenuButton = driver.findElement(By.id("vector-page-tools-dropdown-checkbox"));
        toolMenuButton.click();

        WebElement downloadPDFLink = driver.findElement(By.xpath("//a[@title='Download this page as a PDF file']"));
        downloadPDFLink.click();
        wait.until(ExpectedConditions.urlContains("Special:DownloadAsPdf"));

        WebElement pageTitle = driver.findElement(By.cssSelector(".mw-electronpdfservice-selection-label-desc"));
        String downloadedFileName = pageTitle.getText().trim();

        String filePath = getFullPath(RELATIVE_RESOURCE_PATH + downloadedFileName);
        File downloadedFile = new File(filePath);

        WebElement downloadButton = driver.findElement(By.cssSelector("button[class*='oo-ui-buttonElement-button']"));
        downloadButton.click();

        Assert.assertTrue(isFileAvailable(downloadedFile), "File isn't downloaded");
        System.out.println("File (" + downloadedFileName + ") is downloaded.");
    }

    private boolean isFileAvailable(File file) {
        try {
            Awaitility.await().atMost(Duration.ofSeconds(MAX_WAIT)).until(file::exists);
            return true;
        } catch (ConditionTimeoutException exception) {
            return false;
        }
    }
}
