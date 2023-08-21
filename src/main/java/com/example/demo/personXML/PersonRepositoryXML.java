package com.example.demo.personXML;

import com.example.demo.person.Person;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Repository
public class PersonRepositoryXML {

    private static final String fileName = "src/main/java/com/example/demo/personXML/data.xml";

    /**
     * Função responsável por retornar todos as pessoas armazenadas no XML.
     *
     * @return List<Person>
     */
     public List<Person> findAll() {
         DocumentBuilderFactory DomFac = DocumentBuilderFactory.newInstance();
         List<Person> persons = new ArrayList<>();
         try {
            DocumentBuilder Dom = DomFac.newDocumentBuilder();

            Document doc = Dom.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("person");
            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Person person = new Person();

                    person.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
                    person.setName(element.getElementsByTagName("name").item(0).getTextContent());
                    person.setEmail(element.getElementsByTagName("email").item(0).getTextContent());
                    person.setTelephone(String.valueOf(element.getElementsByTagName("tel").item(0).getTextContent()));
                    person.setDob(LocalDate.parse(element.getElementsByTagName("dob").item(0).getTextContent()));

                    persons.add(person);
                }
            }
         }
         catch (ParserConfigurationException | SAXException | IOException e) {
             e.printStackTrace();
         }
         return persons;
     }


    /**
     * Retorna a Pessoa que foi informada o Id.
     *
     * @param personId Long - personId
     * @return Optional<Person>
     */
     public Optional<Person> findById(Long personId) {
        DocumentBuilderFactory DomFac = DocumentBuilderFactory.newInstance();

        Optional<Person> personOptional = Optional.of(new Person());

        try {
            DocumentBuilder Dom = DomFac.newDocumentBuilder();

            Document doc = Dom.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("person");

            for(int i = 0; i < list.getLength(); i++){
                Node node = list.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Person person = new Person();

                    if(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()) == personId){

                        person.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
                        person.setName(element.getElementsByTagName("name").item(0).getTextContent());
                        person.setEmail(element.getElementsByTagName("email").item(0).getTextContent());
                        person.setTelephone(String.valueOf(element.getElementsByTagName("tel").item(0).getTextContent()));
                        person.setDob(LocalDate.parse(element.getElementsByTagName("dob").item(0).getTextContent()));

                        personOptional = Optional.of(person);
                        return personOptional;
                    }
                }
            }
        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return personOptional;
     }

    /**
     * Salva uma nova pessoa no arquivo XML.
     *
     * @param person Person
     */
    public void save(Person person) {
        try {
            File xmlFile = new File(fileName);

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = domFactory.newDocumentBuilder();

            Document doc;
            Element rootElement;

            if (xmlFile.exists() && xmlFile.length() > 40) {
                doc = docBuilder.parse(xmlFile);
                rootElement = doc.getDocumentElement();
            } else {
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("people");
                doc.appendChild(rootElement);
            }

            rootElement.appendChild(createPersonElement(doc, person));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

     private static Node createPersonElement(Document doc, Person person) {
         Element personElement = doc.createElement("person");

         personElement.appendChild(createElements(doc, "id"   , String.valueOf(autoIncrementId())));
         personElement.appendChild(createElements(doc, "name" , person.getName()));
         personElement.appendChild(createElements(doc, "email", person.getEmail()));
         personElement.appendChild(createElements(doc, "tel"  , person.getTelephone()));
         personElement.appendChild(createElements(doc, "dob"  , String.valueOf(person.getDob())));

         return personElement;
     }

     private static Node createElements(Document Doc, String name, String value) {
         Element node = Doc.createElement(name);
         node.appendChild(Doc.createTextNode(value));
         return node;
     }

    public void deleteById(Long personId) {
        DocumentBuilderFactory DomFac = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder DocBuilder = DomFac.newDocumentBuilder();
            Document doc = DocBuilder.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("person");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());

                    if (id.equals(personId)) {
                        node.getParentNode().removeChild(node);
                        break;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult file = new StreamResult(new File(fileName));

            transformer.transform(source, file);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void addXmlHeader() {
        DocumentBuilderFactory DomFac = DocumentBuilderFactory.newInstance();
        File file = new File(fileName);
        try {
            DocumentBuilder DocBuilder = DomFac.newDocumentBuilder();
            Document Doc = DocBuilder.newDocument();

            NodeList lista = Doc.getElementsByTagName("people");
            if(lista.getLength() > 0) {
                return;
            }
            Node rootElement = Doc.createElement("people");

            Doc.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(Doc);

            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public static Long autoIncrementId() {
        DocumentBuilderFactory DomFac = DocumentBuilderFactory.newInstance();

        long nextId = 0L;
        try {
            DocumentBuilder DocBuilder = DomFac.newDocumentBuilder();
            Document Doc = DocBuilder.parse(fileName);
            Doc.getDocumentElement().normalize();

            NodeList lista = Doc.getElementsByTagName("person");
            for (int i = 0; i < lista.getLength(); i++) {
                Element element = (Element) lista.item(i);
                if(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()) > nextId) {
                    nextId = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                }
            }
        }catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return nextId + 1;
    }

    public void updatePerson(Long personId,Person updatedPerson) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(fileName));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("person");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    Long id = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());

                    if (id.equals(personId)) {
                        element.getElementsByTagName("name").item(0).setTextContent(updatedPerson.getName());
                        element.getElementsByTagName("email").item(0).setTextContent(updatedPerson.getEmail());
                        element.getElementsByTagName("tel").item(0).setTextContent(updatedPerson.getTelephone());
                        element.getElementsByTagName("dob").item(0).setTextContent(updatedPerson.getDob().toString());
                        break;
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(fileName));

            transformer.transform(source, result);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
