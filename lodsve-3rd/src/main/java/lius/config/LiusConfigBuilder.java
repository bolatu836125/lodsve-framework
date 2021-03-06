package lius.config;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

/**
 *
 * Classe permettant de parser le fichier de configuration. <br/><br/>Class for  
 * parsing configuration file.  
 * @author Rida Benjelloun (ridabenjelloun@gmail.com)  
 */

public class LiusConfigBuilder {

	static Logger logger = Logger.getRootLogger();

	private LiusConfig xc = null;

	private static LiusConfigBuilder liusConfigBuilder;

	private HashMap configsCach = new HashMap();

	private LiusConfigBuilder() {
	}

	public static LiusConfigBuilder getSingletonInstance() {

		if (liusConfigBuilder == null)

			liusConfigBuilder = new LiusConfigBuilder();

		return liusConfigBuilder;

	}

	protected Document parse(String file) {

		org.jdom.Document xmlDoc = new org.jdom.Document();

		try {

			SAXBuilder builder = new SAXBuilder();

			xmlDoc = builder.build(new File(file));

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		} catch (IOException e) {

			logger.error(e.getMessage());

		}

		return xmlDoc;

	}

	/**
	 *
	 * Méthode permettant de parser le fichier de configuration et de retourner
	 *
	 * un objet de type LiusConfig. <br/><br/>Method for parsing the
	 *
	 * configuration file, returns a LiusConfig object.
	 *
	 */

	public LiusConfig getLiusConfig(String xmlConfigFile) {

		if (configsCach.get(xmlConfigFile) != null) {

			return (LiusConfig) configsCach.get(xmlConfigFile);

		} else {

			Document doc = parse(xmlConfigFile);

			xc = new LiusConfig();

			populateLiusConfig(doc, xc);

			configsCach.put(xmlConfigFile, xc);

		}

		return xc;

	}

	private void populateLiusConfig(Document doc, LiusConfig xc) {

		xc.setAnalyzer(getAnlyzerClassString(doc));

		xc.setStopWord(getStopWordValue(doc));

		xc.setValueCreateIndex(getCreateIndexValue(doc));

		xc.setMergeFactor(getMergeFactorValue(doc));

		xc.setMaxMergeDocs(getMaxMergeDocs(doc));

		xc.setOptimizeValue(getOptimizeValue(doc));

		xc.setXmlFileFields(getXmlFileFields(doc));

		xc.setXmlNodesFields(getXmlNodesFields(doc));

		xc.setDisplayFields(getDisplayFields(doc));

		xc.setBrowseFieldsToDisplay(getBrowseFieldsToDisplay(doc));

		xc.setElemSearch(getQueryType(doc, xc));

		xc.setJavaBeansFields(getJavaBeansFields(doc));

		xc.setPdfFields(getPdfFields(doc));

		xc.setMsWordFields(getMsWordFields(doc));

		xc.setHtmlFields(getHtmlFields(doc));

		xc.setMixedFields(getMixedFields(doc));

		xc.setRtfFields(getRtfFields(doc));

		xc.setExcelFields(getExcelFields(doc));

		xc.setTxtFields(getTxtFields(doc));

		xc.setOOFields(getOOFields(doc));

		xc.setPPTFields(getPPTFields(doc));

		xc.setMP3Fields(getMP3Fields(doc));

		xc.setTexFields(getTexFields(doc));

		xc.setVCardFields(getVCardFields(doc));

		xc.setFontFields(getFontFields(doc));

		xc.setZipFields(getZipFields(doc));

		xc.setHighlighter(getHighlighter(doc));

		xc.setMimeTypeClass(getMimeTypeClass(doc));

	}

	/**
	 *
	 * Méthode permettant de récupérer l'analyseur à partir du fichier XML.
	 *
	 * <br/><br/>Method for getting the analyser from XML file.
	 *
	 */

