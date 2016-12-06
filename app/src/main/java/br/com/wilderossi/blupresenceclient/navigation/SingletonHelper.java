package br.com.wilderossi.blupresenceclient.navigation;

import android.bluetooth.BluetoothAdapter;

import java.util.UUID;

import br.com.wilderossi.blupresenceclient.ProcurarConexoesListActivity;
import br.com.wilderossi.blupresenceclient.api.BaseApi;

public class SingletonHelper {

    public static BluetoothAdapter bluetoothAdapter;
    public static ProcurarConexoesListActivity procurarConexoesListActivity;
    public static String urlInstituicao;
    public static String serverIdAluno;
    public static Boolean threadAwaits;
    public static final UUID APP_UUID = UUID.fromString("9c2907f9-7ab7-42a9-9130-41ba83ec64ec");
}
