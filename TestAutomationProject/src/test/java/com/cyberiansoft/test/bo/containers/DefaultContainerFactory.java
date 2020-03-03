package com.cyberiansoft.test.bo.containers;

import org.openqa.selenium.WebElement;

public class DefaultContainerFactory implements ContainerFactory {
    @Override
    public <C extends IWebContainer> C create(final Class<C> containerClass, final WebElement wrappedElement) {
        final C container = createInstanceOf(containerClass);
        container.init(wrappedElement);
        return container;
    }

    private <C extends IWebContainer> C createInstanceOf(final Class<C> containerClass) {
        try {
            return containerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
