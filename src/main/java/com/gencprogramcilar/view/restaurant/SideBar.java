package com.gencprogramcilar.view.restaurant;

import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.view.Header;
import com.vaadin.ui.*;

public class SideBar extends VerticalLayout
{
    HorizontalLayout mainLayout=new HorizontalLayout();
    VerticalLayout sideBar = new VerticalLayout();
    FormLayout content = new FormLayout();
    private Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public SideBar(Restaurant restaurant)
    {
        setRestaurant(restaurant);
        buildLayout();
    }

    private void buildLayout()
    {
        this.removeAllComponents();
        sideBar.setMargin(true);
        content.setMargin(true);
//        this.setHeight(null);
//        this.setWidth(100.0f,Unit.PERCENTAGE);
//        this.addStyleName("v-scrollable");

        content.setHeight("");
        content.setWidth("");

        Button btnAddFood=new Button("Yemek Ekle");
        btnAddFood.setWidth("150");
        btnAddFood.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {
                RestaurantFood restaurantFood = new RestaurantFood(restaurant);
                ChangeContent(restaurantFood);
            }
        });

        sideBar.addComponent(btnAddFood);
        sideBar.setWidth("");
        sideBar.setHeight("");

        Button btnAcceptOrder=new Button("Sipariş Onayla");
        btnAcceptOrder.setWidth("150");
        btnAcceptOrder.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {
                OrderApprove orderApprove = new OrderApprove(restaurant);
                ChangeContent(orderApprove);
            }
        });

        sideBar.addComponent(btnAcceptOrder);

        HorizontalLayout bodyLayout=new HorizontalLayout();

        bodyLayout.addComponent(sideBar);
        bodyLayout.setComponentAlignment(sideBar, Alignment.MIDDLE_CENTER);
        bodyLayout.addComponent(content);
        bodyLayout.setComponentAlignment(content,Alignment.MIDDLE_CENTER);


        Header header=new Header(restaurant.getName());
        header.setHeight(15.0f,Unit.PERCENTAGE);
//        this.addComponent(bodyLayout);
        this.addComponents(header,bodyLayout);
        this.setExpandRatio(header,0.2f);
        this.setExpandRatio(bodyLayout,0.8f);


    }

    private void ChangeContent(Component object) {
        content.removeAllComponents();
        content.addComponent(object);
    }
}
