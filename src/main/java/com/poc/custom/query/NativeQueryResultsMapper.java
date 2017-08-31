package com.poc.custom.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

public class NativeQueryResultsMapper {
	private static Logger log = Logger.getLogger(NativeQueryResultsMapper.class);

    public static <T> List<T> map(List<Object[]> objectArrayList, Class<T> genericType) {
        List<T> ret = new ArrayList<T>();
        List<Field> mappingFields = getNativeQueryResultColumnAnnotatedFields(genericType);
        try {
            for (Object[] objectArr : objectArrayList) {
                T t = genericType.newInstance();
                for (int i = 0; i < objectArr.length; i++) {
                	try {
                    BeanUtils.setProperty(t, mappingFields.get(i).getName(), objectArr[i]);
                	} catch(ArrayIndexOutOfBoundsException ae){
                	   log.warn("Non Entity object doesn't have map ,ignored index["+i+"]");
                	}
                }
                ret.add(t);
            }
        } catch (InstantiationException ie) {
            log.debug("Cannot instantiate: ", ie);
            ret.clear();
        } catch (IllegalAccessException iae) {
            log.debug("Illegal access: ", iae);
            ret.clear();
        } catch (InvocationTargetException ite) {
            log.debug("Cannot invoke method: ", ite);
            ret.clear();
        }
        return ret;
    }

    // Get ordered list of fields
    private static <T> List<Field> getNativeQueryResultColumnAnnotatedFields(Class<T> genericType) {
        Field[] fields = genericType.getDeclaredFields();
        List<Field> orderedFields = Arrays.asList(new Field[fields.length]);
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(NativeQueryResultColumn.class)) {
                NativeQueryResultColumn nqrc = fields[i].getAnnotation(NativeQueryResultColumn.class);
                orderedFields.set(nqrc.index(), fields[i]);
            }
        }
        return orderedFields;
    }
}
