package com.obai.platform.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

public final class EntityPatchUtil {
    private EntityPatchUtil() {
    }

    public static <T> void copyNonNull(T source, T target) {
        Class<?> type = source.getClass();
        while (type != null && type != Object.class) {
            copyDeclaredFields(source, target, type);
            type = type.getSuperclass();
        }
    }

    private static void copyDeclaredFields(Object source, Object target, Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            if (shouldSkip(field)) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException ex) {
                throw new BusinessException(500, "实体更新失败：" + field.getName());
            }
        }
    }

    private static boolean shouldSkip(Field field) {
        String name = field.getName();
        return Modifier.isStatic(field.getModifiers())
                || "id".equals(name)
                || "createdAt".equals(name)
                || "updatedAt".equals(name)
                || "deleted".equals(name)
                || Collection.class.isAssignableFrom(field.getType())
                || Map.class.isAssignableFrom(field.getType());
    }
}
