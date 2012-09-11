package de.g18.ubb.android.client.activities.main;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import de.g18.ubb.android.client.R;
import de.g18.ubb.android.client.activities.AbstractValidationFormularActivity;
import de.g18.ubb.android.client.activities.budgetbook.BudgetBookOverviewActivity;
import de.g18.ubb.android.client.activities.register.RegisterActivity;
import de.g18.ubb.android.client.communication.WebServiceProvider;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class MainActivity extends AbstractValidationFormularActivity<MainActivityModel, MainActivityValidator> {

    static {
        WebServiceProvider.register();
    }


    private Button registerButton;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainActivityModel createModel() {
        return new MainActivityModel();
    }

    @Override
    protected MainActivityValidator createValidator() {
        return new MainActivityValidator(getModel());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getModel().setEMail(getPreferences().getUsername());
        getModel().setPassword(getPreferences().getPassword());
        getModel().setStayLoggedInChecked(true);
        getModel().setServerAddress(getPreferences().getServerAddress());
        WebServiceProvider.setServerAddress(getModel().getServerAddress());

        initComponents();
        bindComponents();
        initEventHandling();
    }

    private void initComponents() {
        registerButton = (Button) findViewById(R.MainLayout.register);
    }

    private void bindComponents() {
        bind(MainActivityModel.PROPERTY_PASSWORD, R.MainLayout.password);
        bind(MainActivityModel.PROPERTY_EMAIL, R.MainLayout.email);
        bind(MainActivityModel.PROPERTY_STAY_LOGGED_IN, R.MainLayout.stayLoggedIn);
        bind(MainActivityModel.PROPERTY_SERVER_ADDRESS, R.MainLayout.serverAddress);
    }

    private void initEventHandling() {
        registerButton.setOnClickListener(new RegisternButtonListener());
    }

    @Override
    protected int getSubmitButtonId() {
        return R.MainLayout.login;
    }

    @Override
    protected void preSubmit() {
        String serverAddress = getModel().getServerAddress();
        getPreferences().saveServerAddress(serverAddress);
        WebServiceProvider.setServerAddress(serverAddress);
    }

    @Override
    protected String submit() {
        boolean loginSuccessfull = WebServiceProvider.authentificate(getModel().getEMail(),
                                                                     getModel().getPassword());
        if (!loginSuccessfull) {
            return MainActivityResource.MESSAGE_LOGIN_FAILED.formatted();
        }
        return StringUtil.EMPTY;
    }

    @Override
    protected void postSubmit() {
        showValidationMessages();
        if (!isSubmitSuccessfull()) {
            return;
        }

        if (getModel().isStayLoggedInChecked()) {
            getPreferences().saveLoginData(getModel().getEMail(),
                                           getModel().getPassword());
        }
        switchActivity(BudgetBookOverviewActivity.class);
    }

    @Override
    protected String getSubmitWaitMessage() {
        return MainActivityResource.MESSAGE_LOGIN.formatted();
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class RegisternButtonListener implements OnClickListener {

        public void onClick(View aView) {
            switchActivity(RegisterActivity.class);
        }
    }
}
