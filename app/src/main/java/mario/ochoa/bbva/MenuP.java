package mario.ochoa.bbva;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MenuP extends AppCompatActivity {

    private TextView tvEmail;

    Button idEncender_Aux, idApagar, idDesconectar;
    TextView idBufferIn;
    //////////////////////////////////
    Handler bluetoothIn;
    final int HandlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    //private ConnectedThread  MyconexionBT;

    private static final UUID BTMODULEUUID = UUID.fromString("");
    public static String address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_p);

        tvEmail = (TextView)findViewById(R.id.txtEmailMenu);
        tvEmail.setText(getIntent().getExtras().getString("Email"));

        idEncender_Aux = (Button)findViewById(R.id.idEncender);
        idApagar = (Button)findViewById(R.id.idApagar);
        idDesconectar = (Button)findViewById(R.id.idDesconectar);
        idBufferIn = (TextView)findViewById(R.id.idBufferIn);

        //bluetoothIn = (Handler)

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        VerificarEstadoBT();

        idEncender_Aux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        idApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        idDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSocket!=null){

                    try{btSocket.close();}
                    catch (IOException e){
                        Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    private BluetoothSocket createBluetoothSocket (BluetoothDevice device) throws IOException{
        return device.createInsecureRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();

        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADRESS);

        BluetoothDevice device  = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        }catch (IOException e){
            Toast.makeText(getBaseContext(), " La creacion del socket fallo", Toast.LENGTH_SHORT).show();
        }
        try {
            btSocket.connect();
        }catch (IOException e ){
            try {
                btSocket.close();
            }catch (IOException e2){}
        }
        //MyconexionBT = new ConnectedThread(btSocket);
        //MyconexionBT.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        try{
            btSocket.close();
        }catch (IOException e2){}
    }

    private void VerificarEstadoBT(){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter==null){
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }else {
            if (btAdapter.isEnabled()){
            }else {
                Intent enableBtInent  = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtInent,1);
            }
        }
    }

    /*private class ConnectedThread extends Thread{
        private final InputStream mmInStream;
        private final OutputStream mmOutput;
    }*/
}
