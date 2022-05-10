import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;

public class ExpandParams implements IParameterValues {

	@Override
	public Map getParameterValues() {
		HashMap<String, boolean> param = new HashMap<>();
		param.put("true", true);
		param.put("false", false);
		
		return param;
	}

}
