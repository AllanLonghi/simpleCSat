package br.ifsc.satisfaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FormSpecs {
	public String title() default "";
	public String actionName() default "Submit";
	public int width() default 300;
	public int height() default 300;
}
