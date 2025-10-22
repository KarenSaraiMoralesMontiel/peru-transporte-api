package gov.peru.transporte.peru.transporte.api.util;

import gov.peru.transporte.peru.transporte.api.entity.ReadingBaseEntity;

import java.util.Comparator;

public class Util {

    // Use generic type properly
    public static <T extends ReadingBaseEntity> Comparator<T> applyComparator(String[] order_param, String order) {
        if (order_param == null || order_param.length == 0) {
            return createComparator("id_establecimiento");
        }

        Comparator<T> comparator = createComparator(order_param[0]);

        // Chain additional fields
        for (int i = 1; i < order_param.length; i++) {
            comparator = comparator.thenComparing(createComparator(order_param[i]));
        }

        // Apply DESC order if needed
        if ("DESC".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }

    // Make this generic too
    public static <T extends ReadingBaseEntity> Comparator<T> createComparator(String field) {
        switch (field) {
            case "id_establecimiento":
                return Comparator.comparing(T::getId_establecimiento);
            case "no_ruc":
                return Comparator.comparing(T::getNo_ruc,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "departamento":
                return Comparator.comparing(T::getDepartamento,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "provincia":
                return Comparator.comparing(T::getProvincia,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "distrito":
                return Comparator.comparing(T::getDistrito,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "nombre_centro":
                return Comparator.comparing(T::getNombre_centro,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "direccion_centro":
                return Comparator.comparing(T::getDireccion_centro,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "email":
                return Comparator.comparing(T::getEmail,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "phone":
                return Comparator.comparing(T::getPhone,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
            case "estado_autorizacion":
                return Comparator.comparing(T::isEstado_autorizacion);
            default:
                // Default to ID if field not recognized
                return Comparator.comparing(T::getId_establecimiento);
        }
    }
}
