package andrevictor.com.jarbas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    //Array que copulam a listview (verificar se vai precisar de todos)
    //static ArrayList<String> lugares;
    static ArrayList<String> latlonglocais;
    static ArrayList<String> locais;
    private AdapterListView adapterListView;
    private ArrayList<ItemListView> itens;
    static ArrayAdapter arrayAdapter;

    //Componentes
    GoogleMap mapa;
    ListView listaPrincipal;
    SupportMapFragment mapFragment;
    FloatingActionButton btnAddButton;

    //String de permissoes (obvio)
    String[] permissoes = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Muda a fonte padrão
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Chama a classe de permissoes para validacao
        PermissionUtils.validate(this,0,permissoes);

        //Parte da declaracao dos elementos da tela
        ///////////////////////////////////////////////////////////////////////////////
        //Lista com os locais que precisam ser visitados
        listaPrincipal = (ListView) findViewById(R.id.ListPrincipal);

        //Fragmento do mapa
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentPrincipal);
        mapFragment.getMapAsync(this);

        //Botao flutuante - Criar rota
        btnAddButton = (FloatingActionButton) findViewById(R.id.fab);
        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Criar nova rota", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
               // Bundle bundle = new Bundle();
               // bundle.putInt("posicao",position);
               // intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //Fim da parte de criar elementos da tela
        ///////////////////////////////////////////////////////////////////////////////

        //Por enquanto não vai usar pq no outro tem o listview com o icone e a imagem

        //Copula o array e joga na listview
        ///////////////////////////////////////////////////////////////////////////////
        locais = new ArrayList<>();
        locais.add("Rota A - Extra Paulista");
        locais.add("Rota B - Pão de açucar Ana  Rosa");
        locais.add("Rota C - Carrefour Ana Rosa");
        locais.add("Rota D - Supermercado Sol Jandira");
        locais.add("Rota E - Supermercado Japão Barueri ");
        locais.add("Rota E - Supermercado Nenhum ");

        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, locais);
        //listaPrincipal.setAdapter(arrayAdapter);
        ///////////////////////////////////////////////////////////////////////////////

        //OnClick da lista que chama a outra tela e passa as informacoes da rota (verificar se vai ser assim mesmo)
        ///////////////////////////////////////////////////////////////////////////////
        listaPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("posicao",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        ///////////////////////////////////////////////////////////////////////////////


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
        ///////////////////////////////////////////////////////////////////////////////

        createListView();
    }

    private void createListView()
    {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();

        int contador = 0;


        for ( contador = 0; contador < 6; contador++){
            ItemListView item = new ItemListView(locais.get(contador), R.drawable.ic_format_color_text_black_24dp);
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
                    //Esse já e o main então não tem nada aqui
        } else if (id == R.id.nav_rotas) {
            //Alerta do historico de rotas
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Historico de rotas")
                    .setMessage("Enviar historico por e-mail?")
                    .setIcon(R.drawable.ic_history_black_24dp)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "E-mail enviado",
                                    Toast.LENGTH_SHORT).show();
                            //Colocar metodo para mandar solicitação para o back mandar o e-mail
                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        } else if (id == R.id.nav_perfil) {
            //Vai pro perfil
            Intent intent = new Intent(getApplicationContext(), MainPerfilActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_informacoes) {
            //Vai pro informacoes
            Intent intent = new Intent(getApplicationContext(), MainInformacoesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sair) {
            //Colocar metodo para voltar para o login e senha
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Fim dos metodos que tem a ver com o menu lateral
    /////////////////////////////////////////////////////////////////////////////////


    //Parte que mexe com o mapa desde a criacao no app
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        mapa.getUiSettings().setAllGesturesEnabled(false);

        LatLng ponto = new LatLng(-23.581485,-46.638507);
        mapa.addMarker(new MarkerOptions().position(ponto));

        //Dar zoom no mapa
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ponto,18));
    }

    //Parte que mexe com permissoes (Verificar para Android 5.0 e 4.0)
    /////////////////////////////////////////////////////////////////////////////////
    //O proprio Android tem que chamar esse metodo caso precise de alguma permissao
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        for (int result : grantResults){
            if(result == PackageManager.PERMISSION_DENIED) {
                //Alguma permissao foi negada
                alertAndFinish();
                return;
            }
        }
    }

    //Se alguma permissao for negada vai vir para esse metodo
    private void alertAndFinish(){
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, voce precisa aceitar as permissoes.");
            //Adiciona os botoes
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    //Fim da parte que mexe com permissoes
    /////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
