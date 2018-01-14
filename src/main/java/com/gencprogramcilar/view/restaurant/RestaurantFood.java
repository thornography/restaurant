package com.gencprogramcilar.view.restaurant;

import com.gencprogramcilar.component.CategoryCombobox;
import com.gencprogramcilar.model.Category;
import com.gencprogramcilar.model.Food;
import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.FoodService;
import com.gencprogramcilar.service.ImageReceiver;
import com.gencprogramcilar.view.MyUI;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;


public class RestaurantFood extends HorizontalLayout
{
    private boolean canAdd=false;
    private TextField txfName;
    private TextField txfPrice;
    private Restaurant restaurant;
    private CategoryCombobox categoryCombobox;
    private Table table;
    private Category category;
    String file;
    private int foodId=0;
    private Window subwindow;

    public RestaurantFood()
    {
        buildLayout();
    }

    public RestaurantFood(Restaurant restaurant)
    {
        this.restaurant=restaurant;
        buildLayout();
    }

    private void buildLayout()
    {
        this.removeAllComponents();
        this.setSizeFull();
        VerticalLayout pageHolder=new VerticalLayout();
        pageHolder.setWidth(null);

        categoryCombobox = new CategoryCombobox();
        categoryCombobox.setCaption("Kategori: ");
        categoryCombobox.setNullSelectionAllowed(false);
        pageHolder.addComponent(categoryCombobox);

        categoryCombobox.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                category = (Category) event.getProperty().getValue();
            }
        });


        txfName = new TextField();
        txfName.setCaption("İsim: ");
        pageHolder.addComponent(txfName);
        txfPrice = new TextField();
        txfPrice.setCaption("Fiyat: ");
        pageHolder.addComponent(txfPrice);

        final Image image = new Image("Yüklenen Resim");
        image.setVisible(false);

        ImageReceiver receiver = new ImageReceiver();
        final Upload upload = new Upload("Resim: ", receiver);
        upload.setButtonCaption("YÜKLE");
        pageHolder.addComponent(upload);
        pageHolder.addComponent(image);

        final long UPLOAD_LIMIT = 1000000l;
        upload.addStartedListener(new Upload.StartedListener() {
            @Override
            public void uploadStarted(Upload.StartedEvent event) {
                if (event.getContentLength() > UPLOAD_LIMIT) {
                    Notification.show("DOSYA BOYUTU ÇOK FAZLA", Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
                else {
                    uploadImage(event, image);
                }
            }
        });

        Button btnAddFood=new Button("Yemeği Ekle");
        pageHolder.addComponent(btnAddFood);
        btnAddFood.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {
                if(canAdd) {
                    try {
                        byte[] resim = null;
                        if (file != null)
                            resim = extractBytes(file);
                        String name = txfName.getValue();
                        float price = Float.parseFloat(txfPrice.getValue());

                        Notification.show(addFood(foodId, restaurant, category, name, resim, price));
                        fillTable(table);
                    } catch (Exception e) {
                        System.out.println("hata");
                    }
                }
                else
                    Notification.show("Alanları Doldurunuz", Notification.Type.ERROR_MESSAGE);
            }
        });

        table=new Table();
        table.setSelectable(true);
        table.setHeight("350px");
