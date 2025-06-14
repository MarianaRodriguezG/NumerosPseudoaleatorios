package views;

import algoritmos.*;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import pruebas.*;
import controlador.ControladorSimulacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {

    private JComboBox<String> comboAlgoritmo;
    private JPanel panelCentral;
    private CardLayout cardLayout;

    // Paneles y campos
    private JPanel panelCuadrados, panelProductos, panelMultiplicador;
    private JTextField txtSemillaC, txtCantidadC;
    private JTextField txtSemillaP1, txtSemillaP2, txtCantidadP;
    private JTextField txtSemillaM, txtConstante, txtCantidadM;

    private JCheckBox chkMedias, chkVarianza, chkUniformidad;
    private JRadioButton radio90, radio95, radio99;
    private JTextArea txtResultado;
    private JTable tablaNumeros;
    private DefaultTableModel modeloTabla;

    public VentanaPrincipal() {
        setTitle("Algoritmos y Pruebas con Números Pseudoaleatorios");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Barra de menú para cambiar tema
        JMenuBar menuBar = new JMenuBar();
        JMenu menuTema = new JMenu("Tema");

        JMenuItem claro = new JMenuItem("Claro");
        claro.addActionListener(a -> setTema("claro"));

        JMenuItem oscuro = new JMenuItem("Oscuro");
        oscuro.addActionListener(a -> setTema("oscuro"));

        menuTema.add(claro);
        menuTema.add(oscuro);
        menuBar.add(menuTema);
        setJMenuBar(menuBar);

        // Combo de algoritmos
        comboAlgoritmo = new JComboBox<>(new String[]{"Cuadrados Medios", "Productos Medios", "Multiplicador Constante"});
        comboAlgoritmo.addActionListener(e -> switchPanel(comboAlgoritmo.getSelectedIndex()));
        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Algoritmo:"));
        panelTop.add(comboAlgoritmo);
        add(panelTop, BorderLayout.NORTH);

        // Panel con CardLayout
        panelCentral = new JPanel();
        cardLayout = new CardLayout();
        panelCentral.setLayout(cardLayout);

        // Panel Cuadrados Medios
        panelCuadrados = new JPanel(new GridLayout(2, 2));
        txtSemillaC = new JTextField();
        txtSemillaC.setToolTipText("Debe tener exactamente 4 dígitos.");
        txtCantidadC = new JTextField();
        txtCantidadC.setToolTipText("Cantidad de números a generar.");
        panelCuadrados.add(new JLabel("Semilla (X0):"));
        panelCuadrados.add(txtSemillaC);
        panelCuadrados.add(new JLabel("Cantidad r:"));
        panelCuadrados.add(txtCantidadC);

        // Panel Productos Medios
        panelProductos = new JPanel(new GridLayout(3, 2));
        txtSemillaP1 = new JTextField();
        txtSemillaP1.setToolTipText("Semilla 1 de 4 dígitos.");
        txtSemillaP2 = new JTextField();
        txtSemillaP2.setToolTipText("Semilla 2 de 4 dígitos.");
        txtCantidadP = new JTextField();
        txtCantidadP.setToolTipText("Cantidad de números a generar.");
        panelProductos.add(new JLabel("Semilla (X0):"));
        panelProductos.add(txtSemillaP1);
        panelProductos.add(new JLabel("Semilla (X1):"));
        panelProductos.add(txtSemillaP2);
        panelProductos.add(new JLabel("Cantidad r:"));
        panelProductos.add(txtCantidadP);

        // Panel Multiplicador Constante
        panelMultiplicador = new JPanel(new GridLayout(3, 2));
        txtSemillaM = new JTextField();
        txtSemillaM.setToolTipText("Semilla de 4 dígitos.");
        txtConstante = new JTextField();
        txtConstante.setToolTipText("Constante multiplicativa de 4 dígitos.");
        txtCantidadM = new JTextField();
        txtCantidadM.setToolTipText("Cantidad de números a generar.");
        panelMultiplicador.add(new JLabel("Semilla:"));
        panelMultiplicador.add(txtSemillaM);
        panelMultiplicador.add(new JLabel("Constante:"));
        panelMultiplicador.add(txtConstante);
        panelMultiplicador.add(new JLabel("Cantidad r:"));
        panelMultiplicador.add(txtCantidadM);

        panelCentral.add(panelCuadrados, "cuadrados");
        panelCentral.add(panelProductos, "productos");
        panelCentral.add(panelMultiplicador, "multiplicador");

        cardLayout.show(panelCentral, "cuadrados");

        // Panel pruebas y confianza
        JPanel panelInferior = new JPanel(new GridLayout(3, 1));
        chkMedias = new JCheckBox("Prueba de medias");
        chkVarianza = new JCheckBox("Prueba de varianza");
        chkUniformidad = new JCheckBox("Prueba de uniformidad");
        JPanel panelPruebas = new JPanel();
        panelPruebas.add(chkMedias);
        panelPruebas.add(chkVarianza);
        panelPruebas.add(chkUniformidad);

        JPanel panelConfianza = new JPanel();
        radio90 = new JRadioButton("90%");
        radio95 = new JRadioButton("95%", true);
        radio99 = new JRadioButton("99%");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radio90);
        grupo.add(radio95);
        grupo.add(radio99);
        panelConfianza.add(new JLabel("Nivel de confianza:"));
        panelConfianza.add(radio90);
        panelConfianza.add(radio95);
        panelConfianza.add(radio99);

        JButton btnGenerar = new JButton("Generar");
        btnGenerar.addActionListener(this::generar);

        panelInferior.add(panelPruebas);
        panelInferior.add(panelConfianza);
        panelInferior.add(btnGenerar);
        add(panelInferior, BorderLayout.SOUTH);

        // Panel resultados en pestañas
        JTabbedPane pestañas = new JTabbedPane();

        modeloTabla = new DefaultTableModel(new Object[]{"i", "Número"}, 0);
        tablaNumeros = new JTable(modeloTabla);
        tablaNumeros.setEnabled(false);
        JScrollPane scrollTabla = new JScrollPane(tablaNumeros);
        pestañas.addTab("Números Generados", scrollTabla);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scrollTexto = new JScrollPane(txtResultado);
        pestañas.addTab("Resultados de Pruebas", scrollTexto);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelCentral, pestañas);
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);
    }

    private void switchPanel(int index) {
        switch (index) {
            case 0 ->
                cardLayout.show(panelCentral, "cuadrados");
            case 1 ->
                cardLayout.show(panelCentral, "productos");
            case 2 ->
                cardLayout.show(panelCentral, "multiplicador");
        }
    }

    private void generar(ActionEvent e) {
        GeneradorNumPseudoaleatorios generador = null;
        int cantidad = 0;

        try {
            switch (comboAlgoritmo.getSelectedIndex()) {
                case 0 -> {
                    int semilla = Integer.parseInt(txtSemillaC.getText());
                    cantidad = Integer.parseInt(txtCantidadC.getText());
                    generador = new CuadradosMedios(semilla);
                }
                case 1 -> {
                    int s1 = Integer.parseInt(txtSemillaP1.getText());
                    int s2 = Integer.parseInt(txtSemillaP2.getText());
                    cantidad = Integer.parseInt(txtCantidadP.getText());
                    generador = new ProductosMedios(s1, s2);
                }
                case 2 -> {
                    int semilla = Integer.parseInt(txtSemillaM.getText());
                    int constante = Integer.parseInt(txtConstante.getText());
                    cantidad = Integer.parseInt(txtCantidadM.getText());
                    generador = new MultiplicadorConstante(semilla, constante);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener y mostrar números
        modeloTabla.setRowCount(0);
        List<Double> numeros = generador.generar(cantidad);
        for (int i = 0; i < numeros.size(); i++) {
            modeloTabla.addRow(new Object[]{i + 1, numeros.get(i)});
        }

        // Pruebas seleccionadas
        List<PruebaEstadistica> pruebas = new ArrayList<>();
        if (chkMedias.isSelected()) {
            pruebas.add(new PruebaMedias());
        }
        if (chkVarianza.isSelected()) {
            pruebas.add(new PruebaVarianza());
        }
        if (chkUniformidad.isSelected()) {
            pruebas.add(new PruebaUniformidad());
        }

        double confianza = radio90.isSelected() ? 0.90 : radio95.isSelected() ? 0.95 : 0.99;

        // Ejecutar simulación
        ControladorSimulacion sim = new ControladorSimulacion();
        sim.setGenerador(generador);
        sim.setCantidad(cantidad);
        sim.setNivelConfianza(confianza);
        sim.setPruebas(pruebas);

        String resultado = sim.ejecutarSimulacion();
        txtResultado.setText(resultado);
    }

      private void setTema(String tema) {
        try {
           switch(tema){
               case "claro" -> UIManager.setLookAndFeel(new FlatIntelliJLaf());
               case "oscuro"-> UIManager.setLookAndFeel(new FlatDarkLaf() );
           }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo cambiar el tema.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
