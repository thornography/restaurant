package com.gencprogramcilar.view;

import com.gencprogramcilar.component.CategoryCombobox;
import com.gencprogramcilar.component.RestaurantCombobox;
import com.gencprogramcilar.model.*;
import com.gencprogramcilar.service.CustomerOrderService;
import com.gencprogramcilar.service.FoodService;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPage extends VerticalLayout {

    private CategoryCombobox boxCategory;
    private RestaurantCombobox boxRestaurant;
    private Window subwindow;
    private List<Food> foodList = new ArrayList<>();

    HorizontalLayout tarafHolder=new HorizontalLayout();

    VerticalLayout solTaraf=new VerticalLayout();
    VerticalLayout ortaTaraf=new VerticalLayout();
    VerticalLayout sagTaraf=new VerticalLayout();


//    private Panel panel;
    private Table foodOptionsTable;
    private Table foodBucketTable;
    private FormLayout content;

    private Customer customer;
    private Restaurant restaurant;
    private Category category;
    private List<Food> bucketList=new ArrayList<>();

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public OrderPage() {
        try
        {
            buildLayout();
        }catch (Exception ex){ex.printStackTrace();}
    }

    public OrderPage(Customer customer) {
        this.customer=customer;
        try
        {
            buildLayout();
        }catch (Exception ex){ex.printStackTrace();}
    }

    private void buildLayout() throws SQLException {
        content = new FormLayout();

        Header header=new Header(customer.getName());
        this.addComponent(header);
        this.addComponent(tarafHolder);

        this.setExpandRatio(header,0.2f);
        this.setExpandRatio(tarafHolder,0.8f);

        tarafHolder.addComponent(solTaraf);
        tarafHolder.addComponent(ortaTaraf);
        tarafHolder.addComponent(sagTaraf);


        solTaraf.addComponent(content);
        solTaraf.setMargin(true);

        ortaTaraf.setMargin(true);
        ortaTaraf.setSpacing(true);

        sagTaraf.setMargin(true);
        sagTaraf.setSpacing(true);


        boxRestaurant = new RestaurantCombobox();
        boxRestaurant.setCaption("Restoran İsmi: ");
        content.addComponent(boxRestaurant);

        boxCategory = new CategoryCombobox();
        boxCategory.setCaption("Kategori İsmi: ");
        content.addComponent(boxCategory);

//        Table foodOptionsTable=new Table();

        Button btnTakeLook= new Button("Göz At");
        btnTakeLook.addStyleName("primary");
        btnTakeLook.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String restName=restaurant.getName();
                String catName=category.getName();
                Notification.show(restName+" - "+catName);


                FoodService foodService = new FoodService();
                List<Food> foodList=foodService.get(restaurant.getId(),category.getId());

                for (int i=0;i<foodList.size();i++)
                {
                    Food food=foodList.get(i);
                    Item item=foodOptionsTable.addItem(food);
                    item.getItemProperty("ID").setValue(food.getId());
                    item.getItemProperty("Yemek").setValue(food.getName());
                    item.getItemProperty("Fiyat").setValue(food.getPrice());
                    Image image = new Image();
                    image.setWidth("50px");
                    image.setHeight("50px");
                    image.setSource(bytesToResource(food.getPicture()));
                    item.getItemProperty("Resim").setValue(image);

                    Button btnEkle=new Button("Ekle");
                    btnEkle.addClickListener(new Button.ClickListener()
                    {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent)
                        {
                            bucketList.add(food);
                            refreshBucketTable();
                        }
                    });
                    item.getItemProperty("İşlem").setValue(btnEkle);
                }
            }
        });

        content.addComponent(btnTakeLook);

        boxRestaurant.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                restaurant=(Restaurant) event.getProperty().getValue();

            }
        });


        boxCategory.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                category=(Category) event.getProperty().getValue();

            }
        });

        foodOptionsTable = new Table("Seçilebilir Yemekler");
        foodOptionsTable.setSelectable(true);
        foodOptionsTable.setMultiSelect(false);
        foodOptionsTable.setImmediate(true);

        foodOptionsTable.addContainerProperty("ID",Integer.class,null);
        foodOptionsTable.addContainerProperty("Yemek",String.class,null);
        foodOptionsTable.addContainerProperty("Fiyat",Float.class,null);
        foodOptionsTable.addContainerProperty("Resim",Image.class,null);
        foodOptionsTable.addContainerProperty("İşlem",Button.class,null);
        foodOptionsTable.setHeight("250px");
        ortaTaraf.addComponent(foodOptionsTable);

        foodBucketTable = new Table("Sepetim");
        foodBucketTable.setHeight("250px");
        foodBucketTable.setSelectable(true);

        foodBucketTable.addContainerProperty("ID",Integer.class,null);
        foodBucketTable.addContainerProperty("Yemek",String.class,null);
        foodBucketTable.addContainerProperty("Fiyat",Float.class,null);
        foodBucketTable.addContainerProperty("Sil",Button.class,null);

        refreshBucketTable();

        sagTaraf.addComponent(foodBucketTable);


        Button btnOnayla = new Button("Siparişi Onayla");
        btnOnayla.addStyleName("primary");
        sagTaraf.addComponent(btnOnayla);
        btnOnayla.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {
                addWindowOrderConfirm();
            }
        });
    }

    private void refreshBucketTable()
    {
        foodBucketTable.removeAllItems();
        for(int i=0;i<bucketList.size();i++)
        {
            Food food=bucketList.get(i);
            Item item=foodBucketTable.addItem(food);

            if(item==null) return;

            item.getItemProperty("ID").setValue(food.getId());
            item.getItemProperty("Yemek").setValue(food.getName());
            item.getItemProperty("Fiyat").setValue(food.getPrice());
            Button sil = new Button();
            sil.setCaption("SİL");
            sil.addStyleName("danger");
            sil.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    bucketList.remove(food);
                    refreshBucketTable();
                }
            });
            item.getItemProperty("Sil").setValue(sil);

       }}

    private StreamResource bytesToResource(byte[] bytes) {

        StreamResource imgResource = new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                InputStream input = new ByteArrayInputStream(bytes);
                return input;
            }
        }, "");

        return imgResource;
    }

