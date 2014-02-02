package com.personlist.xmlprocessing;

import com.personlist.model.pojo.Employee;
import com.personlist.model.pojo.ManagerInfo;
import com.personlist.model.pojo.Role;
import com.thoughtworks.xstream.XStream;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class XmlWorker {

    private static XmlWorker instance;

    private XmlWorker(){

    }

    public static XmlWorker getInstance(){
        if(instance == null){
            instance = new XmlWorker();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public  <A> List<A> fromXML(Class<A> aClass, InputStream inputStream, XStream xStreamWithAlias) {
        try (
                Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        ) {
            Object object = xStreamWithAlias.fromXML(reader);
            List<A> listOfObjects = new ArrayList<>();
            if (object instanceof List && !((List) object).isEmpty()) {
                listOfObjects.addAll((List<A>) object);
            } else if (aClass.isInstance(object)) {
                listOfObjects.add((A) object);
            }
            return listOfObjects;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public  boolean toXML(Object object, File file,XStream streamWithAlias) {
        XStream xStream = streamWithAlias;
        try (
                OutputStream outputStream = new FileOutputStream(file);
                Writer writer = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
        ) {
            xStream.toXML(object, writer);
        } catch (Exception exp) {

            return false;
        }
        return true;
    }

    public  XStream createEmployeeAliasXStream(){
        XStream xStream = new XStream();
        xStream.alias("employee",Employee.class);
        xStream.alias("manager",ManagerInfo.class);
        xStream.alias("role",Role.class);
        return xStream;
    }
}
