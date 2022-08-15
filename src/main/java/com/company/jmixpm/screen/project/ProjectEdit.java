package com.company.jmixpm.screen.project;

import com.company.jmixpm.app.ProjectsService;
import com.company.jmixpm.app.datatype.ProjectLabels;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.validation.Validator;
import io.jmix.ui.component.validator.BeanPropertyValidator;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {

    @Autowired
    private TextArea<ProjectLabels> labelsField;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private Notifications notifications;

    @Autowired
    private javax.validation.Validator validator;

    @Subscribe
    public void onInitEntity1(InitEvent event) {
        Collection<Validator<ProjectLabels>> validators = new ArrayList<>(labelsField.getValidators());
        for (Validator<ProjectLabels> fieldValidator : validators) {
            if (fieldValidator instanceof BeanPropertyValidator) {
                labelsField.removeValidator(fieldValidator);
            }
        }
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<Project> event) {
        labelsField.setEditable(true);

        event.getEntity().setLabels(new ProjectLabels(List.of("bug", "enhancement", "task")));
    }

    @Subscribe("saveWithBeanValidationBtn")
    public void onSaveWithBeanValidationBtnClick(Button.ClickEvent event) {
        try {
            projectsService.saveProject(getEditedEntity());
            close(new StandardCloseAction(WINDOW_COMMIT, false));
        } catch (ConstraintViolationException e) {
            showBeanValidationExceptions(e.getConstraintViolations());
        }
    }

    @Subscribe("programmaticValidationBtn")
    public void onProgrammaticValidationBtnClick(Button.ClickEvent event) {
        Set<ConstraintViolation<Project>> violations = validator.validate(getEditedEntity(),
                Default.class, UiComponentChecks.class, UiCrossFieldChecks.class);

        showBeanValidationExceptions((Set) violations);
    }

    private void showBeanValidationExceptions(Set<ConstraintViolation<?>> constraintViolations) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            sb.append(constraintViolation.getMessage()).append("\n");
        }

        notifications.create()
                .withCaption("Alert")
                .withDescription(sb.toString())
                .show();
    }
}