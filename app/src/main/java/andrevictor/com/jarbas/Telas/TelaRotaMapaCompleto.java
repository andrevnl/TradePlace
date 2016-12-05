package andrevictor.com.jarbas.Telas;

import android.content.Intent;
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

import static andrevictor.com.jarbas.API.Utils.listlatlong;

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

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        // Ponto A
        LatLng pontoA = new LatLng(bundle.getDouble("mLat1") , bundle.getDouble("mLng1")); //-25.443195, -49.280977);
        LatLng pontoB = new LatLng(bundle.getDouble("mLat2"),bundle.getDouble("mLng2")); //-25.442207, -49.278403);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pontoA,13));

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

            po.color(Color.rgb(36,166,154)).width(8);
            polyline = mMap.addPolyline(po);
        } else {
            polyline.setPoints(listlatlong);
        }
    }
}
