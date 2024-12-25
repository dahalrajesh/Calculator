import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurrencyConverter extends JFrame {
    private JTextField amountTextField;
    private JComboBox<String> fromComboBox;
    private JComboBox<String> toComboBox;
    private JLabel resultLabel;

    // Example exchange rates (replace with actual data)
    private ExchangeRateService exchangeRateService = new ExchangeRateService();

    public CurrencyConverter() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        JLabel amountLabel = new JLabel("Amount:");
        add(amountLabel);

        amountTextField = new JTextField();
        add(amountTextField);

        JLabel fromLabel = new JLabel("From:");
        add(fromLabel);

        fromComboBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP", "NPR", "INR"}); // Add more currencies as needed
        add(fromComboBox);

        JLabel toLabel = new JLabel("To:");
        add(toLabel);

        toComboBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP", "NPR", "INR"}); // Add more currencies as needed
        add(toComboBox);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertButtonListener());
        add(convertButton);

        JLabel resultTextLabel = new JLabel("Result:");
        add(resultTextLabel);

        resultLabel = new JLabel();
        add(resultLabel);

        setVisible(true);
    }

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromCurrency = (String) fromComboBox.getSelectedItem();
            String toCurrency = (String) toComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountTextField.getText());

            double result = convertCurrency(amount, fromCurrency, toCurrency);
            resultLabel.setText(String.format("%.2f %s", result, toCurrency));
        }
    }

    // Method to convert currency
    private double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double fromRate = exchangeRateService.getRate(fromCurrency);
        double toRate = exchangeRateService.getRate(toCurrency);

        return amount * (toRate / fromRate);
    }

    // Mock Exchange Rate Service
    private class ExchangeRateService {
        // Example rates (replace with actual data)
        private double getRate(String currency) {
            switch (currency) {
                case "USD":
                    return 1.0;
                case "EUR":
                    return 0.85;
                case "GBP":
                    return 0.75;
                case "NPR":
                    return 116.0; // Nepali Rupee rate
                case "INR":
                    return 74.0; // Indian Rupee rate
                // Add more currencies and their exchange rates as needed
                default:
                    throw new IllegalArgumentException("Unknown currency: " + currency);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverter());
    }
}