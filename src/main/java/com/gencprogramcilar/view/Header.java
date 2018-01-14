package com.gencprogramcilar.view;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;

public class Header extends HorizontalLayout {
    private String name;
    private Label baslik;
    private Image homeImage;
    private String imagePath="";

    public Header(String name) {
        this.setSizeFull();
        this.setMargin(true);
        this.name = name;

        buildLayout();
    }

    private void buildLayout()
    {
        this.setMargin(false);
        Panel panel = new Panel();
        panel.setHeight("100%");
        HorizontalLayout h = new HorizontalLayout();
        h.setWidth("100%");
        this.addComponent(panel);
        imagePath= VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()
                +"/WEB-INF/classes/pictures/lion.jpeg";
        homeImage=new Image(null,new FileResource(new File(imagePath)));
        homeImage.setWidth("75");
        homeImage.setHeight("73");
        h.addComponent(homeImage);

        baslik = new Label("HOŞGELDİNİZ "+name);
        baslik.setWidth(null);
        baslik.setHeight(null);
        h.addComponent(baslik);
        h.setComponentAlignment(baslik,Alignment.MIDDLE_CENTER);

        Button cikis = new Button("ÇIKIŞ");
        cikis.addStyleName("danger");
        cikis.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {
                MyUI.getMyUI().layout.removeAllComponents();
                MyUI.getMyUI().layout.addComponent(new Login());
            }
        });
        h.addComponent(cikis);
        h.setComponentAlignment(cikis, Alignment.MIDDLE_RIGHT);

        h.setExpandRatio(homeImage,0.2f);
        h.setExpandRatio(baslik,0.6f);
        h.setExpandRatio(cikis,0.2f);
        h.setMargin(true);
        panel.setContent(h);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
