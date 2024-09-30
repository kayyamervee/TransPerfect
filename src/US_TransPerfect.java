import Utility.BaseDriver;
import Utility.Tools;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class US_TransPerfect extends BaseDriver {
    String firstName = "Merve";
    String phoneNumber = "90505667788";

    @Test
    public void transPerfect() throws InterruptedException, IOException, AWTException {
        driver.get("https://www.transperfect.com/");
        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='/industries'])[1]")));
        WebElement industriesBtn = driver.findElement(By.xpath("(//a[@href='/industries'])[1]"));
        wait.until(ExpectedConditions.elementToBeClickable(industriesBtn));
        action.moveToElement(industriesBtn).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Retail & E-Commerce")));
        WebElement retailEcommerce = driver.findElement(By.linkText("Retail & E-Commerce"));
        wait.until(ExpectedConditions.elementToBeClickable(retailEcommerce));
        action.click(retailEcommerce).perform();

        Tools.Wait(5);

        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/industries/retail-and-ecommerce"));
        Assert.assertTrue("Commerce page could not be reached", driver.getCurrentUrl().contains("retail-and-ecommerce"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("paragraph-t_logo_carousel-17512")));
        WebElement clientStories = driver.findElement(By.id("paragraph-t_logo_carousel-17512"));
        Tools.scrollElement(clientStories);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("paragraph-t_simple_text_block-9426")));
        WebElement visibleText = driver.findElement(By.xpath("//div[text()='Client Stories']"));
        Assert.assertTrue("Text could not matched", visibleText.getText().equalsIgnoreCase("Client Stories"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='t-search-link']")));
        WebElement searchBtn = driver.findElement(By.cssSelector("span[class='t-search-link']"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
        action.moveToElement(searchBtn).click().perform();

        wait.until(ExpectedConditions.elementToBeClickable(By.name("search_api_fulltext")));
        WebElement textTranslation = driver.findElement(By.name("search_api_fulltext"));
        textTranslation.sendKeys("translation");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[text()='Translation & Language Services'])[1]")));
        textTranslation.clear();

        wait.until(ExpectedConditions.elementToBeClickable(By.name("search_api_fulltext")));
        WebElement searchBox = driver.findElement(By.name("search_api_fulltext"));
        searchBox.sendKeys("quote" + Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Request a Free Quote']")));
        WebElement selectRequest = driver.findElement(By.xpath("//a[text()='Request a Free Quote']"));
        wait.until(ExpectedConditions.elementToBeClickable(selectRequest));
        Tools.jsClick(selectRequest);

        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/request-quote"));
        WebElement describeWebLoc = driver.findElement(By.xpath("(//label[@class='form-check-label'])[2]"));
        action.moveToElement(describeWebLoc).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='translation']")));
        WebElement clickTransService = driver.findElement(By.xpath("//input[@value='translation']"));
        wait.until(ExpectedConditions.elementToBeClickable(clickTransService));
        Tools.jsClick(clickTransService);
        Assert.assertTrue("Not enabled!", clickTransService.isEnabled());
        Assert.assertTrue("Not selected!", clickTransService.isSelected());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='legal']")));
        WebElement clickLegalService = driver.findElement(By.xpath("//input[@value='legal']"));
        wait.until(ExpectedConditions.elementToBeClickable(clickLegalService));
        Tools.jsClick(clickLegalService);
        Assert.assertTrue("Not enabled!", clickLegalService.isEnabled());
        Assert.assertTrue("Not selected!", clickLegalService.isSelected());

        action.scrollByAmount(0, 100);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-first-name")));
        WebElement firstNameBox = driver.findElement(By.id("edit-first-name"));
        wait.until(ExpectedConditions.elementToBeClickable(firstNameBox));
        firstNameBox.sendKeys(firstName + Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phone_work")));
        WebElement phoneNumberBox = driver.findElement(By.name("phone_work"));
        wait.until(ExpectedConditions.elementToBeClickable(phoneNumberBox));
        phoneNumberBox.sendKeys(phoneNumber + Keys.ENTER);

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter imgFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.mm.ss");

        if (phoneNumberBox.isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOf(phoneNumberBox));
            TakesScreenshot ss = (TakesScreenshot) driver;
            File file = ss.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(file, new File("formImage\\" + localDateTime.format(imgFormat) + "screenShot.jpg"));
        }

        action.scrollByAmount(100, 0);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='English/English']")));
        WebElement changeLanguageBtn = driver.findElement(By.xpath("//button[text()='English/English']"));
        wait.until(ExpectedConditions.elementToBeClickable(changeLanguageBtn));
        action.moveToElement(changeLanguageBtn).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul>li[hreflang] a[hreflang='it']")));
        WebElement selectItalian = driver.findElement(By.cssSelector("ul>li[hreflang] a[hreflang='it']"));
        wait.until(ExpectedConditions.elementToBeClickable(selectItalian));
        Tools.jsClick(selectItalian);

        WebElement link = driver.findElement(By.linkText("Soluzioni"));
        action.keyDown(Keys.CONTROL).click(link).keyUp(Keys.CONTROL).perform();
        Set<String> windowHandle = driver.getWindowHandles();
        for (String newPage : windowHandle) {
            driver.switchTo().window(newPage);
        }

        tearDown();
    }
}