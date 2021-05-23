package com.jQueryTest;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class JQueryFirstAssignmentTest {
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public void login(){
        //set up webdriver
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\gturd\\IdeaProjects\\FirstAssignment\\webdriver\\chromedriver.exe");
        //set ChromeDriver

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //open the testing site
        driver.get("https://jqueryui.com/");
        boolean isJQuerySiteDisplayed = driver.findElement(By.linkText("jQuery UI")).isDisplayed();
        System.out.println("JQuery UI site is displayed: " + isJQuerySiteDisplayed);
    }
    @Test
    public void draggableTest(){
        //Click on the draggable link and display draggable page
        WebElement clickOnDraggableLink = driver.findElement(By.linkText("Draggable"));
        clickOnDraggableLink.click();
        boolean isDraggableTitleDisplayed = driver.findElement(By.xpath("//div[@id='content']/h1")).isDisplayed();
        System.out.println("Draggable title is displayed: " + isDraggableTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        //switch to frame
        driver.switchTo().frame(iframe);
        WebElement dragMeAround = driver.findElement(By.id("draggable"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(dragMeAround).dragAndDropBy(dragMeAround,100,100).release().build().perform();
        Assert.assertTrue(dragMeAround.getText().contains("Drag me around"));
        //switch to default window
        driver.switchTo().defaultContent();

    }
    @Test
    public void droppableTest(){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement clickOnDroppableLink = driver.findElement(By.linkText("Droppable"));
        clickOnDroppableLink.click();
        boolean isDroppableTitleDisplayed = driver.findElement(By.xpath("//h1[text()='Droppable']")).isDisplayed();
        System.out.println("Droppable title is displayed: " + isDroppableTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement dragMeToMyTarget = driver.findElement(By.id("draggable"));
        WebElement dropHere = driver.findElement(By.id("droppable"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(dragMeToMyTarget).dragAndDrop(dragMeToMyTarget,dropHere).release().build().perform();
        Assert.assertTrue(dropHere.getText().contains("Dropped!"));
        driver.switchTo().defaultContent();

    }
    @Test
    public void resizableTest(){
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement clickOnResizableLink = driver.findElement(By.linkText("Resizable"));
        clickOnResizableLink.click();
        boolean isResizableTitleDisplayed = driver.findElement(By.xpath("//h1[text()='Resizable']")).isDisplayed();
        System.out.println("Resizable title is displayed: " + isResizableTitleDisplayed);
        WebElement element = driver.findElement(By.xpath("//h1[text()='Resizable']"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        driver.switchTo().frame(0);
        WebElement clickOnResizablePoint = driver.findElement(By.xpath("//div[@id='resizable']/div[3]"));
        wait.until(ExpectedConditions.visibilityOf(clickOnResizablePoint));
        WebElement beforeResize = driver.findElement(By.xpath("//div[@id='resizable']/div[3]"));
        int beforeResizingWidth = beforeResize.getSize().getWidth();
        System.out.println("before resize width is: " + beforeResizingWidth);
        Actions actions = new Actions(driver);
        actions.clickAndHold(clickOnResizablePoint).dragAndDropBy(beforeResize, 200,50).release().build().perform();
        WebElement afterResize = driver.findElement(By.id("resizable"));
        int afterResizingWidth = afterResize.getSize().getWidth();
        System.out.println("after resize width is: " + afterResizingWidth);
        softAssert.assertTrue(beforeResizingWidth != afterResizingWidth);
        softAssert.assertAll();
        driver.switchTo().defaultContent();
    }
    @Test
    public void selectableTest(){
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement clickOnSelectableLink = driver.findElement(By.linkText("Selectable"));
        clickOnSelectableLink.click();
        boolean isSelectableTitleDisplayed = driver.findElement(By.xpath("//h1[text()='Selectable']")).isDisplayed();
        System.out.println("Selectable title is displayed: " + isSelectableTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        List<WebElement> selectableItems = driver.findElements(By.xpath("//li[@class='ui-widget-content ui-selectee']"));
        int totalItems = selectableItems.size();
        for (WebElement eachItem : selectableItems) {
            System.out.println(eachItem.getText());
            eachItem.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        softAssert.assertTrue(totalItems==7);
        softAssert.assertAll();
        driver.switchTo().defaultContent();
        WebElement highlightExamplesContent = driver.findElement(By.xpath("//div[@class = 'demo-list']"));
        wait.until(ExpectedConditions.visibilityOf(highlightExamplesContent));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: purple; border: 2px solid blue;');"
                ,highlightExamplesContent);
        List<WebElement> exampleLinks = driver.findElements(By.xpath("//div[@class='demo-list']/ul/li"));
        int totalLinks = exampleLinks.size();
        for (WebElement example : exampleLinks){
            System.out.println(example.getText());
            example.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals(totalLinks, 3);

    }
    @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
    }

}