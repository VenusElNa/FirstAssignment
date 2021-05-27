package com.jQueryTest;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
    public void login() {
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

    @Test()
    public void draggableTest() {
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
        actions.clickAndHold(dragMeAround).dragAndDropBy(dragMeAround, 100, 100).release().build().perform();
        Assert.assertTrue(dragMeAround.getText().contains("Drag me around"));
        //switch to default window
        driver.switchTo().defaultContent();

    }

    @Test(priority = 1)
    public void droppableTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
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
        actions.clickAndHold(dragMeToMyTarget).dragAndDrop(dragMeToMyTarget, dropHere).release().build().perform();
        Assert.assertTrue(dropHere.getText().contains("Dropped!"));
        driver.switchTo().defaultContent();

    }

    @Test(priority = 2)
    public void resizableTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement clickOnResizableLink = driver.findElement(By.linkText("Resizable"));
        wait.until(ExpectedConditions.visibilityOf(clickOnResizableLink));
        clickOnResizableLink.click();
        boolean isResizableTitleDisplayed = driver.findElement(By.xpath("//h1[text()='Resizable']")).isDisplayed();
        System.out.println("Resizable title is displayed: " + isResizableTitleDisplayed);
        WebElement element = driver.findElement(By.xpath("//h1[text()='Resizable']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        driver.switchTo().frame(0);
        WebElement clickOnResizablePoint = driver.findElement(By.xpath("//div[@id='resizable']/div[3]"));
        wait.until(ExpectedConditions.visibilityOf(clickOnResizablePoint));
        WebElement beforeResize = driver.findElement(By.xpath("//div[@id='resizable']/div[3]"));
        int beforeResizingWidth = beforeResize.getSize().getWidth();
        System.out.println("before resize width is: " + beforeResizingWidth);
        Actions actions = new Actions(driver);
        actions.clickAndHold(clickOnResizablePoint).dragAndDropBy(beforeResize, 200, 50).release().build().perform();
        WebElement afterResize = driver.findElement(By.id("resizable"));
        int afterResizingWidth = afterResize.getSize().getWidth();
        System.out.println("after resize width is: " + afterResizingWidth);
        softAssert.assertTrue(beforeResizingWidth != afterResizingWidth);
        softAssert.assertAll();
        driver.switchTo().defaultContent();
    }

    @Test(priority = 3)
    public void selectableTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement clickOnSelectableLink = driver.findElement(By.linkText("Selectable"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSelectableLink));
        clickOnSelectableLink.click();
        boolean isSelectableTitleDisplayed = driver.findElement(By.xpath("//h1[text()='Selectable']")).isDisplayed();
        System.out.println("Selectable title is displayed: " + isSelectableTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        List<WebElement> selectableItems = driver.findElements(By.xpath("//li[@class='ui-widget-content ui-selectee']"));
        int totalItems = selectableItems.size();
        System.out.println(totalItems);
        for (WebElement eachItem : selectableItems) {
            System.out.println(eachItem.getText());
            eachItem.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        softAssert.assertTrue(totalItems == 7);
        softAssert.assertAll();
        driver.switchTo().defaultContent();
        WebElement highlightExamplesContent = driver.findElement(By.xpath("//div[@class = 'demo-list']"));
        wait.until(ExpectedConditions.visibilityOf(highlightExamplesContent));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: purple; border: 2px solid blue;');"
                , highlightExamplesContent);
        List<WebElement> exampleLinks = driver.findElements(By.xpath("//div[@class='demo-list']/ul/li"));
        int totalLinks = exampleLinks.size();
        for (WebElement example : exampleLinks) {
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

    @Test(priority = 4)
    public void sortableTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement clickOnSortableLink = driver.findElement(By.xpath("//a[text()='Sortable']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSortableLink));
        clickOnSortableLink.click();
        boolean isSortableTitleDisplayed = driver.findElement(By.xpath("//div[@id='content']/h1")).isDisplayed();
        System.out.println("Sortable title is displayed: " + isSortableTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement item1 = driver.findElement(By.xpath("//ul[@id='sortable']/li[text()='Item 1']"));
        wait.until(ExpectedConditions.visibilityOf(item1));
        WebElement item4 = driver.findElement(By.xpath("//ul[@id='sortable']/li[text()='Item 4']"));
        Actions actions = new Actions(driver);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actions.dragAndDrop(item1,item4).release().build().perform();
        WebElement item2InFirstPlace = driver.findElement(By.xpath("//ul[@id='sortable']/li[1]"));
        Assert.assertTrue(item2InFirstPlace.getText().contains("Item 2"));
    }

    @Test(priority = 5)
    public void accordionTest() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement clickOnAccordionLink = driver.findElement(By.xpath("//a[text()='Accordion']"));
        clickOnAccordionLink.click();
        boolean isAccordionTitleDisplayed = driver.findElement(By.xpath("//div[@id='content']/h1")).isDisplayed();
        System.out.println("The title Accordion is displayed: " + isAccordionTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement clickOnSectionThree = driver.findElement(By.xpath("//h3[text()='Section 3']"));
        clickOnSectionThree.click();
        boolean sectionThreeContentDisplayed = driver.findElement(By.xpath
                ("//div[@class='ui-accordion-content ui-corner-bottom ui-helper-reset ui-widget-content ui-accordion-content-active']"))
                .isDisplayed();
        Assert.assertTrue(sectionThreeContentDisplayed);
        driver.switchTo().defaultContent();
        WebElement highlightExamplesContent = driver.findElement(By.xpath("//div[@class = 'demo-list']"));
        wait.until(ExpectedConditions.visibilityOf(highlightExamplesContent));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 2px solid red;');"
                , highlightExamplesContent);

    }
    @Test(priority = 6)
    public void autocompleteTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnAutocompleteLink = driver.findElement(By.linkText("Autocomplete"));
        clickOnAutocompleteLink.click();
        boolean isAutocompleteTitleDisplayed = driver.findElement(By.xpath("//div[@id='content']/h1")).isDisplayed();
        System.out.println("The title of Autocomplete is displayed: " + isAutocompleteTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement clickOnTagsBox = driver.findElement(By.xpath("//input[@id='tags']"));
        clickOnTagsBox.click();
        clickOnTagsBox.sendKeys("J");
        // Create object on Actions class
        Actions actions = new Actions(driver);
        // find the element which we want to Select from auto suggestion
        WebElement clickChosenElement = driver.findElement(By.xpath
                ("//ul[@class='ui-menu ui-widget ui-widget-content ui-autocomplete ui-front']/li[3]/div"));
        wait.until(ExpectedConditions.visibilityOf(clickChosenElement));
        clickChosenElement.click();
        // use Mouse hover action for that element
        actions.moveToElement(clickChosenElement).build().perform();
        // finally click on that element
        actions.click(clickChosenElement).build().perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().defaultContent();
    }
    @Test(priority = 7)
    public void buttonTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnButtonLink = driver.findElement(By.linkText("Button"));
        wait.until(ExpectedConditions.visibilityOf(clickOnButtonLink));
        clickOnButtonLink.click();
        boolean isButtonTitleDisplayed = driver.findElement(By.xpath("//div[@id='content']/h1")).isDisplayed();
        System.out.println("The title of Button is displayed: " + isButtonTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement clickOnAButtonElement = driver.findElement(By.xpath("//div[@class='widget']/button"));
        wait.until(ExpectedConditions.visibilityOf(clickOnAButtonElement));
        Actions actions = new Actions(driver);
        actions.click(clickOnAButtonElement).release().build().perform();
        Assert.assertTrue(clickOnAButtonElement.getText().contains("A button element"));
        WebElement clickOnAnAnchor = driver.findElement(By.xpath("//div[@class='widget']/a[text()='An anchor']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnAnAnchor));
        actions.click(clickOnAnAnchor).release().build().perform();
        Assert.assertTrue(clickOnAnAnchor.getText().contains("An anchor"));
        driver.switchTo().defaultContent();
    }
    @Test(priority = 8)
    public void checkboxRadioTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnCheckboxRadioLink = driver.findElement(By.linkText("Checkboxradio"));
        wait.until(ExpectedConditions.visibilityOf(clickOnCheckboxRadioLink));
        clickOnCheckboxRadioLink.click();
        boolean isCheckboxRadioTitleDisplayed = driver.findElement(By.xpath("//h1[@class='entry-title']")).isDisplayed();
        System.out.println("The title of Button is displayed: " + isCheckboxRadioTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement checkRadioGroupButton = driver.findElement(By.xpath("//label[text()='London']"));
        wait.until(ExpectedConditions.visibilityOf(checkRadioGroupButton));
        Actions actions = new Actions(driver);
        actions.click(checkRadioGroupButton).release().build().perform();
        Assert.assertTrue(checkRadioGroupButton.getText().contains("London"));
        WebElement checkCheckbox = driver.findElement(By.xpath("//label[text()='5 Star']"));
        wait.until(ExpectedConditions.visibilityOf(checkCheckbox));
        actions.click(checkCheckbox).release().build().perform();
        Assert.assertTrue(checkCheckbox.getText().contains("5 Star"));
        driver.switchTo().defaultContent();
    }
    @Test(priority = 9)
    public void controlgroupTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnControlgroupLink = driver.findElement(By.xpath("//a[text()='Controlgroup']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnControlgroupLink));
        clickOnControlgroupLink.click();
        boolean isControlgroupTitleDisplayed = driver.findElement(By.xpath("//div[@id='content']/h1")).isDisplayed();
        System.out.println("The title of Controlgroup is displayed: " + isControlgroupTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement clickOnCompactCarButton = driver.findElement(By.xpath("//span[@id='car-type-button']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnCompactCarButton));
        clickOnCompactCarButton.click();
        WebElement clickACarType = driver.findElement(By.xpath("//ul[@id='car-type-menu']/li[5]"));
        wait.until(ExpectedConditions.visibilityOf(clickACarType));
        clickACarType.click();
        WebElement clickRadioButton = driver.findElement(By.xpath
                ("//div[@class='controlgroup ui-controlgroup ui-controlgroup-horizontal ui-helper-clearfix']/label[2]"));
        wait.until(ExpectedConditions.visibilityOf(clickRadioButton));
        clickRadioButton.click();
        Assert.assertTrue(clickRadioButton.getText().contains("Automatic"));
        WebElement clickCheckboxRadioButton = driver.findElement(By.xpath
                ("//div[@class='controlgroup ui-controlgroup ui-controlgroup-horizontal ui-helper-clearfix']/label[3]"));
        wait.until(ExpectedConditions.visibilityOf(clickCheckboxRadioButton));
        clickCheckboxRadioButton.click();
        Assert.assertTrue(clickCheckboxRadioButton.getText().contains("Insurance"));
        WebElement inputNumberOfCar = driver.findElement(By.xpath("//input[@id='horizontal-spinner']"));
        wait.until(ExpectedConditions.visibilityOf(inputNumberOfCar));
        inputNumberOfCar.sendKeys("1");
        boolean isNumberOfCarDisplayed = driver.findElement(By.xpath("//input[@id='horizontal-spinner']")).isDisplayed();
        Assert.assertTrue(isNumberOfCarDisplayed);
        WebElement clickOnBookNowButton = driver.findElement(By.xpath
                ("//button[@class='ui-widget ui-controlgroup-item ui-button ui-corner-right']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnBookNowButton));
        clickOnBookNowButton.click();
        driver.switchTo().defaultContent();
    }
    @Test(priority = 10)
    public void datePickerTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnDatepickerLink = driver.findElement(By.xpath("//a[text()='Datepicker']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnDatepickerLink));
        clickOnDatepickerLink.click();
        boolean isDatepickerTitleDisplayed = driver.findElement(By.xpath("//h1[text()='Datepicker']")).isDisplayed();
        System.out.println("The title of Datepicker is displayed: " + isDatepickerTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement clickOnDatepickerField = driver.findElement(By.id("datepicker"));
        wait.until(ExpectedConditions.visibilityOf(clickOnDatepickerField));
        clickOnDatepickerField.click();
        WebElement pickTheMonth = driver.findElement(By.xpath("//span[text()='May']"));
        pickTheMonth.click();
        Assert.assertTrue(pickTheMonth.getText().contains("May"));
        WebElement pickTheYear = driver.findElement(By.xpath("//span[text()='2021']"));
        pickTheYear.click();
        Assert.assertTrue(pickTheYear.getText().contains("2021"));
        WebElement pickTheDate = driver.findElement(By.xpath
                ("//table[@class='ui-datepicker-calendar']/tbody/tr[5]/td[6]"));
        pickTheDate.click();
        Assert.assertTrue(pickTheDate.getText().contains("28"));
        driver.switchTo().defaultContent();
        List<WebElement> links = driver.findElements(By.xpath("//div[@class='demo-list']/ul/li"));
        int totalLinks = links.size();
        System.out.println("The total links of Examples part: " + totalLinks);
        Assert.assertEquals(totalLinks,14);
    }
    @Test(priority = 11)
    public void dialogTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnDialogLink = driver.findElement(By.linkText("Dialog"));
        wait.until(ExpectedConditions.visibilityOf(clickOnDialogLink));
        clickOnDialogLink.click();
        boolean isDialogTitleDisplayed = driver.findElement(By.xpath("//h1[@class='entry-title']")).isDisplayed();
        System.out.println("The title of Dialog is displayed: " + isDialogTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement clickOnResizableButton = driver.findElement(By.xpath("//div[@class='ui-resizable-handle ui-resizable-e']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnResizableButton));
        clickOnResizableButton.click();
        Actions actions = new Actions(driver);
        WebElement beforeResize = driver.findElement(By.xpath("//div[@id='dialog']"));
        int beforeResizingWidth = beforeResize.getSize().getWidth();
        System.out.println("Before resizing width is: " + beforeResizingWidth);
        actions.clickAndHold(clickOnResizableButton).dragAndDropBy(beforeResize,400,50).release().build().perform();
        WebElement afterResize = driver.findElement(By.xpath("//div[@class='ui-dialog-content ui-widget-content']"));
        int afterResizingWidth = afterResize.getSize().getWidth();
        System.out.println("After resizing width is: " + afterResizingWidth);
        Assert.assertTrue(beforeResizingWidth != afterResizingWidth);
        driver.switchTo().defaultContent();
        List<WebElement> examplesLinks = driver.findElements(By.xpath("//div[@class='demo-list']/ul/li"));
        int totalLinks = examplesLinks.size();
        for (WebElement link : examplesLinks){
            System.out.println(link.getText());
            link.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assert.assertEquals(totalLinks,5);
    }
    @Test(priority = 12)
    public void menuTest(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement clickOnMenuLink = driver.findElement(By.xpath("//a[text()='Menu']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnMenuLink));
        clickOnMenuLink.click();
        boolean isMenuTitleDisplayed = driver.findElement(By.xpath("//h1[@class='entry-title']")).isDisplayed();
        System.out.println("The title of Menu is displayed: " + isMenuTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        driver.switchTo().frame(iframe);
        WebElement clickOnMusicTab = driver.findElement(By.xpath("//div[text()='Music']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnMusicTab));
        clickOnMusicTab.click();
        WebElement clickOnPopTab = driver.findElement(By.xpath("//div[text()='Pop']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnPopTab));
        clickOnPopTab.click();
        boolean isPopTabClicked = driver.findElement(By.xpath("//div[text()='Pop']")).isDisplayed();
        Assert.assertTrue(isPopTabClicked);
    }
    @Test
    public void selectMenuTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnSelectmenuLink = driver.findElement(By.xpath("//a[text()='Selectmenu']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSelectmenuLink));
        clickOnSelectmenuLink.click();
        boolean isSelectmenuTitleDisplayed = driver.findElement(By.xpath("//h1[@class='entry-title']")).isDisplayed();
        System.out.println("The title of Selectmenu is displayed: " + isSelectmenuTitleDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement clickOnSelectASpeedButton = driver.findElement(By.xpath("//span[@id='speed-button']"));
        clickOnSelectASpeedButton.click();
        WebElement clickOnItemToSelect = driver.findElement(By.xpath("//div[text()='Faster']"));
        clickOnItemToSelect.click();
        boolean isItemDisplayed = driver.findElement(By.xpath("//span[text()='Faster']")).isDisplayed();
        Assert.assertTrue(isItemDisplayed);
        WebElement clickOnFilesButton = driver.findElement(By.xpath("//span[@id='files-button']"));
        clickOnFilesButton.click();
        WebElement clickOnFileToChoose = driver.findElement(By.xpath("//div[text()='ui.jQuery.js'"));
        clickOnFileToChoose.click();
        boolean isFileDisplayed = driver.findElement(By.xpath("//span[text()='ui.jQuery.js']")).isDisplayed();
        Assert.assertTrue(isFileDisplayed);
        WebElement clickOnNumberButton = driver.findElement(By.xpath("//span[@id='number-button']"));
        clickOnNumberButton.click();
        WebElement selectANumber = driver.findElement(By.xpath("//div[text()='3']"));
        selectANumber.click();
        boolean isANumberSelected = driver.findElement(By.xpath("//span[text()='3']")).isSelected();
        Assert.assertTrue(isANumberSelected);
        WebElement clickOnSelectATitle = driver.findElement(By.xpath("//span[@id='salutation-button']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSelectATitle));
        clickOnSelectATitle.click();
        WebElement selectATitle = driver.findElement(By.xpath("//div[text()='Dr.']"));
        selectATitle.click();
        boolean isATitleDisplayed = driver.findElement(By.xpath("//span[text()='Dr.']")).isDisplayed();
        Assert.assertTrue(isATitleDisplayed);
        driver.switchTo().defaultContent();
    }
    @Test
    public void sliderTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnSlidermenuLink = driver.findElement(By.xpath("//a[text()='Slider']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSlidermenuLink));
        clickOnSlidermenuLink.click();
        boolean isTitleSliderDisplayed = driver.findElement(By.xpath("//h1[text()='Slider']")).isDisplayed();
        System.out.println("The title of Slider is displayed: " + isTitleSliderDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement clickOnSliderHandlePoint = driver.findElement
                (By.xpath("//span[@class='ui-slider-handle ui-corner-all ui-state-default']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSliderHandlePoint));
        clickOnSliderHandlePoint.click();
        int beforeSliderHandleWidth = clickOnSliderHandlePoint.getSize().getWidth();
        System.out.println("Before slider handle width: " + beforeSliderHandleWidth);
        Actions actions = new Actions(driver);
        actions.clickAndHold(clickOnSliderHandlePoint).dragAndDropBy
                (clickOnSliderHandlePoint,400,0).release().build().perform();
        WebElement afterSliderHandlePoint = driver.findElement(By.xpath
                ("//span[@class='ui-slider-handle ui-corner-all ui-state-default ui-state-hover']"));
        int afterSliderHandleWidth = afterSliderHandlePoint.getSize().getWidth();
        System.out.println("After slider handle width: " + afterSliderHandleWidth);
        Assert.assertTrue(beforeSliderHandleWidth != afterSliderHandleWidth);
        driver.switchTo().defaultContent();

    }
    @Test
    public void spinnerTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnSpinnerLink = driver.findElement(By.xpath("//a[text()='Spinner']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnSpinnerLink));
        clickOnSpinnerLink.click();
        boolean isTitleSpinnerDisplayed = driver.findElement(By.xpath("//h1[text()='Spinner']")).isDisplayed();
        System.out.println("The title of Slider is displayed: " + isTitleSpinnerDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement enterNumericValueInSpinnerTab = driver.findElement(By.xpath("//input[@id='spinner']"));
        enterNumericValueInSpinnerTab.sendKeys("12");
        boolean isNumericValueDisplayed = driver.findElement(By.xpath("//input[@name='value']")).isDisplayed();
        Assert.assertTrue(isNumericValueDisplayed);
    }
    @Test
    public void tabsTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnTabsLink = driver.findElement(By.xpath("//a[text()='Tabs']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnTabsLink));
        clickOnTabsLink.click();
        boolean isTitleTabsDisplayed = driver.findElement(By.xpath("//h1[text()='Tabs']")).isDisplayed();
        System.out.println("The title of Slider is displayed: " + isTitleTabsDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement clickOnProinDolorTab = driver.findElement(By.xpath
                ("//li[@class='ui-tabs-tab ui-corner-top ui-state-default ui-tab ui-tabs-active ui-state-active']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnProinDolorTab));
        clickOnProinDolorTab.click();
        boolean isProinDolorTabDisplayed = driver.findElement(By.xpath("//a[text()='Proin dolor']")).isDisplayed();
        Assert.assertTrue(isProinDolorTabDisplayed);
        driver.switchTo().defaultContent();
    }
    @Test
    public void tooltipTest(){
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement clickOnTooltipLink = driver.findElement(By.xpath("//a[text()='Tooltip']"));
        wait.until(ExpectedConditions.visibilityOf(clickOnTooltipLink));
        clickOnTooltipLink.click();
        boolean isTooltipDisplayed = driver.findElement(By.xpath("//h1[text()='Tooltip']")).isDisplayed();
        System.out.println("The title of Slider is displayed: " + isTooltipDisplayed);
        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='demo-frame']"));
        wait.until(ExpectedConditions.visibilityOf(iframe));
        driver.switchTo().frame(iframe);
        WebElement enterYourAge = driver.findElement(By.xpath("//input[@id='age']"));
        wait.until(ExpectedConditions.visibilityOf(enterYourAge));
        enterYourAge.sendKeys("45");
        boolean isTitleContentDisplayed = driver.findElement(By.xpath
                ("//input[@title='We ask for your age only for statistical purposes.']")).isDisplayed();
        Assert.assertTrue(isTitleContentDisplayed);
        driver.switchTo().defaultContent();
    }

   @AfterClass
    public void tearDown(){
        driver.close();
        driver.quit();
    }

}