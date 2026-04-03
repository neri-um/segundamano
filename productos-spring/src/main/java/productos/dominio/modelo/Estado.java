package productos.dominio.modelo;

public enum Estado {
    NUEVO, COMO_NUEVO, BUEN_ESTADO, ACEPTABLE, PARA_PIEZAS;

    public int getNivel() {
        switch (this) {
            case NUEVO:        return 5;
            case COMO_NUEVO:   return 4;
            case BUEN_ESTADO:  return 3;
            case ACEPTABLE:    return 2;
            case PARA_PIEZAS:  return 1;
            default:           return 0;
        }
    }
}