package controlador;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloActualizarProducto;
import modelo.ModeloBuscarProducto;
import modelo.ModeloEliminarProducto;
import modelo.ModeloGuardarProducto;
import modelo.ModeloMostrarInventario;
import modelo.ModeloReporteProducto;
import modelo.Producto;
import vista.UIPrincipal;

public class ControladorInventario {

    UIPrincipal vista;

    ModeloGuardarProducto objModGuardar;
    ModeloMostrarInventario objModMostrar;
    ModeloBuscarProducto objModBuscar;
    ModeloActualizarProducto objModActualizar;
    ModeloEliminarProducto objModEliminar;
    ModeloReporteProducto objModReporte;

    ArrayList<Integer> posiciones;

    public ControladorInventario(UIPrincipal vista) throws IOException {
        this.vista = vista;

        this.objModGuardar   = new ModeloGuardarProducto();
        this.objModMostrar   = new ModeloMostrarInventario();
        this.objModBuscar    = new ModeloBuscarProducto();
        this.objModActualizar = new ModeloActualizarProducto();
        this.objModEliminar  = new ModeloEliminarProducto();
        this.objModReporte   = new ModeloReporteProducto();

        this.posiciones = new ArrayList<>();
    }

    public void listarProductos() {
        String[] columnas = {"ID", "Nombre", "Descripción", "Existencia", "Estado"};

        Object[][] datos = objModMostrar.mostrarInventario();

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        vista.getTblProductos().setModel(modelo);
        posiciones = objModMostrar.getPosiciones();
    }

    public void guardarProducto() {
        try {
            if (camposVacios()) {
                JOptionPane.showMessageDialog(vista, "Completa todos los campos");
                return;
            }

            int id = Integer.parseInt(vista.getTxtId().getText());
            String nombre = vista.getTxtNombre().getText();
            String descripcion = vista.getTxtDescripcion().getText();
            int existencia = Integer.parseInt(vista.getTxtExistencia().getText());

            boolean guardado = objModGuardar.guardarProducto(id, nombre, descripcion, existencia);

            if (guardado) {
                listarProductos();
                vista.limpiarCajas();
                JOptionPane.showMessageDialog(vista, "Producto guardado correctamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo guardar. Verifica si el ID ya existe");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID y existencia deben ser números");
        }
    }

    public void seleccionarProducto() {
        try {
            int fila = vista.getTblProductos().getSelectedRow();
            if (fila == -1) return;

            int posReal = posiciones.get(fila);
            int id = Integer.parseInt(vista.getTblProductos().getValueAt(fila, 0).toString());

            Producto p = objModBuscar.buscarProducto(id);

            if (p != null) {
                vista.getTxtId().setText(String.valueOf(p.id));
                vista.getTxtNombre().setText(p.nombre);
                vista.getTxtDescripcion().setText(p.descripcion);
                vista.getTxtExistencia().setText(String.valueOf(p.existencia));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al seleccionar producto");
        }
    }

    public void actualizarProducto() {
        try {
            int fila = vista.getTblProductos().getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Selecciona un producto de la tabla");
                return;
            }

            if (camposVacios()) {
                JOptionPane.showMessageDialog(vista, "Completa todos los campos");
                return;
            }

            int posReal = posiciones.get(fila);
            int id = Integer.parseInt(vista.getTxtId().getText());
            String nombre = vista.getTxtNombre().getText();
            String descripcion = vista.getTxtDescripcion().getText();
            int existencia = Integer.parseInt(vista.getTxtExistencia().getText());

            boolean actualizado = objModActualizar.actualizarProducto(
                    posReal, id, nombre, descripcion, existencia
            );

            if (actualizado) {
                listarProductos();
                vista.limpiarCajas();
                JOptionPane.showMessageDialog(vista, "Producto actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "ID y existencia deben ser números");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al actualizar producto");
        }
    }

    public void eliminarProducto() {
        try {
            int fila = vista.getTblProductos().getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Selecciona un producto de la tabla");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Seguro que deseas eliminar este producto?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (respuesta != JOptionPane.YES_OPTION) return;

            int posReal = posiciones.get(fila);
            boolean eliminado = objModEliminar.eliminarProducto(posReal);

            if (eliminado) {
                listarProductos();
                vista.limpiarCajas();
                JOptionPane.showMessageDialog(vista, "Producto eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar producto");
        }
    }

    public void buscarProducto() {
        try {
            if (vista.getTxtId().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingresa el ID a buscar");
                return;
            }

            int id = Integer.parseInt(vista.getTxtId().getText());
            Producto p = objModBuscar.buscarProducto(id);
            int pos = objModBuscar.buscarPosicionProducto(id);

            if (p == null || pos == -1) {
                JOptionPane.showMessageDialog(vista, "No se encontró el producto");
                return;
            }

            vista.getTxtId().setText(String.valueOf(p.id));
            vista.getTxtNombre().setText(p.nombre);
            vista.getTxtDescripcion().setText(p.descripcion);
            vista.getTxtExistencia().setText(String.valueOf(p.existencia));

            seleccionarFilaPorPosicion(pos);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser numérico");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al buscar producto");
        }
    }

    public void generarReporte() {
        boolean generado = objModReporte.generarReporte();
        if (generado) {
            JOptionPane.showMessageDialog(vista, "Reporte generado correctamente");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al generar reporte");
        }
    }

    private boolean camposVacios() {
        return vista.getTxtId().getText().trim().isEmpty()
                || vista.getTxtNombre().getText().trim().isEmpty()
                || vista.getTxtDescripcion().getText().trim().isEmpty()
                || vista.getTxtExistencia().getText().trim().isEmpty();
    }

    private void seleccionarFilaPorPosicion(int posReal) {
        for (int i = 0; i < posiciones.size(); i++) {
            if (posiciones.get(i) == posReal) {
                vista.getTblProductos().setRowSelectionInterval(i, i);
                return;
            }
        }
    }
}
