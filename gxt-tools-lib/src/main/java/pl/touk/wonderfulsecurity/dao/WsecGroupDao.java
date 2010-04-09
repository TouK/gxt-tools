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



package pl.touk.wonderfulsecurity.dao;

import java.util.List;
import pl.touk.wonderfulsecurity.beans.WsecGroup;
import pl.touk.wonderfulsecurity.beans.WsecUser;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public interface WsecGroupDao<T extends WsecGroup> extends WsecBaseDao{
// -------------------------- OTHER METHODS --------------------------
    /**
     * Fetch group by name
     */
    WsecGroup getGroupByName(String roleName);

    /**
     * Gete all users from group
     * @param groupName
     * @return
     * @deprecated
     */
    @Deprecated
    public List<WsecUser> getUsers(String groupName);
}
