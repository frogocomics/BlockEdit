/*
 * BlockEdit, a general purpose software to edit Minecraft
 * Copyright (c) 2015. Jeff Chen and others
 *
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.blockedit.core.output;

import org.apache.commons.io.FileUtils;
import org.blockedit.core.block.Block;
import org.blockedit.exception.DataException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Utilities to store a block (.block or .blk) file, for all Minecraft blocks, including custom
 * blocks.
 *
 * @author Jeff Chen
 */
@ParametersAreNonnullByDefault
public final class BlockStorageFormat extends BasicGZipStorageFormat {

    /**
     * Write the file to a specified destination.
     *
     * @param file  The destination
     * @param block The block to be written
     * @throws IOException IO related issues
     * @throws TransformerException When the transformer is unable to work
     */
    public void writeBlock(File file, Block block) throws IOException, TransformerException, ParserConfigurationException {
        OutputWriterThread writer = new OutputWriterThread(file, block);
        writer.call();
    }

    /**
     * Reads a file, if it exists and is in the right format.
     *
     * @param file
     * @return
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws DataException
     */
    public Optional<Block> readBlock(File file) throws IOException, ParserConfigurationException, SAXException, DataException {
        OutputReaderThread reader = new OutputReaderThread(file);
        return reader.call();
    }

    private class OutputWriterThread implements Callable<Boolean> {

        private File file;
        private Block block;

        private OutputWriterThread(File file, Block block) {
            this.file = file;
            this.block = block;
        }

        @Override
        public Boolean call() throws ParserConfigurationException, TransformerException, IOException {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element rootElement = document.createElement("blocks");
            document.appendChild(rootElement);

            Element blockElement = document.createElement("block");
            rootElement.appendChild(blockElement);
            Attr blockIdAttribute = document.createAttribute("id");
            blockIdAttribute.setValue(String.valueOf(block.getId()));
            blockElement.setAttributeNode(blockIdAttribute);

            Element nameElement = document.createElement("name");
            nameElement.appendChild(document.createTextNode(block.getBlockName()));
            blockElement.appendChild(nameElement);

            Element displayNameElement = document.createElement("displayName");
            displayNameElement.appendChild(document.createTextNode(block.getBlockDisplayName()));
            blockElement.appendChild(displayNameElement);

            Element blockDatasElement = document.createElement("blockDatas");
            blockElement.appendChild(blockDatasElement);

            for (int i = 0; i < block.getBlockData().length; i++) {
                Block.BlockData next = block.getBlockData()[i];
                Element blockDataElement = document.createElement("blockData");
                Element idElement = document.createElement("id");
                idElement.appendChild(document.createTextNode(String.valueOf(next.getDataValue())));
                blockDataElement.appendChild(idElement);
                Element imageElement = document.createElement("image");
                imageElement.appendChild(document.createTextNode(next.getImageLocation().getAbsolutePath().replace("\\", "\\\\")));
                blockDataElement.appendChild(imageElement);
                blockDatasElement.appendChild(blockDataElement);
            }

            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.VERSION, "1.1");
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            BasicGZipStorageFormat zipUtils = new BasicGZipStorageFormat();
            zipUtils.write(file, new String(Base64.getEncoder().encode(writer.toString().getBytes("UTF-8")), "UTF-8"));
            System.gc();
            return true;
        }
    }

    private class OutputReaderThread implements Callable<Optional<Block>> {

        private File file;

        public OutputReaderThread(File file) {
            this.file = file;
        }

        @Override
        public Optional<Block> call() throws IOException, ParserConfigurationException, SAXException, DataException {
            BasicGZipStorageFormat zipUtils = new BasicGZipStorageFormat();
            String inputRaw = zipUtils.read(this.file);
            String input = new String(Base64.getDecoder().decode(inputRaw.getBytes("UTF-8")), "UTF-8");
            System.out.println(input);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource source = new InputSource();
            source.setCharacterStream(new StringReader(input));
            Document document = builder.parse(source);

            Block.Builder blockBuilder = new Block.Builder();
            NodeList rootNodes = document.getElementsByTagName("blocks");
            if (rootNodes.getLength() >= 1) {
                NodeList blockNode = document.getElementsByTagName("block");
                if (blockNode.getLength() == 1) {
                    Element blockElement = (Element) blockNode.item(0);
                    System.out.println(true);
                    NodeList idNode = blockElement.getElementsByTagName("id");
                    if (idNode.getLength() == 1) {
                        Element idElement = (Element) idNode.item(0);
                        blockBuilder.id(Integer.parseInt(idElement.getChildNodes().item(0).getNodeValue()));
                        NodeList nameNode = blockElement.getElementsByTagName("name");
                        if (nameNode.getLength() == 1) {
                            Element nameElement = (Element) nameNode.item(0);
                            blockBuilder.blockName(nameElement.getChildNodes().item(0).getNodeValue());
                            NodeList displayNameNode = blockElement.getElementsByTagName("displayName");
                            if (displayNameNode.getLength() == 1) {
                                Element displayNameElement = (Element) displayNameNode.item(0);
                                blockBuilder.blockDisplayName(displayNameElement.getChildNodes().item(0).getNodeValue());
                                NodeList blockDatasNode = blockElement.getElementsByTagName("blockDatas");
                                if (blockDatasNode.getLength() == 1) {
                                    Element blockDatasElement = (Element) blockDatasNode.item(0);
                                    NodeList blockDataNode = blockDatasElement.getElementsByTagName("blockData");
                                    Block.BlockData[] dataValues = new Block.BlockData[blockDatasNode.getLength()];
                                    for (int i = 0; i < blockDataNode.getLength(); i++) {
                                        Block.BlockData.Builder dataBuilder = new Block.BlockData.Builder();
                                        NodeList dataIdNode = ((Element) blockDataNode.item(i)).getElementsByTagName("id");
                                        if (dataIdNode.getLength() == 1) {
                                            dataBuilder.data(Integer.parseInt(dataIdNode.item(0).getChildNodes().item(0).getNodeValue()));
                                            NodeList dataImageNode = ((Element) blockDataNode.item(i)).getElementsByTagName("image");
                                            if (dataImageNode.getLength() == 1) {
                                                dataBuilder.image(new File(dataImageNode.item(0).getChildNodes().item(0).getNodeValue()));
                                                dataValues[i] = dataBuilder.build();
                                            }
                                        }
                                    }
                                    blockBuilder.blockDataValues(dataValues);
                                    System.gc();
                                    Block block = blockBuilder.build();
                                    FileUtils.copyFile(this.file, new FileOutputStream(new File(System.getProperty("user.dir") + "\\blocks\\" + String.valueOf(block.getId()) + "-" + block.getBlockName() + ".blk")));
                                    return Optional.of(block);
                                }
                            }
                        }
                    }
                }
            }
            return Optional.empty();
        }
    }
}

