package imangazaliev.scripto.js;

import org.json.JSONArray;
import org.json.JSONException;

import imangazaliev.scripto.utils.ScriptoUtils;

public class JavaArguments {

    private String raw;
    private JSONArray jsonArgsArray;
    private Class<?>[] argsTypes;
    private Object[] argsObjects;
    private boolean hasNullArg;

    public JavaArguments(String jsonArgs) {
        try {
            this.jsonArgsArray = new JSONArray(jsonArgs);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON arguments from JS!", e);
        }

        this.argsTypes = initArgsTypes();
        this.argsObjects = initArgs();
        this.hasNullArg = ScriptoUtils.hasNull(argsObjects);
    }

    public String getRaw() {
        return raw;
    }

    private Class<?>[] initArgsTypes() {
        Class<?>[] argsTypes = new Class<?>[jsonArgsArray.length()];
        for (int i = 0; i < jsonArgsArray.length(); i++) {
            try {
                argsTypes[i] = jsonArgsArray.get(i).getClass();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return argsTypes;
    }

    private Object[] initArgs() {
        Object[] args = new Object[jsonArgsArray.length()];
        for (int i = 0; i < jsonArgsArray.length(); i++) {
            try {
                if (jsonArgsArray.isNull(i)) {
                    args[i] = null;
                } else {
                    args[i] = jsonArgsArray.get(i);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return args;
    }

    public Object[] getArgs() {
        return argsObjects;
    }

    public Class<?>[] getArgsTypes() {
        return argsTypes;
    }

    public boolean hasNullArg() {
        return hasNullArg;
    }

}
