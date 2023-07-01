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
package org.geotools.data.db2;

import java.io.IOException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.jdbc.JDBCDataStoreAPIOnlineTest;
import org.geotools.jdbc.JDBCDataStoreAPITestSetup;

public class DB2DataStoreAPIOnlineTest extends JDBCDataStoreAPIOnlineTest {
    @Override
    protected JDBCDataStoreAPITestSetup createTestSetup() {
        return new DB2DataStoreAPITestSetup();
    }

    @Override
    public void connect() throws Exception {
        super.connect();
        dataStore.setDatabaseSchema("geotools");
    }

    // TODO - DWA
    /**
     * This is due to some DB2 JDBC functions not working correctly.
     *
     * <p>1. DB2SQLDialect should implement lookupGeneratedValuesPostInsert(), returning true so
     * that key columns are not included in the insert operation when the primary key is
     * autogenerated. 2. When this change is made, JDBCDataStore::insertPS uses
     * cx.prepareStatement(sql, keysFetcher.getColumnNames()) in order to tell DB2 which columns to
     * get autogenerated key values for. Unfortunately, when the key column name is lower-case, DB2
     * defaults to the upper-case value and the ps.AddBatch() for the insert subsequently fails with
     * SQLCODE -206 because the upper-case value doesn't match the actual lower-case key column
     * name. This fails at ps.addBatch() in JDBCDataStore::insertPS 3. Changing the primary key to
     * upper-case (FID), allows ps.addBatch to succeed and then KeysFetcher::postInsert is called
     * but this fails due to ps.getGeneratedKeys() which is broken when ps.AddBatch() is used. The
     * ResultSet is empty instead of returning the keys which causes subsequent failures. This is
     * known by the DB2 JDBC development team but is not likely to be fixed. 4. Changing the primary
     * key to upper-case is not viable because JDBCInsertFeatureWriter::flush() has the primary key
     * hard-coded as "fid".
     */
    @Override
    public void testGetFeatureStoreAddFeatures() throws IOException {
        // TODO skip
    }

    @Override
    public void testGetFeatureStoreSetFeatures() {
        // TODO skip
    }

    @Override
    public void testGetFeaturesWriterAdd() throws IOException {
        // TODO skip
    }

    @Override
    public void testTransactionIsolation() throws Exception {
        // TODO skip, check for DB2
    }

    @Override
    public void testGetFeatureWriterConcurrency() throws Exception {
        // TODO skip , check for DB2
    }

    @Override
    protected boolean areCRSEqual(CoordinateReferenceSystem crs1, CoordinateReferenceSystem crs2) {
        if (crs1 == null && crs2 == null) return true;

        if (crs1 == null) return false;
        if (crs2 == null) return false;

        String name1 = crs1.getName().toString();
        String name2 = crs2.getName().toString();

        return (name1.contains("WGS")
                && name1.contains("84")
                && name2.contains("WGS")
                && name2.contains("84"));
    }
}
