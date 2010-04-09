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

import java.io.Serializable;

/**
 * Model bean that extends WsecGroup by adding some additional fields that has only meaning for views that displays them.
 * Those beans should be treated as DTOs and are usually copies of original beans made via copy constructor.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class GroupView extends WsecGroup implements Serializable {
// ------------------------------ FIELDS ------------------------------

    /**
     * True indicates that this group is assigned for user {@link #assignedToUserId}
     *
     * See {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchAllAvailableGroupsMarkAssignedToUser(String, Long)}
     */
    boolean assigned = false;
    /**
     * This field indicates to which user this Group is assigned
     *
     * See {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchAllAvailableGroupsMarkAssignedToUser(String, Long)}
     */
    Long assignedToUserId;

// --------------------------- CONSTRUCTORS ---------------------------

    public GroupView() {
    }

    public GroupView(WsecGroup group) {
        super(group);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
