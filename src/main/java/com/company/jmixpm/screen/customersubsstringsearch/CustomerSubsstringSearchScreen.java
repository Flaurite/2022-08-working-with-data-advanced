package com.company.jmixpm.screen.customersubsstringsearch;

import com.company.jmixpm.entity.Customer;
import com.google.common.base.Strings;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("CustomerSubsstringSearchScreen")
@UiDescriptor("customer-subsstring-search-screen.xml")
public class CustomerSubsstringSearchScreen extends Screen {

//    @Autowired
//    private CollectionLoader<Customer> customersDl;
//
//    @Subscribe("nameField")
//    public void onNameFieldValueChange(HasValue.ValueChangeEvent<String> event) {
//        customersDl.setParameter("name",
//                Strings.isNullOrEmpty(event.getValue())
//                        ? ""
//                        : "(?i)%" + event.getValue() + "%");
//        customersDl.load();
//    }
}