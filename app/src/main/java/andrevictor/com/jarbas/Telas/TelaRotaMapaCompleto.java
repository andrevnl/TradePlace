package andrevictor.com.jarbas.Telas;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import andrevictor.com.jarbas.R;

import static andrevictor.com.jarbas.Telas.TelaRota.listlatlong;

public class TelaRotaMapaCompleto extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Polyline polyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotamapacompleto);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double a = -23.545991;
        double b = -46.913044;
        double c = -23.536249;
        double d = -46.646157;

        // Ponto A
        LatLng pontoA = new LatLng(a,b); //-25.443195, -49.280977);
        LatLng pontoB = new LatLng(c,d); //-25.442207, -49.278403);
        LatLng pontoC = new LatLng(-23.522567, -46.753754); //-25.442207, -49.278403);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pontoC,10));

        mMap.addMarker(new MarkerOptions().title("teste").snippet("teste1").position(pontoA));
        mMap.addMarker(new MarkerOptions().title("teste").snippet("teste1").position(pontoB));

        drawRoute();

    }

    //Metodo para desenhar no mapa
    public void drawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();
            for(int i = 0, tam = listlatlong.size(); i < tam; i++){
                po.add(listlatlong.get(i));
                Log.i("listlatlongCompleto",""+listlatlong.get(1));
            }

            po.color(Color.BLACK).width(4);
            polyline = mMap.addPolyline(po);
        } else {
            polyline.setPoints(listlatlong);
        }
    }
}
