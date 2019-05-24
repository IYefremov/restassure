package com.cyberiansoft.test.vnext.webelements.decoration;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;
import java.util.List;


//TODO: COMPLETE REFACTORING NEEDED!!!
public class FiledDecorator extends DefaultFieldDecorator {

    public FiledDecorator(SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        Class<IWebElement> decoratableClass = decoratableClass(field);
        if (decoratableClass != null) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) {
                return null;
            }

            if (List.class.isAssignableFrom(field.getType())) {
                return createList(loader, locator, decoratableClass);
            }

            return createElement(loader, locator, decoratableClass);
        }
        return super.decorate(loader, field);
    }

    @SuppressWarnings("unchecked")
    private Class<IWebElement> decoratableClass(Field field) {
        Class<?> clazz = field.getType();

        if (List.class.isAssignableFrom(clazz)) {
            if (field.getAnnotation(FindBy.class) == null) {
                return null;
            }

            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) {
                return null;
            }
            return (Class<IWebElement>) ((ParameterizedType) genericType).
                    getActualTypeArguments()[0];
        } else if (IWebElement.class.isAssignableFrom(clazz)) {
            return (Class<IWebElement>) clazz;
        } else {
            return null;
        }
    }

    protected IWebElement createElement(ClassLoader loader,
                                        ElementLocator locator,
                                        Class<IWebElement> clazz) {
        WebElement proxy = proxyForLocator(loader, locator);
        return WrapperFactory.createInstance(clazz, proxy);
    }

    @SuppressWarnings("unchecked")
    protected List<IWebElement> createList(ClassLoader loader,
                                           ElementLocator locator,
                                           Class<IWebElement> clazz) {
        InvocationHandler handler =
                new ListInvocationHandler(locator, clazz);
        List<IWebElement> elements =
                (List<IWebElement>) Proxy.newProxyInstance(
                        loader, new Class[]{List.class}, handler);
        return elements;
    }
}
