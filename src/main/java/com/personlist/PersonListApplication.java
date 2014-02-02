package com.personlist;

import com.personlist.view.HomePage;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * main application class
 */
public class PersonListApplication extends WebApplication {
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }
    public static PersonListApplication get() {
        return (PersonListApplication) Application.get();
    }

    @Override
    protected void init() {
        super.init();
        getMarkupSettings().setStripWicketTags(true);
    }
}
