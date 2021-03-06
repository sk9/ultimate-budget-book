package de.g18.ubb.android.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import de.g18.ubb.android.client.preferences.Preferences;
import de.g18.ubb.android.client.shared.ApplicationStateStore;
import de.g18.ubb.android.client.shared.binding.Bindings;
import de.g18.ubb.common.domain.AbstractModel;

/**
 * Abstrakte Oberklasse für alle Activities, die ein {@link AbstractModel} an die {@link Activity} bindet und einige
 * "helfer"-Metohden für Ableitende Klassen zu verfügung stellt.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public abstract class AbstractActivity<_Model extends AbstractModel> extends FragmentActivity {

    private _Model model;
    private Preferences preferences;


    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        setContentView(getLayoutId());
    }

    /**
     * Erstellt das Model der Activity "Lazy" durch den aufruf von {@link #createModel()} und gibt es zurück.
     */
    public final _Model getModel() {
        if (model == null) {
            model = createModel();
        }
        return model;
    }

    /**
     * Setzt alle Daten der {@link Activity} zurück.
     */
    protected void reset() {
        resetModel();
    }

    /**
     * Setzt das Model zurück auf <code>null</code>
     */
    protected final void resetModel() {
        model = null;
    }

    /**
     * Gibt eine "Lazy" erstellte Instanz der {@link Preferences} zurück.
     */
    protected final Preferences getPreferences() {
        if (preferences == null) {
            preferences = new Preferences(this);
        }
        return preferences;
    }

    /**
     * Öffnet die übergebene {@link Activity}.
     */
    protected final void switchActivity(Class<? extends Activity> aActivityClass) {
        Intent myIntent = new Intent(getApplicationContext(), aActivityClass);
        startActivityForResult(myIntent, 0);
    }

    /**
     * Bindet eine Property des Models anhand ihres Namens an eine {@link View} anhand ihrer id
     * und gibt die gebundene {@link View} zurück.
     */
    protected final View bind(String aPropertyname, int aViewId) {
        return bind(getModel(), aPropertyname, aViewId);
    }

    /**
     * Bindet eine Property des Models anhand ihres Namens an eine {@link View} anhand ihrer id
     * und gibt die gebundene {@link View} zurück.
     */
    protected final View bind(AbstractModel aModel, String aPropertyname, int aViewId) {
        View view = findViewById(aViewId);
        Bindings.bind(view, aModel, aPropertyname);
        return view;
    }

    /**
     * Gibt eine Instanz des {@link ApplicationStateStore} zurück.
     */
    protected final ApplicationStateStore getApplicationStateStore() {
        return ApplicationStateStore.getInstance();
    }

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    /**
     * Erstellt das {@link AbstractModel}, dass in mit dieser {@link Activity} gebunden werden soll.
     */
    protected abstract _Model createModel();

    /**
     * Gibt die Id des Layouts der {@link Activity} zurück.
     */
    protected abstract int getLayoutId();
}
