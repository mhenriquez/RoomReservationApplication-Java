package reserveroomapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Moises Henriquez
 * @date April 24, 2016
 */
public class ReserveRoomGUI extends JFrame implements ActionListener, FocusListener, ItemListener
{
    //Instance Variables
    
    private int intNights = 0;
    private double dblTotal = 0;
    
    //Constants
    
    private final double TAX_RATE = .07;
    //Nightly Room Cost
    private final double BUDGET_ROOM_CHARGE = 100.00;
    private final double BUSINESS_ROOM_CHARGE = 150.00;
    private final double DELUXE_ROOM_CHARGE = 300.00;
    //Daily Package Cost
    private final double BREAKFAST_PREMIUM = 7.00;
    private final double DINNER_PREMIUM = 15.00;
    private final double SMOKING_SURCHARGE = 5.00;
    
    //JFrame Components
    
    //Labels
    private JLabel lblName = new JLabel("Name:");
    private JLabel lblNights = new JLabel("Number of Nights:");
    private JLabel lblRoom = new JLabel("Type of Room:");
    private JLabel lblMessage = new JLabel(String.format("Amount Owed $%,.2f", dblTotal));
    //TextFields
    private JTextField txtName = new JTextField(11);
    private JTextField txtNights = new JTextField("0", 5);
    //ComboBox
    private JComboBox<String> cmbRoomDropdown = new JComboBox<>();
    //CheckBoxes
    private JCheckBox chkBreakfastBox = new JCheckBox("Breakfast Pkg ($7.00 per day)", false);
    private JCheckBox chkDinnerBox = new JCheckBox("Dinner Pkg ($15.00 per day)", false);
    //RadioButtons
    private ButtonGroup smokingGroup = new ButtonGroup();
    private JRadioButton noSmoking = new JRadioButton("No Smoking", true);
    private JRadioButton yesSmoking = new JRadioButton("Smoking ($5.00 per day)", false);
    //Buttons
    private JButton btnCalc = new JButton("Reserve");
    private JButton btnExit = new JButton("Exit");
    
    //Constructor
    
    public ReserveRoomGUI(String title, boolean visible) {
        super(title);
        setSize(233, 370);
        setResizable(false);
        initializeUserInterface();
        setVisible(visible); //<--Must be placed at end to render JFrame components
    }
    
    //Methods
    
    public void initializeUserInterface() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //
        add(lblName);
        add(txtName);
        //
        add(lblNights);
        add(txtNights);
        txtNights.addFocusListener(this);
        //
        add(lblRoom);
        add(cmbRoomDropdown);
        cmbRoomDropdown.addItem("Budget ($100/night)");
        cmbRoomDropdown.addItem("Business ($150/night)");
        cmbRoomDropdown.addItem("Deluxe ($300/night)");
        cmbRoomDropdown.addItemListener(this);
        //
        add(chkBreakfastBox);
        chkBreakfastBox.addItemListener(this);
        //
        add(chkDinnerBox);
        chkDinnerBox.addItemListener(this);
        //
        add(noSmoking);
        add(yesSmoking);
        yesSmoking.addItemListener(this);
        //
        smokingGroup.add(noSmoking);
        smokingGroup.add(yesSmoking);
        //
        add(lblMessage);
        //
        add(btnCalc);
        btnCalc.addActionListener(this);
        //
        add(btnExit);
        btnExit.addActionListener(this);
    }
    
    public void calculateTotal() {
        lblMessage.setText(String.format("Amount Owed $%,.2f", dblTotal + (dblTotal * TAX_RATE)));
    }
    
    public void resetValues() {
        txtNights.setText("0");
        intNights = 0;
        dblTotal = 0;
        calculateTotal();
    }
    
    public void getRoomDropdownValue(int value) {
        switch(value){
            case 0:
                dblTotal = BUDGET_ROOM_CHARGE * intNights;
                break;
            case 1:
                dblTotal = BUSINESS_ROOM_CHARGE * intNights;
                break;
            case 2:
                dblTotal = DELUXE_ROOM_CHARGE * intNights;
                break;
            default: 
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source instanceof JButton){
            if(source == btnCalc){
                JOptionPane.showMessageDialog(null, txtName.getText() + " has reserved a room for " + intNights + String.format(" night(s) for a total of $%,.2f", dblTotal + (dblTotal * TAX_RATE)), "Message", JOptionPane.INFORMATION_MESSAGE);
            }else{
                System.exit(0);
            }
        }
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        Object source = e.getSource();
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object source = e.getSource();
        if(source == txtNights){
            if(txtNights != null){
                try
                {
                    intNights = Integer.parseInt(txtNights.getText());
                    if(intNights > 0){
                        getRoomDropdownValue(cmbRoomDropdown.getSelectedIndex());
                        calculateTotal();
                    }else{
                        throw new NumberFormatException();
                    }
                }
                catch(NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, ex.getMessage() + ". Value must be numeric larger than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    resetValues();
                }
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        int select = e.getStateChange();
        if(source == cmbRoomDropdown){
            if(select == ItemEvent.SELECTED){
                getRoomDropdownValue(cmbRoomDropdown.getSelectedIndex());
            }
        }else if(source == chkBreakfastBox){
            if(select == ItemEvent.SELECTED){
                dblTotal += BREAKFAST_PREMIUM;
            }else{
                dblTotal -= BREAKFAST_PREMIUM;
            }
        }else if(source == chkDinnerBox){
            if(select == ItemEvent.SELECTED){
                dblTotal += DINNER_PREMIUM;
            }else{
                dblTotal -= DINNER_PREMIUM;
            }
        }else if(source == yesSmoking){
            if(yesSmoking.isSelected()){
                dblTotal += SMOKING_SURCHARGE * intNights;
            }else{
                dblTotal -= SMOKING_SURCHARGE * intNights;
            }
        }
        calculateTotal();
    }
}
