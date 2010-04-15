package pl.touk.rapidgxt.dataaccess;

import pl.touk.wonderfulsecurity.beans.PagedQueryResult;
import pl.touk.wonderfulsecurity.dao.WsecBaseDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class GenericDataAccessServiceImpl implements GenericDataAccessService {

    protected WsecBaseDao genericDao;

    public ArrayList fetchPagedList(HashMap<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, String className)  {
        try {
            return genericDao.fetchPagedList(queryParameters, offset, howMany, sortColumn, desc, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PagedQueryResult fetchPagedListWithOverallCount(HashMap<String, ?> queryParameters, Integer offset, Integer howMany, String sortColumn, Boolean desc, String className)  {
        try {
            return genericDao.fetchPagedListWithOverallCount(queryParameters, offset, howMany, sortColumn, desc, Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGenericDao(WsecBaseDao genericDao) {
        this.genericDao = genericDao;
    }
}
