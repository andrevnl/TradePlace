package andrevictor.com.jarbas.Telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import andrevictor.com.jarbas.API.Direcoes;
import andrevictor.com.jarbas.API.Utils;
import andrevictor.com.jarbas.ClassesDiversas.AdapterListView;
import andrevictor.com.jarbas.ClassesDiversas.ItemListView;
import andrevictor.com.jarbas.R;

public class TelaRota extends AppCompatActivity implements AdapterView.OnItemClickListener,OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {

    //Array que copulam a listview
    public static ArrayList<String> lugares;
    static ArrayAdapter arrayAdapter;
    private Polyline polyline;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;
    public static ArrayList<LatLng> listlatlong;

    String origem = "-23.545991,%20-46.913044";    // "-25.443195,%20-49.280977";
    String destino = "-23.536249,%20-46.646157";   //"-25.442207,%20-49.278403";

    //Componentes
    private GoogleMap mMap;
    private ProgressDialog load;
    ListView listaPrincipal;
    FloatingActionButton btnFinalizarRota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentPrincipal);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int PosicaoList = bundle.getInt("posicao");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Botao flutuante - Finalizar rota
        btnFinalizarRota = (FloatingActionButton) findViewById(R.id.fabConcluir);
        btnFinalizarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Finalizar rota", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Intent intent = new Intent(getApplicationContext(), Mainvity.class);
                // Bundle bundle = new Bundle();
                // bundle.putInt("posicao",position);
                // intent.putExtras(bundle);
                //startActivity(intent);
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

        GetJson download = new GetJson();

        listaPrincipal = (ListView) findViewById(R.id.ListPrincipal);

        listaPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TelaRotaListViewCompleto.class);
                //Bundle bundle = new Bundle();
                //bundle.putInt("posicao",position);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //Isso aqui vai ser preenchido dentro de um for pelo json

        lugares = new ArrayList<>();

        createListView();
        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lugares);
        //listaPrincipal.setAdapter(arrayAdapter);

        //Chama Async Task
        download.execute();
    }

    private void createListView()
    {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();

        int contador = 0;


        for ( contador = 0; contador < lugares.size(); contador++){
            ItemListView item = new ItemListView(lugares.get(contador), R.drawable.ic_caminhar_mm);
            itens.add(item);
        }

        //Cria o adapter
        adapterListView = new AdapterListView(this, itens);

        //Define o Adapter
        listaPrincipal.setAdapter(adapterListView);
        //Cor quando a lista é selecionada para ralagem.
        listaPrincipal.setCacheColorHint(Color.TRANSPARENT);
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
            //Colocar metodo para voltar para o login e senha
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Fim dos metodos que tem a ver com o menu lateral
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mMap.setMyLocationEnabled(true); //Essa e a linha caso precise usar a localizacao
        mMap.setTrafficEnabled(false); //Tira os botoes de baixo no mapa

        //Descobrir a rota (exemplo do livro)
        //Esse exemplo abre o google maps para mostrar as rotas e direcoes, bem legal mas nao serve pra gente
        /*
        String origem = "-25.443195, -49.280977";
        String destino = "-25.442207, -49.278403";
        String url = "http://maps.google.com/maps?f=d&saddr="+origem+"&daddr="+destino+"&hl=pt";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        */

        double a = -23.545991;
        double b = -46.913044;
        double c = -23.536249;
        double d = -46.646157;


        // Ponto A
        LatLng pontoA = new LatLng(a,b); //-25.443195, -49.280977);
        LatLng pontoB = new LatLng(c,d); //-25.442207, -49.278403);
        LatLng pontoC = new LatLng(-23.522567, -46.753754); //-25.442207, -49.278403);


        //mMap.addMarker(new MarkerOptions().position(pontoA).title("Ponto A"));

        //Dar zoom no mapa
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pontoC,10));

        listlatlong = new ArrayList<LatLng>();

       // listlatlong.add(pontoA);
       // listlatlong.add(pontoB);

        mMap.addMarker(new MarkerOptions().title("teste").snippet("teste1").position(pontoA));
        mMap.addMarker(new MarkerOptions().title("teste").snippet("teste1").position(pontoB));

        //adicionaPolyline(mMap,pontoA,pontoB);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    private class GetJson extends AsyncTask<Void, Void, Direcoes> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(TelaRota.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected Direcoes doInBackground(Void... params) {
            Utils util = new Utils();
            //return util.getInformacao("https://maps.googleapis.com/maps/api/directions/json?origin=-23.545991,%20-46.913044&destination=-23.536249,%20-46.646157&mode=transit&key=AIzaSyDe7az8c6z4jO2MQzuJLoG1gq2WpHATomc");
            return util.getInformacao("https://maps.googleapis.com/maps/api/directions/json?origin="+origem+"&destination="+destino+"&mode=transit&language=pt-br&key=AIzaSyDe7az8c6z4jO2MQzuJLoG1gq2WpHATomc");
        }

        @Override
        protected void onPostExecute(Direcoes direcoes){


            //lugares.add(direcoes.getNomeLocal());
            lugares.add(direcoes.getPreco());
            //listaPrincipal.setAdapter(arrayAdapter);
            createListView();

            drawRoute();
            load.dismiss();

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(getApplicationContext(), TelaRotaMapaCompleto.class);
                    startActivity(intent);
                }
            });
        }
    }

    //Metodo para desenhar no mapa
    public void drawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();
            for(int i = 0, tam = listlatlong.size(); i < tam; i++){
                po.add(listlatlong.get(i));
            }

            po.color(Color.BLACK).width(4);
            polyline = mMap.addPolyline(po);
        } else {
            polyline.setPoints(listlatlong);
        }
    }
}
