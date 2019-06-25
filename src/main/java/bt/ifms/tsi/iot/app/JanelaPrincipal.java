package bt.ifms.tsi.iot.app;

import br.ifms.tsi.iot.model.Comandos;
import br.ifms.tsi.iot.util.RXTX;
import com.google.gson.Gson;
import gnu.io.CommPortIdentifier;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.swing.JOptionPane;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class JanelaPrincipal extends javax.swing.JFrame {

    private Session session;
    private static Gson gson = new Gson();
    private static RXTX arduino;

    public JanelaPrincipal() {
        initComponents();
        conectarComArduino();
        conectarComWebSocket();
    }

    private void conectarComArduino() {
        List<CommPortIdentifier> list = RXTX.listarPortasSeriais();
        if (list.size() > 0) {
            CommPortIdentifier porta = list.get(0);
            try {
                arduino = new RXTX(porta, 9600);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void conectarComWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(EndPoint.class, URI.create("ws://i9.kinghost.net:21210/iot/websocket"));

        } catch (DeploymentException ex) {
            JOptionPane.showMessageDialog(null, "Deploy: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @ClientEndpoint
    public static class EndPoint {

        @OnOpen
        public void quandoConectar(Session session) {
            System.out.println("Conectado com o servidor WebSocket");
        }

        @OnMessage
        public void quandoChegarMensagem(String mensagem, Session session) throws IOException {
            Comandos cmd = gson.fromJson(mensagem, Comandos.class);
            arduino.enviarDados(cmd.getVentilador());
            arduino.enviarDados(cmd.getSala());
            arduino.enviarDados(cmd.getQuarto());
            arduino.enviarDados(cmd.getTv());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
