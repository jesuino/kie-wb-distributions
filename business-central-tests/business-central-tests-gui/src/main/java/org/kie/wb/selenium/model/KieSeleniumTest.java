/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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
package org.kie.wb.selenium.model;

import java.util.Arrays;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.kie.wb.selenium.model.persps.HomePerspective;
import org.kie.wb.selenium.util.ScreenshotOnFailure;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public abstract class KieSeleniumTest {

    @Drone
    protected static WebDriver driver;
    @Page
    protected LoginPage login;
    @Page
    protected HomePerspective home;
    @Rule
    public ScreenshotOnFailure screenshotter = new ScreenshotOnFailure();
    protected static final BusinessCentralDistribution DISTRO = getDistribution();

    private static BusinessCentralDistribution getDistribution() {
        String prop = System.getProperty("app.name");
        return BusinessCentralDistribution
                .fromWarNameString(prop)
                .orElseThrow(() -> new IllegalStateException("Invalid app.name='" + prop + "' Expecting one of " + Arrays.asList(BusinessCentralDistribution.values())));
    }
}
