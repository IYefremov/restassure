package com.cyberiansoft.test.vnext.webelements.decoration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

//TODO: COMPLETE REFACTORING NEEDED!!!
public class ListInvocationHandler implements InvocationHandler {
    private final ElementLocator locator;
    private final Class<IWebElement> clazz;

    public ListInvocationHandler(ElementLocator locator, Class<IWebElement> clazz) {
        this.locator = locator;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<WebElement> elements = locator.findElements();
        List<IWebElement> customs = new ArrayList<>();

        for (WebElement element : elements) {
            customs.add(WrapperFactory.createInstance(clazz, element));
        }
        try {
            return method.invoke(customs, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
