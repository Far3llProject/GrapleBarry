package com.example.cadastrocrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ListarAlunosActivity extends AppCompatActivity {

     ListView listView;
     AlunoDAO dao;
     List<Aluno> Alunos;
     List<Aluno> AlunosFiltrados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        listView = findViewById(R.id.lista_Alunos);
        dao = new AlunoDAO(this);

        Alunos = dao.obterTodos();
        AlunosFiltrados.addAll(Alunos);

       // ArrayAdapter<Aluno> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AlunosFiltrados);
        AlunoAdapter adp = new AlunoAdapter(this, AlunosFiltrados);
        listView.setAdapter(adp);

        registerForContextMenu(listView);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraAluno(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void procuraAluno(String nome){
    AlunosFiltrados.clear();
    for (Aluno a : Alunos){
        if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
            AlunosFiltrados.add(a);
        }
    }
    listView.invalidateViews();
    }

    public void excluir (MenuItem item){

        //SABER QUAL ITEM FOI SELECIONADO
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoExcluir = AlunosFiltrados.get(menuInfo.position);

        //DIALOGO DE CONFIMAÇÃO
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir esse item?")
                .setNegativeButton("NÂO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlunosFiltrados.remove(alunoExcluir);
                        Alunos.remove(alunoExcluir);
                        dao.excluir(alunoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    public  void cadastrar(MenuItem item){
        Intent it = new Intent(this, CadastrarActivity.class);
        startActivity(it);
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = AlunosFiltrados.get(menuInfo.position);

        Intent it = new Intent(this, CadastrarActivity.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);

    }

    @Override
    public void onResume(){
        super.onResume();
        Alunos = dao.obterTodos();
        AlunosFiltrados.clear();
        AlunosFiltrados.addAll(Alunos);
        listView.invalidateViews();
    }
}
