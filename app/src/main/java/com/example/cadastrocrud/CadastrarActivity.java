package com.example.cadastrocrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

public class CadastrarActivity extends AppCompatActivity {

    EditText Nome;
    EditText CPF;
    EditText Telefone;
    private AlunoDAO dao;
    private Aluno aluno =  null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Nome = findViewById(R.id.editNome);
        CPF = findViewById(R.id.editCPF);
        Telefone = findViewById(R.id.editTelefone);
        dao = new AlunoDAO(this);

        Intent it = getIntent();

        if(it.hasExtra("aluno")){
            aluno = (Aluno) it.getSerializableExtra("aluno");
            Nome.setText(aluno.getNome());
            CPF.setText(aluno.getCPF());
            Telefone.setText(aluno.getTelefone());

        }

    }

    public void salvar(View view){
        if (aluno == null){
            aluno = new Aluno();

            aluno.setNome(Nome.getText().toString());
            aluno.setCPF(CPF.getText().toString());
            aluno.setTelefone(Telefone.getText().toString());

                long id = dao.inserir(aluno);
                Toast.makeText(this,"Salvando..." , Toast.LENGTH_SHORT).show();

                finish();

    }
        else{
        aluno.setNome(Nome.getText().toString());
        aluno.setCPF(CPF.getText().toString());
        aluno.setTelefone(Telefone.getText().toString());
        dao.atualizar(aluno);
        Toast.makeText(this,"O aluno foi atualizado" , Toast.LENGTH_SHORT).show();
        finish();
    }
    }

    public void canclar(View view){
        finish();
    }

    public void verificaCampo(View view){
        if(Nome.getText().toString().equals("")){
            Toast.makeText(this,"Campo Nome Está vazio" , Toast.LENGTH_LONG).show();
            Nome.requestFocus();
        }else{
            salvar(view);
        }
    }

}