	protected String getAnlyzerClassString(Document doc) {

		String className = null;

		try {

			Attribute classN = (Attribute) XPath.selectSingleNode(doc,

					"//properties/analyzer/@class");

			if (classN != null)

				className = classN.getValue();

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return className;

	}

	protected boolean getHighlighter(Document doc) {

		String highlightValue = null;

		boolean highlight = false;

		try {

			Attribute highlightAttr = (Attribute) XPath.selectSingleNode(doc,

					"//fieldsToDisplay/@setHighlighter");

			if (highlightAttr != null) {

				highlightValue = highlightAttr.getValue();

				if (highlightValue.equalsIgnoreCase("true"))

					highlight = true;

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return highlight;

	}

	/**
	 *
	 * Méthode permettant de récupérer la valeur de création de l'index (true,
	 *
	 * false ou auto) dans le fichier de configuration. <br/><br/>Method for
	 *
	 * getting the creation value of index (true, false, auto) in the
	 *
	 * configuration file.
	 *
	 */

	protected String getCreateIndexValue(Document doc) {

		String createIndexValue = null;

		try {

			Attribute createIndexV = (Attribute) XPath.selectSingleNode(doc,

					"//properties/createIndex/@value");

			createIndexValue = createIndexV.getValue();

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return createIndexValue;

	}

	/**
	 *
	 * Méthode permettant de récupérer la valeur du mergeFactor dans le fichier
	 *
	 * de configuration. <br/><br/>Method for getting mergeFactor value in
	 *
	 * configuration file.
	 *
	 */

	protected String getMergeFactorValue(Document doc) {

		String mergeFactorValue = "";

		try {

			Attribute mergeFactor = (Attribute) XPath.selectSingleNode(doc,

					"//indexWriterProperty/@mergeFactor");

			if (mergeFactor != null)

				mergeFactorValue = mergeFactor.getValue();

			else

				return mergeFactorValue = null;

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return mergeFactorValue;

	}

	/**
	 *
	 * Méthode permettant de récupérer la valeur du maxMergeDocs dans le fichier
	 *
	 * de configuration. <br/><br/>Method for getting the maxMergeDocs value in
	 *
	 * the configuration file.
	 *
	 */

	protected String getMaxMergeDocs(Document doc) {

		String maxMergeDocsValue = "";

		try {

			Attribute maxMergeDocs = (Attribute) XPath.selectSingleNode(doc,

					"//indexWriterProperty/@maxMergeDocs");

			if (maxMergeDocs != null)

				maxMergeDocsValue = maxMergeDocs.getValue();

			else

				return maxMergeDocsValue = null;

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return maxMergeDocsValue;

	}

	protected String[] getSortField(Document doc) {

		String[] sortFields = null;

		try {

			Attribute searchSort = (Attribute) XPath.selectSingleNode(doc,

					"//searchResult/@sortBy");

			if (searchSort != null) {

				String attrContent = searchSort.getValue().trim();

				sortFields = attrContent.split(",");

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return sortFields;

	}

	/**
	 *
	 * Méthode permettant de récupérer la valeur d'optimize dans le fichier de
	 *
	 * configuration. <br/><br/>Method for getting the optimize value in the
	 *
	 * configuration file.
	 *
	 */

	protected String getOptimizeValue(Document doc) {

		String optimizeValue = "";

		try {

			Attribute optimize = (Attribute) XPath.selectSingleNode(doc,

					"//indexWriterProperty/@optimize");

			if (optimize != null)

				optimizeValue = optimize.getValue();

			else

				return optimizeValue = null;

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return optimizeValue;

	}

	/**
	 *
	 * Méthode permettant de retourner une collection d'objets LiusField à
	 *
	 * partir du fichier XML si l'élément "xmlFile" existe. <br/><br/>Method
	 *
	 * returning a Collection of LiusField objects from the XML file if the
	 *
	 * element xmlFile exists.
	 *
	 */

	private Collection getXmlFileFields(Document doc) {

		Collection res = new ArrayList();

		HashMap hm = new HashMap();

		try {

			List ls = XPath.selectNodes(doc, "//xmlFile");

			Iterator i = ls.iterator();

			while (i.hasNext()) {

				Collection coll = new ArrayList();

				Element elemNode = (Element) i.next();

				List fieldList = XPath.selectNodes(elemNode,
						"fields/luceneField");

				for (int j = 0; j < fieldList.size(); j++) {

					Element elem = (Element) fieldList.get(j);

					LiusField lf = buildLiusField(elem);

					coll.add(lf);

				}

				if (elemNode.getAttributeValue("setBoost") != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(elemNode.getAttributeValue("setBoost"));

					coll.add(ldp);

				}

				hm.put(elemNode.getAttributeValue("ns"), coll);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		res.add(hm);

		return res;

	}

	/**
	 *
	 * Méthode permettant de retourner un HashMap contenant comme clé
	 *
	 * l'expression XPATH du noeud à indexer, et comme valeur une collection
	 *
	 * d'objets de type LiusField. <br/><br/>Method returning an HashMap which
	 *
	 * key containing the XPath expression for the node to index and for value a
	 *
	 * Collection of LiusField objects.
	 *
	 */

	private HashMap getXmlNodesFields(Document doc) {

		HashMap hm = new HashMap();

		try {

			List ls = XPath.selectNodes(doc, "//xmlNodes/node");

			Iterator i = ls.iterator();

			while (i.hasNext()) {

				Collection coll = new ArrayList();

				Element elemNode = (Element) i.next();

				List fieldList = XPath.selectNodes(elemNode, "luceneField");

				for (int j = 0; j < fieldList.size(); j++) {

					Element elem = (Element) fieldList.get(j);

					LiusField lf = buildLiusField(elem);

					coll.add(lf);

				}

				if (elemNode.getAttributeValue("setBoost") != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(elemNode.getAttributeValue("setBoost"));

					coll.add(ldp);

				}

				hm.put(elemNode.getAttributeValue("select"), coll);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return hm;

	}

	/**
	 *
	 * Méthode permettant de retourner un tableau de mots vides à partir de
	 *
	 * l'élément "stopWord". <br/><br/>Method that returns an array of stop
	 *
	 * words from the element "stopWord".
	 *
	 */

	protected String[] getStopWordValue(Document doc) {

		String[] stopWords = null;

		String stopWordsString = null;

		String sep = null;

		try {

			Element elem = (Element) XPath.selectSingleNode(doc, "//stopWord");

			if (elem != null) {

				stopWordsString = elem.getText().trim();

				Attribute att = (Attribute) XPath.selectSingleNode(doc,

						"//stopWord/@sep");

				sep = att.getValue();

				if (sep == null)

					sep = ",";

				stopWords = stopWordsString.split(sep);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return stopWords;

	}

	/**
	 *
	 * Permet de retourner un tableau d'éléments sur lesquels la recherche va
	 *
	 * être effectuée. Ce dernier sera passé comme argument au constructeur
	 *
	 * "MultiFieldQueryParser". <br/><br/>Returns an array of elements which
	 *
	 * will be searched. It will be passed as parameter for the constructor
	 *
	 * "MultiFieldQueryParser".
	 *
	 */

	protected String[] getSearchFieldsValue(String text, String sep) {

		String[] champsRecherche = text.split(sep);

		return champsRecherche;

	}

	/**
	 *
	 * Méthode retournant un tableau qui va contenir la liste des champs à
	 *
	 * afficher dans le résultat de recherche. <br/><br/>Method that returns an
	 *
	 * array containing the list of fields to show in the search result.
	 *
	 */

	protected List getDisplayFields(Document doc) {

		Vector displayFields = new Vector();

		try {

			List elems = XPath.selectNodes(doc,

					"//searchResult/fieldsToDisplay/luceneField");

			for (int i = 0; i < elems.size(); i++) {

				Element elem = (Element) elems.get(i);

				LiusField lf = buildLiusField(elem);

				displayFields.add(lf);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return displayFields;

	}

	protected List getBrowseFieldsToDisplay(Document doc) {

		Vector displayFields = new Vector();

		try {

			List elems = XPath.selectNodes(doc,

					"//browseResults/fieldsToDisplay/luceneField");

			for (int i = 0; i < elems.size(); i++) {

				Element elem = (Element) elems.get(i);

				LiusField lf = buildLiusField(elem);

				displayFields.add(lf);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return displayFields;

	}

	/**
	 *
	 * Méthode utilisée pour la recherche. La construction de LiusQuery se base
	 *
	 * sur l'élément trouvé dans le fichier de configuration pour constuire
	 *
	 * l'objet. <br/><br/>Method used for search. The construction of LiusQuery
	 *
	 * is based on the element found in the configuration file for constructing
	 *
	 * the object.
	 *
	 */

	protected String getQueryType(Document doc, LiusConfig xc) {

		String nomElem = "";

		String[] xpathExp = { "//search/queryTerm", "//search/queryParser",

				"//search/multiFieldQueryParser", "//search/rangeQuery" };

		try {

			for (int i = 0; i < xpathExp.length; i++) {

				Element testElem = (Element) XPath.selectSingleNode(doc,

						xpathExp[i]);

				if (testElem != null) {

					if (i == 0) {

						xc.setQueryTermClass(testElem

								.getAttributeValue("class"));

						xc.setTermFiled(testElem.getChild("term")

								.getAttributeValue("field"));

						return nomElem = "queryTerm";

					}

					else if (i == 1) {

						xc.setDefaultSearchField(testElem.getChild(

								"defaultSearchField").getAttributeValue(

								"value"));

						return nomElem = "queryParser";

					} else if (i == 2) {

						xc.setSearchFields(getSearchFieldsValue(testElem

								.getChild("searchFields").getText(), testElem

								.getChild("searchFields").getAttributeValue(

										"sep")));

						return nomElem = "multiFieldQueryParser";

					} else if (i == 3) {

						String[] vals = new String[2];

						vals[0] = testElem.getChild("term")

								.getAttributeValue("field");

						vals[1] = testElem.getChild("booleanInclusive")

								.getAttributeValue("value");

						xc.setRangeQueryFileds(vals);

						return nomElem = "rangeQuery";

					}

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return nomElem;

	}

	/**
	 *
	 * Méthode retournant un Map qui va contenir les informations pour
	 *
	 * l'indexation. <br/><br/>Method returning a Map containing the
	 *
	 * information for indexation.
	 *
	 */

	protected Map getJavaBeansFields(Document doc) {

		Collection coll = null;

		Map javaBeansMap = new HashMap();

		try {

			List javaBeans = XPath.selectNodes(doc, "//JavaBeans/Bean");

			for (int i = 0; i < javaBeans.size(); i++) {

				coll = new ArrayList();

				Element beanC = (Element) javaBeans.get(i);

				List bean = beanC.getChildren("luceneField");

				for (int j = 0; j < bean.size(); j++) {

					LiusField lf = buildLiusField((Element) bean.get(j));

					coll.add(lf);

				}

				if (beanC.getAttributeValue("setBoost") != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(beanC.getAttributeValue("setBoost"));

					coll.add(ldp);

				}

				javaBeansMap.put(beanC.getAttributeValue("class"), coll);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return javaBeansMap;

	}

	/**
	 *
	 * Méthode retournant une collection contenant les propriétés à utiliser
	 *
	 * pour l'indexation du PDF à partir du fichier de configuration de Lius.
	 *
	 * <br/><br/>Method returning a collection containing the properties to use
	 *
	 * for indexing PDF files from the Lius configuration file.
	 *
	 */

	protected Collection getPdfFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List pdfLs = XPath.selectNodes(doc, "//pdf/fields/luceneField");

			if (pdfLs != null) {

				for (int i = 0; i < pdfLs.size(); i++) {

					LiusField lf = buildLiusField((Element) pdfLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//pdf/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	/**
	 *
	 * Méthode retournant une collection contenant les propriétés à utiliser
	 *
	 * pour l'indexation du Excel à partir du fichier de configuration de Lius.
	 *
	 * <br/><br/>Method returning a collection containing the properties to use
	 *
	 * for indexing Excel files for Lius configuration file.
	 *
	 */

	protected Collection getExcelFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List excelLs = XPath.selectNodes(doc,
					"//msExcel/fields/luceneField");

			if (excelLs.size() > 0) {

				for (int i = 0; i < excelLs.size(); i++) {

					LiusField lf = buildLiusField((Element) excelLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//msExcel/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	/**
	 *
	 * Méthode retournant une collection contenant les propriétés à utiliser
	 *
	 * pour l'indexation du MS WORD à partir du fichier de configuration de
	 *
	 * Lius. <br/><br/>Method returning a collection containing the properties
	 *
	 * to use for indexing MS WORD files for Lius configuration file.
	 *
	 */

	protected Collection getMsWordFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List wordLs = XPath.selectNodes(doc, "//msWord/fields/luceneField");

			if (wordLs.size() > 0) {

				for (int i = 0; i < wordLs.size(); i++) {

					LiusField lf = buildLiusField((Element) wordLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//msWord/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	/**
	 *
	 * Méthode retournant une collection contenant les propriétés à utiliser
	 *
	 * pour l'indexation du XHTML à partir du fichier de configuration de Lius.
	 *
	 * <br/><br/>Method returning a collection containing the properties to use
	 *
	 * for indexing XHTML files for Lius configuration file.
	 *
	 */

	protected Collection getHtmlFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List listeElem = XPath
					.selectNodes(doc, "//html/fields/luceneField");

			for (int i = 0; i < listeElem.size(); i++) {

				LiusField lf = buildLiusField((Element) listeElem.get(i));

				coll.add(lf);

			}

			Attribute boost = (Attribute) XPath.selectSingleNode(doc,

					"//html/@setBoost");

			if (boost != null) {

				LiusDocumentProperty ldp = new LiusDocumentProperty();

				ldp.setBoost(boost.getValue());

				coll.add(ldp);

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	/**
	 *
	 * Méthode retournant une collection contenant les propriétés à utiliser
	 *
	 * pour l'indexation du RTF à partir du fichier de configuration de Lius.
	 *
	 * <br/><br/>Method returning a collection containing the properties to use
	 *
	 * for indexing RTF files for Lius configuration file.
	 *
	 */

	protected Collection getRtfFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List rtfLs = XPath.selectNodes(doc, "//rtf//fields/luceneField");

			if (rtfLs.size() > 0) {

				for (int i = 0; i < rtfLs.size(); i++) {

					LiusField lf = buildLiusField((Element) rtfLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//rtf/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getTxtFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List rtfLs = XPath.selectNodes(doc, "//txt//fields/luceneField");

			if (rtfLs.size() > 0) {

				for (int i = 0; i < rtfLs.size(); i++) {

					LiusField lf = buildLiusField((Element) rtfLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//txt/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getPPTFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List rtfLs = XPath.selectNodes(doc,
					"//msPowerPoint/fields/luceneField");

			if (rtfLs.size() > 0) {

				for (int i = 0; i < rtfLs.size(); i++) {

					LiusField lf = buildLiusField((Element) rtfLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//msPowerPoint/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getOOFields(Document doc) {

		Collection col = new ArrayList();

		try {

			List listeElem = XPath

					.selectNodes(doc, "//openOffice/fields/luceneField");

			if (listeElem.size() == 0) {

				return col = null;

			} else {

				for (int i = 0; i < listeElem.size(); i++) {

					LiusField lf = buildLiusField((Element) listeElem.get(i));

					col.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//openOffice/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					col.add(ldp);

				}

			}

		}

		catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return col;

	}

	protected Collection getMixedFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List mixedLs = XPath.selectNodes(doc,
					"//mixedIndexing/fields/luceneField");

			if (mixedLs != null) {

				for (int i = 0; i < mixedLs.size(); i++) {

					LiusField lf = buildLiusField((Element) mixedLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//mixedIndexing/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getMP3Fields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List mp3Ls = XPath.selectNodes(doc, "//mp3/fields/luceneField");

			if (mp3Ls != null) {

				for (int i = 0; i < mp3Ls.size(); i++) {

					LiusField lf = buildLiusField((Element) mp3Ls.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//mp3/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getTexFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List texLs = XPath.selectNodes(doc, "//tex/fields/luceneField");

			if (texLs != null) {

				for (int i = 0; i < texLs.size(); i++) {

					LiusField lf = buildLiusField((Element) texLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//tex/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getVCardFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List vcardLs = XPath.selectNodes(doc, "//vcard/fields/luceneField");

			if (vcardLs != null) {

				for (int i = 0; i < vcardLs.size(); i++) {

					LiusField lf = buildLiusField((Element) vcardLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//vcard/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getFontFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List fontLs = XPath.selectNodes(doc, "//font/fields/luceneField");

			if (fontLs != null) {

				for (int i = 0; i < fontLs.size(); i++) {

					LiusField lf = buildLiusField((Element) fontLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//font/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}

	protected Collection getZipFields(Document doc) {

		Collection coll = new ArrayList();

		try {

			List zipLs = XPath.selectNodes(doc, "//zip/fields/luceneField");

			if (zipLs != null) {

				for (int i = 0; i < zipLs.size(); i++) {

					LiusField lf = buildLiusField((Element) zipLs.get(i));

					coll.add(lf);

				}

				Attribute boost = (Attribute) XPath.selectSingleNode(doc,

						"//zip/@setBoost");

				if (boost != null) {

					LiusDocumentProperty ldp = new LiusDocumentProperty();

					ldp.setBoost(boost.getValue());

					coll.add(ldp);

				}

			}

		} catch (JDOMException e) {

			logger.error(e.getMessage());

		}

		return coll;

	}



	protected Map getMimeTypeClass(Document doc) {
		Map mimeM = new HashMap();
		try {
			List mimes = XPath.selectNodes(doc, "//mime");
			for (int i = 0; i < mimes.size(); i++) {
				Element mime = (Element) mimes.get(i);
				String mimeStr = mime.getTextTrim();
				if (!mimeM.containsKey(mimeStr)) {
					String className = ((Element)mime.getParent()).getAttributeValue("class");
					mimeM.put(mimeStr, className);
				}
			}

		} catch (JDOMException e) {
			logger.error(e.getMessage());
		}
		return mimeM;
	}

	protected LiusField buildLiusField(Element elem) {

		LiusField lf = new LiusField();

		if (elem.getAttribute("name") != null)

			lf.setName(elem.getAttributeValue("name"));

		if (elem.getAttribute("xpathSelect") != null)

			lf.setXpathSelect(elem.getAttributeValue("xpathSelect"));

		if (elem.getAttribute("type") != null)

			lf.setType(elem.getAttributeValue("type"));

		if (elem.getAttribute("ocurSep") != null)

			lf.setOcurSep(elem.getAttributeValue("ocurSep"));

		if (elem.getAttribute("dateFormat") != null)

			lf.setDateFormat(elem.getAttributeValue("dateFormat"));

		if (elem.getAttribute("label") != null)

			lf.setLabel(elem.getAttributeValue("label"));

		if (elem.getAttribute("getMethod") != null)

			lf.setGetMethod(elem.getAttributeValue("getMethod"));

		if (elem.getAttribute("fileMimeType") != null)

			lf.setFileMimeType(elem.getAttributeValue("fileMimeType"));

		if (elem.getAttribute("get") != null)

			lf.setGet(elem.getAttributeValue("get"));

		if (elem.getAttribute("setFragmenter") != null)

			lf.setFragmenter(elem.getAttributeValue("setFragmenter"));

		if (elem.getAttribute("setBoost") != null) {

			Float f = new Float(elem.getAttributeValue("setBoost"));

			lf.setBoost(f.floatValue());

			lf.setIsBoosted(true);

		}

		return lf;

	}

}