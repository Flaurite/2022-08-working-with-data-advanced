package com.company.jmixpm.app.validation;

import com.company.jmixpm.app.datatype.ProjectLabels;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class SizeProjectLabelsValidator implements ConstraintValidator<SizeProjectLabels, ProjectLabels> {

    private int min;
    private int max;

    @Override
    public void initialize(SizeProjectLabels constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(ProjectLabels value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        List<String> labels = value.getLabels();

        return labels.size() >= min && labels.size() <= max;
    }
}