//    private void configureAndAddTable(List<Food> foodList, Table table) throws SQLException {
//        table.removeAllItems();
//        table.setHeight("250px");
//        table.addContainerProperty("ID", Integer.class, null);
//        table.addContainerProperty("İSİM", String.class, null);
//        table.addContainerProperty("FİYAT", Float.class, null);
//        table.addContainerProperty("KATEGORİ", String.class, null);
//        table.addContainerProperty("RESTORAN", String.class, null);
//        if (table == this.foodOptionsTable) {
//            table.addContainerProperty("RESİM", Image.class, null);
//        }
//        if (table == this.foodBucketTable) {
//            table.addContainerProperty("SİL", Button.class, null);
//        }
//
//        content.addComponent(table);
//    }

    private void addWindowOrderConfirm() {
        subwindow = new Window();
        subwindow.setSizeUndefined();
        subwindow.center();
        Label label= new Label();
        label.setCaption("Siparişinizi onaylıyor musunuz?");
        Label label1 = new Label("Fiyat: ");
        Button btn = new Button("Onayla");
        btn.addStyleName("primary");
        VerticalLayout layout= new VerticalLayout();
        layout.setMargin(true);
        layout.addComponent(label);
        layout.addComponent(btn);
        //layout.addComponent(label1);
        subwindow.setContent(layout);
        MyUI.getMyUI().addWindow(subwindow);
        btn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                for(int i=0;i<bucketList.size();i++)
                {
                    Food food=bucketList.get(i);
                    Date orderDate=new Date();
                    orderDate.setTime(System.currentTimeMillis());
                    CustomerOrder order=new CustomerOrder(-1,customer,food,orderDate,false);
                    CustomerOrderService orderService=new CustomerOrderService();
                    orderService.add(order);
                    bucketList.remove(i);
                    refreshBucketTable();
                    Notification.show("Siparişiniz başarıyla alındı",Notification.Type.HUMANIZED_MESSAGE);
                }
                subwindow.close();

            }
        });
    }


}