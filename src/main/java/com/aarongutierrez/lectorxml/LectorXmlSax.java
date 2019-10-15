package com.aarongutierrez.lectorxml;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class LectorXmlSax {
    public static void main(String[] args) {
        crearDocumento();
        try {
            XMLReader procesador = XMLReaderFactory.createXMLReader();
            LectorXML lector = new LectorXML();
            procesador.setContentHandler(lector);
            InputSource fichero = new InputSource("carrito.xml");
            procesador.parse(fichero);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void crearDocumento(){
        double[] precios = {10.0,5.0,3.0,9.99};
        String[] noms = {"Pollo","","Pan","Lejia","Arroz","Hamburguesa","Salchichas","Mantequilla"};
        int[] cantidad= {1,4,5,10};
        Random rnd = new Random();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //Elemento raiz
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("carrito");
            doc.appendChild(rootElement);
            for (int i=0; i<noms.length; i++) {
                //primer elemento
                Element producto = doc.createElement("producto");
                rootElement.appendChild(producto);
                Attr atributo = doc.createAttribute("id");
                atributo.setValue(String.valueOf(i+1));
                producto.setAttributeNode(atributo);
                //Agregamos los atributos
                //agregamos nombre producto
                Element nombre = doc.createElement("nombre");
                producto.appendChild(nombre);
                Text valorNombre = doc.createTextNode(noms[i]);
                nombre.appendChild(valorNombre);
                //precio producto
                Element tipo = doc.createElement("precio");
                producto.appendChild(tipo);
                Text valorTipo = doc.createTextNode(String.valueOf(precios[rnd.nextInt(4)]));
                tipo.appendChild(valorTipo);

                Element cantidadP = doc.createElement("cantidad");
                producto.appendChild(tipo);
                Text valorCantidad = doc.createTextNode(String.valueOf(cantidad[rnd.nextInt(4)]));
                tipo.appendChild(valorTipo);


                //Se escribe el contenido del xml a un arxivo
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("./carrito.xml"));
                transformer.transform(source, result);
            }

        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}