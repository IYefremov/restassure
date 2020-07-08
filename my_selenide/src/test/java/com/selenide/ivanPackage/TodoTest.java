package com.selenide.ivanPackage;

import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.CollectionCondition.*;

public class TodoTest {
    @Test
    void completeTask() {
        open("http://todomvc.com/examples/emberjs/");
        element("#new-todo").setValue("a").pressEnter();
        element("#new-todo").setValue("b").pressEnter();
        element("#new-todo").setValue("c").pressEnter();

        // check a, b, c elements were added
        elements("#todo-list>li").shouldHave(exactTexts("a", "b", "c"));
        // toggle b item
        element("ul[id='todo-list'] li:nth-child(2) .toggle").click();
        // verify completed task should be b
        element("ul[id='todo-list'] li.completed").shouldBe(exactText("b"));

        // verify active task should be a and c
        elements("#todo-list li:not( .completed)").shouldHave(exactTexts("a", "c"));
    }
}
