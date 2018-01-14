package com.gencprogramcilar.view;

import javax.servlet.annotation.WebServlet;

import com.gencprogramcilar.view.restaurant.ContentAbstract;
import com.gencprogramcilar.view.restaurant.OrderApprove;
import com.gencprogramcilar.view.restaurant.PageRestaurant;
import com.gencprogramcilar.view.restaurant.RestaurantFood;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

@Theme("mytheme")
@Widgetset("com.gencprogramcilar.MyAppWidgetset")
public class MyUI extends UI {

    VerticalLayout layout = new VerticalLayout();
    ContentAbstract restaurantContent;
    public PageRestaurant pageRestaurant;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {

        Login loginobj=new Login();
        layout.addComponent(loginobj);

        layout.setSizeFull();

        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);


    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public static MyUI getMyUI() {
        return (MyUI) MyUI.getCurrent();
    }


}
