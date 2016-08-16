package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.Update.ListaGrupos;
import com.darthorg.bozo.adapter.PartidasListaAdapter;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Partida;

import java.util.List;

public class ListaDePartidas extends AppCompatActivity {


    private Toolbar toolbar;
    private ListView listViewPartidas;
    private PartidasListaAdapter partidasListAdapter;
    private PartidaDAO partidaDAO;
    private List<Partida> partidaList;
    TextView contadorGrupos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_partidas);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);



        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);

        //Aparecer imagem quando a lista estiver vazia
        listViewPartidas.setEmptyView(findViewById(R.id.list_vazio));

        //botão ficar precionado
        registerForContextMenu(listViewPartidas);


        partidaDAO = new PartidaDAO(this);
        partidaList = partidaDAO.buscarPartidas();

        partidasListAdapter = new PartidasListaAdapter(getApplicationContext(), partidaList);
        listViewPartidas.setAdapter(partidasListAdapter);



        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaDePartidas.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);

                finish();
            }
        });

        //Contagem de grupos
        contadorGrupos = (TextView) findViewById(R.id.cotagemGrupos);
        if(partidaList.size() == 0){
            contadorGrupos.setText("nenhum grupo");
        }else if (partidaList.size() == 1){
            contadorGrupos.setText(partidaList.size() + " Grupo");
        }else if(partidaList.size() >= 2){
            contadorGrupos.setText(partidaList.size() + " Grupos");
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.action_apagar:

                int position = 0;
                Toast.makeText(ListaDePartidas.this, partidaList.get(position).getNome()+" - foi excluido da sua lista", Toast.LENGTH_SHORT).show();
                PartidaDAO partidaDAO = new PartidaDAO(ListaDePartidas.this);
                partidaDAO.deletarPartida(partidaList.get(position));
                partidaList.remove(position);
                partidasListAdapter.notifyDataSetChanged();
                if(partidaList.size() == 0){
                    contadorGrupos.setText(R.string.NenhumGrupo);
                }else if (partidaList.size() == 1){
                    contadorGrupos.setText(partidaList.size() + getString(R.string.Grupo));
                }else if(partidaList.size() >= 2){
                    contadorGrupos.setText(partidaList.size() + getString(R.string.Grupos));
                }
                return true;
            default:
                return super.onContextItemSelected(item);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partida_salvas_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_nova_partida) {
            Intent intent = new Intent(ListaDePartidas.this, NovaPartida.class);
            startActivity(intent);
        } else if (id == R.id.action_atualizar) {
            ListaGrupos.atualizar(this,item);

        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
