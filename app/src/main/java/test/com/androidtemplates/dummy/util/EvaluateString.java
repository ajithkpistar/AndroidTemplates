package test.com.androidtemplates.dummy.util;

/**
 * Created by ajith on 14-02-2017.
 */


import com.udojava.evalex.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import test.com.androidtemplates.dummy.pojo.Variable;


public class EvaluateString {

    List<Variable> variableList;

    private HashMap<String, Variable> integerIntegerHashMap;

    public EvaluateString() {
        integerIntegerHashMap = new HashMap<>();
    }

    public void createValues(List<Variable> variableList) {
        this.variableList = variableList;
        if (variableList.size() > 0) {
            for (Variable variable : variableList) {
                integerIntegerHashMap.put(variable.getName(), variable);
            }
        }
    }

    public Double evaluateExpression(String expression, HashMap<String, String> inputValues) {
        Double result = 0.0;
        try {
            for (String evaluateString : expression.split("!#")) {
                if (inputValues.size() > 0) {
                    for (String key : inputValues.keySet()) {
                        evaluateString.replaceAll(key, inputValues.get(key));
                    }
                }

                Expression exp = new Expression(evaluateString.split("=")[1]).setPrecision(128);

                for (String key : integerIntegerHashMap.keySet()) {
                    exp = exp.with(key, integerIntegerHashMap.get(key).getValue() + "");
                }
                result = Double.parseDouble(exp.eval().toPlainString());

                if (variableList.size() > 0) {
                    for (Variable variable : variableList) {
                        if (evaluateString.split("=")[0].equalsIgnoreCase(variable.getName())) {
                            if (result.intValue() >= variable.getMinvalue() && result.intValue() <= variable.getMaxvalue()) {
                                Variable variable1 = variable;
                                variable1.setValue(result.intValue());
                                integerIntegerHashMap.put(evaluateString.split("=")[0], variable1);
                            }
                        }
                    }
                }
                System.out.println("res---------------------------------------------- " + evaluateString + " ------------ " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public HashMap<String, Variable> getIntegerIntegerHashMap() {
        return integerIntegerHashMap;
    }

    public void setIntegerIntegerHashMap(HashMap<String, Variable> integerIntegerHashMap) {
        this.integerIntegerHashMap = integerIntegerHashMap;
    }
}