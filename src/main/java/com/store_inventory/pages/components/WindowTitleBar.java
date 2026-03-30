package com.store_inventory.pages.components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WindowTitleBar extends JPanel {
  private final JFrame frame;
  private Point dragStart = null;

  public WindowTitleBar(JFrame frame, String title) {
    this.frame = frame;
    setLayout(new BorderLayout());
    setBackground(UITheme.TITLEBAR_BACKGROUND);
    setBorder(new EmptyBorder(6, 10, 6, 10));

    JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    left.setOpaque(false);

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(UITheme.customFont(UITheme.FONT_FAMILY, Font.BOLD, 14));
    titleLabel.setForeground(UITheme.TITLEBAR_TEXT);
    left.add(titleLabel);

    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
    right.setOpaque(false);

    JButton minimize = titleButton("—");
    JButton maximize = titleButton("□");
    JButton close = titleButton("×");

    minimize.addActionListener(e -> frame.setState(Frame.ICONIFIED));
    maximize.addActionListener(e -> toggleMaximize());
    close.addActionListener(e -> frame.dispatchEvent(
        new java.awt.event.WindowEvent(frame,
                                       java.awt.event.WindowEvent.WINDOW_CLOSING)));

    right.add(minimize);
    right.add(maximize);
    right.add(close);

    add(left, BorderLayout.WEST);
    add(right, BorderLayout.EAST);

    MouseAdapter drag = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        if (dragStart == null) {
          return;
        }
        if (frame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
          return;
        }
        Point location = frame.getLocation();
        int x = location.x + e.getX() - dragStart.x;
        int y = location.y + e.getY() - dragStart.y;
        frame.setLocation(x, y);
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          toggleMaximize();
        }
      }
    };
    addMouseListener(drag);
    addMouseMotionListener(drag);
  }

  private JButton titleButton(String text) {
    JButton button = new JButton(text);
    button.setFont(UITheme.customFont(UITheme.FONT_FAMILY, Font.BOLD, 12));
    button.setForeground(UITheme.TITLEBAR_TEXT);
    button.setBackground(UITheme.TITLEBAR_BACKGROUND);
    button.setBorder(new EmptyBorder(4, 8, 4, 8));
    button.setFocusPainted(false);
    button.setContentAreaFilled(false);
    button.setOpaque(true);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    Color hover =
        "×".equals(text) ? UITheme.TITLEBAR_BUTTON_CLOSE_HOVER
                         : UITheme.TITLEBAR_BUTTON_HOVER;
    Color base = UITheme.TITLEBAR_BACKGROUND;
    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(hover);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(base);
      }
    });

    return button;
  }

  private void toggleMaximize() {
    int state = frame.getExtendedState();
    if ((state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
      frame.setExtendedState(Frame.NORMAL);
    } else {
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
  }
}
