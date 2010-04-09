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
package pl.touk.wonderfulsecurity.gwt.client;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public final class WsEvents {

    // TODO: document those events plus what context do they need in event data to execute successfully

    public static final EventType INIT_SECURITY_CONSOLE = new EventType(399999);

    public static final EventType USER_GRID_DOUBLE_CLICK = new EventType(399998);

    public static final EventType START_USER_EDIT = new EventType(399997);

    public static final EventType ASSIGN_GROUP_TO_USER = new EventType(399996);
    
    public static final EventType DEASSIGN_GROUP_FROM_USER = new EventType(399995);
    
    public static final EventType GROUP_GRID_DOUBLE_CLICK  = new EventType(399994);

    public static final EventType ROLE_GRID_DOUBLE_CLICK  = new EventType(3999993);

    public static final EventType START_GROUP_EDIT  = new EventType(399992);

    public static final EventType ASSIGN_ROLE_TO_GROUP  = new EventType(399991);

    public static final EventType DEASSIGN_ROLE_FROM_GROUP  = new EventType(399990);

    public static final EventType ASSIGN_ROLE_TO_USER  = new EventType(399989);

    public static final EventType DEASSIGN_ROLE_FROM_USER  = new EventType(399988);

    public static final EventType CREATE_NEW_USER  = new EventType(399987);

    public static final EventType SAVE_NEW_USER  = new EventType(399986);

	public static final EventType ASSIGN_PERMISSION_TO_USER  = new EventType(399985);

    public static final EventType DEASSIGN_PERMISSION_FROM_USER  = new EventType(399984);

	public static final EventType ASSIGN_PERMISSION_TO_GROUP  = new EventType(399983);

    public static final EventType DEASSIGN_PERMISSION_FROM_GROUP  = new EventType(399982);

	public static final EventType PERMISSION_GRID_DOUBLE_CLICK = new EventType(399981);

	public static final EventType SAVE_EXISTING_USER = new EventType(399980);

	public static final EventType START_ROLE_EDIT = new EventType(399979);

	public static final EventType ASSIGN_PERMISSION_TO_ROLE = new EventType(399978);

	public static final EventType DEASSIGN_PERMISSION_FROM_ROLE = new EventType(399977);

    public static final EventType CREATE_NEW_GROUP = new EventType(399976);

    public static final EventType SAVE_NEW_GROUP = new EventType(399975);
    
    public static final EventType CREATE_NEW_ROLE = new EventType(399974);

    public static final EventType SAVE_NEW_ROLE = new EventType(399973);

	public static final EventType SAVE_EXISTING_GROUP = new EventType(399972);

	public static final EventType SAVE_EXISTING_ROLE = new EventType(399971);
}
