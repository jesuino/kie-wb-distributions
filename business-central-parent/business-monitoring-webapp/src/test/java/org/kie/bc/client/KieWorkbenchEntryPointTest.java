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

package org.kie.bc.client;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.dashbuilder.client.cms.screen.explorer.NavigationExplorerScreen;
import org.dashbuilder.client.navigation.NavigationManager;
import org.dashbuilder.client.navigation.event.NavTreeLoadedEvent;
import org.dashbuilder.client.navigation.impl.NavigationManagerImpl;
import org.dashbuilder.client.navigation.widget.editor.NavTreeEditor;
import org.dashbuilder.navigation.NavGroup;
import org.dashbuilder.navigation.NavItem;
import org.dashbuilder.navigation.NavTree;
import org.dashbuilder.navigation.service.NavigationServices;
import org.guvnor.common.services.shared.config.AppConfigService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.bc.client.KieWorkbenchEntryPoint;
import org.kie.bc.client.navigation.NavTreeDefinitions;
import org.kie.bc.client.resources.i18n.NavigationConstants;
import org.kie.workbench.common.workbench.client.admin.DefaultAdminPageHelper;
import org.kie.workbench.common.workbench.client.authz.PermissionTreeSetup;
import org.kie.workbench.common.workbench.client.error.DefaultWorkbenchErrorCallback;
import org.kie.workbench.common.workbench.client.menu.DefaultWorkbenchFeaturesMenusHelper;
import org.mockito.Mock;
import org.uberfire.client.mvp.ActivityBeansCache;
import org.uberfire.client.workbench.Workbench;
import org.uberfire.client.workbench.widgets.menu.megamenu.WorkbenchMegaMenuPresenter;
import org.uberfire.ext.security.management.client.ClientUserSystemManager;
import org.uberfire.mocks.CallerMock;
import org.uberfire.mocks.EventSourceMock;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class KieWorkbenchEntryPointTest {

    @Mock
    private AppConfigService appConfigService;

    @Mock
    private ActivityBeansCache activityBeansCache;

    @Mock
    private DefaultWorkbenchFeaturesMenusHelper menusHelper;

    @Mock
    protected ClientUserSystemManager userSystemManager;

    @Mock
    protected WorkbenchMegaMenuPresenter menuBar;

    @Mock
    protected Workbench workbench;

    @Mock
    protected PermissionTreeSetup permissionTreeSetup;

    @Mock
    private DefaultAdminPageHelper adminPageHelper;

    private NavTreeDefinitions navTreeDefinitions;

    private NavigationManager navigationManager;

    @Mock
    protected NavigationExplorerScreen navigationExplorerScreen;

    @Mock
    protected NavigationConstants navigationConstants;

    @Mock
    protected NavigationServices navigationServices;

    @Mock
    protected NavTreeEditor navTreeEditor;

    @Mock
    protected EventSourceMock<NavTreeLoadedEvent> navTreeLoadedEvent;

    @Mock
    private DefaultWorkbenchErrorCallback defaultWorkbenchErrorCallback;

    private KieWorkbenchEntryPoint kieWorkbenchEntryPoint;

    @Before
    public void setup() {
        navTreeDefinitions = new NavTreeDefinitions();
        navigationManager = new NavigationManagerImpl(new CallerMock<>(navigationServices),
                                                      null,
                                                      navTreeLoadedEvent,
                                                      null,
                                                      null);

        doAnswer(invocationOnMock -> {
            ((Command) invocationOnMock.getArguments()[0]).execute();
            return null;
        }).when(userSystemManager).waitForInitialization(any(Command.class));

        doReturn(mock(MenuFactory.TopLevelMenusBuilder.class)).when(menusHelper).buildMenusFromNavTree(any());

        CallerMock<AppConfigService> appConfigServiceCallerMock = new CallerMock<>(appConfigService);

        kieWorkbenchEntryPoint = spy(new KieWorkbenchEntryPoint(appConfigServiceCallerMock,
                                                                activityBeansCache,
                                                                menusHelper,
                                                                userSystemManager,
                                                                menuBar,
                                                                workbench,
                                                                permissionTreeSetup,
                                                                adminPageHelper,
                                                                navTreeDefinitions,
                                                                navigationManager,
                                                                navigationExplorerScreen,
                                                                defaultWorkbenchErrorCallback));

        doNothing().when(kieWorkbenchEntryPoint).hideLoadingPopup();
        when(navigationExplorerScreen.getNavTreeEditor()).thenReturn(navTreeEditor);
    }

    @Test
    public void initTest() {
        kieWorkbenchEntryPoint.init();

        verify(workbench).addStartupBlocker(KieWorkbenchEntryPoint.class);
        verify(navTreeEditor).setMaxLevels(NavTreeDefinitions.GROUP_WORKBENCH,
                                           2);
    }

    @Test
    public void testInitializeWorkbench(){
        kieWorkbenchEntryPoint.initializeWorkbench();

        verify(permissionTreeSetup).configureTree();
    }

    @Test
    public void setupMenuTest() {
        kieWorkbenchEntryPoint.setupMenu();

        verify(menuBar).addMenus(any());
        verify(menusHelper).addUtilitiesMenuItems();

        verify(workbench).removeStartupBlocker(KieWorkbenchEntryPoint.class);
    }

    @Test
    public void defaultNavTreeTest() {
        kieWorkbenchEntryPoint.setupMenu();

        NavTree navTree = navigationManager.getNavTree();

        NavGroup workbench = (NavGroup) navTree.getItemById(NavTreeDefinitions.GROUP_WORKBENCH);

        NavGroup design = (NavGroup) navTree.getItemById(NavTreeDefinitions.GROUP_DESIGN);
        NavItem pages = navTree.getItemById(NavTreeDefinitions.ENTRY_PAGES);

        NavGroup deploy = (NavGroup) navTree.getItemById(NavTreeDefinitions.GROUP_DEPLOY);
        NavItem execServers = navTree.getItemById(NavTreeDefinitions.ENTRY_EXECUTION_SERVERS);

        NavGroup manage = (NavGroup) navTree.getItemById(NavTreeDefinitions.GROUP_MANAGE);
        NavItem processDef = navTree.getItemById(NavTreeDefinitions.ENTRY_PROCESS_DEFINITIONS);
        NavItem processInst = navTree.getItemById(NavTreeDefinitions.ENTRY_PROCESS_INSTANCES);
        NavItem taskAdmin = navTree.getItemById(NavTreeDefinitions.ENTRY_ADMINISTRATION_TASKS);
        NavItem jobs = navTree.getItemById(NavTreeDefinitions.ENTRY_JOBS);
        NavItem executionErrors = navTree.getItemById(NavTreeDefinitions.ENTRY_EXECUTION_ERRORS);

        NavGroup track = (NavGroup) navTree.getItemById(NavTreeDefinitions.GROUP_TRACK);
        NavItem tasks = navTree.getItemById(NavTreeDefinitions.ENTRY_TASKS_LIST);
        NavItem processDashboard = navTree.getItemById(NavTreeDefinitions.ENTRY_PROCESS_DASHBOARD);
        NavItem taskDashboard = navTree.getItemById(NavTreeDefinitions.ENTRY_TASK_DASHBOARD);

        assertNotNull(workbench);
        assertNotNull(design);
        assertNotNull(deploy);
        assertNotNull(manage);
        assertNotNull(track);
        assertEquals(design.getParent(),
                     workbench);
        assertEquals(deploy.getParent(),
                     workbench);
        assertEquals(manage.getParent(),
                     workbench);
        assertEquals(track.getParent(),
                     workbench);

        assertNotNull(pages);
        assertEquals(pages.getParent(),
                     design);

        assertNotNull(execServers);
        assertEquals(execServers.getParent(),
                     deploy);

        assertNotNull(processDef);
        assertNotNull(processInst);
        assertNotNull(taskAdmin);
        assertNotNull(jobs);
        assertNotNull(executionErrors);
        assertEquals(processDef.getParent(),
                     manage);
        assertEquals(processInst.getParent(),
                     manage);
        assertEquals(taskAdmin.getParent(),
                     manage);
        assertEquals(jobs.getParent(),
                     manage);
        assertEquals(executionErrors.getParent(),
                     manage);

        assertNotNull(tasks);
        assertNotNull(processDashboard);
        assertNotNull(taskDashboard);
        assertEquals(tasks.getParent(),
                     track);
        assertEquals(processDashboard.getParent(),
                     track);
        assertEquals(taskDashboard.getParent(),
                     track);

        assertFalse(design.isModifiable());
        assertFalse(pages.isModifiable());
    }

    @Test
    public void testSetupAdminPage(){
        kieWorkbenchEntryPoint.setupAdminPage();

        verify(adminPageHelper).setup(false,
                                      true,
                                      false);
    }
}
