package com.gencprogramcilar.view;

import com.gencprogramcilar.model.Category;
import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.CategoryService;
import com.gencprogramcilar.service.RestaurantService;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class KategoriEkle extends VerticalLayout{

    FormLayout form=new FormLayout();
    private Table kategoriTable;
    private TextField name;
    private Button kaydetButon;
    private int id;
    private Window subwindow;

    public KategoriEkle() throws SQLException {

        buildLayout();

    }

    public void buildLayout() throws SQLException{

        kategoriTable = new Table();
        kategoriTable.setHeight("250px");
        kategoriTable.setImmediate(true);
        kategoriTable.setSelectable(true);

        getTableData(kategoriTable);

        kategoriTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                id = (Integer) itemClickEvent.getItem().getItemProperty("ID").getValue();
                String name = (String) itemClickEvent.getItem().getItemProperty("İSİM").getValue();
                fillFormForEdit(name);
            }
        });

        name=new TextField("Kategori Adı :");
        kaydetButon =new Button();
        kaydetButon.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String ad = name.getValue();
                addCategory(ad);
                cleanForm();
                try {
                    getTableData(kategoriTable);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        form.setSizeFull();
        form.setMargin(true);
        addComponent(form);

        addComponent(kategoriTable);

        form.addComponent(name);
        form.addComponent(kaydetButon);
        kaydetButon.setCaption("KAYDET");
        }

    private void fillFormForEdit(String name) {
        this.name.setValue(name);
    }

    private void addCategory(String ad) {
        if(ad != "" && ad != null) {
            CategoryService service = new CategoryService();
            Category category = new Category();
            category.setName(ad);
            if(id>0)
            {
                category.setId(id);
                service.edit(category);
                Notification.show("Kategori güncellendi");
                id = 0;
            }
            else {
                service.add(category);
                Notification.show("Kategori Eklendi.");
            }

        }
        else{
            Notification.show("Alanları doldurunuz!!");
        }
    }

    private void getTableData(Table table) throws SQLException {
        table.removeAllItems();
        CategoryService service = new CategoryService();
        List<Category> list = service.get();
        table.setHeight("250px");
        table.setWidth("500px");
        table.addContainerProperty("ID", Integer.class, null);
        table.addContainerProperty("İSİM", String.class, null);
        table.addContainerProperty("SİL",Button.class,null);

        int id;
        String name;

        int i =0 ;
        for (Category category : list) {
            id = category.getId();
            name = category.getName();

            Button sil = new Button();
            sil.setCaption("SİL");
            sil.addStyleName("danger");
            table.addItem(new Object[]{id,name,sil},i);
            i++;
            int finalId1 = id;
            sil.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    addWindowForDelete(finalId1);
                }
            });
        }
        addComponent(table);
    }

    public void cleanForm(){
        name.setValue("");
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

                    CategoryService service1 = new CategoryService();
                    service1.delete(id);
                    getTableData(kategoriTable);
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