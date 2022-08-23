package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntityLoadingEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("jmixpm_TaskChangedListener")
public class TaskChangedListener {
    private static final Logger log = LoggerFactory.getLogger(TaskChangedListener.class);

    private DataManager dataManager;

    public TaskChangedListener(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    // stackoverflow while using DynamicAttributesPanel?
//    @EventListener
    public void onTaskChangedEvent(EntityChangedEvent<Task> event) {
        Project project;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Object> projectId = event.getChanges().getOldReferenceId("project");
            project = (Project) dataManager.load(projectId).one();
        } else {
            Task task = dataManager.load(event.getEntityId()).one();
            project = task.getProject();
        }

        Integer estimatedEfforts = project.getTasks().stream()
                .mapToInt(task -> task.getEstimatedEfforts() == null ? 0 : task.getEstimatedEfforts())
                .sum();

        project.setTotalEstimatedEfforts(estimatedEfforts);

        dataManager.save(project);
    }

    @EventListener
    public void onTaskSaving(EntitySavingEvent<Task> event) {
        log.info("EntitySavingEvent: " + event.getEntity().getId());
    }

    @EventListener
    public void onTaskLoading(EntityLoadingEvent<Task> event) {
        log.info("EntityLoadingEvent: " + event.getEntity().getId());
    }
}