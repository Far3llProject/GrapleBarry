package com.example.cadastrocrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlunoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }


    public long inserir(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("Nome", aluno.getNome());
        values.put("CPF", aluno.getCPF());
        values.put("Telefone", aluno.getTelefone());
        return banco.insert("aluno", null, values);

    }


    public List<Aluno> obterTodos(){
        Aluno aluno;
        List<Aluno> alunos = new ArrayList<>();

        Cursor cursor = banco.query("aluno", new String[]{"id","Nome","CPF","Telefone"},null,
                null,null,null,null);
        cursor.moveToFirst();

    while (!cursor.isAfterLast()){
        aluno = new Aluno();
        aluno.setId(cursor.getInt(0));
        aluno.setNome(cursor.getString(1));
        aluno.setCPF(cursor.getString(2));
        aluno.setTelefone(cursor.getString(3));
        alunos.add(aluno);
        cursor.moveToNext();
    }
        return alunos;
    }


    public void excluir(Aluno a){
        banco.delete("aluno", "id = ?", new String[]{String.valueOf(a.getId())});

    }

    public void atualizar (Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("Nome", aluno.getNome());
        values.put("CPF", aluno.getCPF());
        values.put("Telefone", aluno.getTelefone());
        banco.update("aluno", values, "id = ?", new String[]{String.valueOf(aluno.getId())});


    }
}

