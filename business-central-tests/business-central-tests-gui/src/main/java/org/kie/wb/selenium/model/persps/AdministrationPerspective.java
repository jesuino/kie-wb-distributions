/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
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
package org.kie.wb.selenium.model.persps;

import org.kie.wb.selenium.util.Waits;
import org.openqa.selenium.By;

public class AdministrationPerspective extends AbstractPerspective {

    private static final By REPOSITORY_EDITOR_TITLE = By.cssSelector("span[title='Repository Editor']");
    private static final By FILE_EXPLORER_CONTENT = By.cssSelector(" .fa-folder,.fa-folder-open");

    @Override
    public void waitForLoaded() {
        Waits.elementPresent(FILE_EXPLORER_CONTENT);
        Waits.pause(1000);
    }

    @Override
    public boolean isDisplayed() {
        return Waits.isElementPresent(REPOSITORY_EDITOR_TITLE);
    }
}
