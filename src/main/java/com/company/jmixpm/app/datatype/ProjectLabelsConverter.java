package com.company.jmixpm.app.datatype;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProjectLabelsConverter implements AttributeConverter<ProjectLabels, String> {

    @Override
    public String convertToDatabaseColumn(ProjectLabels attribute) {
        return Joiner.on(",").join(attribute.getLabels());
    }

    @Override
    public ProjectLabels convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return new ProjectLabels(Splitter.on(",")
                .splitToList(dbData));
    }
}
