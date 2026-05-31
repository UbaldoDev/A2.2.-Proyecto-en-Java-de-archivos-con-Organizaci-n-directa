package modelo;

import java.io.IOException;

public class ModeloBuscarProducto {
    ArchivoInventario archivo;

    public ModeloBuscarProducto() throws IOException {
        archivo = new ArchivoInventario("inventario.dat");
    }

    public Producto buscarProducto(int idBuscar) {
        try {
            int total = archivo.totalRegistros();
            for (int i = 0; i < total; i++) {
                Producto p = archivo.leer(i);
                if (p != null && p.estado) {
                    if (p.id == idBuscar) {
                        return p;
                    }
                }
            }
        } catch (IOException ex) {
            return null;
        }
        return null;
    }

    public int buscarPosicionProducto(int idBuscar) {
        try {
            int total = archivo.totalRegistros();
            for (int i = 0; i < total; i++) {
                Producto p = archivo.leer(i);
                if (p != null && p.estado) {
                    if (p.id == idBuscar) {
                        return i;
                    }
                }
            }
        } catch (IOException ex) {
            return -1;
        }
        return -1;
    }
}
