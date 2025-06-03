/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author yesica
 */
public class CatalogoView extends javax.swing.JFrame {

    private ProductoController productoController;
    private AuthController authController;
    private JTextField txtBuscar;
    private JComboBox<String> cmbCategoria;
    private JPanel panelProductos;
    private JScrollPane scrollPane;
    
    public CatalogoView() {
        productoController = ProductoController.getInstance();
        authController = AuthController.getInstance();
        inicializarcomponentes();
        setupLayout();
        setupEvents();
        cargarProductos();
    }
    
    private void inicializarcomponentes() {
        setTitle("Catálogo de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        txtBuscar = new JTextField(20);
        cmbCategoria = new JComboBox<>();
        cmbCategoria.addItem("Todas las categorías");
        
        for (String categoria : productoController.getCategorias()) {
            cmbCategoria.addItem(categoria);
        }
        
        panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(panelProductos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior - búsqueda y filtros
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(new TitledBorder("Buscar y Filtrar"));
        
        topPanel.add(new JLabel("Buscar:"));
        topPanel.add(txtBuscar);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(new JLabel("Categoría:"));
        topPanel.add(cmbCategoria);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFocusPainted(false);
        topPanel.add(btnBuscar);
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupEvents() {
        txtBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });
        
        cmbCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductos();
            }
        });
    }
    
    private void cargarProductos() {
        mostrarProductos(productoController.getTodosLosProductos());
    }
    
    private void buscarProductos() {
        String termino = txtBuscar.getText().trim();
        String categoria = (String) cmbCategoria.getSelectedItem();
        
        List<Producto> productos;
        
        if (!termino.isEmpty()) {
            productos = productoController.buscarProductos(termino);
        } else {
            productos = productoController.getTodosLosProductos();
        }
        
        if (!"Todas las categorías".equals(categoria)) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria().equals(categoria))
                    .collect(java.util.stream.Collectors.toList());
        }
        
        mostrarProductos(productos);
    }
    
    private void mostrarProductos(List<Producto> productos) {
        panelProductos.removeAll();
        
        if (productos.isEmpty()) {
            JLabel lblNoResultados = new JLabel("No se encontraron productos");
            lblNoResultados.setHorizontalAlignment(SwingConstants.CENTER);
            lblNoResultados.setForeground(Color.GRAY);
            panelProductos.add(lblNoResultados);
        } else {
            for (Producto producto : productos) {
                panelProductos.add(crearPanelProducto(producto));
                panelProductos.add(Box.createVerticalStrut(10));
            }
        }
        
        panelProductos.revalidate();
        panelProductos.repaint();
    }
    
    private JPanel crearPanelProducto(Producto producto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Información del producto
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblDescripcion = new JLabel(producto.getDescripcion());
        lblDescripcion.setForeground(Color.GRAY);
        
        JLabel lblPrecio = new JLabel("$" + String.format("%.2f", producto.getPrecio()) + 
                " - " + producto.getCategoria());
        lblPrecio.setForeground(new Color(60, 179, 113));
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 12));
        
        infoPanel.add(lblNombre);
        infoPanel.add(lblDescripcion);
        infoPanel.add(lblPrecio);
        
        // Botón de agregar a deseos
        Usuario usuario = authController.getUsuarioActual();
        boolean enDeseos = usuario.getListaDeseos().contains(producto);
        
        JButton btnDeseos = new JButton(enDeseos ? "Remover de Deseos" : "Agregar a Deseos");
        btnDeseos.setBackground(enDeseos ? new Color(220, 20, 60) : new Color(255, 165, 0));
        btnDeseos.setForeground(Color.BLACK);
        btnDeseos.setFocusPainted(false);
        btnDeseos.setPreferredSize(new Dimension(150, 30));
        
        btnDeseos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuario.getListaDeseos().contains(producto)) {
                    usuario.removerDeDeseos(producto);
                    JOptionPane.showMessageDialog(CatalogoView.this, 
                            "Producto removido de la lista de deseos", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    usuario.agregarADeseos(producto);
                    JOptionPane.showMessageDialog(CatalogoView.this, 
                            "Producto agregado a la lista de deseos", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                // Refrescar la vista
                buscarProductos();
            }
        });
        
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(btnDeseos, BorderLayout.EAST);
        
        return panel;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CatalogoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CatalogoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CatalogoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CatalogoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CatalogoView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
