package br.com.wilderossi.blupresenceclient.components;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;

public class BluetoothSocketConnector implements Runnable {

    private final BluetoothSocket socket;

    public BluetoothSocketConnector(BluetoothSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            socket.connect();
        } catch (IOException connectException) {
            Log.e("ERRO: ", connectException.getMessage());
            SingletonHelper.procurarConexoesListActivity.showConnectionErrorDialog();
            try {
                socket.close();
            }
            catch (IOException closeException) { }
            return;
        }
        new BluetoothTransactionHandler(socket).run();
    }

    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }
}
