package com.gencprogramcilar.view;



import com.gencprogramcilar.controller.CustomerController;
import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.service.CustomerService;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Not;
import com.vaadin.ui.*;

public class Register extends VerticalLayout
{
    private boolean canRegister=false;
    private TextField txfEmail;
    private PasswordField txfPassword;
    private TextField txfConfirmPassword;

    public Register()
    {
        buildLayout();
    }

    private void buildLayout()
    {

        FormLayout mainForm=new FormLayout();
        mainForm.setSizeFull();

        Panel panel=new Panel("Kayıt Ol",mainForm);
        addComponent(panel);

        mainForm.setMargin(true);
        panel.setSizeUndefined();
        mainForm.setSizeUndefined();

        setComponentAlignment(panel,Alignment.MIDDLE_CENTER);


        TextField txfName=new TextField("Kullanıcı Adı");
        txfName.setRequired(true);
        mainForm.addComponent(txfName);

        TextField txfSurname=new TextField("Kullanıcı Soyadı");
        txfSurname.setRequired(true);
        mainForm.addComponent(txfSurname);


        txfEmail = new TextField("Email");
        txfEmail.setRequired(true);
        mainForm.addComponent(txfEmail);
        txfEmail.addValueChangeListener(new Property.ValueChangeListener()
        {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent)
            {
                isEmailUnique();
            }
        });


        txfPassword = new PasswordField("Şifre");
        txfPassword.setRequired(true);
        mainForm.addComponent(txfPassword);
        txfConfirmPassword = new TextField("Tekrar Şifre");
        txfConfirmPassword.setRequired(true);
        mainForm.addComponent(txfConfirmPassword);
        txfConfirmPassword.addValueChangeListener(new Property.ValueChangeListener()
        {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent)
            {
                isPasswordMatch();
            }
        });

        TextField txfPhone=new TextField("Telefon");
        mainForm.addComponent(txfPhone);
        TextArea txaAddress=new TextArea("Adres");
        mainForm.addComponent(txaAddress);

        Button btnRegister=new Button("Üye ol");
        btnRegister.addStyleName("primary");
        mainForm.addComponent(btnRegister);
        btnRegister.addClickListener(new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {

                if(canRegister)
                {
                    String name = txfName.getValue();
                    String surname = txfSurname.getValue();
                    String adress = txaAddress.getValue();
                    String emaill = txfEmail.getValue();
                    String password = txfPassword.getValue();
                    String phone = txfPhone.getValue();


                    if(name==null||surname==null||adress==null||emaill==null){
                        Notification.show("Lütfen zorunlu alanları doldurunuz !!");
                    }
                    else
                        addCustomer(name,surname,adress,emaill,password,phone);

                    }
                else
                    Notification.show("Lütfen girdiğiniz bilgileri kontol ediniz !!");
            }
        });

    }

    private void addCustomer(String name, String surname, String adress, String emaill, String password, String phone) {

        if(isEmailUnique() && isEmailUnique()) {
            CustomerService service = new CustomerService();
            Customer customer = new Customer(0, name, surname, emaill, password, adress, phone);
            service.add(customer);
            Notification.show("KAYIT BAŞARILI");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Login logObj=new Login();
            removeAllComponents();
            addComponent(logObj);

        }
        }





    /**
     * returns the password if the passwords match.
     * @return password
     */
    private boolean isPasswordMatch()
    {
        String password=txfPassword.getValue();
        String passConfirm=txfConfirmPassword.getValue();

        if(password.equals(passConfirm))
        {
            canRegister=true;
            return canRegister;
        }
        else
        {
            canRegister=false;
            Notification.show("Girdiğiniz şifreler aynı olmalı !", Notification.Type.ERROR_MESSAGE);
            return canRegister;
        }
    }

    /**
     * returns email if email doesnt exist in the system.
     * @return email
     */
    private boolean isEmailUnique()
    {
        String email=txfEmail.getValue();
        CustomerService customerService=new CustomerService();
        canRegister=customerService.emailControl(email);
        if(!canRegister)
            Notification.show("Email sistemde mevcut", Notification.Type.ERROR_MESSAGE);
        return  canRegister;
    }

}
