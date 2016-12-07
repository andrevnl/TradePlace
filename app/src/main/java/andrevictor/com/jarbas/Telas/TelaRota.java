package andrevictor.com.jarbas.Telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import andrevictor.com.jarbas.API.Direcoes;
import andrevictor.com.jarbas.API.Utils;
import andrevictor.com.jarbas.ClassesDiversas.AdapterListViewPrincipal;
import andrevictor.com.jarbas.ClassesDiversas.AdapterListViewRota;
import andrevictor.com.jarbas.ClassesDiversas.ItemListView;
import andrevictor.com.jarbas.R;

import static andrevictor.com.jarbas.API.Utils.EnderecoMercado;
import static andrevictor.com.jarbas.API.Utils.LatitudeMercado;
import static andrevictor.com.jarbas.API.Utils.LongitudeMercado;
import static andrevictor.com.jarbas.API.Utils.controlaIntrucoesRota;
import static andrevictor.com.jarbas.API.Utils.controlaPolilynesRota;
import static andrevictor.com.jarbas.API.Utils.enderecoPromotor;
import static andrevictor.com.jarbas.API.Utils.latitudePromotor;
import static andrevictor.com.jarbas.API.Utils.listlatlong;
import static andrevictor.com.jarbas.API.Utils.locais;
import static andrevictor.com.jarbas.API.Utils.longitudePromotor;
import static andrevictor.com.jarbas.API.Utils.lugares;
import static andrevictor.com.jarbas.API.Utils.nomePromotor;
import static andrevictor.com.jarbas.API.Utils.polilynesRota;
import static andrevictor.com.jarbas.API.Utils.precoRota;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.adapterListView;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.itens;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.letrasAtivas;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.letrasVisitadas;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.linhaAtiva;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.listaPrincipal;

public class TelaRota extends AppCompatActivity implements AdapterView.OnItemClickListener,OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {

    //Array que copulam a listview

    private Polyline polyline;
    private AdapterListViewRota adapterListViewRota;
    private ArrayList<ItemListView> itensRota;
    private int controleInstrucoes;

    //-23.546161, -46.913081 Casa
    //-23.536544, -46.646308 Senai

    String origem = "-23.546161,%20-46.913081";    // "-25.443195,%20-49.280977";
    String destino = "-23.536544,%20-46.646308";   //"-25.442207,%20-49.278403";

    int PosicaoList;

