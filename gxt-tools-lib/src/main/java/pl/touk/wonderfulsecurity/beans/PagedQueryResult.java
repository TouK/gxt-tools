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
package pl.touk.wonderfulsecurity.beans;


import java.util.*;
import java.io.Serializable;

/**
 * This DTO has to extend and use Map otherwise hibernate4gwt will not detect and
 * substitute hibernate proxies inside regular POJO that encapsulates collection of HB managed pojos
 *<br>
 *
 * This is workaround for bug in hibernate4gwt 
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class PagedQueryResult<T extends Serializable> extends HashMap<String, Serializable> implements Serializable {
// ------------------------------ FIELDS ------------------------------

    public static final String RESULT_KEY = "RESULT_";

    public static final String OVERALL_COUNT_KEY = "OVERALL_COUNT_";

// --------------------------- CONSTRUCTORS ---------------------------

    public PagedQueryResult() {
    }

    public PagedQueryResult(T result, int overallCount) {
        this.put(RESULT_KEY, result);
        this.put(OVERALL_COUNT_KEY, overallCount);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public T getResult() {
        return (T)this.get(RESULT_KEY);
    }

    public <T extends Serializable> void setResult(T result) {
        this.put(RESULT_KEY, result);
    }

// -------------------------- OTHER METHODS --------------------------

    public int getOverallCount() {
        return (Integer)this.get(OVERALL_COUNT_KEY);
    }

}
