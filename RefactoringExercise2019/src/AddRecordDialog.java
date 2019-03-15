/*
 * 
 * This is a dialog for adding new Employees and saving records to file
 * 
 * */

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class AddRecordDialog extends JDialog implements ActionListener {
	JTextField idField, ppsField, surnameField, firstNameField, salaryField;
	JComboBox<String> genderCombo, departmentCombo, fullTimeCombo;
	JButton save, cancel;
	EmployeeDetails parent;
	
	public AddRecordDialog(EmployeeDetails parent) {
		setTitle("Add Record");
		setModal(true);
		this.parent = parent;
		this.parent.setEnabled(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane(dialogPane());
		setContentPane(scrollPane);
		
		getRootPane().setDefaultButton(save);
		
		setSize(500, 370);
		setLocation(350, 250);
		setVisible(true);
	}

	// initialize dialog container
	public Container dialogPane() {
		JPanel empDetails, buttonPanel;
		empDetails = new JPanel(new MigLayout());
		buttonPanel = new JPanel();
		JTextField field;

		empDetails.setBorder(BorderFactory.createTitledBorder("Employee Details"));

		empDetails.add(new JLabel("ID:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(idField = new JTextField(20), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);
		idField.setEditable(false);
		

		empDetails.add(new JLabel("PPS Number:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(ppsField = new JTextField(20), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);

		empDetails.add(new JLabel("Surname:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(surnameField = new JTextField(20), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);

		empDetails.add(new JLabel("First Name:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(firstNameField = new JTextField(20), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);

		empDetails.add(new JLabel("Gender:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(genderCombo = new JComboBox<String>(this.parent.gender), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);

		empDetails.add(new JLabel("Department:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(departmentCombo = new JComboBox<String>(this.parent.department), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);

		empDetails.add(new JLabel("Salary:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(salaryField = new JTextField(20), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);

		empDetails.add(new JLabel("Full Time:"), LayoutType.grow + ", " + LayoutType.push);
		empDetails.add(fullTimeCombo = new JComboBox<String>(this.parent.fullTime), LayoutType.grow +", " + LayoutType.push + ", " + LayoutType.wrap);
		

		buttonPanel.add(save = new JButton("Save"));
		save.addActionListener(this);
		save.requestFocus();
		buttonPanel.add(cancel = new JButton("Cancel"));
		cancel.addActionListener(this);

		empDetails.add(buttonPanel, LayoutType.span + ", " + LayoutType.grow + ", " + LayoutType.push + ", " + LayoutType.wrap);
		// loop through all panel components and add fonts and listeners
		for (int i = 0; i < empDetails.getComponentCount(); i++) {
			empDetails.getComponent(i).setFont(this.parent.font1);
			if (empDetails.getComponent(i) instanceof JComboBox) {
				empDetails.getComponent(i).setBackground(Colors.color_white);
			}// end if
			else if(empDetails.getComponent(i) instanceof JTextField){
				field = (JTextField) empDetails.getComponent(i);
				if(field == ppsField)
					field.setDocument(new JTextFieldLimit(9));
				else
				field.setDocument(new JTextFieldLimit(20));
			}
		}
		idField.setText(Integer.toString(this.parent.getNextFreeId()));
		return empDetails;
	}

	// add record to file
	public void addRecord() {
		boolean fullTime = false;
		Employee theEmployee;

		if (((String) fullTimeCombo.getSelectedItem()).equalsIgnoreCase("Yes"))
			fullTime = true;
		// create new Employee record with details from text fields
		theEmployee = new Employee(Integer.parseInt(idField.getText()), ppsField.getText().toUpperCase(), surnameField.getText().toUpperCase(),
				firstNameField.getText().toUpperCase(), genderCombo.getSelectedItem().toString().charAt(0),
				departmentCombo.getSelectedItem().toString(), Double.parseDouble(salaryField.getText()), fullTime);
		this.parent.currentEmployee = theEmployee;
		this.parent.addRecord(theEmployee);
		this.parent.displayRecords(theEmployee);
	}

	// check for input in text fields
	public boolean checkInput() {
		boolean valid = true;
		
		if (ppsField.getText().equals("")) {
			ppsField.setBackground(Colors.color_red);
			valid = false;
		}
		if (this.parent.correctPps(this.ppsField.getText().trim(), -1)) {
			ppsField.setBackground(Colors.color_red);
			valid = false;
		}
		if (surnameField.getText().isEmpty()) {
			surnameField.setBackground(Colors.color_red);
			valid = false;
		}
		if (firstNameField.getText().isEmpty()) {
			firstNameField.setBackground(Colors.color_red);
			valid = false;
		}
		if (genderCombo.getSelectedIndex() == 0) {
			genderCombo.setBackground(Colors.color_red);
			valid = false;
		}
		if (departmentCombo.getSelectedIndex() == 0) {
			departmentCombo.setBackground(Colors.color_red);
			valid = false;
		}
		try {
			Double.parseDouble(salaryField.getText());
			// check if salary is greater than 0
			if (Double.parseDouble(salaryField.getText()) < 0) {
				salaryField.setBackground(Colors.color_red);
				valid = false;
			}
		}
		catch (NumberFormatException num) {
			salaryField.setBackground(Colors.color_red);
			valid = false;
		}
		if (fullTimeCombo.getSelectedIndex() == 0) {
			fullTimeCombo.setBackground(Colors.color_red);
			valid = false;
		}
		return valid;
	}

	// set text field to white colour
	public void setToWhite() {
		ppsField.setBackground(Colors.color_white);
		surnameField.setBackground(Colors.color_white);
		firstNameField.setBackground(Colors.color_white);
		salaryField.setBackground(Colors.color_white);
		genderCombo.setBackground(Colors.color_white);
		departmentCombo.setBackground(Colors.color_white);
		fullTimeCombo.setBackground(Colors.color_white);
	}

	
	public void actionPerformed(ActionEvent e) {
		// if chosen option save, save record to file
		if (e.getSource() == save) {
			// if inputs correct, save record
			if (checkInput()) {
				addRecord();
				dispose();
				this.parent.changesMade = true;
			}
			// else display message and set text fields to white colour
			else {
				JOptionPane.showMessageDialog(null, "Wrong values or format! Please check!");
				setToWhite();
			}
		}
		else if (e.getSource() == cancel)
			dispose();
	}
}