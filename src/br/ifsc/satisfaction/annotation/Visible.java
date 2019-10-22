package br.ifsc.satisfaction.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.ifsc.satisfaction.presentation.component.GeneratedField;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Visible {
	public String label() default "";
	public boolean required() default false;
	public int size() default 10;
	public Class<?> type() default GeneratedField.class;
	public boolean setDateToNow() default false;
	public int width() default 5;
	public int height() default 5;
}
