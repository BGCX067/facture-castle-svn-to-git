package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
 
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;


public class AutoCompleteDocument extends PlainDocument {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<String> dictionary = new ArrayList<String>();
 
	private final JTextComponent _textField;
 
	public AutoCompleteDocument(JTextComponent field, String[] aDictionary) {
		_textField = field;
		dictionary.addAll(Arrays.asList(aDictionary));
	}
 
	public void addDictionaryEntry(String item) {
		dictionary.add(item);
	}
 
	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		super.insertString(offs, str, a);
		String word = autoComplete(getText(0, getLength()));
		if (word != null) {
			super.insertString(offs + str.length(), word, a);
			_textField.setCaretPosition(offs + str.length());
			_textField.moveCaretPosition(getLength());
			// _textField.setCaretPosition(getLength());
			// _textField.moveCaretPosition(offs + str.length());
		}
	}
 
	public String autoComplete(String text) {
		for (Iterator<String> i = dictionary.iterator(); i.hasNext();) {
			String word = i.next();
			if (word.startsWith(text)) {
				return word.substring(text.length());
			}
		}
		return null;
	}
}