package sg.edu.nus.iss.ssa.util;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                    "	under \"data\" folder and same is added in classpath. ");
        }

        //by now we know that file is available for processing.
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFile.toURI()))) {
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

    public void writeToFile(List entityList, Entity entityToCreate) throws IOException{
        String dataFilePathPrefix = getInputDataFileLocation();
        String fileName = entityToCreate.getFileName();
        String[] fields = entityToCreate.getProperties();
        File outputFile = new File(dataFilePathPrefix + fileName);
        if (outputFile == null || !outputFile.exists()) {
            throw new FileNotFoundException(fileName + " file is not available. Please make sure file is present " +
                    "	under \"data\" folder and same is added in classpath. ");
        }
        StringBuilder sb = new StringBuilder();
        try {
            for (Object o : entityList) {
                Class<?> clazz = o.getClass();
                for (String field : fields) {
                    Field f = clazz.getDeclaredField(field);
                    f.setAccessible(true);
                    Object value = f.get(o);
                    sb.append(value.toString()).append(StoreConstants.FIELD_DELIMITER);
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(System.lineSeparator());
            }
            System.out.println(sb);
          //  URL url = getClass().getClassLoader().getResource(fileName);
            PrintWriter pw = new PrintWriter(outputFile);

            pw.print(sb.toString());
            pw.close();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }


    public String getInputDataFileLocation() throws IOException {
        String currentDirectory = null;
        currentDirectory = System.getProperty("user.dir");
        if (currentDirectory == null || currentDirectory.equals("")) {
            File currentDir = new File(".");
            currentDirectory = currentDir.getAbsolutePath();
        }
        currentDirectory += StoreConstants.PROJECT_NAME;
        return currentDirectory + "/data/";
    }

}
