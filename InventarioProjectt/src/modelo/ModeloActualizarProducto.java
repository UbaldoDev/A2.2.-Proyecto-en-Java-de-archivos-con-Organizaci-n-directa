package modelo;

import java.io.IOException;

public class ModeloActualizarProducto {

    ArchivoInventario archivo;

    public ModeloActualizarProducto() throws IOException {
        archivo = new ArchivoInventario("inventario.dat");
    }

    public boolean actualizarProducto(
            int posicion,
            int id,
            String nombre,
            String descripcion,
            int existencia) {

        try {
            Producto productoActualizado = new Producto(
                    id,
                    nombre,
                    descripcion,
                    existencia,
                    true
            );
            archivo.guardar(productoActualizado, posicion);
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
}
