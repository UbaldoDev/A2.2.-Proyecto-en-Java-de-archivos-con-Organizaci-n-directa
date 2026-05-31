package modelo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ArchivoInventario {
    RandomAccessFile archivo;

    public ArchivoInventario(String nombre) throws IOException {
        archivo = new RandomAccessFile(nombre, "rw");
    }

    public void guardar(Producto p, int pos) throws IOException {
        archivo.seek(pos * Producto.TAM_REG);
        archivo.writeInt(p.id);
        archivo.writeChars(p.nombre);
        archivo.writeChars(p.descripcion);
        archivo.writeInt(p.existencia);
        archivo.writeBoolean(p.estado);
    }

    public Producto leer(int pos) throws IOException {
        if ((pos + 1) * Producto.TAM_REG > archivo.length()) return null;

        archivo.seek(pos * Producto.TAM_REG);

        int id = archivo.readInt();

        char[] nom = new char[Producto.TAM_NOMBRE];
        for (int i = 0; i < nom.length; i++) {
            nom[i] = archivo.readChar();
        }

        char[] desc = new char[Producto.TAM_DESC];
        for (int i = 0; i < desc.length; i++) {
            desc[i] = archivo.readChar();
        }

        int existencia = archivo.readInt();
        boolean estado = archivo.readBoolean();

        return new Producto(
                id,
                new String(nom).trim(),
                new String(desc).trim(),
                existencia,
                estado
        );
    }

    public int totalRegistros() throws IOException {
        return (int) (archivo.length() / Producto.TAM_REG);
    }
}
