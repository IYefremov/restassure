package com.selenide.ivanPackage;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class toDoTest {

    @Test
    void completeTask() {
        open("http://todomvc.com/examples/emberjs/");
        element("#new-todo").setValue("a").pressEnter();
        element("#new-todo").setValue("b").pressEnter();
        element("#new-todo").setValue("c").pressEnter();

        elements("#todo-list>li").shouldHave(exactTexts("a", "b", "c"));

        // elements("#todo-list>li").findBy(exactText("b")).find(".toggle").click();
          elements("#ember6 > div > label").find(Condition.attribute(".toggle")).click();



        // completed task should be b
        elements("#todo-list>li").filterBy(cssClass("completed")).shouldHave(exactTexts("b"));
        // active tasks should be "a" and "c"

        elements("#todo-list>li").filterBy(not(cssClass("completed"))).shouldHave(exactTexts("a", "c"));
    }
}
