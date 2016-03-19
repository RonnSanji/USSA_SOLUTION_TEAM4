package sg.edu.nus.iss.ssa.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.exception.FileUnableToWriteException;
import sg.edu.nus.iss.ssa.model.Entity;

/**
 * It has responsibility to read file from disk and populate to Model object.
 * Class has been written in very generic way utilizing reflection. 
 * In order to add new entity mapping :
 * 	1. Specific entity must be created extending {@Entity} class which holds metadata
 *  2. data file must be present under /data folder
 *  3. Comma separated fields in file must match properties specified in corresponding entity
 * 
 * @author Amarjeet B Singh
 *
 * @param <E>
 */
public class IOService<E> {

	/**
	 *
	 * @throws FileNotFoundException - This exception will generate when file being processed is not present in class path.
	 * @throws FieldMismatchExcepion - properties specified in model object and commas separated fields present in file must match.
	 *  
	 */
	public  void readFromFile(Map entityMap, List entityList,  Entity entityToCreate) throws FileNotFoundException, FieldMismatchExcepion  {
		InputStream inputStream = null;
		BufferedReader br = null;
		String fileName = entityToCreate.getFileName();
		String[] fields = entityToCreate.getProperties();
		String className = entityToCreate.getClassName();
		String mapKey = entityToCreate.getMapKey();
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);	
			if(inputStream == null) {
				throw new FileNotFoundException(fileName + " file not found in classpath. Please make sure file is present "+
						"	under \"data\" folder and same is added in classpath. " );
			}
			//by now we know that file is available for processing.
			String line;
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			int count = 0;
			while((line = br.readLine()) != null){
				count++;
				StringTokenizer tokens = new StringTokenizer(line, StoreConstants.FIELD_DELIMITER);
				if(fields.length != tokens.countTokens()){
					throw new FieldMismatchExcepion("Please specify all the field values for file " + fileName + " at line No : " + count);
				}
				Class<?> cls = Class.forName(className);
				Object entity  = cls.newInstance();
				String keyValue= null;
				for(int i = 0;  i < fields.length; i++){
					String property = fields[i]; 
					String value = tokens.nextToken();
					set(entity, property,value);
					if(property.equals(mapKey)){
						keyValue = value;
					}
				}
				if (entityMap != null ) {
					entityMap.put(keyValue, entity);
				}else {
					entityList.add(entity);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}


	}

	/**
	 * generic method to set property value
	 * @param object
	 * @param fieldName
	 * @param fieldValue
	 */
	public void set(Object object, String fieldName, String fieldValue) {
		Class<?> clazz = object.getClass();
		if (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				setValue(object, field, fieldValue);	            
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Generic method to set property value.
	 * Determines actual type of property at run time and sets the value accordingly.
	 * @param object - Object for which property is to be set.
	 * @param field - property Name
	 * @param value - property value
	 */
	private void setValue(Object object, Field field, String value) {
		Class<?> type = field.getType();
		try{
			if(type.getSimpleName().equals("long") ){			
				field.setLong(object, Long.valueOf(value));	
			}else if(type.getSimpleName().equals("double")){
				field.setDouble(object, Double.valueOf(value));	
			}else {
				field.set(object, value);	
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void writeToFile(Map entityMap, Entity entityToCreate)
			throws FileUnableToWriteException, IOException {
		String fileName = entityToCreate.getFileName();
		String[] fields = entityToCreate.getProperties();
		String className = entityToCreate.getClassName();
		String mapKey = entityToCreate.getMapKey();

		StringBuilder sb = new StringBuilder();
		for (Object o : entityMap.values()) {

			try {
				Class<?> clazz = o.getClass();
				for (String field : fields) {
					Field f = clazz.getDeclaredField(field);
					f.setAccessible(true);
					Object value = f.get(o);

					sb.append(value.toString()).append(StoreConstants.FIELD_DELIMITER);

				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(System.lineSeparator());

			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		System.out.println(sb);
		URL url = getClass().getClassLoader().getResource(fileName);

		PrintWriter pw = new PrintWriter(url.getPath());
		
		pw.print(sb.toString());
		pw.close();
		
	}

}
