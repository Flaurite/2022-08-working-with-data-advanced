package com.company.jmixpm.entity.timeentry;

import com.company.jmixpm.entity.Project;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.TimeEntry;

@UiController("TimeEntry.edit")
@UiDescriptor("time-entry-edit.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryEdit extends StandardEditor<TimeEntry> {

}