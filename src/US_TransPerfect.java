import Utility.BaseDriver;
import Utility.Tools;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class US_TransPerfect extends BaseDriver {

    public static Actions action = new Actions(driver);
    String firstName = "Merve";
    long randomNumber = ThreadLocalRandom.current().nextLong(10000000000L, 100000000000L);

    @Test
    public void transPerfect() throws IOException {
        //1-Go to www.transperfect.com
        driver.get("https://www.transperfect.com/");
        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/"));

        //2-Close the popup if needed
        List<WebElement> popUp = driver.findElements(By.xpath("//button[.='Deny all']"));
        if (!popUp.isEmpty()) {
            popUp.get(0).click();
        }

        //3-Click on Industries in the top navigation bar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@href='/industries'])[1]")));
        WebElement industriesBtn = driver.findElement(By.xpath("(//a[@href='/industries'])[1]"));
        wait.until(ExpectedConditions.elementToBeClickable(industriesBtn));
        industriesBtn.click();

        //4-Click on Retail & E-commerce
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='t-page-cards-item-content'])[2]")));
        WebElement retailCommerceBtn = driver.findElement(By.xpath("(//div[@class='t-page-cards-item-content'])[2]"));
        Tools.scrollElement(retailCommerceBtn);
        wait.until(ExpectedConditions.elementToBeClickable(retailCommerceBtn));
        action.click(retailCommerceBtn).perform();

        //5-Wait 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        //6-Scroll down/move the screen until Client Stories are visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[.='Client Stories']")));
        WebElement visibleClientStories = driver.findElement(By.xpath("//div[.='Client Stories']"));
        Tools.scrollElement(visibleClientStories);

        //7-Click on the search engine icon in the top navigation bar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='t-search-link']")));
        WebElement searchIcon = driver.findElement(By.cssSelector("span[class='t-search-link']"));
        wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        action.click(searchIcon).perform();

        //8-Enter text "translation" in the Search text... textbox
        wait.until(ExpectedConditions.elementToBeClickable(By.name("search_api_fulltext")));
        WebElement searchBox = driver.findElement(By.name("search_api_fulltext"));
        searchBox.sendKeys("translation");

        //9-Delete the text you just entered
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[text()='Translation & Language Services'])[1]")));
        searchBox.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

        //10-Enter "quote" in the Search text... textbox
        wait.until(ExpectedConditions.elementToBeClickable(By.name("search_api_fulltext")));
        searchBox.sendKeys("quote" + Keys.ENTER);

        //11-Wait for the "Request a Free Quote" search result to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[.='Request a Free Quote']")));
        WebElement selectRequest = driver.findElement(By.xpath("//a[text()='Request a Free Quote']"));

        //12-Click on Request a Free Quote
        wait.until(ExpectedConditions.elementToBeClickable(selectRequest));
        selectRequest.click();

        //13-Hover the mouse button over Website Localization to cause the popup with the description to appear
        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/request-quote"));
        WebElement describeWebLoc = driver.findElement(By.xpath("(//label[@class='form-check-label'])[2]"));
        action.moveToElement(describeWebLoc).perform();

        //14-Tick the boxes for Translation Services and Legal Services
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='translation']")));
        WebElement clickTransService = driver.findElement(By.xpath("//input[@value='translation']"));
        wait.until(ExpectedConditions.elementToBeClickable(clickTransService));
        Tools.jsClick(clickTransService);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='legal']")));
        WebElement clickLegalService = driver.findElement(By.xpath("//input[@value='legal']"));
        wait.until(ExpectedConditions.elementToBeClickable(clickLegalService));
        Tools.jsClick(clickLegalService);

        action.scrollByAmount(0, 100);

        //15-Enter text into First Name text box
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-first-name")));
        WebElement firstNameBox = driver.findElement(By.id("edit-first-name"));
        wait.until(ExpectedConditions.elementToBeClickable(firstNameBox));
        firstNameBox.sendKeys(firstName + Keys.ENTER);

        //16-Generate a random number and enter it into Telephone text box
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("phone_work")));
        WebElement phoneNumberBox = driver.findElement(By.name("phone_work"));
        wait.until(ExpectedConditions.elementToBeClickable(phoneNumberBox));
        String phoneNumber = String.valueOf(randomNumber);
        phoneNumberBox.sendKeys(phoneNumber + Keys.ENTER);

        //17-Take a screenshot and save it to your desktop
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter imgFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH.mm.ss");

        TakesScreenshot ss = (TakesScreenshot) driver;
        File file = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("formImage\\" + localDateTime.format(imgFormat) + "screenShot.jpg"));

        File fileInMemory = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String desktop = System.getProperty("user.home") + "/Desktop";

        try {
            FileUtils.copyFile(fileInMemory, new File(desktop + "\\screenShot.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        action.scrollByAmount(100, 0);

        //18-Change the website language from English to Italian
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.='English/English']")));
        WebElement changeLanguageBtn = driver.findElement(By.xpath("//button[.='English/English']"));
        wait.until(ExpectedConditions.elementToBeClickable(changeLanguageBtn));
        action.moveToElement(changeLanguageBtn).perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul>li[hreflang] a[hreflang='it']")));
        WebElement selectItalian = driver.findElement(By.cssSelector("ul>li[hreflang] a[hreflang='it']"));
        wait.until(ExpectedConditions.elementToBeClickable(selectItalian));
        selectItalian.click();

        //19-Open the Solutions (Soluzioni) page in a new tab
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Soluzioni")));
        WebElement link = driver.findElement(By.linkText("Soluzioni"));
        action.keyDown(Keys.CONTROL).click(link).keyUp(Keys.CONTROL).perform();

        //20-Switch to new opened TAB
        Set<String> windowHandle = driver.getWindowHandles();
        for (String newPage : windowHandle) {
            driver.switchTo().window(newPage);
        }

        //21-Close the browser
        wait.until(ExpectedConditions.urlToBe("https://www.transperfect.com/it/solutions"));
        driver.quit();
    }
}
