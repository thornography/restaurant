package com.gencprogramcilar.component;

import com.gencprogramcilar.model.Category;
import com.gencprogramcilar.service.CategoryService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;

import java.util.List;

public class CategoryCombobox extends ComboBox{

    public CategoryCombobox(){
        fill();
        setNullSelectionAllowed(false);
    }

    private void fill()  {
        try
        {
            CategoryService service = new CategoryService();
            List<Category> categoryList = service.get();
            BeanItemContainer<Category> container = new BeanItemContainer<Category>(Category.class);
            container.addAll(categoryList);
            this.setContainerDataSource(container);
            this.setItemCaptionMode(ItemCaptionMode.PROPERTY);
            this.setItemCaptionPropertyId("name");
        }catch (Exception ex) {ex.printStackTrace();}
    }
}
