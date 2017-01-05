package br.com.wilderossi.blupresenceclient.components;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.wilderossi.blupresenceclient.AuthenticationFormActivity;
import br.com.wilderossi.blupresenceclient.navigation.SingletonHelper;
import br.com.wilderossi.blupresenceclient.transaction.Aluno;
import br.com.wilderossi.blupresenceclient.transaction.services.AlunoService;

public class BluetoothTransactionHandler {

    private final BluetoothSocket socket;
    private AlunoService alunoService;

    private final String REQUEST_URL = "REQUEST_URL";

    public BluetoothTransactionHandler(BluetoothSocket socket) {
        this.socket = socket;
    }

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

        alunoService = new AlunoService(SingletonHelper.procurarConexoesListActivity);
        Aluno alunoJaLogado = alunoService.findByUrl(urlInstituicao);
        String serverIdAluno;
        if (alunoJaLogado == null) {
            SingletonHelper.procurarConexoesListActivity.redirectTo(AuthenticationFormActivity.class);
            SingletonHelper.threadAwaits = Boolean.TRUE;
            while (SingletonHelper.threadAwaits) { }
            if (SingletonHelper.serverIdAluno == null){
                output.write("AUTH ERROR".getBytes());
                socket.close();
                return;
            }
            serverIdAluno = SingletonHelper.serverIdAluno;

            AlunoService alunoService = new AlunoService(SingletonHelper.procurarConexoesListActivity);
            Aluno aluno = new Aluno();

            aluno.setServerId(serverIdAluno);
            aluno.setUrl(urlInstituicao);

            alunoService.salvar(aluno);
        } else {
            serverIdAluno = alunoJaLogado.getServerId();
        }

        output.write(serverIdAluno.getBytes());
        buffer = new byte[1024];
        input.read(buffer);
        String response = new String(getFilledBuffer(buffer));
        SingletonHelper.procurarConexoesListActivity.threadedFinish(response);
        socket.close();
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
