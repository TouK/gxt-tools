package pl.touk.tola.spring.mvc.export.dao;

import pl.touk.tola.spring.mvc.export.ExportDataGetter;
import pl.touk.tola.spring.mvc.export.beans.ExportData;
import org.springframework.beans.BeanUtils;
import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.wonderfulsecurity.dao.WsecBaseDao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class HibernateExportGetter implements ExportDataGetter {

    WsecBaseDao wsecBaseDao;

    public ExportData getData(ExportParameters parameters) throws Exception {
        return new ExportData(exportDataFromGivenTable(parameters), getFieldNames(parameters.getClazz()));
    }


    protected List<String> getFieldNames(String clazzName) throws ClassNotFoundException {
        Class clazz = Class.forName(clazzName);
        ArrayList<String> properties = new ArrayList<String>();
        PropertyDescriptor propertyDescriptors[] = BeanUtils.getPropertyDescriptors(clazz);
        for (PropertyDescriptor field : propertyDescriptors) {
            if (!field.isHidden() && field.getReadMethod().getModifiers() == Method.DECLARED) {
                properties.add(field.getName());
            }
        }
        return properties;
    }

    protected ArrayList exportDataFromGivenTable(ExportParameters exportParameters) throws ClassNotFoundException {
        return wsecBaseDao.fetchList(exportParameters.getParameters(), exportParameters.getSortColumn(), true, Class.forName(exportParameters.getClazz()));
    }

    public void setWsecBaseDao(WsecBaseDao wsecBaseDao) {
        this.wsecBaseDao = wsecBaseDao;
    }
}

