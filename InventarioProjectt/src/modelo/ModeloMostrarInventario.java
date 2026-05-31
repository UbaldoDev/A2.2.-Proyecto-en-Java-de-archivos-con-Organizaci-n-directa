package modelo;

import java.io.IOException;
import java.util.ArrayList;

public class ModeloMostrarInventario {

    ArchivoInventario archivo;
    private ArrayList<Integer> posiciones;

    public ModeloMostrarInventario() throws IOException {
        archivo = new ArchivoInventario("inventario.dat");
        posiciones = new ArrayList<>();
    }

    public Object[][] mostrarInventario() {
        try {
            posiciones.clear();
            int total = archivo.totalRegistros();
            ArrayList<Object[]> filas = new ArrayList<>();

            for (int i = 0; i < total; i++) {
                Producto p = archivo.leer(i);
                if (p != null && p.estado) {
                    filas.add(new Object[]{
                        p.id,
                        p.nombre,
                        p.descripcion,
                        p.existencia,
                        p.estado
                    });
                    posiciones.add(i);
                }
            }

            Object[][] datos = new Object[filas.size()][5];
            for (int i = 0; i < filas.size(); i++) {
                datos[i] = filas.get(i);
            }

            return datos;

        } catch (IOException ex) {
            return new Object[0][0];
        }
    }

    public ArrayList<Integer> getPosiciones() {
        return posiciones;
    }
}
