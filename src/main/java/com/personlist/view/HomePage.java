package com.personlist.view;

import com.googlecode.wicket.jquery.core.Options;
import com.personlist.comparator.HireDateComporator;
import com.personlist.comparator.SurnameComparator;
import com.personlist.controller.EmployeeManipulator;
import com.personlist.dao.EmployeesDao;
import com.personlist.dao.RolesDao;
import com.personlist.model.pojo.Employee;
import com.personlist.model.pojo.ManagerInfo;
import com.personlist.model.pojo.Role;
import com.personlist.xmlprocessing.XmlWorker;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.json.XML;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class that representing main functional page
 */
public class HomePage extends WebPage {
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    private FileUploadField xmlUploadField;

    public HomePage(final PageParameters parameters) throws SQLException, NamingException {
        super(parameters);
        init();
    }


    /**
     * method that initialize Home page
     * @throws SQLException
     * @throws NamingException
     */
    private void init() throws SQLException, NamingException {
        final WebMarkupContainer containerForListOfPersons = new WebMarkupContainer("listContainer");
        containerForListOfPersons.setOutputMarkupId(true);

        final ArrayList<Employee> employees = new ArrayList<>(EmployeesDao.getInstance().getAll());
        ListDataProvider<Employee> dataProvider = new ListDataProvider<>(employees);
        DataView<Employee> listOfPersons = new DataView<Employee>("personList", dataProvider) {
            @Override
            protected void populateItem(Item listItem) {
                try {

                    final Employee employee = (Employee) listItem.getModelObject();

                    Label surnameField = new Label("surnameField", new PropertyModel<String>(employee, "surname"));
                    Label nameField = new Label("nameField", new PropertyModel<String>(employee, "name"));
                    Label secondNameField = new Label("secondNameField", new PropertyModel<String>(employee, "secondName"));
                    Options datePickerOptions = new Options();
                    datePickerOptions.set("dateFormat", "\"dd.mm.yy\"");
                    DateLabel inputDateOfBirth = DateLabel.forDatePattern("personDateOfBirth", new PropertyModel<Date>(employee, "dateOfBirth"), DATE_PATTERN);
                    DateLabel inputHireDate = DateLabel.forDatePattern("personHireDate", new PropertyModel<Date>(employee, "hireDate"), DATE_PATTERN);
                    DropDownChoice<ManagerInfo> managerName = new DropDownChoice<>("managerSelect", new PropertyModel<ManagerInfo>(employee, "managerInfo"), new ArrayList<>(EmployeesDao.getInstance().getAllManagers()));
                    managerName.setChoiceRenderer(new ChoiceRenderer<ManagerInfo>("surname", "id"));
                    managerName.setNullValid(true);
                    final FormComponent employeeDescription = new TextArea<>("employeeDescription", new PropertyModel<String>(employee, "employeeDescription"));
                    if (!employee.getRole().getRoleDescription().equals("other")) {
                        employeeDescription.add(new AttributeAppender("disabled", new Model<>("true")));
                    }
                    employeeDescription.setOutputMarkupId(true);
                    DropDownChoice<Role> role = new DropDownChoice<>("roleSelect", new PropertyModel<Role>(employee, "role"), RolesDao.getInstance().getAll());
                    role.add(new AjaxFormComponentUpdatingBehavior("onchange") {
                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            if (employee.getRole().getRoleDescription().equals("other")) {
                                employeeDescription.add(AttributeModifier.remove("disabled"));
                            } else {
                                employeeDescription.add(new AttributeModifier("disabled", new Model<>("true")));
                            }
                            target.add(employeeDescription);
                        }
                    });
                    role.setNullValid(true);
                    role.setChoiceRenderer(new ChoiceRenderer<Role>("roleDescription", "id"));
                    AjaxLink saveButton = new AjaxLink("saveButton") {

                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            try {
                                EmployeesDao.getInstance().updateOne(employee);
                            } catch (NamingException | SQLException e) {
                                e.printStackTrace();
                            }
                            setResponsePage(HomePage.class);
                        }
                    };

                    AjaxLink deleteButton = new AjaxLink("deleteButton") {

                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            try {
                                EmployeesDao.getInstance().deleteOne(employee);
                            } catch (NamingException | SQLException e) {
                                e.printStackTrace();
                            }
                            setResponsePage(HomePage.class);
                        }
                    };
                    listItem.add(surnameField);
                    listItem.add(nameField);
                    listItem.add(secondNameField);
                    listItem.add(inputDateOfBirth);
                    listItem.add(inputHireDate);
                    listItem.add(deleteButton);
                    listItem.add(role);
                    listItem.add(managerName);
                    listItem.add(employeeDescription);
                    listItem.add(saveButton);
                    listItem.add(deleteButton);

                } catch (NamingException | SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        AjaxLink sortByHireDate = new AjaxLink("sortByHireDate") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Collections.sort(employees, new HireDateComporator());
                target.add(containerForListOfPersons);
            }
        };
        AjaxLink sortBySurname = new AjaxLink("sortBySurname") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Collections.sort(employees, new SurnameComparator());
                target.add(containerForListOfPersons);
            }
        };
        Form saveForm = new Form("saveForm");
        saveForm.setMultiPart(true);
        DownloadLink downloadXmlLink = new DownloadLink("toXml", new Model<>(new File(TEMP_DIR + "\\employeelist.xml"))) {
            @Override
            public void onClick() {
                try {
                    EmployeeManipulator.getInstance().saveAllEmployeesToXml(getModelObject());
                } catch (SQLException | NamingException e) {
                    e.printStackTrace();
                }
                super.onClick();
            }
        };
        downloadXmlLink.setDeleteAfterDownload(true);
        saveForm.add(downloadXmlLink);
        xmlUploadField = new FileUploadField("xmlUpload");
        Form uploadForm = new Form("uploadXmlForm");
        AjaxButton uploadButton = new AjaxButton("uploadButton", new Model<>("Загрузить файл с списком сотрудников"), uploadForm) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                FileUpload fileUpload = xmlUploadField.getFileUpload();
                try {
                    EmployeeManipulator.getInstance().getEmployeesFromXmlStreamAndInsertToDB(fileUpload.getInputStream());
                } catch (IOException | SQLException | NamingException e) {
                    e.printStackTrace();
                } catch (ConversionException e) {
                    target.appendJavaScript("alert('Некорректный тег в xml')");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    target.appendJavaScript("alert('Некорректно построеный XML')");
                }
                setResponsePage(HomePage.class);

            }
        };
        uploadForm.setMultiPart(true);
        uploadForm.add(xmlUploadField);
        add(uploadForm);
        listOfPersons.setItemsPerPage(15);
        containerForListOfPersons.add(listOfPersons);
        add(containerForListOfPersons);
        add(new AddEmployeePanel("addPersonPanel", new Model<>(new Employee())));
        add(sortByHireDate);
        add(sortBySurname);
        add(saveForm);
        add(uploadButton);
    }


}
