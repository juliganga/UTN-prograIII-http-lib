package org.example.Modelo;

/**
 * Un enum que denota que tipo de dato se espera recivir, para aplicar un formato JSON
 * @see BackendRequest
 */
public enum GetRequestType {
    /**
     * Una lista de objetos recividos del servidor
     */
    ARRAY,
    /**
     * Un solo objeto
     */
    OBJECT
}
