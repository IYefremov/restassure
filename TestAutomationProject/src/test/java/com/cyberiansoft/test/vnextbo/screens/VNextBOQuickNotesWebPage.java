package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class VNextBOQuickNotesWebPage extends VNextBOBaseWebPage {

    @FindBy(xpath = "//button[@data-bind='click: onAddNewQuickNoteClick']")
    private WebElement addQuickNotesButton;

    @FindBy(xpath = "//div[@data-bind='text: noteText']")
    private List<WebElement> quickNotes;

    public VNextBOQuickNotesWebPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
//        wait.until(ExpectedConditions.visibilityOf(addQuickNotesButton));
    }

    public VNextBOQuickNotesWebPage moveQuickNotes(String quickNoteSource, String quickNoteTarget) {
        wait.until(ExpectedConditions.visibilityOfAllElements(quickNotes));
        WebElement source = getQuickNoteByName(quickNoteSource);
        WebElement target = getQuickNoteByName(quickNoteTarget);
        System.out.println("source: "+source + "\n" + source.isDisplayed());
        System.out.println("target: "+target + "\n" + target.isDisplayed());
        System.out.println(source.isDisplayed());
        scrollToElement(source);
        waitABit(1000);

        actions.dragAndDrop(source, target).build().perform();   //variant 1
//        new Actions(driver).clickAndHold(source).moveToElement(target).release().perform();   //variant 2
//        String xto=Integer.toString(source.getLocation().x);   //variant 3
//        String yto=Integer.toString(target.getLocation().y);
//        ((JavascriptExecutor)driver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)" +
//                        "if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document" +
//                        ".createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document" +
//                        ".defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject()," +
//                        "a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1," +
//                        "a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} " +
//
//                        "var eventMatchers={HTMLEvents:/^(" +
//                        "?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/" +
//                        ",MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
//                        "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\"," +
//                        "arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
//                source,xto,yto);

        //variant 4
//        String script = "(function( $ ) {" +
//                "    $.fn.simulateDragDrop = function(options) {" +
//                "        return this.each(function() {" +
//                "            new $.simulateDragDrop(this, options);" +
//                "        });" +
//                "    };" +
//                "    $.simulateDragDrop = function(elem, options) {" +
//                "        this.options = options;" +
//                "        this.simulateEvent(elem, options);" +
//                "    };" +
//                "    $.extend($.simulateDragDrop.prototype, {" +
//                "        simulateEvent: function(elem, options) {" +
//                "            /*Simulating drag start*/" +
//                "            var type = 'dragstart';" +
//                "            var event = this.createEvent(type);" +
//                "            this.dispatchEvent(elem, type, event);" +
//                "            /*Simulating drop*/" +
//                "            type = 'drop';" +
//                "            var dropEvent = this.createEvent(type, {});" +
//                "            dropEvent.dataTransfer = event.dataTransfer;" +
//                "            this.dispatchEvent($(options.dropTarget)[0], type, dropEvent);" +
//                "            /*Simulating drag end*/" +
//                "            type = 'dragend';" +
//                "            var dragEndEvent = this.createEvent(type, {});" +
//                "            dragEndEvent.dataTransfer = event.dataTransfer;" +
//                "            this.dispatchEvent(elem, type, dragEndEvent);" +
//                "        }," +
//                "        createEvent: function(type) {" +
//                "            var event = document.createEvent(\"CustomEvent\");" +
//                "            event.initCustomEvent(type, true, true, null);" +
//                "            event.dataTransfer = {" +
//                "                data: {" +
//                "                }," +
//                "                setData: function(type, val){" +
//                "                    this.data[type] = val;" +
//                "                }," +
//                "                getData: function(type){" +
//                "                    return this.data[type];" +
//                "                }" +
//                "            };" +
//                "            return event;" +
//                "        }," +
//                "        dispatchEvent: function(elem, type, event) {" +
//                "            if(elem.dispatchEvent) {" +
//                "                elem.dispatchEvent(event);" +
//                "            }else if( elem.fireEvent ) {" +
//                "                elem.fireEvent(\"on\"+type, event);" +
//                "            }" +
//                "        }" +
//                "    });" +
//                "})(jQuery);";

//        ((JavascriptExecutor)driver).executeScript(script+ "$('div:contains("+ quickNoteSource + ")').simulateDragDrop({ dropTarget: 'div:contains(" + quickNoteTarget + ")'});");
        waitForLoading();
        return this;
    }

    public VNextBOQuickNotesWebPage addQuickNotes(String ...quickNotes) {
        for (String quickNote : quickNotes) {
            clickAddNotesButton()
                    .typeDescription(quickNote)
                    .clickQuickNotesDialogAddButton();
        }
        return this;
    }

    public WebElement getQuickNoteByName(String quickNote) {
        return wait
                .until(ExpectedConditions.elementToBeClickable(driver
                        .findElement(By.xpath("//div[text()='" + quickNote + "']"))));
//                        .findElement(By.cssSelector("div:contains('" + quickNote + "')"))));
    }

    public int getQuickNoteOrderInList(String quickNote) {
        List<WebElement> quickNotesList = getQuickNotesList();
        for (int i = 0; i < quickNotesList.size(); i++) {
            try {
                wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(quickNotesList.get(i))));
                System.out.println("quickNote: " + i + " ********* " +quickNotesList.get(i).getText());
            } catch (Exception ignored) {}
            if (wait
                    .until(ExpectedConditions.visibilityOf(quickNotesList.get(i)))
                    .getText()
                    .equals(quickNote)) {
                return i;
            }
        }
        System.out.println();
        return 0;
    }

    public VNextBONewNotesDialog clickAddNotesButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addQuickNotesButton)).click();
        } catch (Exception e) {
            waitABit(1000);
            wait.until(ExpectedConditions.elementToBeClickable(addQuickNotesButton)).click();
        }
        waitForLoading();
        return PageFactory.initElements(driver, VNextBONewNotesDialog.class);
    }

    public boolean isQuickNoteDisplayed(String quickNoteName) {
        return getQuickNotesList()
                .stream()
                .anyMatch(e -> e.getText().equals(quickNoteName));
    }

    public boolean areQuickNotesDisplayed(String ...quickNotes) {
        for (String note : quickNotes) {
            boolean displayed = getQuickNotesList()
                    .stream()
                    .anyMatch(e -> e.getText().equals(note));
            Assert.assertTrue(displayed, "The quick Note " + note + " hasn't been displayed");
        }
        return true;
    }

    public VNextBOQuickNotesWebPage clickDeleteQuickNote(String quickNoteName) {
        clickFilteredQuickNote(quickNoteName, ".//../..//span[@title='Delete']");
        return this;
    }

    public VNextBONewNotesDialog clickEditQuickNote(String quickNoteName) {
        clickFilteredQuickNote(quickNoteName, ".//../..//span[@title='Edit']");
        return PageFactory.initElements(driver, VNextBONewNotesDialog.class);
    }

    public void clickFilteredQuickNote(String quickNoteName, String locator) {
        final WebElement webElement = getQuickNotesList()
                .stream()
                .filter(e -> e.getText().equals(quickNoteName))
                .findFirst().get().findElement(By.xpath(locator));
        scrollToElement(webElement);
        clickWithJS(webElement);
//                .ifPresent(el -> new Actions(driver)
//                        .moveToElement(el.findElement(By.xpath(locator)))
//                        .click()
//                        .build()
//                        .perform());
    }

    public VNextBOQuickNotesWebPage deleteQuickNote(String quickNoteName) {
        int numberOfQuickNotes = getNumberOfQuickNotesDisplayed(quickNoteName);
        clickDeleteQuickNote(quickNoteName);
        try {
            wait.until((num) -> numberOfQuickNotes > getNumberOfQuickNotesDisplayed(quickNoteName));
        } catch (Exception e) {
            // sometimes cannot be deleted because of intercom
            Assert.fail("The Quick Note hasn't been deleted");
        }
        return this;
    }

    public VNextBOQuickNotesWebPage deleteQuickNotesIfPresent(String quickNoteName) {
        while (isQuickNoteDisplayed(quickNoteName)) {
            deleteQuickNote(quickNoteName);
        }
        return this;
    }

    public VNextBOQuickNotesWebPage deleteQuickNotesIfPresent(String ...quickNotes) {
        for (String note: quickNotes) {
            deleteQuickNotesIfPresent(note);
        }
        return this;
    }

    public int getNumberOfQuickNotesDisplayed(String quickNoteName) {
        return getQuickNotesList()
                .stream()
                .filter(e -> e.getText().equals(quickNoteName))
                .collect(Collectors.toList())
                .size();
    }

    private List<WebElement> getQuickNotesList() {
        return wait
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfAllElements(quickNotes));
    }

    public int getNumberOfQuickNotesDisplayed() {
        return getQuickNotesList().size();
    }
}
