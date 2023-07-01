/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.sld.bindings;

import javax.xml.namespace.QName;
import org.geotools.api.filter.FilterFactory;
import org.geotools.api.filter.expression.Expression;
import org.geotools.styling.ShadedRelief;
import org.geotools.styling.StyleFactory;
import org.geotools.xsd.AbstractComplexBinding;
import org.geotools.xsd.ElementInstance;
import org.geotools.xsd.Node;
import org.picocontainer.MutablePicoContainer;

/**
 * Binding object for the element http://www.opengis.net/sld:ShadedRelief.
 *
 * <p>
 *
 * <pre>
 *         <code>
 *  &lt;xsd:element name="ShadedRelief"&gt;
 *      &lt;xsd:annotation&gt;
 *          &lt;xsd:documentation&gt;         &quot;ShadedRelief&quot; specifies
 *              the application of relief shading         (or &quot;hill
 *              shading&quot;) to a DEM raster to give it somewhat of a
 *              three-dimensional effect and to make elevation changes more
 *              visible.       &lt;/xsd:documentation&gt;
 *      &lt;/xsd:annotation&gt;
 *      &lt;xsd:complexType&gt;
 *          &lt;xsd:sequence&gt;
 *              &lt;xsd:element ref="sld:BrightnessOnly" minOccurs="0"/&gt;
 *              &lt;xsd:element ref="sld:ReliefFactor" minOccurs="0"/&gt;
 *          &lt;/xsd:sequence&gt;
 *      &lt;/xsd:complexType&gt;
 *  &lt;/xsd:element&gt;
 *
 *          </code>
 *         </pre>
 *
 * @generated
 */
public class SLDShadedReliefBinding extends AbstractComplexBinding {
    StyleFactory styleFactory;
    FilterFactory filterFactory;

    public SLDShadedReliefBinding(StyleFactory styleFactory, FilterFactory filterFactory) {
        this.styleFactory = styleFactory;
        this.filterFactory = filterFactory;
    }

    /** @generated */
    @Override
    public QName getTarget() {
        return SLD.SHADEDRELIEF;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    @Override
    public int getExecutionMode() {
        return AFTER;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    @Override
    public Class getType() {
        return ShadedRelief.class;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    @Override
    public void initialize(ElementInstance instance, Node node, MutablePicoContainer context) {}

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    @Override
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        Expression reliefFactor = null;

        if (node.hasChild("ReliefFactor")) {
            Double d = (Double) node.getChildValue("ReliefFactor");
            reliefFactor = filterFactory.literal(d.doubleValue());
        }

        ShadedRelief shadedRelief = styleFactory.createShadedRelief(reliefFactor);

        if (node.hasChild("BrightnessOnly")) {
            Boolean b = (Boolean) node.getChildValue("BrightnessOnly");
            shadedRelief.setBrightnessOnly(b.booleanValue());
        }

        return shadedRelief;
    }
}
