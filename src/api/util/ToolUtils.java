package api.util;

import java.io.Serializable;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.MaskFormatter;

public class ToolUtils implements Serializable {
	private static final long serialVersionUID = -4641942226686116508L;

	public String formatarCpf(String cpfCnpj) {

		try {

			String cpfCnpjFormatado = cpfCnpj.trim();
			cpfCnpjFormatado = cpfCnpjFormatado.replaceAll("[^0-9]", "");

			MaskFormatter a = new MaskFormatter("###.###.###-##");
			a.setValueContainsLiteralCharacters(false);
			return a.valueToString(cpfCnpjFormatado);

		} catch (ParseException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String somenteNumeros(String str) {
		return str.replaceAll("[^0-9]", "");
	}

	public boolean telefoneValido(String telefone) {
		if(isNull(telefone))
			return false;
	    String formato = "^\\([1-9]{2}\\)\\ [2-9]{1}[0-9]{3}\\-[0-9]{4}$";
	    if(telefone.matches(formato))
	    	return true;
    	return false;
	}

	public boolean validaCpf(String s_aux) {
		
		s_aux = s_aux.replaceAll("[^0-9]", "");
		if (s_aux.length() == 11) {

			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			String nDigResult;
			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;

			for (int n_Count = 1; n_Count < s_aux.length() - 1; n_Count++) {
				digitoCPF = Integer.valueOf(s_aux.substring(n_Count - 1, n_Count)).intValue();
				d1 = d1 + (11 - n_Count) * digitoCPF;
				d2 = d2 + (12 - n_Count) * digitoCPF;
			}

			resto = (d1 % 11);
			if (resto < 2)
				digito1 = 0;
			else
				digito1 = 11 - resto;

			d2 += 2 * digito1;
			resto = (d2 % 11);
			if (resto < 2)
				digito2 = 0;
			else
				digito2 = 11 - resto;

			String nDigVerific = s_aux.substring(s_aux.length() - 2, s_aux.length());
			nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
			return nDigVerific.equals(nDigResult);
		}  else
			return false;
	}

	public boolean validaEmail(String email) {
		if (email == null)
			return true;
		if (email.equals(""))
			return true;
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);

		boolean matchFound = m.matches();

		if (!matchFound) {
			return false;
		}
		return true;
	}
	
	public String formataMascaraFone(String fone) {		
		try {

			String foneFormatado = fone;
			foneFormatado = foneFormatado.replaceAll("[^0-9]", "");

			MaskFormatter a;
			if (foneFormatado.length() > 9) {
				if (foneFormatado.length() == 10)
					a = new MaskFormatter("(##)####-####");
				else
					a = new MaskFormatter("(##)#####-####");
			} else
				return foneFormatado;

			a.setValueContainsLiteralCharacters(false);
			return a.valueToString(foneFormatado);

		} catch (ParseException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isNull(String campo) {
		if (campo == null)
			return true;
		if (campo.trim().equals(""))
			return true;
		if (campo.equals("null"))
			return true;
		return false;
	}
}