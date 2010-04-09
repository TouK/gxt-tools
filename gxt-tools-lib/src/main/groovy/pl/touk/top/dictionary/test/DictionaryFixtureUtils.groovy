/*
 * Copyright (c) 2008 TouK.pl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.touk.top.dictionary.test

import org.apache.commons.logging.LogFactory

import org.apache.commons.logging.Log

import pl.touk.wonderfulsecurity.dao.WsecBaseDao

import pl.touk.top.dictionary.model.domain.DictionaryEntry

/**
 *
 * Przygotowanie danych do zaslepionej wersji konsoli oraz do unit testow
 *
 * @author Łukasz Kucharski - lkc@touk.pl
 */
class DictionaryFixtureUtils {

  private static final Log log = LogFactory.getLog(DictionaryFixtureUtils.class)




    private static def entries = [
            ["ERROR_CATEGORY","Kategoria_1","Kategoria_1","",false],
            ["ERROR_CATEGORY","Kategoria_2","Kategoria_2","",false],
            ["ERROR_CATEGORY","Kategoria_3","Kategoria_3","",false],
            ["ERROR_CATEGORY","Kategoria_4","Kategoria_4","",false],
            ["ERROR_CATEGORY","Kategoria_5","Kategoria_5","",false],
            ["ERROR_CATEGORY","Kategoria_6","Kategoria_6","",false],
            ["ERROR_CATEGORY","Kategoria_7","Kategoria_7","",false],
            ["ERROR_CATEGORY","Kategoria_8","Kategoria_8","",false],
            ["ERROR_CATEGORY","Kategoria_9","Kategoria_9","",false],
            ["ERROR_CATEGORY","Kategoria_10","Kategoria_10","",false],
            ["ERROR_CATEGORY","Kategoria_11","Kategoria_11","",false],
            ["ERROR_CATEGORY","OTHER","Niesklasyfikowane","",false],
            ["TYPE","1","typ 1","",false],
            ["TYPE","2","typ 2","",false],
            ["TYPE","3","typ 3","",false],
            ["INSTANCE","1","1 instancja","",false],
            ["INSTANCE","2","2 instancja","",false],
            ["INSTANCE","3","3 instancja","",false],
            ["INSTANCE","4","4 instancja","",false],
            ["INSTANCE","5","5 instancja","",false],
            ["BRAND","BRAND_1","Brand 1","",false],
            ["BRAND","BRAND_2","Brand 2","",false],
            ["Operators","001","Polkomtel S.A","",false],
            ["Operators","002","PTC","",false],
            ["CONTRACT_TYPE","0","Standardowy","",false],
            ["CONTRACT_TYPE","1","One Visit","",false],
            ["PROCESS_TYPE","PREPAID","Prepaid","",false],
            ["PROCESS_TYPE","POSTPAID","Postpaid","",false]
            ]

  

    public static Collection ENTRIES = null;
    public static Collection EAGER_ENTRIES = null;
    public static Collection TYPE_ENTRIES = null;

    public static void prepareCasesFixture(WsecBaseDao genericDao ){

        ENTRIES = entries.collect{
            new DictionaryEntry(*it)
        }

        EAGER_ENTRIES = ENTRIES.grep{!it.lazyLoad}
        TYPE_ENTRIES = ENTRIES.grep{it.category == "TYPE"}


      genericDao.persistAll(ENTRIES)



    }

}