    //Componentes
    private GoogleMap mMap;
    ListView listaRota;
    FloatingActionButton btnFinalizarRota;
    TextView textPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentPrincipal);
        mapFragment.getMapAsync(this);

        //Obtem a posicao do item clicado na lista da tela anterior
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        PosicaoList = bundle.getInt("posicao");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textPreco = (TextView) findViewById(R.id.textPreco);

        textPreco.setText("VALOR DA VIAGEM: R$0,00");


        //Botao flutuante - Finalizar rota
        btnFinalizarRota = (FloatingActionButton) findViewById(R.id.fabConcluir);
        btnFinalizarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linhaAtiva == PosicaoList) {
                    //Troca o icone,cor do texto e cor da linha ativa
                    itens.get(linhaAtiva).setIconeRid(letrasVisitadas.get(linhaAtiva));
                    itens.get(linhaAtiva).setColor("#EBEBEB");
                    itens.get(linhaAtiva).setCorLinha("#A4A4A4");
                    //Troca o icone, cor do texto e cor da linha da proxima
                    try{
                        itens.get(linhaAtiva + 1).setIconeRid(letrasAtivas.get(linhaAtiva + 1));
                        itens.get(linhaAtiva + 1).setColor("#26A79B");
                        itens.get(linhaAtiva + 1).setCorLinha("#FFFFFF");
                    }catch (IndexOutOfBoundsException e){

                    }
                    /////////////////////////////////////////////////////////
                    listaPrincipal.setAdapter(adapterListView);
                    linhaAtiva++;
                    finish();
                }else{
                    finish();
                }
            }
        });

        //Comeco da parte que mexe com menu lateral
        //////////////////////////////////////////////////////////////////////////////
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Fim da parte que mexe com menu lateral
        //////////////////////////////////////////////////////////////////////////////


        listaRota = (ListView) findViewById(R.id.ListPrincipal);

        listaRota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TelaRotaListViewCompleto.class);
                startActivity(intent);
            }
        });

        createListView();



    }

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itensRota = new ArrayList<ItemListView>();

        int contador = 0;

        if(linhaAtiva > 0 && linhaAtiva == PosicaoList){
        for (contador = controlaIntrucoesRota.get(linhaAtiva-1); contador < controlaIntrucoesRota.get(linhaAtiva); contador++){
            ItemListView item = new ItemListView(lugares.get(contador), R.drawable.ic_caminhar_g);
            itensRota.add(item);
        }
        }else {
            for (contador = 0; contador < controlaIntrucoesRota.get(linhaAtiva); contador++) {
                ItemListView item = new ItemListView(lugares.get(contador), R.drawable.ic_caminhar_g);
                itensRota.add(item);
            }
        }

        //Cria o adapter
        adapterListViewRota = new AdapterListViewRota(this, itensRota);

        //Define o Adapter
        listaRota.setAdapter(adapterListViewRota);
    }

    //Todos os metodos abaixo tem a ver com o menu lateral
    /////////////////////////////////////////////////////////////////////////////////
    //Faz aparecer o menu passando o dedo para o lado
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_principal) {
            finish();
        } else if (id == R.id.nav_rotas) {
            HistoricoDeRotas();
        } else if (id == R.id.nav_perfil) {
            //Vai pro perfil
            Intent intent = new Intent(getApplicationContext(), TelaPerfil.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_informacoes) {
            //Vai pro informacoes
            Intent intent = new Intent(getApplicationContext(), TelaInformacoes.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_sair) {
            //Vai pra tela de login
            //Intent intent = new Intent(getApplicationContext(), TelaLogin.class);
            //startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Fim dos metodos que tem a ver com o menu lateral
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //Metodo para desenhar no mapa
    public void drawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();
            for(int i = 0, tam = listlatlong.size(); i < tam; i++){
                po.add(listlatlong.get(i));
            }

            po.color(Color.rgb(36,166,154)).width(8);
            polyline = mMap.addPolyline(po);
        } else {
            polyline.setPoints(listlatlong);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        textPreco.setText("VALOR DA VIAGEM: R$"+ new DecimalFormat("#0.00").format(precoRota.get(linhaAtiva)));

        mMap = googleMap;

        //mMap.setMyLocationEnabled(true); //Essa e a linha caso precise usar a localizacao
        mMap.setTrafficEnabled(false); //Tira os botoes de baixo no mapa

        LatLng pontoA;

        //Marcador
        if(linhaAtiva == 0){
            pontoA = new LatLng(latitudePromotor,longitudePromotor);
            mMap.addMarker(new MarkerOptions().title(locais.get(linhaAtiva)).snippet(EnderecoMercado.get(linhaAtiva)).position(pontoA));
        }else {
            pontoA = new LatLng(LatitudeMercado.get(linhaAtiva-1), LongitudeMercado.get(linhaAtiva-1));
            mMap.addMarker(new MarkerOptions().title(locais.get(linhaAtiva-1)).snippet(EnderecoMercado.get(linhaAtiva-1)).position(pontoA));
        }

        LatLng pontoB;

        if(linhaAtiva < locais.size() && linhaAtiva == 0) {
            pontoB = new LatLng(LatitudeMercado.get(linhaAtiva), LongitudeMercado.get(linhaAtiva)); //direcoes.getLatitudeMercado(),direcoes.getLongitudeMercado());
            mMap.addMarker(new MarkerOptions().title(nomePromotor).snippet(enderecoPromotor).position(pontoB));
        }else if(linhaAtiva < locais.size()) {
            pontoB = new LatLng(LatitudeMercado.get(linhaAtiva), LongitudeMercado.get(linhaAtiva)); //direcoes.getLatitudeMercado(),direcoes.getLongitudeMercado());
            mMap.addMarker(new MarkerOptions().title(locais.get(linhaAtiva)).snippet(EnderecoMercado.get(linhaAtiva)).position(pontoB));
        }else{
            pontoB = new LatLng(latitudePromotor, longitudePromotor); //direcoes.getLatitudeMercado(),direcoes.getLongitudeMercado());
            mMap.addMarker(new MarkerOptions().title(nomePromotor).snippet(enderecoPromotor).position(pontoB));
        }

        if(linhaAtiva == 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pontoB, 13));
        }else{
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pontoA, 13));
        }

        int contador2 = 0;

        listlatlong = new ArrayList<>();

        if(linhaAtiva > 0 && linhaAtiva == PosicaoList){
            for (contador2 = controlaPolilynesRota.get(linhaAtiva-1); contador2 < controlaPolilynesRota.get(linhaAtiva); contador2++){
                for(LatLng p : decodePolyline(polilynesRota.get(contador2))){
                    listlatlong.add(p);
                }
            }
        }else {
            for (contador2 = 0; contador2 < controlaPolilynesRota.get(linhaAtiva); contador2++) {
                for(LatLng p : decodePolyline(polilynesRota.get(contador2))){
                    listlatlong.add(p);
                }
            }
        }

        //        LatLngBounds bounds = new LatLngBounds.Builder()
        //              .include(pontoA)
        //            .include(pontoB)
        //          .build();


        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds,50));

        drawRoute();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Bundle bundle = new Bundle();
                //  bundle.putDouble("mLat1", direcoes.getLatitudePromotor());
                //  bundle.putDouble("mLng1", direcoes.getLongitudePromotor());
                //  bundle.putDouble("mLat2", direcoes.getLatitudeMercado());
                //  bundle.putDouble("mLng2", direcoes.getLongitudeMercado());
                Intent intent = new Intent(getApplicationContext(), TelaRotaMapaCompleto.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    // DECODE POLYLINE
    private List<LatLng> decodePolyline(String encoded) {

        List<LatLng> listPoints = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            Log.i("Script", "POL: LAT: "+p.latitude+" | LNG: "+p.longitude);
            listPoints.add(p);
        }
        return listPoints;
    }


    public void HistoricoDeRotas(){
        //Alerta do historico de rotas
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Historico de rotas")
                .setMessage("Enviar historico por e-mail?")
                .setIcon(R.drawable.ic_history_black_24dp)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TelaRota.this, "E-mail enviado",
                                Toast.LENGTH_SHORT).show();
                        //Colocar metodo para mandar solicitação para o back mandar o e-mail
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
