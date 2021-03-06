package de.g18.ubb.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * Ermöglicht den Zugriff auf Getter und Setter eine Property eines {@link AbstractModel} via reflektion.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class PropertyAccessor<_PropertyType> {

    private final AbstractModel model;
    private final String propertyname;

    private Method getter;
    private Method setter;


    /**
     * Erstellt einen neuen {@link PropertyAccessor} für die Property im übergenenen {@link AbstractModel}.
     */
    public PropertyAccessor(AbstractModel aModel, String aPropertyname) {
        model = aModel;
        propertyname = aPropertyname;
    }

    /**
     * Führt den Getter der Property über reflektion aus und gibt den Rückgabewert des aufrufs als Ergebnis zurück.
     */
    @SuppressWarnings("unchecked")
    public _PropertyType invokeGetter() {
        try {
            return (_PropertyType) getGetter().invoke(model);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(getFullGetterName() + " should not have any arguments!", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(getFullGetterName() + " is not visible!", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(getFullGetterName() + " could not be invoked!", e);
        }
    }

    /**
     * Gibt den vollen Namen des Getters im Format Klasse#Methode zurück
     */
    private String getFullGetterName() {
        return model.getClass().getName() + "#" + getGetter().getName();
    }

    /**
     * Gibt den Getter der Property zurück.
     */
    public Method getGetter() {
        if (getter == null) {
            getter = resolveGetter();
        }
        return getter;
    }

    /**
     * Versucht über reflektion den Getter der Property über dessen Namen aufzulösen und zurückzugeben.
     */
    private Method resolveGetter() {
        for (Method m : model.getClass().getMethods()) {
            if (StringUtil.startsNotWith(m.getName(), "get")
                  && StringUtil.startsNotWith(m.getName(), "is")) {
                continue;
            }
            if (StringUtil.endsWithIgnoreCase(m.getName(), propertyname)) {
                return m;
            }
        }
        throw new IllegalStateException("Getter for property " + propertyname + " not found in class " + model.getClass().getName() + "!");
    }

    /**
     * Führt den Setter der Property über reflektion aus und gibt ihm den Übergebenen Wert als Argument mit.
     */
    public void invokeSetter(_PropertyType aNewValue) {
        try {
            getSetter().invoke(model, aNewValue);
        } catch (IllegalArgumentException e) {
            String argumentType = aNewValue == null ? StringUtil.NULL : aNewValue.getClass().getName();
            throw new IllegalStateException(getFullSetterName() + " is not applicable for a argument of type "
                                            + argumentType + "!", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(getFullSetterName() + " is not visible!", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(getFullSetterName() + " could not be invoked!", e);
        }
    }

    /**
     * Gibt den vollen Namen des Getters im Format Klasse#Methode zurück
     */
    private String getFullSetterName() {
        return model.getClass().getName() + "#" + getSetter().getName();
    }

    /**
     * Gibt den Setter der Property zurück.
     */
    public Method getSetter() {
        if (setter == null) {
            setter = resolveSetter();
        }
        return setter;
    }

    /**
     * Versucht über reflektion den Setter der Property über dessen Namen aufzulösen und zurückzugeben.
     */
    private Method resolveSetter() {
        for (Method m : model.getClass().getMethods()) {
            if (StringUtil.startsNotWith(m.getName(), "set")) {
                continue;
            }
            if (StringUtil.endsWithIgnoreCase(m.getName(), propertyname)) {
                return m;
            }
        }
        throw new IllegalStateException("Setter for property " + propertyname + " not found in class " + model.getClass().getName() + "!");
    }
}
