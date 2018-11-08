/*
 * Copyright 2016 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.wb.selenium.model.widgets;

import org.jboss.arquillian.graphene.fragment.Root;
import org.kie.wb.selenium.util.BusyPopup;
import org.kie.wb.selenium.util.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DropdownMenu {

    public static final String DROPDOWN_TOGGLE = ".dropdown-toggle";

    @Root
    private WebElement dropdownRoot;
    @FindBy(css = DROPDOWN_TOGGLE)
    private WebElement dropdownToggle;

    private void open() {
        Waits.elementClickable(By.cssSelector(DROPDOWN_TOGGLE));
        BusyPopup.retryClickUntilPopupDisappears(dropdownToggle);
    }

    private boolean isOpened() {
        String expandedAttr = dropdownToggle.getAttribute("aria-expanded");
        return "true".equals(expandedAttr);
    }

    public void selectItem(String item) {
        open();
        dropdownRoot.findElement(By.linkText(item)).click();
    }
}
