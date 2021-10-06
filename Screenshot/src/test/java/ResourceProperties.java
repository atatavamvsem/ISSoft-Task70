import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ResourceProperties {
    private static FileInputStream fileConfigInputStream;
    private static Properties CONF_PROPERTIES;

    static {
        try {
            fileConfigInputStream = new FileInputStream("src/test/resources/credentials.properties");
            CONF_PROPERTIES = new Properties();
            CONF_PROPERTIES.load(fileConfigInputStream);
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        } finally {
            if (Objects.nonNull(fileConfigInputStream))
                try {
                    fileConfigInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static String getCredProperty(String key) {
        return CONF_PROPERTIES.getProperty(key);
    }
}