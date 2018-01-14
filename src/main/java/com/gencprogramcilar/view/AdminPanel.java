package com.gencprogramcilar.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class AdminPanel extends VerticalLayout {

    VerticalLayout sideBar = new VerticalLayout();
    VerticalLayout content = new VerticalLayout();

    Button restoranEkleButon = new Button("Restoran Ekle");
    Button kategoriEkleButon = new Button("KategoriEkle");

    VerticalLayout reference = new VerticalLayout();

    RestoranEkle restoranekleObj;
    KategoriEkle kategoriEkleObj;


    public AdminPanel() {
        try {
            build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void build() throws SQLException {

        sideBar.setMargin(true);
        content.setMargin(true);


        restoranekleObj = new RestoranEkle();
        kategoriEkleObj = new KategoriEkle();

        sideBar.setSpacing(true);
        content.setSpacing(true);

        sideBar.setWidth("250");
        reference.setHeight("155");
        restoranEkleButon.setWidth("100%");
        kategoriEkleButon.setWidth("100%");

        HorizontalLayout bodyLayout=new HorizontalLayout();

        Header header=new Header("admin");

        this.addComponents(header,bodyLayout);
        this.setExpandRatio(header,0.2f);
        this.setExpandRatio(bodyLayout,0.8f);

        bodyLayout.addComponent(sideBar);
        bodyLayout.addComponent(content);

        sideBar.addComponent(reference);

        sideBar.addComponent(restoranEkleButon);
        sideBar.addComponent(kategoriEkleButon);


        restoranEkleButon.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                content.removeAllComponents();
                content.addComponent(restoranekleObj);


            }
        });

        kategoriEkleButon.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                content.removeAllComponents();
                content.addComponent(kategoriEkleObj);


            }
        });


    }

}
