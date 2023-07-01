/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2019, Open Source Geospatial Foundation (OSGeo)
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

package org.geotools.grid.oval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.geometry.MismatchedDimensionException;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.grid.DefaultGridFeatureBuilder;
import org.geotools.grid.GridElement;
import org.geotools.grid.GridFeatureBuilder;
import org.geotools.grid.PolygonElement;
import org.geotools.grid.TestBase;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Test;

/** Unit tests for the Ovals class. */
public class OvalsTest extends TestBase {

    @Test
    public void create() {
        PolygonElement oval = Ovals.create(1, 2, 3, 4, null);
        assertNotNull(oval);
        assertEnvelope(new ReferencedEnvelope(1, 4, 2, 6, null), oval.getBounds());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badWidth() {
        Ovals.create(1, 2, -1, 4, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void badHeight() {
        Ovals.create(1, 2, 3, -1, null);
    }

    @Test
    public void createGrid() throws Exception {
        final SimpleFeatureType TYPE =
                DataUtilities.createType("obtype", "oval:Polygon,id:Integer");

        final double SPAN = 100;
        final ReferencedEnvelope bounds = new ReferencedEnvelope(0, SPAN, 0, SPAN, null);

        class Setter extends GridFeatureBuilder {
            int id = 0;

            public Setter(SimpleFeatureType type) {
                super(type);
            }

            @Override
            public void setAttributes(GridElement el, Map<String, Object> attributes) {
                attributes.put("id", ++id);
            }
        }

        Setter setter = new Setter(TYPE);

        final double WIDTH = 5.0;
        final double HEIGHT = 10.0;
        SimpleFeatureSource gridSource = Ovals.createGrid(bounds, WIDTH, HEIGHT, setter);
        assertNotNull(gridSource);

        int expectedCols = (int) (SPAN / WIDTH);
        int expectedRows = (int) (SPAN / HEIGHT);

        assertEquals(expectedCols * expectedRows, setter.id);
        assertEquals(setter.id, gridSource.getFeatures().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGrid_InvalidBounds() {
        Ovals.createGrid(ReferencedEnvelope.EVERYTHING, 1.0, 1.0, new DefaultGridFeatureBuilder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGrid_NullBounds() {
        Ovals.createGrid(null, 1.0, 1.0, new DefaultGridFeatureBuilder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGrid_badWidth() {
        Ovals.createGrid(
                new ReferencedEnvelope(0, 10, 0, 10, null),
                0,
                1.0,
                new DefaultGridFeatureBuilder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGrid_badHeight() {
        Ovals.createGrid(
                new ReferencedEnvelope(0, 10, 0, 10, null), 1, 0, new DefaultGridFeatureBuilder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createGrid_MisMatchedCRS() {
        try {
            ReferencedEnvelope env =
                    new ReferencedEnvelope(0, 10, 0, 10, DefaultGeographicCRS.WGS84);
            CoordinateReferenceSystem otherCRS = CRS.parseWKT(getSydneyWKT());
            GridFeatureBuilder builder = new DefaultGridFeatureBuilder(otherCRS);

            Ovals.createGrid(env, 0, 1.0, builder);

        } catch (FactoryException | MismatchedDimensionException ex) {
            throw new IllegalStateException("Error in test code");
        }
    }
}
