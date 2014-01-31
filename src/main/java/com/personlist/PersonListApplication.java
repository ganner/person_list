package com.personlist;

import com.personlist.view.HomePage;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created with IntelliJ IDEA.
 * User: DAP
 * Date: 26.01.14
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
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
