/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflection;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob
 */
public class Reflection
{

	/**
	 * @param args the command line arguments
	 */
	public static String main() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		boolean data = true;
		String text="";
		try
		{
			Path path = Paths.get("assets.files/Objects2.dat");
			File file = new File(path.toAbsolutePath().toString());

			if (!file.exists())
			{
				//Screen.message("cannot find file");
				System.out.println("cannot find file");
			} else
			{
				FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

				while (data)
				{
					try
					{
						Object object = (Object) objectInputStream.readObject();
						//object = (Object) new PrivateClass();

						printName(object);
						printFieldNames(object);
						//showConstructors(object);
						showMethods(object);
						showValues(object);
					} catch (EOFException end)
					{
						data = false;
					}

				}
			}
		} catch (Exception ex)
		{
			//Screen.message("Problem reading from file"+ ex);
			System.out.println("Problem reading from file" + ex);
		}
		{

		}
		System.out.println(new File(".").getAbsolutePath());
		return text;

	}
//		FileInputStream fiis = new FileInputStream("Objects2.dat");
//    InputStream fis = new FilterInputStream(fiis) {
//        @Override
//        public void close() throws IOException {
//            // ignore
//        }
//    };
//    try {
//        while (true) {
//            ObjectInputStream in = new ObjectInputStream(fis);
//            Object o = (Object) in.readObject();
//            in.close();
//            System.out.println(o);
//        }
//    } catch (EOFException ex) {
//        // done
//    }
//    fiis.close();
//}

	static void printName(Object object)
	{
		Class class_ = object.getClass();
		String name = class_.getName();
		System.out.println(name);
	}

	static void printFieldNames(Object object)
	{
		Class class_ = object.getClass();
		for (Field field : class_.getFields())
		{
			int modifiers = field.getModifiers();
			String type = field.getType().getName();
			String name = field.getName();

			System.out.println(Modifier.toString(modifiers) + " " + type + " " + name + ";");
		}
	}

	static void showConstructors(Object o)
	{
		Class FB = o.getClass();
		Constructor[] theConstructors = FB.getConstructors();
		for (int i = 0; i < theConstructors.length; i++)
		{
			System.out.print("(");
			Class[] parameterTypes = theConstructors[i].getParameterTypes();
			for (int k = 0; k < parameterTypes.length; k++)
			{
				String parameterString = parameterTypes[k].getName();
				parameterString += parameterTypes[k].toGenericString();

				System.out.print(parameterString + "");
			}
			System.out.println(")");
		}
	}

	static void showMethods(Object o)
	{
		Class FB = o.getClass();
		Method[] methods = FB.getMethods();
		for (Method method : methods)
		{
			int modifiers = method.getModifiers();
			String name = method.getName();
			//System.out.println("Name: " + name);
			String returnString = method.getReturnType().getName();
			//System.out.println(" Return Type: " + returnString);
			Class[] parameterTypes = method.getParameterTypes();

			String parameterString = "";
			if (parameterTypes.length != 0)
			{
				//System.out.print(" Parameter Types: ");
				int k;
				for (k = 0; k < parameterTypes.length; k++)
				{
					parameterString = parameterTypes[k].getName();
				}

				for (; k < parameterTypes.length; k++)
				{
					parameterString += ",  " + parameterTypes[k].getName();
					//System.out.print(",  " + parameterString);
				}
			}
			System.out.println(Modifier.toString(modifiers) + " " + returnString + " " + name + "(" + parameterString + ");");

		}
	}

	static void showValues(Object o) throws IllegalArgumentException
	{
		Class class_ = o.getClass();
		Field[] publicFields = class_.getDeclaredFields();
		for (Field field : publicFields)
		{

			showValue(field, o);
		}
		for (Method method : class_.getMethods())
		{
			//showValue(method);
		}
	}

	private static void showValue(Field field, Object object) throws SecurityException, IllegalArgumentException
	{
		try
		{
			field.setAccessible(true);

			Object value = field.get(object);
			System.out.println(value.toString());

		} catch (IllegalAccessException ex)
		{
			Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
		}

		// System.out.println("Field Name: "+ fieldName + ", Type: " + fieldValue);
	}

//	private static void showValue(Method method)
//	{
//		Class class_ = method.getClass();
//		Method[] methods = class_.getMethods();
//		try
//		{
//			int Value;
//			method.setAccessible(true);
//			Value = method.getInt(method);
//			System.out.println(Value);
//
//		} catch (IllegalAccessException ex)
//		{
//			Logger.getLogger(Reflection.class.getName()).log(Level.SEVERE, null, ex);
//		}
}

class PrivateClass
{

	private int privateField = 0;
	public int publicField = 0;
	protected int protectedField = 0;
}
