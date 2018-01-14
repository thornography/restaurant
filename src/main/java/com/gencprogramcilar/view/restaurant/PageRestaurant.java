package com.gencprogramcilar.view.restaurant;

import com.gencprogramcilar.model.Restaurant;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class PageRestaurant extends HorizontalLayout
{
    VerticalLayout sideBar = new VerticalLayout();
    VerticalLayout content = new VerticalLayout();

    private Restaurant restaurant;

    public PageRestaurant()
    {
        buildLayout();
    }

    public PageRestaurant(Restaurant restaurant)
    {
        this.restaurant=restaurant;
        buildLayout();
    }

    private void buildLayout()
    {
        SideBar side=new SideBar(restaurant);
        sideBar.addComponent(side);
        this.addComponent(sideBar);

        content.addComponent(content);
        this.addComponent(content);
    }


    public void setContentToOrder()
    {
        content=new OrderApprove(restaurant);
    }

    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

}
