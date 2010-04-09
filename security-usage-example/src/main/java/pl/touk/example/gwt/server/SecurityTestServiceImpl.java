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
package pl.touk.example.gwt.server;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.annotation.Secured;
import pl.touk.example.gwt.client.rpc.SecurityTestService;
import pl.touk.wonderfulsecurity.core.ServerSecurity;

/**
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public class SecurityTestServiceImpl implements SecurityTestService {

    private static final Log log = LogFactory.getLog(SecurityTestServiceImpl.class);

    @Secured("PERMISSION_NO15")
    public void callSecuredMethodWhichICanExecute() {
        // this method should terminate normally causing onSuccess flow in client
        log.info("Executing alloved method");
        return;
    }

    @Secured("PERMISSION_NO2")
    public void callSecureMethodWhichIHaveNoRightToExecute() {
        // this method will not be invoked since logged in user will not have permission to invoke it
        log.info("Executing forbidden method");
        return;
    }


    public void callManuallySecuredMethodWhichICanNotExecute() {
        if (!ServerSecurity.hasPermission("PERMISSION_NO2")) {
            throw new IllegalStateException("No permission");
        }
        log.info("Executing forbidden manually secured method");
    }
}
