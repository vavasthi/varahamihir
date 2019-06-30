/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.avasthi.varahamihir.common.enums;

import com.avasthi.varahamihir.common.constants.VarahamihirConstants;

import java.util.List;


/**
 * Created by praveen on 12/4/16.
 */

public enum SubscriptionApps {

    GCM(VarahamihirConstants.STR_NOTIFICATION_TYPE_GCM, VarahamihirConstants.SUBSCRIPTION_GCM_APPS),
    APNS(VarahamihirConstants.STR_NOTIFICATION_TYPE_APNS, VarahamihirConstants.SUBSCRIPTION_APNS_APPS);

    private final String appType;
    private final List<String> appIdList;


    SubscriptionApps(String appType, List<String> appIdList) {
        this.appType = appType;
        this.appIdList = appIdList;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public List<String> getValue() {
        return appIdList;
    }
}
