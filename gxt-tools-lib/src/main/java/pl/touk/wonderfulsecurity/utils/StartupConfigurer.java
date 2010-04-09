/*
 * Copyright (c) 2007 TouK
 * All rights reserved
 */
package pl.touk.wonderfulsecurity.utils;

import pl.touk.wonderfulsecurity.dao.WsecUserDao;
import pl.touk.wonderfulsecurity.beans.WsecPermission;
import pl.touk.wonderfulsecurity.beans.WsecUser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.TransactionStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import pl.touk.wonderfulsecurity.dao.WsecPermissionDao;

/**
 * Podczas startu aplikacji sprawdza czy w systemie znajdują się wymagane uprawnienia, jeśli nie
 * to do logów zostaje wypisany odpowiedni komunikat i wymagane uprawnienia systemowe zostają dodane
 * do bazy.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class StartupConfigurer implements InitializingBean {

    private static final Log log = LogFactory.getLog(StartupConfigurer.class);
    protected WsecUserDao wsecUserDao;
    protected WsecPermissionDao wsecPermissionDao;
    /**
     * Pole powinno zawierac login superadministratora tzn uzytkownika, ktory ma dostęp do calego panelu
     * administracyjnego. Jesli taki uzytkownik znajduje sie w systemie zostaną mu nadane wszyskie uprawnienia
     * systemowe dla administracyjnej.
     */
    protected String rootLogin;

    public void setRootLogin(String rootLogin) {
        this.rootLogin = rootLogin;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    protected TransactionTemplate transactionTemplate;

    public void setWsecUserDao(WsecUserDao wsecUserDao) {
        this.wsecUserDao = wsecUserDao;
    }

    public void setWsecPermissionDao(WsecPermissionDao wsecPermissionDao) {
        this.wsecPermissionDao = wsecPermissionDao;
    }

    public void afterPropertiesSet() throws Exception {

        final List<WsecPermission> available = wsecUserDao.fetchAll(WsecPermission.class);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (WsecPermission systemPermission : WsecPermission.SYSTEM_PERMISSIONS_COLLECTION) {

                    if (!available.contains(systemPermission)) {
                        log.info("PODCZAS STARTU APLIKACJI NIE ZNALEZIONO UPRAWNIENIA SYSTEMOWEGO " + systemPermission);
                        log.info("W REZULTACIE UPRAWNIENIE O NAZWIE " + systemPermission.getName() + " ZOSTANIE DODANE ");
                        wsecUserDao.saveOrUpdate(systemPermission);
                    }
                }
            }
        });
        // teraz sprawdz czy rootLogin user istnieje jesli tak to nadaj mu wszystkie uprawnienia systemowe
        if (rootLogin != null && !rootLogin.trim().equals("")) {

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    WsecUser rootUser = wsecUserDao.getUserByLogin(rootLogin);

                    if (rootUser != null) {
                        log.info("ROOT USER " + rootLogin + " ZNALEZIONY NADAJE UZYTKOWNIKOWI UPRAWNIENIA SYSTEMOWE");
                        List<WsecPermission> systemPerms = wsecPermissionDao.fetchSystemPermissions();
                        rootUser.getPermissions().addAll(systemPerms);
                    } else {
                        log.info("ROOT USER " + rootLogin + " NIE ZOSTAŁ ZNALEZIONY");
                    }
                }
            });
        }
    }
}
