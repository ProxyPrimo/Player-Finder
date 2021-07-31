package football.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <T> T parseXml(Class<T> objectClass, String filePath) throws JAXBException;
    <T> void exportXml(T object, Class<T> objectClass, String filePath) throws JAXBException;
}
