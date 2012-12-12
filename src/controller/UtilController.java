package controller;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.admin.FormField;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import repository.FormFieldRepository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UtilController {
  public static final String VERSION_NUMBER = "1.0";

  @Autowired
  private FormFieldRepository formFieldRepository;

  public Map<String, Object> getFormFieldsForForm(String formName) {
    List<FormField> formFields = formFieldRepository.getFormFields(formName);
    Map<String, Object> formFieldMap = new HashMap<String, Object>();

    Map<String, String> mirroredFields = new HashMap<String, String>();
    for (FormField ff : formFields) {
      Map<String, Object> fieldProperties = new HashMap<String, Object>();
      fieldProperties.put(FormField.DISPLAY_NAME, ff.getDisplayName());
      fieldProperties.put(FormField.DEFAULT_VALUE, ff.getDefaultValue());
      fieldProperties.put(FormField.HIDDEN, ff.getHidden());
      fieldProperties.put(FormField.DERIVED, ff.getDerived());
      fieldProperties.put(FormField.SOURCE_FIELD, ff.getSourceField());

      formFieldMap.put(ff.getField(), fieldProperties);
      if (ff.getDerived())
        mirroredFields.put(ff.getField(), ff.getSourceField());
    }

    ObjectMapper mapper = new ObjectMapper();
    StringWriter writer = new StringWriter();
    try {
      mapper.writeValue(writer, mirroredFields);
    } catch (JsonGenerationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println(writer.toString());
    formFieldMap.put("mirroredFields", writer.toString());

    return formFieldMap;
  }

  private List<String> getRequiredFields(String formName) {
    return formFieldRepository.getRequiredFormFields(formName);
  }

  public void checkRequiredFields(Object form, String formName, Errors errors) {
    try {
      Map<String, Object> properties = BeanUtils.describe(form);
      List<String> requiredFields = getRequiredFields(formName);
      for (String requiredField : requiredFields) {
        if (properties.containsKey(requiredField)) {
          Object fieldValue = properties.get(requiredField);
          if (fieldValue == null ||
              (fieldValue instanceof String && ((String)fieldValue).isEmpty())
             )
            errors.rejectValue(formName + "." + requiredField, "requiredField.error", "This field is required");
        }
      }
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
