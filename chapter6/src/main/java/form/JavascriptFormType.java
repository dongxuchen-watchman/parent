package form;

import org.activiti.engine.form.AbstractFormType;

/**
 * Created by Administrator on 2019/7/18.
 */
public class JavascriptFormType extends AbstractFormType{
    @Override
    public Object convertFormValueToModelValue(String s) {
        return s;
    }

    @Override
    public String convertModelValueToFormValue(Object o) {
        return (String)o;
    }

    @Override
    public String getName() {
        return "javascript";
    }
}
