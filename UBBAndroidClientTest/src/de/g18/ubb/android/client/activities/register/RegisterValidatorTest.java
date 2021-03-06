package de.g18.ubb.android.client.activities.register;

import de.g18.ubb.android.client.communication.MockServiceProvider;
import de.g18.ubb.android.client.mock.service.MockUserServiceImpl;
import de.g18.ubb.android.client.validation.AbstractValidatorTestCase;
import de.g18.ubb.android.client.validation.ValidationUtil;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class RegisterValidatorTest extends AbstractValidatorTestCase<RegisterModel, RegisterValidator> {

    private static final String VALID_PASSWORD = "validPassword";


    @Override
    public void setUpTestCase() throws Exception {
        MockServiceProvider.register();
    }

    @Override
    protected RegisterModel createValidModel() {
        RegisterModel model = new RegisterModel();
        model.setUsername("test");
        model.setEMail("test@mail.com");
        model.setPassword(VALID_PASSWORD);
        model.setPasswordCheck(VALID_PASSWORD);
        return model;
    }

    @Override
    protected RegisterValidator createValidator() {
        return new RegisterValidator(getModel());
    }

    public void testUsernameMandatory() {
        getModel().setUsername(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_USERNAME);
    }

    public void testEMailMandatory() {
        getModel().setEMail(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_EMAIL);
    }

    public void testEMailFormat() {
        String email = "invalidemailaddressde";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.de";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemailaddress.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "invalidemail@address.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@address.de";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "@.";
        getModel().setEMail(email);
        assertValidationResult(ValidationUtil.createInvalidEMailFormatMessage(email));

        email = "validemail@address.de";
        getModel().setEMail(email);
        assertValidationSuccessfull();
    }

    public void testPasswordMandatory() {
        getModel().setPassword(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_PASSWORD);
    }

    public void testPasswordCheckMandatory() {
        getModel().setPasswordCheck(StringUtil.EMPTY);
        assertValidationMustNotBeEmpty(RegisterResource.FIELD_PASSWORD_CHECK);
    }

    public void testPasswordsMustBeEqual() {
        getModel().setPassword(VALID_PASSWORD + "invalidPart");
        assertValidationResult(RegisterResource.VALIDATION_PASSWORDS_MUST_BE_EQUAL);

        getModel().setPassword(VALID_PASSWORD);
        assertValidationSuccessfull();

        getModel().setPasswordCheck(VALID_PASSWORD + "invalidPart");
        assertValidationResult(RegisterResource.VALIDATION_PASSWORDS_MUST_BE_EQUAL);
    }

    public void testEMailMustBeUnique() {
        getModel().setEMail(MockUserServiceImpl.REGISTERED_USER_EMAIL);
        assertValidationResult(RegisterResource.VALIDATION_EMAIL_ALREADY_USED);
    }
}
