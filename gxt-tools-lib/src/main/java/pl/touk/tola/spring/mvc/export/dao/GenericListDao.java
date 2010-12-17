package pl.touk.tola.spring.mvc.export.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import pl.touk.tola.spring.mvc.export.beans.ExportData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

//idea jest taka, zeby miec generyczny mechanizm konwertujacy widok na eksport do csv/xls
public class GenericListDao extends JdbcDaoSupport {

    private Log logger = LogFactory.getLog(GenericListDao.class);

    public ExportData getData(String sql, Object[] params, final List<String> ignoreParameters) {

        final List<String> headers = new ArrayList<String>();

        List<List<Object>> ret =  getJdbcTemplate().query(sql, params, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                List<Object> data = new ArrayList<Object>();
                boolean addHeader = headers.isEmpty();
                //kolumny sa chyba od 1 ;)
                for (int j=1;j<=resultSet.getMetaData().getColumnCount();j++) {
                    if (ignoreParameters.contains(resultSet.getMetaData().getColumnName(j))) {
                        continue;
                    }
                    data.add(resultSet.getObject(j));
                    if (addHeader) {
                        headers.add(resultSet.getMetaData().getColumnName(j));
                    }
                }
                return data;
            }
        });
        return new ExportData(ret, headers);
    }
    

}
