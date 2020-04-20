package ojects;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadObject {
	Properties properties = new Properties();
    public Properties getObjectRepository() throws IOException{
    	FileInputStream in = new FileInputStream("/Users/kreddy/eclipseworkspace/SeleniumHybridFW/src/ojects/object.properties");
    	properties.load(in);
        return properties;
         
    }

}
