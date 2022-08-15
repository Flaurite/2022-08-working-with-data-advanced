package com.company.jmixpm.screen.userloadinginfo;

import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.KeyValueContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("UserLoadingInfoScreen")
@UiDescriptor("user-loading-info-screen.xml")
public class UserLoadingInfoScreen extends Screen {

    @Autowired
    private KeyValueContainer userLoadingInfoDc;

    @Autowired
    private Notifications notifications;

    public UserLoadingInfoScreen withItem(KeyValueEntity item) {
        userLoadingInfoDc.setItem(item);
        return this;
    }

    @Subscribe("closeWindow")
    public void onCloseWindow(Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }

    @Subscribe(id = "userLoadingInfoDc", target = Target.DATA_CONTAINER)
    public void onUserLoadingInfoDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<KeyValueEntity> event) {
        notifications.create()
                .withType(Notifications.NotificationType.TRAY)
                .withCaption("Property changed: " + event.getProperty())
                .show();
    }
}