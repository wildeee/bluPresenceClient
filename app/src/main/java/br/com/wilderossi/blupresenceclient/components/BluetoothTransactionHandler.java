package br.com.wilderossi.blupresenceclient.components;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;

public class BluetoothTransactionHandler implements Runnable {

    private final BluetoothSocket socket;

    private final String REQUEST_URL = "REQUEST_URL";

    public BluetoothTransactionHandler(BluetoothSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            handleRequest(input, output);
        } catch (IOException e) {
            Log.e("BluetoothTransaction", e.getMessage());
        }
    }

    private void handleRequest(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        output.write(REQUEST_URL.getBytes());
        input.read(buffer);
        String urlInstituicao = new String(getFilledBuffer(buffer));
        SingletonHelper.urlInstituicao = urlInstituicao;
    }

    private byte[] getFilledBuffer(byte[] buffer) {
        int size = 0;
        for (byte b : buffer){
            if (b == 0){
                break;
            }
            size++;
        }
        byte[] ret = new byte[size];
        for (int i = 0; i < size; i++){
            ret[i] = buffer[i];
        }
        return ret;
    }
}
