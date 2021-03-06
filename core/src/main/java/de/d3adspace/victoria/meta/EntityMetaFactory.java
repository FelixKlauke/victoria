package de.d3adspace.victoria.meta;

import de.d3adspace.victoria.annotation.EntityId;
import de.d3adspace.victoria.annotation.EntityTTL;
import de.d3adspace.victoria.annotation.EntityType;

import java.lang.reflect.Field;

/**
 * The factory to load the meta data of an entity.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class EntityMetaFactory {

    /**
     * Create the meta data by the class of the entity.
     *
     * @param elementClazz The class.
     * @return The meta.
     */
    public static EntityMeta createEntityMeta(Class elementClazz) {
        EntityTTL entityTTL = (EntityTTL) elementClazz.getAnnotation(EntityTTL.class);

        Field idField = null;
        String idPrefix = "";

        for (Field field : elementClazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(EntityId.class)) {
                field.setAccessible(true);
                idField = field;
                idPrefix = field.getAnnotation(EntityId.class).prefix();
            }
        }

        EntityType entityType = (EntityType) elementClazz.getAnnotation(EntityType.class);
        String type = entityType == null ? "" : entityType.value();

        return new SimpleEntityMeta(entityTTL, idField, idPrefix, type);
    }
}
