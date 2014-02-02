package com.personlist.view;


import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.datepicker.DatePicker;
import com.personlist.dao.EmployeesDao;
import com.personlist.dao.RolesDao;
import com.personlist.model.pojo.Employee;
import com.personlist.model.pojo.ManagerInfo;
import com.personlist.model.pojo.Role;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * class that represent adding new employee panel
 */
public class AddEmployeePanel extends Panel {


    IModel<Employee> employeeModel;

    public AddEmployeePanel(String id, IModel<Employee> model) throws SQLException, NamingException {
        super(id, model);
        this.employeeModel = model;
        init();
    }

    /**
     * method that initialize this panel
     *
     * @throws SQLException
     * @throws NamingException
     */
    private void init() throws SQLException, NamingException {
        Form form = new Form("addingForm");
        FormComponent inputSurname = new TextField<>("personSurname", new PropertyModel<String>(employeeModel.getObject(), "surname"));
        inputSurname.setRequired(true);
        FormComponent inputName = new TextField<>("personName", new PropertyModel<String>(employeeModel.getObject(), "name"));
        inputName.setRequired(true);
        FormComponent inputSecondName = new TextField<>("personSecondName", new PropertyModel<String>(employeeModel.getObject(), "secondName"));
        inputSecondName.setRequired(true);
        final FormComponent employeeDescription = new TextArea<>("employeeDescription", new PropertyModel<String>(employeeModel.getObject(), "employeeDescription"));
        employeeDescription.setOutputMarkupId(true);
        employeeDescription.add(new AttributeAppender("disabled", new Model<>("true")));

        Options datePickerOptions = new Options();
        datePickerOptions.set("dateFormat", "\"dd.mm.yy\"");
        final DatePicker inputDateOfBirth = new DatePicker("personDateOfBirth", new PropertyModel<Date>(employeeModel.getObject(), "dateOfBirth"), HomePage.DATE_PATTERN, datePickerOptions);
        inputDateOfBirth.setRequired(true);
        DatePicker inputHireDate = new DatePicker("personHireDate", new PropertyModel<Date>(employeeModel.getObject(), "hireDate"), datePickerOptions);
        inputHireDate.setRequired(true);


        DropDownChoice<ManagerInfo> managerName = new DropDownChoice<>("managerSelect", new PropertyModel<ManagerInfo>(employeeModel.getObject(), "managerInfo"), new ArrayList<>(EmployeesDao.getInstance().getAllManagers()));
        managerName.setChoiceRenderer(new ChoiceRenderer<ManagerInfo>("surname", "id"));
        final DropDownChoice<Role> role = new DropDownChoice<>("roleSelect", new PropertyModel<Role>(employeeModel.getObject(), "role"), RolesDao.getInstance().getAll());
        role.setChoiceRenderer(new ChoiceRenderer<Role>("roleDescription", "id"));
        role.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                if (employeeModel.getObject().getRole().getRoleDescription().equals("other")) {
                    employeeDescription.add(AttributeModifier.remove("disabled"));
                } else {
                    employeeDescription.add(new AttributeModifier("disabled", new Model<>("true")));
                    employeeModel.getObject().setEmployeeDescription(null);
                }
                target.add(employeeDescription);
            }
        });
        form.setMultiPart(true);
        form.add(inputName);
        form.add(inputSurname);
        form.add(inputSecondName);
        form.add(managerName);
        form.add(role);
        form.add(employeeDescription);
        form.add(inputDateOfBirth);
        form.add(inputHireDate);
        Button saveButton = new Button("save") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                try {
                    EmployeesDao.getInstance().insertOne(((Employee) AddEmployeePanel.this.getDefaultModelObject()));
                } catch (NamingException | SQLException e) {
                    e.printStackTrace();
                }
                setResponsePage(HomePage.class);
            }

            @Override
            public void onError() {
                super.onError();

                System.out.println(inputDateOfBirth.getModelObject() + " Submit_Erorr");
            }
        };

        form.add(saveButton);
        add(form);
    }


}
