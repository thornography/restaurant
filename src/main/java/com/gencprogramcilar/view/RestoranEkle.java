package com.gencprogramcilar.view;

import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.RestaurantService;
import com.vaadin.data.util.filter.Not;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class RestoranEkle extends HorizontalLayout {


    private Table restaurantTable;
    private TextField name;
    private TextArea address;
    private TextField phone;
    private PasswordField password;
    private TextField email;
    private Window subwindow;

    FormLayout form = new FormLayout();

    public RestoranEkle() throws SQLException {
        buildLayout();
    }

    private void buildLayout() throws SQLException {
        restaurantTable = new Table();
        restaurantTable.setImmediate(true);
        restaurantTable.setSelectable(true);
        restaurantTable.setSizeFull();
        getTableData(restaurantTable);

        final Integer[] restaurantId = {0};

        restaurantTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                Integer value = (Integer) itemClickEvent.getItem().getItemProperty("ID").getValue();
                String name = (String) itemClickEvent.getItem().getItemProperty("İSİM").getValue();
                String mail = (String) itemClickEvent.getItem().getItemProperty("E-MAİL").getValue();
                String address = (String) itemClickEvent.getItem().getItemProperty("ADRES").getValue();
                String phone = (String) itemClickEvent.getItem().getItemProperty("TELEFON").getValue();
                restaurantId[0] = value;
                fillFormForEdit(name, address, phone, mail);

            }
        });

        setSizeFull();


        form.setSizeFull();
        form.setMargin(true);
        addComponent(form);


        name = new TextField();
        name.setCaption("Restoran İsmi: ");
        name.setRequired(true);
        form.addComponent(name);

        password = new PasswordField();
        password.setCaption("Şifre: ");
        password.setRequired(true);
        form.addComponent(password);

        address = new TextArea();
        address.setCaption("Restoran Adres: ");
        address.setRequired(true);
        address.setRows(3);
        form.addComponent(address);

        phone = new TextField();
        phone.setCaption("Restoran Telefon:");
        form.addComponent(phone);

        email = new TextField();
        email.setCaption("Restoran Email: ");
        form.addComponent(email);

        Button kaydet = new Button();
        kaydet.setCaption("KAYDET");
        kaydet.addStyleName("primary");
        form.addComponent(kaydet);
        kaydet.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                saveRestaurant(restaurantId);
                try {
                    getTableData(restaurantTable);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cleanForm();
            }
        });
        this.addComponent(restaurantTable);

        setComponentAlignment(restaurantTable, Alignment.MIDDLE_RIGHT);
    }

    private void deleteRestaurant(Integer value) throws SQLException {
        RestaurantService service = new RestaurantService();
        service.delete(value);
    }

    private void saveRestaurant(Integer[] restaurantId) {
        String saveName = name.getValue();
        String saveAdress = address.getValue();
        String savePhone = phone.getValue();
        String savePassword = password.getValue();
        String saveEmail=email.getValue();

        if (saveName != "" && saveAdress != "" && savePhone != "") {
            if (restaurantId[0] == 0) {
                try {
                    saveRestaurant(saveName, savePassword,saveAdress, savePhone,saveEmail);
                    Notification.show("İşlem Başarılı");
                } catch (SQLException e) {
                    Notification.show("İşlem Başarısız", Notification.Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            } else {
                try {
                    editRestaurant(restaurantId[0],saveEmail,saveName,savePassword,savePhone,saveAdress);
                    cleanForm();
                    getTableData(restaurantTable);
                    Notification.show("İşlem Başarılı");
                    restaurantId[0] = 0;
                } catch (SQLException e) {
                    Notification.show("İşlem Başarısız", Notification.Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

        } else {
            Notification.show("Lütfen alanları doldurunuz!");
        }
    }


    private void saveRestaurant(String name,String password, String address, String phone,String mail) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setPassword(password);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setEmail(mail);
        RestaurantService service = new RestaurantService();
        service.add(restaurant);

    }

    private void editRestaurant(int id, String mail, String name, String password, String address, String phone) throws SQLException {
        Restaurant restaurant = new Restaurant(id, mail, name, password, phone,address);
        RestaurantService restaurantService = new RestaurantService();
        restaurantService.edit(restaurant);

    }

        private void fillFormForEdit (String name, String address, String phone, String mail){
            this.name.setValue(name);
            this.address.setValue(address);
            this.phone.setValue(phone);
            this.email.setValue(mail);
        }

        private void cleanForm (){
            this.name.setValue("");
            this.address.setValue("");
            this.phone.setValue("");
            this.email.setValue("");
            this.password.setValue("");
        }

        private void getTableData (Table table) throws SQLException {
            table.removeAllItems();
            RestaurantService service = new RestaurantService();
            List<Restaurant> list = service.get();
            table.setHeight("250px");
            table.setWidth("550px");
            table.addContainerProperty("ID", Integer.class, null);
            table.addContainerProperty("İSİM", String.class, null);
            table.addContainerProperty("E-MAİL", String.class, null);
            table.addContainerProperty("ADRES", String.class, null);
            table.addContainerProperty("TELEFON", String.class, null);
            table.addContainerProperty("SİL", Button.class, null);

            int id;
            String name;
            String adres;
            String tel;
            String mail;
            int i = 0;


            for (Restaurant restaurant : list) {
                id = restaurant.getId();
                name = restaurant.getName();
                adres = restaurant.getAddress();
                tel = restaurant.getPhone();
                mail=restaurant.getEmail();
                Button sil = new Button();
                sil.setCaption("SİL");
                sil.addStyleName("danger");
                table.addItem(new Object[]{id, name, mail, adres, tel,sil}, i);
                i++;
                int finalId = id;

                sil.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        addWindowForDelete(finalId);
                    }
                });
            }
            addComponent(table);
            setComponentAlignment(table, Alignment.MIDDLE_RIGHT);
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
                try {
                    deleteRestaurant(id);
                    getTableData(restaurantTable);
                    Notification.show("SİLİNDİ", Notification.Type.ERROR_MESSAGE);
                    subwindow.close();
                } catch (SQLException e) {
                    Notification.show("İşlem gerçekleştirilemedi", Notification.Type.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
        }

    }

