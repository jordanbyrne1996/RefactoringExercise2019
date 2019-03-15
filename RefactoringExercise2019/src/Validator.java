import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Validator {

	public boolean validate2(JTextField ppsField, JTextField surnameField, JTextField firstNameField,
			JComboBox<String> genderCombo, JComboBox<String> departmentCombo) {
		boolean valid = true;
		EmployeeDetails ed = new EmployeeDetails();
		if (ppsField.isEditable() && ppsField.getText().trim().isEmpty()) {
			ppsField.setBackground(Colors.color_red);
			valid = false;
		}

		if (surnameField.isEditable() && surnameField.getText().trim().isEmpty()) {
			surnameField.setBackground(Colors.color_red);
			valid = false;
		}
		if (firstNameField.isEditable() && firstNameField.getText().trim().isEmpty()) {
			firstNameField.setBackground(Colors.color_red);
			valid = false;
		}
		if (genderCombo.getSelectedIndex() == 0 && genderCombo.isEnabled()) {
			genderCombo.setBackground(Colors.color_red);
			valid = false;
		}
		if (departmentCombo.getSelectedIndex() == 0 && departmentCombo.isEnabled()) {
			departmentCombo.setBackground(Colors.color_red);
			valid = false;
		}
		return valid;
	}

}
