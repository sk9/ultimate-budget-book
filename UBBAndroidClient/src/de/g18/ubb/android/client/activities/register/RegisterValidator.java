package de.g18.ubb.android.client.activities.register;

import de.g18.ubb.android.client.validation.AbstractValidator;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.service.repository.ServiceRepository;
import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class RegisterValidator extends AbstractValidator<RegisterModel> {

    public RegisterValidator(RegisterModel aModel) {
        super(aModel);
    }

    @Override
    protected String computeValidationResult() {
        if (StringUtil.isEmpty(getModel().getUsername())) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_USERNAME);
        }
        if (StringUtil.isEmpty(getModel().getEMail())) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_EMAIL);
        }
        if (StringUtil.isEmpty(getModel().getPassword())) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_PASSWORD);
        }
        if (StringUtil.isEmpty(getModel().getPasswordCheck())) {
            return ValidationUtil.createMustNotBeEmptyMessage(RegisterResource.FIELD_PASSWORD_CHECK);
        }
        if (!ObjectUtil.equals(getModel().getPassword(), getModel().getPasswordCheck())) {
            return RegisterResource.VALIDATION_PASSWORDS_MUST_BE_EQUAL.formatted();
        }
        if (ServiceRepository.getUserService().existsUserWithEMail(getModel().getEMail())) {
            return RegisterResource.VALIDATION_EMAIL_ALREADY_USED.formatted();
        }
        return ValidationUtil.createEmptyMessage();
    }
}