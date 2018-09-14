package com.td.framework.utils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * class can store the exactly type of list
 *
 * Created by yuzhenbei on 2015/7/25.
 */
public class ListOfJson<T> implements ParameterizedType {

    private Class<?> mType;

    public ListOfJson(Class<T> pType) {
        this.mType = pType;
    }

    /**
     * Returns an array of the actual type arguments for this type.
     * <p/>
     * If this type models a non parameterized type nested within a
     * parameterized type, this method returns a zero length array. The generic
     * type of the following {@code field} declaration is an example for a
     * parameterized type without type arguments.
     * <p/>
     * <pre>
     * A&lt;String&gt;.B field;
     *
     * class A&lt;T&gt; {
     *     class B {
     *     }
     * }</pre>
     *
     * @return the actual type arguments
     * @throws TypeNotPresentException             if one of the type arguments cannot be found
     * @throws MalformedParameterizedTypeException if one of the type arguments cannot be instantiated for some
     *                                             reason
     */
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {mType};
    }

    /**
     * Returns the parent / owner type, if this type is an inner type, otherwise
     * {@code null} is returned if this is a top-level type.
     *
     * @return the owner type or {@code null} if this is a top-level type
     * @throws TypeNotPresentException             if one of the type arguments cannot be found
     * @throws MalformedParameterizedTypeException if the owner type cannot be instantiated for some reason
     */
    @Override
    public Type getOwnerType() {
        return null;
    }

    /**
     * Returns the declaring type of this parameterized type.
     * <p/>
     * The raw type of {@code Set<String> field;} is {@code Set}.
     *
     * @return the raw type of this parameterized type
     */
    @Override
    public Type getRawType() {
        return List.class;
    }
}