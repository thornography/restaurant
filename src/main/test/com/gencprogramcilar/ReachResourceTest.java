package com.gencprogramcilar;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;
import org.junit.Test;

public class ReachResourceTest
{
    @Test
    public void reachResourcesTest()
    {
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        String path= VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
//        Notification.show(path);
        System.out.println(path);
    }
}
