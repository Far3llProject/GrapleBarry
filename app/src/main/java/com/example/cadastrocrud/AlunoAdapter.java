package com.example.cadastrocrud;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AlunoAdapter extends BaseAdapter {
    private List<Aluno> alunos;
    private Activity activity;


    public AlunoAdapter(Activity activity, List<Aluno> alunos) {
       this.activity = activity;
       this.alunos = alunos;

    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        TextView nome = v.findViewById(R.id.txt_Nome);
        TextView CPF = v.findViewById(R.id.txt_CPF);
        TextView Telefone = v.findViewById(R.id.txt_Telefone);
        Aluno a = alunos.get(position);
        nome.setText(a.getNome());
        CPF.setText(a.getCPF());
        Telefone.setText(a.getTelefone());

        return v;
    }
}
