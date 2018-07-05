package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String operator;
    private String operand1;
    private String operand2;
    private TextView resultTextView;

    private Socket socket;

    public ClientThread(String address, int port, String operator, String operand1, String operand2, TextView resultTextView) {
        this.address = address;
        this.port = port;
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.resultTextView = resultTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(operator);
            printWriter.flush();
            printWriter.println(operand1);
            printWriter.flush();
            printWriter.println(operand2);
            printWriter.flush();
            String resultInformation;
            while ((resultInformation = bufferedReader.readLine()) != null) {
                final String finalResultInformation = resultInformation;
                resultTextView.post(new Runnable() {
                   @Override
                    public void run() {
                       resultTextView.setText(finalResultInformation);
                   }
                });
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}
