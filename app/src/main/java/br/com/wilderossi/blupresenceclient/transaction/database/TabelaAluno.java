package br.com.wilderossi.blupresenceclient.transaction.database;

import android.database.sqlite.SQLiteDatabase;

public class TabelaAluno implements TableCreator {

    public static final String TABELA = "alunos";

    public static final String ID       = "id";
    public static final String SERVERID = "serverId";
    public static final String URL      = "url";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + SERVERID + " text, "
                + URL + " text"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
