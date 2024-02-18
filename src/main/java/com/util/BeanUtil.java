package com.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtil {
	
	private static final Log LOG = LogFactory.getLog(BeanUtil.class);

	public BeanUtil() {
		super();
	}
	
	public static void copyProperties(final Object from, final Object to) {
		var fromFields = Arrays.asList(from.getClass().getDeclaredFields());
		var toFields = Arrays.asList(to.getClass().getDeclaredFields());
    	copyProperties(from, to, fromFields, toFields);
	}

	private static void copyProperties(final Object from, final Object to,
											final List<Field> fromFields, 
														final List<Field> toFields) {
		fromFields.stream().forEach(fr ->
    		toFields.stream()
    				.filter(t-> t.getName().equals(fr.getName()) && 
    							t.getGenericType().equals(fr.getGenericType()) )
    				.forEach(t -> {
						try {
							t.setAccessible(true);
							fr.setAccessible(true);
							t.set(to, fr.get(from));
						} catch (IllegalArgumentException | IllegalAccessException e) {
							LOG.error("Error executing {}", e);
						}
					})
    	);
	}

}