package com.company.jmixpm.screen.orderline;

import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.OrderLine;

@UiController("OrderLine.edit")
@UiDescriptor("order-line-edit.xml")
@EditedEntityContainer("orderLineDc")
public class OrderLineEdit extends StandardEditor<OrderLine> {
}