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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author yesica
 */
public class ListaDeseosView extends javax.swing.JFrame {

    private AuthController authController;
    private JPanel panelDeseos;
    private JScrollPane scrollPane;
    private JLabel lblTotal;

    public ListaDeseosView() {
        authController = AuthController.getInstance();
        inicializarcomponentes();
        setupLayout();
        cargarListaDeseos();
    }

    private void inicializarcomponentes() {
        setTitle("Mi Lista de Deseos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        panelDeseos = new JPanel();
        panelDeseos.setLayout(new BoxLayout(panelDeseos, BoxLayout.Y_AXIS));
        panelDeseos.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(panelDeseos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        lblTotal = new JLabel();
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(70, 130, 180));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("MI LISTA DE DESEOS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(70, 130, 180));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(lblTotal, BorderLayout.EAST);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(245, 245, 245));

        JButton btnLimpiar = new JButton("Limpiar Lista");
        btnLimpiar.setBackground(new Color(220, 20, 60));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setFocusPainted(false);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(new Color(128, 128, 128));
        btnCerrar.setForeground(Color.BLACK);
        btnCerrar.setFocusPainted(false);

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarLista();
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        bottomPanel.add(btnLimpiar);
        bottomPanel.add(btnCerrar);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void cargarListaDeseos() {
        panelDeseos.removeAll();

        Usuario usuario = authController.getUsuarioActual();
        List<Producto> listaDeseos = usuario.getListaDeseos();

        if (listaDeseos.isEmpty()) {
            JLabel lblVacia = new JLabel("Tu lista de deseos está vacía");
            lblVacia.setHorizontalAlignment(SwingConstants.CENTER);
            lblVacia.setForeground(Color.GRAY);
            lblVacia.setFont(new Font("Arial", Font.ITALIC, 16));
            panelDeseos.add(Box.createVerticalStrut(50));
            panelDeseos.add(lblVacia);
        } else {
            for (Producto producto : listaDeseos) {
                panelDeseos.add(crearPanelProductoDeseos(producto));
                panelDeseos.add(Box.createVerticalStrut(10));
            }
        }

        actualizarTotal();
        panelDeseos.revalidate();
        panelDeseos.repaint();
    }

    private JPanel crearPanelProductoDeseos(Producto producto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 165, 0), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Información del producto
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblDescripcion = new JLabel(producto.getDescripcion());
        lblDescripcion.setForeground(Color.GRAY);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblPrecio = new JLabel("$" + String.format("%.2f", producto.getPrecio())
                + " - " + producto.getCategoria());
        lblPrecio.setForeground(new Color(60, 179, 113));
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 14));

        infoPanel.add(lblNombre);
        infoPanel.add(lblDescripcion);
        infoPanel.add(lblPrecio);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnRemover = new JButton("Remover");
        btnRemover.setBackground(new Color(220, 20, 60));
        btnRemover.setForeground(Color.BLACK);
        btnRemover.setFocusPainted(false);

        JButton btnComprar = new JButton("Comprar");
        btnComprar.setBackground(new Color(60, 179, 113));
        btnComprar.setForeground(Color.BLACK);
        btnComprar.setFocusPainted(false);

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerProducto(producto);
            }
        });

        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ListaDeseosView.this,
                        "Compra realizada con exito \nEstado:Pendiente  \nProducto: " + producto.getNombre(),
                        "Información \nEstado:Pendiente", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(btnComprar);
        buttonPanel.add(btnRemover);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void removerProducto(Producto producto) {
        int option = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea remover '" + producto.getNombre() + "' de su lista de deseos?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            Usuario usuario = authController.getUsuarioActual();
            usuario.removerDeDeseos(producto);
            cargarListaDeseos();
            JOptionPane.showMessageDialog(this,
                    "Producto removido de la lista de deseos",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarLista() {
        Usuario usuario = authController.getUsuarioActual();
        if (usuario.getListaDeseos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "La lista de deseos ya está vacía",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea limpiar toda su lista de deseos?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            usuario.getListaDeseos().clear();
            cargarListaDeseos();
            JOptionPane.showMessageDialog(this,
                    "Lista de deseos limpiada",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarTotal() {
        Usuario usuario = authController.getUsuarioActual();
        List<Producto> listaDeseos = usuario.getListaDeseos();

        double total = listaDeseos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        lblTotal.setText("Total: $" + String.format("%.2f", total)
                + " (" + listaDeseos.size() + " productos)");
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
            java.util.logging.Logger.getLogger(ListaDeseosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaDeseosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaDeseosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaDeseosView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaDeseosView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
