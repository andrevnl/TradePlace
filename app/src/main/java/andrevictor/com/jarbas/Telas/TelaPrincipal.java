package andrevictor.com.jarbas.Telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import andrevictor.com.jarbas.ClassesDiversas.AdapterListView;
import andrevictor.com.jarbas.ClassesDiversas.ItemListView;
import andrevictor.com.jarbas.ClassesDiversas.PermissionUtils;
import andrevictor.com.jarbas.ClassesDiversas.TypefaceUtil;
import andrevictor.com.jarbas.R;

public class TelaPrincipal extends AppCompatActivity
        implements AdapterView.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    //Array que copulam a listview (verificar se vai precisar de todos)
    public static ArrayList<String> locais;
    public static AdapterListView adapterListView;
    private ArrayList<Integer> letrasDesativadas;
    public static ArrayList<Integer> letrasAtivas;
    public static ArrayList<Integer> letrasVisitadas;
    public static ArrayList<ItemListView> itens;
    private ItemListView item;

    int contador = 0;
    public static int linhaAtiva = 0;

    //Componentes
    GoogleMap mapa;
    public static ListView listaPrincipal;
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

        //Muda a fonte padrão
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(getApplicationContext(), "monospace", "font/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(getApplicationContext(), "normal", "font/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Chama a classe de permissoes para validacao
        PermissionUtils.validate(this,0,permissoes);

        //Parte da declaracao dos elementos da tela
        ///////////////////////////////////////////////////////////////////////////////
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //ListView com os locais que precisam ser visitados
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
                Intent intent = new Intent(getApplicationContext(), TelaAddRota.class);
                startActivity(intent);
            }
        });
        //Fim da parte de criar elementos da tela
        ///////////////////////////////////////////////////////////////////////////////

        //Copula o array e joga na listview
        ///////////////////////////////////////////////////////////////////////////////
        locais = new ArrayList<>();
        locais.add("EXTRA PAULISTA");
        locais.add("PÃO DE AÇÚCAR ANA ROSA");
        locais.add("CARREFOUR ANA ROSA");
        locais.add("EXTRA PAULISTA");
        locais.add("PÃO DE AÇÚCAR ANA ROSA");
        locais.add("CARREFOUR ANA ROSA");
        ///////////////////////////////////////////////////////////////////////////////
        //Copula o array e joga as imagens das letras que vao ser usadas na listview para representar ativo, desativado e ja visitado
        ///////////////////////////////////////////////////////////////////////////////
        letrasDesativadas = new ArrayList<>();
        letrasDesativadas.add(R.drawable.ic_a_desativado);
        letrasDesativadas.add(R.drawable.ic_b_desativado);
        letrasDesativadas.add(R.drawable.ic_c_desativado);
        letrasDesativadas.add(R.drawable.ic_d_desativado);
        letrasDesativadas.add(R.drawable.ic_e_desativado);
        letrasDesativadas.add(R.drawable.ic_f_desativado);
        letrasDesativadas.add(R.drawable.ic_g_desativado);
        letrasDesativadas.add(R.drawable.ic_h_desativado);
        letrasDesativadas.add(R.drawable.ic_i_desativado);
        letrasDesativadas.add(R.drawable.ic_j_desativado);
        letrasDesativadas.add(R.drawable.ic_k_desativado);
        letrasDesativadas.add(R.drawable.ic_l_desativado);
        letrasDesativadas.add(R.drawable.ic_m_desativado);
        letrasDesativadas.add(R.drawable.ic_n_desativado);
        letrasDesativadas.add(R.drawable.ic_o_desativado);
        letrasDesativadas.add(R.drawable.ic_p_desativado);
        letrasDesativadas.add(R.drawable.ic_q_desativado);
        letrasDesativadas.add(R.drawable.ic_r_desativado);
        letrasDesativadas.add(R.drawable.ic_s_desativado);
        letrasDesativadas.add(R.drawable.ic_t_desativado);
        letrasDesativadas.add(R.drawable.ic_u_desativado);
        letrasDesativadas.add(R.drawable.ic_v_desativado);
        letrasDesativadas.add(R.drawable.ic_w_desativado);
        letrasDesativadas.add(R.drawable.ic_x_desativado);
        letrasDesativadas.add(R.drawable.ic_y_desativado);
        letrasDesativadas.add(R.drawable.ic_z_desativado);

        letrasAtivas = new ArrayList<>();
        letrasAtivas.add(R.drawable.ic_a_ativo);
        letrasAtivas.add(R.drawable.ic_b_ativo);
        letrasAtivas.add(R.drawable.ic_c_ativo);
        letrasAtivas.add(R.drawable.ic_d_ativo);
        letrasAtivas.add(R.drawable.ic_e_ativo);
        letrasAtivas.add(R.drawable.ic_f_ativo);
        letrasAtivas.add(R.drawable.ic_g_ativo);
        letrasAtivas.add(R.drawable.ic_h_ativo);
        letrasAtivas.add(R.drawable.ic_i_ativo);
        letrasAtivas.add(R.drawable.ic_j_ativo);
        letrasAtivas.add(R.drawable.ic_k_ativo);
        letrasAtivas.add(R.drawable.ic_l_ativo);
        letrasAtivas.add(R.drawable.ic_m_ativo);
        letrasAtivas.add(R.drawable.ic_n_ativo);
        letrasAtivas.add(R.drawable.ic_o_ativo);
        letrasAtivas.add(R.drawable.ic_p_ativo);
        letrasAtivas.add(R.drawable.ic_q_ativo);
        letrasAtivas.add(R.drawable.ic_r_ativo);
        letrasAtivas.add(R.drawable.ic_s_ativo);
        letrasAtivas.add(R.drawable.ic_t_ativo);
        letrasAtivas.add(R.drawable.ic_u_ativo);
        letrasAtivas.add(R.drawable.ic_v_ativo);
        letrasAtivas.add(R.drawable.ic_w_ativo);
        letrasAtivas.add(R.drawable.ic_x_ativo);
        letrasAtivas.add(R.drawable.ic_y_ativo);
        letrasAtivas.add(R.drawable.ic_z_ativo);
        letrasVisitadas = new ArrayList<>();
        letrasVisitadas.add(R.drawable.ic_a_visitado);
        letrasVisitadas.add(R.drawable.ic_b_visitado);
        letrasVisitadas.add(R.drawable.ic_c_visitado);
        letrasVisitadas.add(R.drawable.ic_d_visitado);
        letrasVisitadas.add(R.drawable.ic_e_visitado);
        letrasVisitadas.add(R.drawable.ic_f_visitado);
        letrasVisitadas.add(R.drawable.ic_g_visitado);
        letrasVisitadas.add(R.drawable.ic_h_visitado);
        letrasVisitadas.add(R.drawable.ic_i_visitado);
        letrasVisitadas.add(R.drawable.ic_j_visitado);
        letrasVisitadas.add(R.drawable.ic_k_visitado);
        letrasVisitadas.add(R.drawable.ic_l_visitado);
        letrasVisitadas.add(R.drawable.ic_m_visitado);
        letrasVisitadas.add(R.drawable.ic_n_visitado);
        letrasVisitadas.add(R.drawable.ic_o_visitado);
        letrasVisitadas.add(R.drawable.ic_p_visitado);
        letrasVisitadas.add(R.drawable.ic_q_visitado);
        letrasVisitadas.add(R.drawable.ic_r_visitado);
        letrasVisitadas.add(R.drawable.ic_s_visitado);
        letrasVisitadas.add(R.drawable.ic_t_visitado);
        letrasVisitadas.add(R.drawable.ic_u_visitado);
        letrasVisitadas.add(R.drawable.ic_v_visitado);
        letrasVisitadas.add(R.drawable.ic_w_visitado);
        letrasVisitadas.add(R.drawable.ic_x_visitado);
        letrasVisitadas.add(R.drawable.ic_y_visitado);
        letrasVisitadas.add(R.drawable.ic_z_visitado);

        ////////////////////////////////////////////////////////////////////////////////



        //OnClick da lista que chama a outra tela e passa as informacoes da rota (verificar se vai ser assim mesmo)
        ///////////////////////////////////////////////////////////////////////////////
        listaPrincipal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //parent.setBackgroundColor(Color.BLUE);
                //view.setBackgroundColor(Color.BLUE);

                if(position == linhaAtiva) {
                    Intent intent = new Intent(getApplicationContext(), TelaRota.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("posicao", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    itens.get(linhaAtiva).setIconeRid(letrasAtivas.get(linhaAtiva));
                    listaPrincipal.setAdapter(adapterListView);
                    itens.get(linhaAtiva).setColor("#26A79B");
                    itens.get(linhaAtiva).setCorLinha("#FFFFFF");


                }else if(position < linhaAtiva){
                    Intent intent = new Intent(getApplicationContext(), TelaRota.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("posicao", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(TelaPrincipal.this);
                    builder.setTitle("Rota inativa").setMessage("Favor finalizar rota anterior para habilitar essa rota!").setIcon(R.drawable.ic_error_outline_black_24dp).show();
                }
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

    private void createListView() {
        //Criamos nossa lista que preenchera o ListView
        itens = new ArrayList<ItemListView>();

        for (contador = 0; contador < locais.size(); contador++){
            item = new ItemListView(locais.get(contador), letrasDesativadas.get(contador));
            itens.add(item);
        }

        //Cria o adapter
        adapterListView = new AdapterListView(this, itens);

        //Define o Adapter
        listaPrincipal.setAdapter(adapterListView);
    }

    //Parte que mexe com o mapa desde a criacao no app
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        mapa.getUiSettings().setAllGesturesEnabled(false);

        LatLng ponto = new LatLng(-23.581485,-46.638507);
        LatLng ponto2 = new LatLng(-23.581465,-46.638503);
        mapa.addMarker(new MarkerOptions().position(ponto));
        mapa.addMarker(new MarkerOptions().position(ponto2));


        //Dar zoom no mapa
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ponto,12));
    }

    //Todos os metodos abaixo tem a ver com o menu lateral
    /////////////////////////////////////////////////////////////////////////////////

    //Acao dos botoes do menu lateral aqui
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_principal) {
            //Esse já e o main então não tem nada aqui
        } else if (id == R.id.nav_rotas) {
            HistoricoDeRotas();
        } else if (id == R.id.nav_perfil) {
            //Vai pro perfil
            Intent intent = new Intent(getApplicationContext(), TelaPerfil.class);
            startActivity(intent);
        }else if (id == R.id.nav_informacoes) {
            //Vai pro informacoes
            Intent intent = new Intent(getApplicationContext(), TelaInformacoes.class);
            startActivity(intent);
        } else if (id == R.id.nav_sair) {
            //Vai pra tela de login
            Intent intent = new Intent(getApplicationContext(), TelaLogin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
    //Fim dos metodos que tem a ver com o menu lateral
    /////////////////////////////////////////////////////////////////////////////////


    //Parte que mexe com permissoes
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

    public void HistoricoDeRotas(){
        //Alerta do historico de rotas
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Historico de rotas")
                .setMessage("Enviar historico por e-mail?")
                .setIcon(R.drawable.ic_history_black_24dp)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TelaPrincipal.this, "E-mail enviado",
                                Toast.LENGTH_SHORT).show();
                        //Colocar metodo para mandar solicitação para o back mandar o e-mail
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

}
