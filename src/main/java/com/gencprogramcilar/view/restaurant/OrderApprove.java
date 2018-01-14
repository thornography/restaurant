package com.gencprogramcilar.view.restaurant;

import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.model.CustomerOrder;
import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.CustomerOrderService;
import com.gencprogramcilar.service.RestaurantService;
import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

import java.util.Date;
import java.util.List;

public class OrderApprove extends ContentAbstract
{
    private Restaurant restaurant;
    private Table orderTable;

    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public OrderApprove()
    {
        buildLayout();
    }

    public OrderApprove(Restaurant restaurant)
    {
        this.restaurant=restaurant;
        buildLayout();
    }

    private void buildLayout()
    {

        setSizeFull();
        orderTable=new Table();
        orderTable.setWidth(50.0f,Unit.PERCENTAGE);
        orderTable.setHeight("350px");
        getTableData(orderTable);

        this.addComponent(orderTable);
        this.setComponentAlignment(orderTable, Alignment.MIDDLE_CENTER);

    }

    private void getTableData(Table orderTable) {
        orderTable.removeAllItems();
        List<CustomerOrder> orderList=new RestaurantService().getOrders(restaurant);
        orderTable.addContainerProperty("Yemek",String.class,null);
        orderTable.addContainerProperty("Müşteri",String.class,null);
        orderTable.addContainerProperty("Sipariş Zamanı",Date.class,null);
        orderTable.addContainerProperty("Onayla",Button.class,null);

        if(orderList != null) {
            for (int i = 0; i < orderList.size(); i++) {
                CustomerOrder order = orderList.get(i);
                Item item = orderTable.addItem(order);
                item.getItemProperty("Yemek").setValue(order.getFoodName());
                item.getItemProperty("Müşteri").setValue(order.getCustomer().getName() + " " + order.getCustomer().getSurname());
                item.getItemProperty("Sipariş Zamanı").setValue(order.getOrderDate());

                Button btnApprove = new Button("Onayla");
                btnApprove.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        onayla(order);
                        getTableData(orderTable);
                    }
                });
                item.getItemProperty("Onayla").setValue(btnApprove);


            }
        }
    }

    private void onayla(CustomerOrder order) {
        RestaurantService service = new RestaurantService();
        service.confirmOrder(order);
        Notification.show("Sipariş Onaylandı");
    }
}
