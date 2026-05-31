package modelo;

import java.io.IOException;

public class ModeloGuardarProducto {
    ArchivoInventario archivo;

    public ModeloGuardarProducto() throws IOException {
        archivo = new ArchivoInventario("inventario.dat");
    }

    public boolean guardarProducto(
            int id,
            String nombre,
            String descripcion,
            int existencia) {

        try {
            int total = archivo.totalRegistros();

            for (int i = 0; i < total; i++) {
                Producto p = archivo.leer(i);
                if (p != null && p.estado) {
                    if (p.id == id) {
                        return false;
                    }
                }
            }

            Producto nuevo = new Producto(
                    id,
                    nombre,
                    descripcion,
                    existencia,
                    true
            );

            archivo.guardar(nuevo, total);
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
}
