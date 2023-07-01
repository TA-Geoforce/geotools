/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.filter.v2_0.bindings;

import javax.xml.namespace.QName;
import org.geotools.api.filter.FilterFactory;
import org.geotools.api.filter.expression.Expression;
import org.geotools.api.filter.temporal.OverlappedBy;
import org.geotools.filter.v2_0.FES;
import org.geotools.xsd.AbstractComplexBinding;
import org.geotools.xsd.ElementInstance;
import org.geotools.xsd.Node;

/**
 * Binding object for the element http://www.opengis.net/fes/2.0:OverlappedBy.
 *
 * <p>
 *
 * <pre>
 *  <code>
 *  &lt;xsd:element name="OverlappedBy" substitutionGroup="fes:temporalOps" type="fes:BinaryTemporalOpType"/&gt;
 *
 *   </code>
 * </pre>
 *
 * @generated
 */
public class OverlappedByBinding extends AbstractComplexBinding {

    FilterFactory filterFactory;

    public OverlappedByBinding(FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    /** @generated */
    @Override
    public QName getTarget() {
        return FES.OverlappedBy;
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
        return OverlappedBy.class;
    }

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
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        Expression[] e = FESParseEncodeUtil.temporal(node, filterFactory);
        return filterFactory.overlappedBy(e[0], e[1]);
    }
}
