package com.cyberiansoft.test.bo.webelements.impl;

import static java.text.MessageFormat.format;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebElement;

import com.cyberiansoft.test.bo.webelements.IWebElement;
import com.cyberiansoft.test.bo.webelements.WebElementFactory;

public class DefaultElementFactory implements WebElementFactory {
    @Override
    public <E extends IWebElement> E create(final Class<E> elementClass, final WebElement wrappedElement) {
        try {
            return findImplementationFor(elementClass)
                    .getDeclaredConstructor(WebElement.class)
                    .newInstance(wrappedElement);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private <E extends IWebElement> Class<? extends E> findImplementationFor(final Class<E> elementClass) {
        try {
            return (Class<? extends E>) Class.forName(format("{0}.{1}Impl", getClass().getPackage().getName(), elementClass.getSimpleName()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
