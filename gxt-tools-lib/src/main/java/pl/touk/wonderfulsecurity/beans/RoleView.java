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

/**
 * Model bean that extends WsecRole by adding some additional fields that has only meaning for views that displays them.
 * Those beans should be treated as DTOs and are usually copies of original beans made via copy constructor.
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */

public class RoleView extends WsecRole{
// ------------------------------ FIELDS ------------------------------

    // TODO: mozna to zrefaktoryzować zeby nie uzywac pol typu assignedToUserId i podobnych, ten kontekst jest tutaj nadmiarowy
    /**
     * If true it means that this particular role instance is assigned to group with id {@link #assignedToGroupId}
     * If false it means that this Role is not assigned to {@link #assignedToGroupId} group
     *
     * See {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchAllAvailableRolesMarkAssignedToGroup(String, Long)}
     */
    protected boolean assignedToGroup;
    /**
     * Indicates to which group this particular instance of Role is assigned
     *
     * See {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchAllAvailableRolesMarkAssignedToGroup(String, Long)}
     */
    protected Long assignedToGroupId;
    /**
     * Indicates that this instance of role is assigned to user. User id to which it is assigned is stored in {@link #assignedToUserId}
     * If false it means that this instance is not assigned to {@link #assignedToUserId}
     *
     * See {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchAllAvailableRolesMarkAssignedToUser(String, Long)}
     */
    protected boolean assignedToUser;
    /**
     * If {@link #assignedToUser} is true then this field should indicate to which user this Role is assigned
     *
     * See {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchAllAvailableRolesMarkAssignedToUser(String, Long)}
     */
    protected Long assignedToUserId;
    /**
     * Indicates from which GROUP this Role is inherited by User see
     * {@link pl.touk.wonderfulsecurity.service.ISecurityManager#fetchInheritedRolesForUser(String, Long)}
     */
    protected String inheritedFromGroup;

// --------------------------- CONSTRUCTORS ---------------------------

    public RoleView() {
    }

    public RoleView(WsecRole role) {
        super(role);
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public Long getAssignedToGroupId() {
        return assignedToGroupId;
    }

    public void setAssignedToGroupId(Long assignedToGroupId) {
        this.assignedToGroupId = assignedToGroupId;
    }

    public Long getAssignedToUserId() {
        return assignedToUserId;
    }

    public void setAssignedToUserId(Long assignedToUserId) {
        this.assignedToUserId = assignedToUserId;
    }

    public String getInheritedFromGroup() {
        return inheritedFromGroup;
    }

    public void setInheritedFromGroup(String inheritedFromGroup) {
        this.inheritedFromGroup = inheritedFromGroup;
    }

    public boolean isAssignedToGroup() {
        return assignedToGroup;
    }

    public void setAssignedToGroup(boolean assignedToGroup) {
        this.assignedToGroup = assignedToGroup;
    }

    public boolean isAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(boolean assignedToUser) {
        this.assignedToUser = assignedToUser;
    }
}
