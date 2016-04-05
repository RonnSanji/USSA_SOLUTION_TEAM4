package sg.edu.nus.iss.ssa.util;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import sg.edu.nus.iss.ssa.constants.StoreConstants;
import sg.edu.nus.iss.ssa.exception.FieldMismatchExcepion;
import sg.edu.nus.iss.ssa.model.Entity;

/**
 * It has responsibility to read file from disk and populate to Model object.
 * Class has been written in very generic way utilizing reflection.
 * In order to add new entity mapping :
 * 1. Specific entity must be created extending {@Entity} class which holds metadata
 * 2. data file must be present under /data folder
 * 3. Comma separated fields in file must match properties specified in corresponding entity
 *
 * @param <E>
 * @author Amarjeet B Singh
 */
public class IOService<E> {

    /**
     * @throws FileNotFoundException - This exception will generate when file being processed is not present in class path.
     * @throws FieldMismatchExcepion - properties specified in model object and commas separated fields present in file must match.
     */
    public void readFromFile(Map entityMap, List entityList, Entity entityToCreate) throws IOException, FieldMismatchExcepion {
        String dataFilePathPrefix = getInputDataFileLocation();
        String fileName = entityToCreate.getFileName();
        String[] fields = entityToCreate.getProperties();
        String className = entityToCreate.getClassName();
        String mapKey = entityToCreate.getMapKey();

        File inputFile = new File(dataFilePathPrefix + fileName);
        if (inputFile == null || !inputFile.exists()) {
            throw new FileNotFoundException(fileName + " file is not available. Please make sure file is present " +
					"	under \"data\" folder. ");
		}

        //by now we know that file is available for processing.
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                StringTokenizer tokens = new StringTokenizer(line, StoreConstants.FIELD_DELIMITER);
                if (fields.length != tokens.countTokens()) {
                    throw new FieldMismatchExcepion("Please specify all the field values for file " + fileName + " at line No : " + count);
                }
                Class<?> cls = Class.forName(className);
                Object entity = cls.newInstance();
                String keyValue = null;
                for (int i = 0; i < fields.length; i++) {
                    String property = fields[i];
                    String value = tokens.nextToken();
                    set(entity, property, value);
                    if (property.equals(mapKey)) {
                        keyValue = value;
                    }
                }
                if (entityMap != null) {
                    entityMap.put(keyValue, entity);
                } else {
                    entityList.add(entity);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * generic method to set property value
     *
     * @param object
     * @param fieldName
     * @param fieldValue
     */
    private void set(Object object, String fieldName, String fieldValue) {
        Class<?> clazz = object.getClass();
        if (clazz != null) {
            if(!setFieldValueToClass(clazz,object,fieldName,fieldValue)) {
                clazz = clazz.getSuperclass();
                setFieldValueToClass(clazz, object, fieldName, fieldValue);
            }
        }
    }

    /**
     *
     * @param clazz
     * @param object
     * @param fieldName
     * @param fieldValue
     * @return
     */
    private boolean setFieldValueToClass(Class<?> clazz, Object object,String fieldName, String fieldValue ){
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            setValue(object, field, fieldValue);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * Generic method to set property value.
     * Determines actual type of property at run time and sets the value accordingly.
     *
     * @param object - Object for which property is to be set.
     * @param field  - property Name
     * @param value  - property value
     */
    private void setValue(Object object, Field field, String value) {
        Class<?> type = field.getType();
        try {
            if (type.getSimpleName().equals("long")) {
                field.setLong(object, Long.valueOf(value));
            } else if (type.getSimpleName().equals("double")) {
                field.setDouble(object, Double.valueOf(value));
            } else if (type.getSimpleName().equals("float")) {
                field.setFloat(object, Float.valueOf(value));
            }else if (type.getSimpleName().equals("int")) {
                field.setInt(object, Integer.valueOf(value));
            } else {
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

	public void writeToFile(Collection entityList, Entity entityToCreate)
			throws IOException, IllegalAccessException, NoSuchFieldException {
		String dataFilePathPrefix = getInputDataFileLocation();
		String fileName = entityToCreate.getFileName();
		String[] fields = entityToCreate.getProperties();
		File outputFile = new File(dataFilePathPrefix + fileName);
		if (outputFile == null || !outputFile.exists()) {
			if (outputFile.createNewFile()) {
				System.out.println("File: " + fileName + " does not exist, creating new ...");
			} else {
				throw new FileNotFoundException(
						fileName + " file is not available, failed to create new one. Please make sure file is present "
								+ "	under \"data\" folder. ");
			}

		}
		StringBuilder sb = new StringBuilder();
		try {
			for (Object o : entityList) {
				Class<?> clazz = o.getClass();
				for (String field : fields) {
					Field f = null;
					try {
						f = clazz.getDeclaredField(field);
					} catch (NoSuchFieldException e) {
						f = findFieldInSuperclass(clazz, field);
						if (f == null) {
							throw new NoSuchFieldException("Field: " + field + " cannot be found");
						}
					}
					f.setAccessible(true);
					Object value = f.get(o);
					sb.append(value.toString()).append(StoreConstants.FIELD_DELIMITER);
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(System.lineSeparator());
			}
			PrintWriter pw = new PrintWriter(outputFile);

			pw.print(sb.toString());
			pw.close();
		} catch (IllegalAccessException | NoSuchFieldException e) {
			throw e;

		}
	}

	private Field findFieldInSuperclass(Class<?> clazz, String fieldName) {
		Field field = null;
		Class superClass = clazz.getSuperclass();
		while (superClass != null) {
			try {
				field = superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e) {
				// e.printStackTrace();
				field = null;
				superClass = clazz.getSuperclass();
			}
			if (field != null) {
				break;
			}
		}
		return field;
	}

	public String getInputDataFileLocation() throws IOException {
		String currentDirectory = null;
		currentDirectory = System.getProperty("user.dir");
		if (currentDirectory == null || currentDirectory.equals("")) {
			File currentDir = new File(".");
			currentDirectory = currentDir.getAbsolutePath();
		}
		if (!currentDirectory.contains(StoreConstants.PROJECT_NAME)) {
			currentDirectory += "/" + StoreConstants.PROJECT_NAME;
		}
		return currentDirectory + "/data/";
	}

	public static String getImageFileLocation() throws IOException {
		String currentDirectory = null;
		currentDirectory = System.getProperty("user.dir");
		if (currentDirectory == null || currentDirectory.equals("")) {
			File currentDir = new File(".");
			currentDirectory = currentDir.getAbsolutePath();
		}
		if (!currentDirectory.contains(StoreConstants.PROJECT_NAME)) {
			currentDirectory += "/" + StoreConstants.PROJECT_NAME;
		}
		return currentDirectory + "/src/img";
	}

}
