package com.gencprogramcilar.component;

import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.RestaurantService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

import java.sql.SQLException;
import java.util.List;

public class RestaurantCombobox extends ComboBox {

    public RestaurantCombobox()  {
        try
        {
            fill();
            setNullSelectionAllowed(false);
        }catch (Exception ex){ex.printStackTrace();}
    }

    private void fill() throws SQLException {
        RestaurantService service = new RestaurantService();
        List<Restaurant> restaurantList = service.get();
        BeanItemContainer<Restaurant> container = new BeanItemContainer<Restaurant>(Restaurant.class);
        container.addAll(restaurantList);
        this.setContainerDataSource(container);
        this.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        this.setItemCaptionPropertyId("name");
    }

}
