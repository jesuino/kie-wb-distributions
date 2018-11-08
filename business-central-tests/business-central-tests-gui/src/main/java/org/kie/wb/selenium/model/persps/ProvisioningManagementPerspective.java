/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.wb.selenium.model.persps;

import org.jboss.arquillian.graphene.Graphene;
import org.kie.wb.selenium.util.Waits;
import org.openqa.selenium.By;

public class ProvisioningManagementPerspective extends AbstractPerspective {

    private static final By PROVISIONING_MANAGEMENT_SCREEN_ITEM = By.cssSelector("[data-field='provider-type-list-group']");

    @Override
    public boolean isDisplayed() {
        try {
            return Waits.isElementPresent(PROVISIONING_MANAGEMENT_SCREEN_ITEM);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void waitForLoaded() {
        Graphene.waitModel().until().element(By.cssSelector(".alert-dismissable")).is().not().present();
    }
}
