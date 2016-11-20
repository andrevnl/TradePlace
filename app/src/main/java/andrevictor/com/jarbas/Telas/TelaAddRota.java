package andrevictor.com.jarbas.Telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import andrevictor.com.jarbas.R;

import static andrevictor.com.jarbas.Telas.TelaPrincipal.arrayAdapter;
import static andrevictor.com.jarbas.Telas.TelaPrincipal.locais;

public class TelaAddRota extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton btnAddRota;
    EditText localizacaoAtual;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        localizacaoAtual = (EditText) findViewById(R.id.editText);
        localizacaoAtual.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.spinner);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,locais);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        //Botao flutuante - Add rota
        btnAddRota = (FloatingActionButton) findViewById(R.id.fabAdd);
        btnAddRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar rota", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Intent intent = new Intent(getApplicationContext(), Mtivity.class);
                // Bundle bundle = new Bundle();
                // bundle.putInt("posicao",position);
                // intent.putExtras(bundle);
                //startActivity(intent);
            }
        });
    }

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                        Toast.makeText(TelaAddRota.this, "E-mail enviado",
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
}
