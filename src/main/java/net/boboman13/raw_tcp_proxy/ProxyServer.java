package net.boboman13.raw_tcp_proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author boboman13
 * @author Austin Bolstridge
 */
public class ProxyServer implements Runnable {

    private Proxy proxy;
    private ServerSocket server;

    /**
     * Creates the ProxyServer.
     *
     * @param proxy
     */
    public ProxyServer(Proxy proxy) {
        this.proxy = proxy;
    }

    public void run() {
        try {
            proxy.debug("Using address: "+proxy.getListeningIP());
            InetAddress address = InetAddress.getByName(proxy.getListeningIP());
            server = new ServerSocket(proxy.getListeningPort(), 10, address);

            // Keep listening for new clients!
            while(true) {
                Socket client = server.accept();

                this.proxy.debug("Accepted new client: "+client.getInetAddress().getHostAddress());

                RegistryEntry registry = new RegistryEntry(this.proxy, client);

                // Start the thread.
                Thread thread = new Thread(registry);
                thread.setName("Client at address " + client.getInetAddress().toString());
                thread.start();
            }
        } catch (IOException ex) {
            // Bad boy, we got an error.
            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE,
                    "An exception was thrown", ex);
        }
    }

}