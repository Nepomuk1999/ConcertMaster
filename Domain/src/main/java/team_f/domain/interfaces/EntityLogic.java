package team_f.domain.interfaces;

import javafx.util.Pair;
import team_f.domain.enums.TransactionType;
import java.util.List;

public interface EntityLogic<T extends DomainEntity, E extends DomainEntityProperty> {
    /**
     * validates one or more attributes on the given object
     * @param entries   a list of entries with a key and and a value
     * @return          a list of key-value pairs:
     *                  key: specifies the property
     *                  value: a detailed error message for the user
     *                  is never null
     */
    public List<Pair<E, String>> validate(Pair<E, Object>... entries);

    /**
     * validates the attributes on the given object
     * @param object    the object which should be checked
     * @return          a list of key-value pairs:
     *                  key: specifies the property
     *                  value: a detailed error message for the user
     *                  is never null
     */
    public List<Pair<E, String>> validate(T object);

    /**
     * updates the attributes on the given object
     * @param transactionType    the transaction type
     * @param object             the object which should be checked
     * @return                   a list of key-value pairs:
     *                           key: specifies the property
     *                           value: a detailed error message for the user
     *                           is never null
     */
    public List<Pair<E, String>> update(TransactionType transactionType, T object); // we do not support TransactionType yet, but it would be passe to the database layer which saves the changes

    /**
     * creates a new object
     * @param transactionType    the transaction type
     * @param object             the object which should be created
     * @return                   a list of key-value pairs:
     *                           key: specifies the property
     *                           value: a detailed error message for the user
     *                           is never null
     */
    public List<Pair<E, String>> create(TransactionType transactionType, T object);

    /**
     * deletes an object
     * @param transactionType     the transaction type
     * @param id                  the unique id of the object which should be deleted
     * @return                    a list of key-value pairs:
     *                            key: specifies the property
     *                            value: a detailed error message for the user
     *                            is never null
     */
    public List<Pair<E, String>> delete(TransactionType transactionType, int id);

    /**
     * get a list of elements filtered by the given key-value list
     * @param entries              a list of entries with a key and and a value
     * @return                     at least one argument given: a list of objects filtered by the given key-value list
     *                             not argument given: returns all objects
     *                             is never null
     */
    public List<T> getList(Pair<E, Object>... entries);

    /**
     * gets an element filtered by the given key-value list
     * @param entries              a list of entries with a key and and a value
     * @return                     at least one argument given: returns the first element which was found with the filter by the given key-value list
     *                             not argument given: returns null
     */
    public T getEntity(Pair<E, Object>... entries);
}
