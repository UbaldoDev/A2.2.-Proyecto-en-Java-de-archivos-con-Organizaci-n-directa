package modelo;

import java.io.IOException;
import java.io.PrintWriter;

public class ModeloReporteProducto {
    ArchivoInventario archivo;

    public ModeloReporteProducto() throws IOException {
        archivo = new ArchivoInventario("inventario.dat");
    }

    public boolean generarReporte() {
        try {
            PrintWriter pw = new PrintWriter("reporte_inventario.txt");

            pw.println("REPORTE DE INVENTARIO");
            pw.println("---------------------");
            pw.println();

            int total = archivo.totalRegistros();

            for (int i = 0; i < total; i++) {
                Producto p = archivo.leer(i);
                if (p != null && p.estado) {
                    pw.println("ID: " + p.id);
                    pw.println("Nombre: " + p.nombre);
                    pw.println("Descripción: " + p.descripcion);
                    pw.println("Existencia: " + p.existencia);
                    pw.println("Estado: " + p.estado);
                    pw.println("---------------------");
                }
            }

            pw.close();
            return true;

        } catch (IOException ex) {
            return false;
        }
    }
}
