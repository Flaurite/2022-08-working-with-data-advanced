package com.company.jmixpm.screen.userloading;

import com.company.jmixpm.screen.userloadinginfo.UserLoadingInfoScreen;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("UserLoadingScreen")
@UiDescriptor("user-loading-screen.xml")
public class UserLoadingScreen extends Screen {

    @Autowired
    private ScreenBuilders screenBuilders;

    @Autowired
    private Table<KeyValueEntity> usersLoadingTable;

    @Subscribe("usersLoadingTable.showUSerLoadingInfo")
    public void onUsersLoadingTableShowUSerLoadingInfo(Action.ActionPerformedEvent event) {
        screenBuilders.screen(this)
                .withScreenClass(UserLoadingInfoScreen.class)
                .build()
                .withItem(usersLoadingTable.getSingleSelected())
                .show();
    }
}