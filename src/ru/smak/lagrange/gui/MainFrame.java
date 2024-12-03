package ru.smak.lagrange.gui;

import math.Lagrange;
import ru.smak.lagrange.coordinates.Converter;
import ru.smak.lagrange.painters.CartesianPainter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private Lagrange poly = null;
    private final HashMap<Double, Double> points = new HashMap<>();

    /**
     * Основная панель для отображения декартовой плоскости
     */
    private final JPanel mainPanel = new JPanel();

    /**
     * Контрольная панель для управления параметрами отображения декартовой плоскости
     */
    private final JPanel controlPanel = new JPanel();

    /**
     * Модель для задания поведения элементом управления нижней границы отрезка по оси абсцисс
     */
    private final SpinnerNumberModel nmXMin = new SpinnerNumberModel(-5.0, -100., +4.9, 0.1);

    /**
     * Модель для задания поведением элементом управления верхней границы отрезка по оси абсции
     */
    private final SpinnerNumberModel nmXMax = new SpinnerNumberModel(+5.0, -4.9, +100., 0.1);

    /**
     * Модель для задания поведением элементом управления нижней границы отрезка по оси ординат
     */
    private final SpinnerNumberModel nmYMin = new SpinnerNumberModel(-5.0, -100., +4.9, 0.1);

    /**
     * Модель для задания поведением элементом управления верхней границы отрезка по оси ординат
     */
    private final SpinnerNumberModel nmYMax = new SpinnerNumberModel(+5.0, -4.9, +100., 0.1);

    private final CartesianPainter cPainter = new CartesianPainter();

    /**
     * Конструктор основного окна
     */
    public MainFrame() {

        // Закрытие приложения при нажатии на кнопку закрытия основного окна приложения
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Установка минимального размера окна приложения
        setMinimumSize(new Dimension(600, 600));

        // Создание основного содержания окна приложения
        createMainContent();

        // Создание контента панели управления
        createControlPanelContent();

        // Настройка окна для отображения заданного содержимого
        pack();

        cPainter.setConverter(new Converter(
                (double)nmXMin.getValue(),
                (double)nmXMax.getValue(),
                (double)nmYMin.getValue(),
                (double)nmYMax.getValue(),
                mainPanel.getWidth(),
                mainPanel.getHeight()
        ));

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                points.put(
                        cPainter.getConverter().xScr2Crt(e.getX()),
                        cPainter.getConverter().yScr2Crt(e.getY())
                );
                poly = new Lagrange(points);
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        cPainter.setSize(mainPanel.getSize());
        cPainter.paint(mainPanel.getGraphics());

    }

    /**
     * Настройка границ отрезков при изменении значений в связанных спиннеров
     */
    private void setSpinnersBehaviour() {
        nmXMin.addChangeListener(e -> {
            nmXMax.setMinimum((double) nmXMin.getValue() + 0.1);
            cPainter.getConverter().setXShape(
                    (double)nmXMin.getValue(),
                    (double)nmXMax.getValue()
            );
            repaint();
        });
        nmXMax.addChangeListener(e -> {
            nmXMin.setMaximum((double) nmXMax.getValue() - 0.1);
            cPainter.getConverter().setXShape(
                    (double)nmXMin.getValue(),
                    (double)nmXMax.getValue()
            );
            repaint();
        });
        nmYMin.addChangeListener(e -> {
            nmYMax.setMinimum((double) nmYMin.getValue() + 0.1);
            cPainter.getConverter().setYShape(
                    (double)nmYMin.getValue(),
                    (double)nmYMax.getValue()
            );
            repaint();
        });
        nmYMax.addChangeListener(e -> {
            nmYMin.setMaximum((double) nmYMax.getValue() - 0.1);
            cPainter.getConverter().setYShape(
                    (double)nmYMin.getValue(),
                    (double)nmYMax.getValue()
            );
            repaint();
        });
    }

    /**
     * Создание контента в панели управления
     */
    private void createControlPanelContent() {
        var gl = new GroupLayout(controlPanel);
        controlPanel.setLayout(gl);
        controlPanel.setBorder(
                new TitledBorder(
                        new EtchedBorder(EtchedBorder.LOWERED),
                        "Параметры декартовой плоскости: "
                )
        );

        var spXMin = new JSpinner(nmXMin);
        var spXMax = new JSpinner(nmXMax);
        var spYMin = new JSpinner(nmYMin);
        var spYMax = new JSpinner(nmYMax);
        setSpinnersBehaviour();

        var lblXMin = new JLabel("Xmin: ");
        var lblXMax = new JLabel("Xmax: ");
        var lblYMin = new JLabel("Ymin: ");
        var lblYMax = new JLabel("Ymax: ");

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(8)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblXMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(spXMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(lblXMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(spXMax, SHRINK, SHRINK, SHRINK)
                )
                .addGap(8)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lblYMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(spYMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(lblYMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(spYMax, SHRINK, SHRINK, SHRINK)
                )
                .addGap(8)
        );
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(8)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblXMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(lblYMin, SHRINK, SHRINK, SHRINK)
                )
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(spXMin, 100, SHRINK, SHRINK)
                        .addComponent(spYMin, 100, SHRINK, SHRINK)
                )
                .addGap(20)
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblXMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(lblYMax, SHRINK, SHRINK, SHRINK)
                )
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(spXMax, 100, SHRINK, SHRINK)
                        .addComponent(spYMax, 100, SHRINK, SHRINK)
                )
                .addGap(8, 8, Integer.MAX_VALUE)
        );
    }

    /**
     * Создание основного содержимого окна приложения
     */
    private void createMainContent() {
        mainPanel.setBackground(Color.WHITE);
        var gl = new GroupLayout(getContentPane());
        setLayout(gl);
        gl.setVerticalGroup(
                gl.createSequentialGroup()
                        .addGap(8)
                        .addComponent(mainPanel, STRETCH, STRETCH, STRETCH)
                        .addGap(8)
                        .addComponent(controlPanel, SHRINK, SHRINK, SHRINK)
                        .addGap(8)
        );
        gl.setHorizontalGroup(
                gl.createSequentialGroup()
                        .addGap(8)
                        .addGroup(
                                gl.createParallelGroup()
                                        .addComponent(mainPanel, STRETCH, STRETCH, STRETCH)
                                        .addComponent(controlPanel, STRETCH, STRETCH, STRETCH)
                        )
                        .addGap(8)
        );
    }

    /**
     * Константа, используемая для указания необходимости растяжения визуального компонента
     * по ширине содержащего его контейнера
     */
    private static final int STRETCH = GroupLayout.DEFAULT_SIZE;

    /**
     * Константа, используемая для указания необходимости сжатия визуального компонента
     * по величине содержащегося в нем содержимого
     */
    private static final int SHRINK = GroupLayout.PREFERRED_SIZE;
}
