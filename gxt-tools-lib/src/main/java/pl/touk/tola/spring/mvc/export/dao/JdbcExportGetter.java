package pl.touk.tola.spring.mvc.export.dao;

import pl.touk.tola.spring.mvc.export.ExportDataGetter;

import java.util.List;
import pl.touk.tola.gwt.shared.ExportParameters;
import pl.touk.tola.spring.mvc.export.beans.ExportData;

/**
 * Przykladowa konfiguracja
 *     <bean id="jdbcExportGetter" class="pl.touk.tola.spring.mvc.export.dao.JdbcExportGetter">
        <property name="genericListDao">
            <bean class="pl.touk.tola.spring.mvc.export.dao.GenericListDao">
                <property name="dataSource" ref="jakiesDataSource"/>
            </bean>
        </property>
    </bean>

 *
 * @author rpietra
 */
public class JdbcExportGetter implements ExportDataGetter{

    GenericListDao genericListDao;

    public ExportData getData(ExportParameters parameters) throws Exception {

        Class klazz = getClass().getClassLoader().loadClass(parameters.getClazz());
        SqlPreparer preparer= ((SqlPreparer) klazz.newInstance());

        return genericListDao.getData(preparer.makeSql(parameters), preparer.getParameters(parameters), preparer.getIgnoredFields());

    }

    public interface SqlPreparer {

        /**
         * Zwraca zapytanie sql uzyte do pobrania danych do exportu
         * @param exportParameters
         * @return
         */
        String makeSql(ExportParameters exportParameters);

        /**
         * Parametry uzyte do zapytania do exportu
         * @param exportParameters
         * @return
         */
        Object[] getParameters(ExportParameters exportParameters);

        /**
         * Ktore pola po odczycie z bazy maja zostac zignorowane i niewyexportowane
         * @return
         */
        List<String> getIgnoredFields();
    }

    public void setGenericListDao(GenericListDao genericListDao) {
        this.genericListDao = genericListDao;
    }
}
