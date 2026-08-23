package com.sunrise.dentalclinic;

import com.sunrise.dentalclinic.ui.MainFrame;

import javax.swing.*;

public class DentalclinicSystem {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}