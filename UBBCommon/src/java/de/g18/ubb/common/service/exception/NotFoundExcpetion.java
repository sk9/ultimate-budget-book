package de.g18.ubb.common.service.exception;

import de.g18.ubb.common.domain.Identifiable;
import de.g18.ubb.common.util.StringUtil;

/**
 * {@link ServiceException}, die von Services geworfen wird, sollte eine Entität nicht gefunden wurden sein.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class NotFoundExcpetion extends ServiceException {

    private static final long serialVersionUID = 1L;


    public NotFoundExcpetion(Class<? extends Identifiable> aEntityClass) {
        super("No " + aEntityClass.getSimpleName() + " has been found!");
    }

    public NotFoundExcpetion(Class<? extends Identifiable> aEntityClass, Object aSearchKey) {
        super(createMessage(aEntityClass, aSearchKey));
    }

    public NotFoundExcpetion(Class<? extends Identifiable> aEntityClass, Object aSearchKey, Throwable aCause) {
        super(createMessage(aEntityClass, aSearchKey), aCause);
    }

    private static String createMessage(Class<? extends Identifiable> aEntityClass, Object aSearchKey) {
        return createMessage(aEntityClass, StringUtil.toString(aSearchKey));
    }

    private static String createMessage(Class<? extends Identifiable> aEntityClass, String aSearchKey) {
        String entityName = aEntityClass.getSimpleName();
        return "No " + entityName + " has been found for key '" + aSearchKey + "'!";
    }
}
