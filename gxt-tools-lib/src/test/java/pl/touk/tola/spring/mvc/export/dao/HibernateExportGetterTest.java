/*
 * Copyright (c) 2008 TouK.pl
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
package pl.touk.tola.spring.mvc.export.dao;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.touk.wonderfulsecurity.dao.WsecBaseDao;


/**
 *
 * @author rpietra
 */
@RunWith(MockitoJUnitRunner.class)
public class HibernateExportGetterTest {

    public class TestClassWithSomeProps {

        public String property1;
        public String otherProperty;
    }
    @Mock
    private WsecBaseDao dao;

    @Test
    public void testIfClassReturnsProperFieldNames() throws ClassNotFoundException {
        //given
        HibernateExportGetter csvExporter = new HibernateExportGetter();
        csvExporter.setWsecBaseDao(dao);

        //when
        List<String> fieldNames = csvExporter.getFieldNames("pl.touk.tola.gwt.shared.ExportParameters");
        //then
        assertTrue("" + fieldNames.size(), fieldNames.size() == 5);

        assertTrue(presentInArray(fieldNames, "sortColumn"));
        assertTrue(presentInArray(fieldNames, "filenamePrefix"));
        assertTrue(presentInArray(fieldNames, "clazz"));
        assertTrue(presentInArray(fieldNames, "parameters"));

        assertFalse(presentInArray(fieldNames, "class")); //no 'class' word!
    }

    private boolean presentInArray(List<String> array, String what) {
        return array.contains(what);
    }
}