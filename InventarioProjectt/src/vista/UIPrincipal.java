package vista;

import controlador.ControladorInventario;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class UIPrincipal extends javax.swing.JFrame {

    ControladorInventario controlador;

    // Componentes de la interfaz
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtExistencia;
    private JTable tblProductos;
    private JScrollPane scrollProductos;

    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnReporte;

    public UIPrincipal() throws IOException {
        initComponents();
        controlador = new ControladorInventario(this);
        controlador.listarProductos();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Configuración del JFrame
        setTitle("Sistema de Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 550);
        setLayout(new BorderLayout(10, 10));

        // ── Panel de formulario (izquierda) ──────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        panelForm.setPreferredSize(new Dimension(280, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtId          = new JTextField(15);
        txtNombre      = new JTextField(15);
        txtDescripcion = new JTextField(15);
        txtExistencia  = new JTextField(15);

        String[] etiquetas = {"ID:", "Nombre:", "Descripción:", "Existencia:"};
        JTextField[] campos = {txtId, txtNombre, txtDescripcion, txtExistencia};

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            panelForm.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            panelForm.add(campos[i], gbc);
        }

        // ── Panel de botones ──────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

        btnGuardar   = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar  = new JButton("Eliminar");
        btnBuscar    = new JButton("Buscar");
        btnReporte   = new JButton("Reporte");

        // Colores distintivos
        btnGuardar.setBackground(new Color(46, 139, 87));
        btnGuardar.setForeground(Color.WHITE);
        btnActualizar.setBackground(new Color(30, 100, 200));
        btnActualizar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(180, 30, 30));
        btnEliminar.setForeground(Color.WHITE);
        btnBuscar.setBackground(new Color(180, 120, 0));
        btnBuscar.setForeground(Color.WHITE);
        btnReporte.setBackground(new Color(80, 80, 80));
        btnReporte.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnReporte);

        // Panel izquierdo = formulario + botones
        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 5));
        panelIzquierdo.add(panelForm, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);

        // ── Tabla de productos ────────────────────────────────────────────────
        tblProductos = new JTable();
        tblProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProductos.setRowHeight(22);
        scrollProductos = new JScrollPane(tblProductos);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Inventario"));
        panelTabla.add(scrollProductos, BorderLayout.CENTER);

        // ── Ensamblado principal ──────────────────────────────────────────────
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);

        // ── Eventos ───────────────────────────────────────────────────────────
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { guardarProducto(); }
        });
        btnActualizar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { actualizarProducto(); }
        });
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { eliminarProducto(); }
        });
        btnBuscar.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { buscarProducto(); }
        });
        btnReporte.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { generarReporte(); }
        });
        tblProductos.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { seleccionarProducto(); }
        });

        pack();
        setSize(750, 550);
    }

    // ── Métodos delegados al controlador ─────────────────────────────────────

    private void guardarProducto()    { controlador.guardarProducto(); }
    private void eliminarProducto()   { controlador.eliminarProducto(); }
    private void actualizarProducto() { controlador.actualizarProducto(); }
    private void buscarProducto()     { controlador.buscarProducto(); }
    private void seleccionarProducto(){ controlador.seleccionarProducto(); }
    private void generarReporte()     { controlador.generarReporte(); }

    public void limpiarCajas() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtExistencia.setText("");
        tblProductos.clearSelection();
    }

    // ── Getters para el controlador ───────────────────────────────────────────
    public JTable     getTblProductos()    { return tblProductos; }
    public JTextField getTxtId()           { return txtId; }
    public JTextField getTxtNombre()       { return txtNombre; }
    public JTextField getTxtDescripcion()  { return txtDescripcion; }
    public JTextField getTxtExistencia()   { return txtExistencia; }

    // ── Main ──────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            // Se mantiene el look & feel por defecto
        }

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new UIPrincipal().setVisible(true);
            } catch (IOException ex) {
                System.getLogger(UIPrincipal.class.getName())
                        .log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }
}
