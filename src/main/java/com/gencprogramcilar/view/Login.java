package com.gencprogramcilar.view;

import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.LoginService;
import com.gencprogramcilar.view.restaurant.PageRestaurant;
import com.gencprogramcilar.view.restaurant.RestaurantFood;
import com.gencprogramcilar.view.restaurant.SideBar;
import com.vaadin.data.Property;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;

import java.sql.SQLException;

public class Login extends VerticalLayout{


    private TextField textFieldUserName;
    private PasswordField textFieldPassword;
    private Button buttonLogin;
    private Button buttonSignUp;
    private String combobox;

    FormLayout form;
    ComboBox combo=new ComboBox("Giriş Tipi");


    public Login()
    {
        this.setSizeFull();
        buildLayout();
    }

    private void buildLayout() {

        combo.setNullSelectionAllowed(false);
        combo.addItem("Admin");
        combo.addItem("Restoran Sahibi");
        combo.addItem("Müşteri");

        combo.setValue("Müşteri");

        combo.addValueChangeListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
                combobox = (String) event.getProperty().getValue();
                System.out.println(combobox);
            }
        });

        form = new FormLayout();
        form.setMargin(true);

        form.addComponent(combo);

        Panel panel = new Panel("Hoşgeldiniz", form);
        panel.setWidth("350");

        textFieldUserName = new TextField("Kullanıcı Adı");
        textFieldUserName.setWidth("100%");
        form.addComponent(textFieldUserName);


        textFieldPassword = new PasswordField("Şifre");
        textFieldPassword.setWidth("100%");
        form.addComponent(textFieldPassword);


        HorizontalLayout buttonContent = new HorizontalLayout();
        form.addComponent(buttonContent);


        buttonLogin = new Button("Giriş");
        buttonLogin.addStyleName("primary");
        buttonContent.addComponent(buttonLogin);



        buttonLogin.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(isEmptyInput()){
                    String mail = textFieldUserName.getValue();
                    String password = textFieldPassword.getValue();
                    try {
                        login(mail,password,combobox);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {

                }
            }
        });

        buttonSignUp = new Button("Kaydol");
        buttonContent.addComponent(buttonSignUp);

        buttonSignUp.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                removeAllComponents();
                Register register = new Register();
                addComponent(register);
            }
        });


        addComponent(panel);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
        setSizeFull();

    }

    private boolean isEmptyInput() {
        updateUI(textFieldUserName, "Bu alan boş bırakılamaz");
        updateUI(textFieldPassword, "Bu alan boş bırakılamaz");

        return !(textFieldUserName.isEmpty() && textFieldPassword.isEmpty());
    }

    private void updateUI(AbstractTextField textField, String message) {
        if (textField.isEmpty()) {
            textField.setComponentError(new UserError(message));
        }
        else {
            textField.setComponentError(null);
        }
    }

    private void login(String name, String password,String combobox) throws SQLException {
        int id = 1;
        if(combobox == "Restoran Sahibi")
            id = 2;
        else if(combobox == "Admin")
            id = 0;
        else if(combobox == "Müşteri")
            id = 1;
        LoginService service = new LoginService();
        Object object=service.login(name,password,id);
        if(object != null){
            if(id == 0) {
                this.removeAllComponents();
                AdminPanel adminPanel = new AdminPanel();
                this.addComponent(adminPanel);
            }
            else if(id == 1){
                this.removeAllComponents();
                Customer customer = (Customer) object;
                OrderPage customerOrder = new OrderPage(customer);
                this.addComponent(customerOrder);
            }
            else{
                this.removeAllComponents();
                Restaurant restaurant = (Restaurant) object;
                SideBar sideBar = new SideBar(restaurant);
                this.addComponent(sideBar);
            }
        }
        else
            Notification.show("Mail ve şifreyi kontrol ediniz", Notification.Type.ERROR_MESSAGE);

    }

}




