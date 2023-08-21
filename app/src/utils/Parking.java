import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Parking {
    ShapefileDataStore busstop;
    ShapefileDataStore crossedge;
    ShapefileDataStore crosswalk;
    ShapefileDataStore fireplug;
    ShapefileDataStore RDL_SCHO_AS;    
    
    public Parking(){
        busstop = loadToShp("./shp/busstop.shp");
        crossedge = loadToShp("./shp/crossedge.shp");
        crosswalk = loadToShp("./shp/crosswalk.shp");
        fireplug = loadToShp("./shp/fireplug.shp");
        RDL_SCHO_AS = loadToShp("./shp/RDL_SCHO_AS.shp");

        
    }

    public ShapefileDataStore loadToShp(String[] filepath){
        File shapefile = new File(filepath);
        ShapefileDataStore dataStore;

        try{
            dataStore = new ShapefileDataStore(shapefile.toURI().toURL());
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return dataStore;
    }

    public void findInterSection(Geometry point, ShapefileDataStore dataStore){
        SimpleFeatureType featureType = dataStore.getSchema();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(featureType);
        featureBuilder.add(point);
        SimpleFeatureCollection featureCollection = new DefaultFeatureCollection(null, featureType);
        ((DefaultFeatureCollection) featureCollection).add(featureBuilder.buildFeature(null));

        SimpleFeatureSource featureSource;
        try {
            featureSource = dataStore.getFeatureSource();
            SimpleFeatureCollection intersection = featureSource.getFeatures().subCollection(featureCollection);
            return intersection.size() > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            dataStore.dispose();
        }
    }

    public int parkingReadout(int x, int y, int time){
        Geometry point = JTSFactoryFinder.getGeometryFactory().createPoint(new Coordinate(x, y));

        if(findInterSection(point, RDL_SCHO_AS) && (time > 8 && time < 20)){
            return 0;
        }
        else if(findInterSection(point, busstop)){
            return 1;
        }
        else if(findInterSection(point, crossedge)){
            return 2;
        }
        else if(findInterSection(point, crosswalk)){
            return 3;
        }
        else if(findInterSection(point, fireplug)){
            return 4;
        }
        else{
            return -1;
        }
    }
}
