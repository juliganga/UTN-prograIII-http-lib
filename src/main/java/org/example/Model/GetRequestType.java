package org.example.Model;

/**
 * Un enum que denota que tipo de dato se espera recibir, para aplicar un formato JSON
 * @see BackendRequest
 */
public enum GetRequestType {
    /**
     * Una lista de objetos recibidos del servidor
     */
    ARRAY,
    /**
     * Un solo objeto
     */
    OBJECT
}
