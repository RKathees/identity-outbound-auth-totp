/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.identity.application.authenticator.totp.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authenticator.totp.TOTPKeyGenerator;
import org.wso2.carbon.identity.application.authenticator.totp.exception.TOTPException;
import org.wso2.carbon.user.api.UserStoreException;

public class TOTPAdminService {

    private static Log log = LogFactory.getLog(TOTPAdminService.class);

    /**
     * Generate TOTP Token for the give user
     *
     * @param username username of the user
     * @param context  Authentication context.
     * @return
     * @throws TOTPException
     */
    public String initTOTP(String username, AuthenticationContext context) throws TOTPException, UserStoreException {
        String qrCodeURL;
        try {
            qrCodeURL = TOTPKeyGenerator.getInstance().getQRCodeURL(username, context);
            return qrCodeURL;
        } catch (TOTPException e) {
            log.error("TOTPAdminService failed to generateTOTP key for the user : " + username, e);
            throw new TOTPException("TOTPAdminService failed to generateTOTP key for the user : " + username, e);
        }
    }

    /**
     * reset TOTP credentials of the user
     *
     * @param username of the user
     * @return
     * @throws TOTPException
     */
    public boolean resetTOTP(String username) throws TOTPException {
        return TOTPKeyGenerator.getInstance().resetLocal(username);
    }

    /**
     * reset TOTP credentials of the user
     *
     * @param username of the user
     * @param context  Authentication context.
     * @return
     * @throws TOTPException
     */
    public String refreshSecretKey(String username, AuthenticationContext context) throws TOTPException {
        String secretKey;
        try {
            secretKey = TOTPKeyGenerator.getInstance().generateTOTPKeyLocal(username, context);
            return secretKey;
        } catch (TOTPException e) {
            log.error("TOTPAdminService failed to generateTOTP key for the user : " + username, e);
            throw new TOTPException("TOTPAdminService failed to generateTOTP key for the user : " + username, e);
        }
    }
}