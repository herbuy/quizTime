package shared;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

/** the goal is to simply the mapview api */

public final class MapViewSimplified {
    MapView mapView;

    public MapViewSimplified(MapView mapView) {
        this.mapView = mapView;
        //was not updating till i added this line
        Configuration.getInstance().setUserAgentValue("github-glenn1wang-myapp");
        //mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        //optional
        mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapView.setMultiTouchControls(true);
        setCenter(0.34759639999999997, 32.5825197);
        mapView.getController().setZoom(10d);

    }

    public final void addOverlay(Overlay overlay){
        mapView.getOverlays().add(overlay);
        invalidate();

    }

    public final void removeOverlay(Overlay overlay){
        if(overlay == null){
            return;
        }
        mapView.getOverlays().remove(overlay);
        mapView.invalidate();

    }

    private double lat;//center lat
    private double lng; //center lng
    public final void setCenter(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
        GeoPoint startPoint = new GeoPoint(lat, lng);
        mapView.getController().setCenter(startPoint);
        mapView.postInvalidate();
    }

    public final void setZoom(double zoomLevel){
        mapView.getController().setZoom(zoomLevel);
    }

    public MapView getMapView() {
        return mapView;
    }

    public void invalidate() {
        mapView.invalidate();
    }
}
