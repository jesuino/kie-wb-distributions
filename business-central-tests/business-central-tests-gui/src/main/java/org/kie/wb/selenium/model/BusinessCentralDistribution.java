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

package org.kie.wb.selenium.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum BusinessCentralDistribution {

    BUSINESS_CENTRAL,
    BUSINESS_MONITORING;

    public static Optional<BusinessCentralDistribution> fromWarNameString(final String warName) {
        return Stream.of(BusinessCentralDistribution.values())
                .filter(distro -> distro.getWarName().equals(warName))
                .findFirst();
    }

    public String getWarName() {
        return this.name().toLowerCase().replace("_", "-");
    }
}
