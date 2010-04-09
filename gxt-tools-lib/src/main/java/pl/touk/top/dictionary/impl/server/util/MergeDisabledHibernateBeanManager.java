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

package pl.touk.top.dictionary.impl.server.util;/*
* Copyright (c) 2007 TouK
* All rights reserved
*/

import net.sf.hibernate4gwt.core.HibernateBeanManager;

/**
 * This is tweaked implementation of HibernateBeanManager. It does not support merging of objects from client JS back to
 * hibernate session. Argument object that comes from GWT is just returned from {@link #merge(Object, boolean)} as is.
 * This class is then only used to kill hibernate proxies in pojos fetchted via hibernate session.
 * Such proxies usually exist in lazy loaded association and cannot be serialized and sent to GWT client side
 * <p/>
 * <br>
 * <bold>Never merge or attach objects to hibernate session directly, but treat them as simple DTO's and always
 * refetch objects you need from session by id. Or use only ids to operate on your beans in DB</bold>
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class MergeDisabledHibernateBeanManager extends HibernateBeanManager {
// -------------------------- OTHER METHODS --------------------------

    /**
     * Override original merge method. This implementation does nothing but passes back object sent by GWTRPC
     */
    @Override
    public Object merge(Object object, boolean assignable) {
        return object;
    }
}