//        pageHolder.addComponent(table);
        fillTable(table);
        table.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                Integer value = (Integer) itemClickEvent.getItem().getItemProperty("Id").getValue();
                String name = (String) itemClickEvent.getItem().getItemProperty("İsim").getValue();
                float price = (float) itemClickEvent.getItem().getItemProperty("Fiyat").getValue();
                foodId = value;
                fillFormForEdit(name,price);

            }
        });

        this.addComponent(pageHolder);
        this.addComponent(table);
        this.setComponentAlignment(pageHolder,Alignment.MIDDLE_CENTER);

    }

    private String addFood(int id,Restaurant restaurant, Category category, String name, byte[] picture, float price) throws SQLException, IOException {
        System.out.println(id);
        if(id == 0){
            Food food = new Food();
            food.setCategory(category);
            food.setRestaurant(restaurant);
            food.setName(name);
            food.setPicture(picture);
            food.setPrica(price);
            FoodService service = new FoodService();
            service.add(food);
            cleanForm();
            return "OK";
        }
        else{
            Food food = new Food();
            food.setId(id);
            food.setName(name);
            food.setPrica(price);
            FoodService service = new FoodService();
            service.edit(food);
            foodId = 0;
            cleanForm();
            return "OK";

        }

    }

    private void fillTable(Table foodTable) {
        foodTable.removeAllItems();
        foodTable.addContainerProperty("Id",Integer.class,null);
        foodTable.addContainerProperty("İsim",String.class,null);
        foodTable.addContainerProperty("Fiyat",Float.class,null);
        foodTable.addContainerProperty("Kategori",String.class,null);
        foodTable.addContainerProperty("Resim", Image.class,null);
        foodTable.addContainerProperty("İşlem",Button.class,null);

        FoodService foodService = new FoodService();
        List<Food> foodList=foodService.get(restaurant);
        if(foodList != null) {
            for (int i = 0; i < foodList.size(); i++) {
                Food food = foodList.get(i);
                Item item = foodTable.addItem(food);
                item.getItemProperty("Id").setValue(food.getId());
                item.getItemProperty("İsim").setValue(food.getName());
                item.getItemProperty("Fiyat").setValue(food.getPrice());
                item.getItemProperty("Kategori").setValue(food.getCategory().getName());
                Image image = new Image();
                image.setWidth("50px");
                image.setHeight("50px");
                image.setSource(bytesToResource(food.getPicture()));
                item.getItemProperty("Resim").setValue(image);

                Button btnDelete = new Button("Sil");
                btnDelete.addStyleName("danger");
                item.getItemProperty("İşlem").setValue(btnDelete);
                btnDelete.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent)
                    {
                        addWindowForDelete(food.getId());
                    }
                });
            }
        }
    }

    private void canAdd() throws Exception
    {
        if(txfName.getValue() == "" && txfPrice.getValue() ==""){
            canAdd = false;
        }
        else
            canAdd = true;
    }

    private byte[] extractBytes (String ImageName) throws IOException {
        Path path = Paths.get(ImageName);
        byte[] bytes = Files.readAllBytes(path);
        return bytes;
    }
    private void uploadImage(Upload.StartedEvent event, Image image) {
        Notification.show("YÜKLEME BAŞARILI",Notification.Type.HUMANIZED_MESSAGE);
        image.setVisible(true);
        image.setHeight("100px");
        image.setWidth("100px");
        image.setSource(new FileResource(new File(event.getFilename())));
        file = event.getFilename();
    }
    private StreamResource bytesToResource(byte[] bytes)  {

        StreamResource imgResource = new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                InputStream input = new ByteArrayInputStream(bytes);
                return input;
            }
        }, "");

        return imgResource;
    }

    private void fillFormForEdit(String name, float price) {
        this.txfName.setValue(name);
        this.txfPrice.setValue(String.valueOf(price));
    }

    private void cleanForm(){
        txfPrice.setValue("");
        txfName.setValue("");
    }

    private void addWindowForDelete(int id){
        subwindow = new Window();
        subwindow.center();
        Label label = new Label("Silinsin Mi?");
        Button btn = new Button("Onayla");
        btn.addStyleName("primary");
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.addComponent(label);
        layout.addComponent(btn);
        subwindow.setContent(layout);
        MyUI.getMyUI().addWindow(subwindow);

        btn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                    new FoodService().delete(id);
                    fillTable(table);
                    Notification.show("SİLİNDİ", Notification.Type.ERROR_MESSAGE);
                    subwindow.close();

            }
        });
    }
}
