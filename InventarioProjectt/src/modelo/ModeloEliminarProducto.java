package modelo;

import java.io.IOException;

public class ModeloEliminarProducto {

    ArchivoInventario archivo;

    public ModeloEliminarProducto() throws IOException {
        archivo = new ArchivoInventario("inventario.dat");
    }

    public boolean eliminarProducto(int posicion) {
        try {
            Producto p = archivo.leer(posicion);
            if (p == null) {
                return false;
            }
            p.estado = false;
            archivo.guardar(p, posicion);
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
}
