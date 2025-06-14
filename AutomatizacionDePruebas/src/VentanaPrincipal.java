
import algoritmos.*;
import com.formdev.flatlaf.FlatIntelliJLaf;
import pruebas.*;

import controlador.ControladorSimulacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    public VentanaPrincipal() {
        setTitle("Algoritmos y Pruebas con Números Pseudoaleatorios");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color fondo = UIManager.getColor("Panel.background");

        // Combo de algoritmos
        comboAlgoritmo = new JComboBox<>(new String[]{"Cuadrados Medios", "Productos Medios", "Multiplicador Constante"});
        comboAlgoritmo.addActionListener(e -> switchPanel(comboAlgoritmo.getSelectedIndex()));
        JPanel panelTop = new JPanel();
        panelTop.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelTop.setBackground(fondo);
        panelTop.add(new JLabel("Algoritmo:"));
        panelTop.add(comboAlgoritmo);
        add(panelTop, BorderLayout.NORTH);

        // Panel con CardLayout
        panelCentral = new JPanel();
        cardLayout = new CardLayout();
        panelCentral.setLayout(cardLayout);

        // Panel Cuadrados Medios
        panelCuadrados = new JPanel(new GridLayout(2, 2, 10, 10));
        txtSemillaC = new JTextField();
        txtCantidadC = new JTextField();
        txtSemillaC.setPreferredSize(new Dimension(100, 24));
        txtCantidadC.setPreferredSize(new Dimension(100, 24));
        panelCuadrados.setBorder(BorderFactory.createTitledBorder("Cuadrados Medios"));
        panelCuadrados.add(new JLabel("Semilla (X0):"));
        panelCuadrados.add(txtSemillaC);
        panelCuadrados.add(new JLabel("Cantidad r:"));
        panelCuadrados.add(txtCantidadC);

        // Panel Productos Medios
        panelProductos = new JPanel(new GridLayout(3, 2, 10, 10));
        txtSemillaP1 = new JTextField();
        txtSemillaP2 = new JTextField();
        txtCantidadP = new JTextField();
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos Medios"));
        panelProductos.add(new JLabel("Semilla (X0):"));
        panelProductos.add(txtSemillaP1);
        panelProductos.add(new JLabel("Semilla (X1):"));
        panelProductos.add(txtSemillaP2);
        panelProductos.add(new JLabel("Cantidad r:"));
        panelProductos.add(txtCantidadP);

        // Panel Multiplicador Constante
        panelMultiplicador = new JPanel(new GridLayout(3, 2, 10, 10));
        txtSemillaM = new JTextField();
        txtConstante = new JTextField();
        txtCantidadM = new JTextField();
        panelMultiplicador.setBorder(BorderFactory.createTitledBorder("Multiplicador Constante"));
        panelMultiplicador.add(new JLabel("Semilla:"));
        panelMultiplicador.add(txtSemillaM);
        panelMultiplicador.add(new JLabel("Constante:"));
        panelMultiplicador.add(txtConstante);
        panelMultiplicador.add(new JLabel("Cantidad r:"));
        panelMultiplicador.add(txtCantidadM);

        panelCentral.add(panelCuadrados, "cuadrados");
        panelCentral.add(panelProductos, "productos");
        panelCentral.add(panelMultiplicador, "multiplicador");

        // Panel resultados
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResultado);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelCentral, scroll);
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        // Panel pruebas y confianza
        JPanel panelInferior = new JPanel(new GridLayout(3, 1));
        panelInferior.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelInferior.setBackground(fondo);

        chkMedias = new JCheckBox("Prueba de medias");
        chkVarianza = new JCheckBox("Prueba de varianza");
        chkUniformidad = new JCheckBox("Prueba de uniformidad");

        JPanel panelPruebas = new JPanel();
        panelPruebas.setBorder(BorderFactory.createTitledBorder("Pruebas a realizar"));
        panelPruebas.setBackground(fondo);
        panelPruebas.add(chkMedias);
        panelPruebas.add(chkVarianza);
        panelPruebas.add(chkUniformidad);

        JPanel panelConfianza = new JPanel();
        panelConfianza.setBorder(BorderFactory.createTitledBorder("Nivel de confianza"));
        panelConfianza.setBackground(fondo);
        radio90 = new JRadioButton("90%");
        radio95 = new JRadioButton("95%", true);
        radio99 = new JRadioButton("99%");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radio90);
        grupo.add(radio95);
        grupo.add(radio99);
        panelConfianza.add(radio90);
        panelConfianza.add(radio95);
        panelConfianza.add(radio99);

        JButton btnGenerar = new JButton("Generar");
        btnGenerar.addActionListener(this::generar);

        panelInferior.add(panelPruebas);
        panelInferior.add(panelConfianza);
        panelInferior.add(btnGenerar);
        add(panelInferior, BorderLayout.SOUTH);

        cardLayout.show(panelCentral, "cuadrados");
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
                    if (String.valueOf(semilla).length() != 4) {
                        JOptionPane.showMessageDialog(this, "La semilla debe tener 4 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cantidad = Integer.parseInt(txtCantidadC.getText());
                    generador = new CuadradosMedios(semilla);
                }
                case 1 -> {
                    int s1 = Integer.parseInt(txtSemillaP1.getText());
                    int s2 = Integer.parseInt(txtSemillaP2.getText());
                    if (String.valueOf(s1).length() != 4 || String.valueOf(s2).length() != 4) {
                        JOptionPane.showMessageDialog(this, "Ambas semillas deben tener 4 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cantidad = Integer.parseInt(txtCantidadP.getText());
                    generador = new ProductosMedios(s1, s2);
                }
                case 2 -> {
                    int semilla = Integer.parseInt(txtSemillaM.getText());
                    int constante = Integer.parseInt(txtConstante.getText());
                    if (String.valueOf(semilla).length() != 4 || String.valueOf(constante).length() != 4) {
                        JOptionPane.showMessageDialog(this, "La semilla y la constante deben tener 4 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cantidad = Integer.parseInt(txtCantidadM.getText());
                    generador = new MultiplicadorConstante(semilla, constante);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa valores válidos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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

        ControladorSimulacion sim = new ControladorSimulacion();
        sim.setGenerador(generador);
        sim.setCantidad(cantidad);
        sim.setNivelConfianza(confianza);
        sim.setPruebas(pruebas);

        String resultado = sim.ejecutarSimulacion();
        txtResultado.setText(resultado);
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
