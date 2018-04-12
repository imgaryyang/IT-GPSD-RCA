package com.gps.web.validator;


import com.gps.vo.helper.ActionForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component("fileValidator")
public class FileValidator implements Validator {

    private static final Logger logger = Logger.getLogger(FileValidator.class);

    @Override
    public boolean supports(Class clazz) {
        return ActionForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        MultipartFile file = (MultipartFile)target;
        if(file.getSize() > 10485760) {
            logger.error("File size is greater than maximum allowed [10485760]. size = "+file.getSize());
            errors.reject("file.size");
        }
    }

}
