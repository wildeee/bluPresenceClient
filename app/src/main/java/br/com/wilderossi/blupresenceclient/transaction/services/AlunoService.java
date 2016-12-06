package br.com.wilderossi.blupresenceclient.transaction.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresenceclient.transaction.Aluno;
import br.com.wilderossi.blupresenceclient.transaction.database.CriaBanco;
import br.com.wilderossi.blupresenceclient.transaction.database.TabelaAluno;

public class AlunoService {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public AlunoService(Context context) {
        banco = new CriaBanco(context);
    }

    public Boolean salvar(Aluno aluno) {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaAluno.SERVERID, aluno.getServerId());
        valores.put(TabelaAluno.URL, aluno.getUrl());

        if (aluno.getId() != null && aluno.getId() != 0){
            String where = TabelaAluno.ID + " = " + aluno.getId();
            resultado = db.update(TabelaAluno.TABELA, valores, where, null);
        } else {
            resultado = db.insert(TabelaAluno.TABELA, null, valores);
        }

        db.close();

        return resultado != -1;
    }

    public boolean remover(Integer id){
        String where = TabelaAluno.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaAluno.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Aluno> buscar(){
        Cursor dados;
        List<Aluno> alunos = new ArrayList<>();
        String[] campos =  {TabelaAluno.ID, TabelaAluno.SERVERID, TabelaAluno.URL};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaAluno.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                alunos.add(new Aluno(dados.getLong(0), dados.getString(1), dados.getString(2)));
            } while (dados.moveToNext());
        }

        db.close();
        return alunos;
    }

    public Aluno findByUrl(String urlInstituicao) {
        for (Aluno aluno : this.buscar()){
            if (urlInstituicao.equals(aluno.getUrl())){
                return aluno;
            }
        }
        return null;
    }
}
