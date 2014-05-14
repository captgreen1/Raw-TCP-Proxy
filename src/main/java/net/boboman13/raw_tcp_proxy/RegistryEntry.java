package net.boboman13.raw_tcp_proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * @author boboman13
 */
public class RegistryEntry implements Runnable {

    private Proxy proxy;
    private boolean isRunning = true;

    private Socket serverSocket;
    private Socket clientSocket;

    public InputStream serverIn;
    public OutputStream serverOut;

    public InputStream clientIn;
    public OutputStream clientOut;

    private SocketListener socketListener;

    /**
     * Creates a Registry instance; Registry represents a client.
     *
     * @param proxy The Proxy instance.
     * @param inSocket The socket to the client.
     * @throws IOException Upon getting an exception, the program will throw an exception.
     */
    public RegistryEntry(Proxy proxy, Socket inSocket) {
        this.proxy = proxy;

        try {
            this.serverSocket = new Socket(proxy.getHost(), proxy.getPort());
            this.clientSocket = inSocket;

            // Initiate the in and out fields.
            this.clientIn = this.clientSocket.getInputStream();
            this.clientOut = this.clientSocket.getOutputStream();

            this.serverIn = this.serverSocket.getInputStream();
            this.serverOut = this.serverSocket.getOutputStream();

        } catch (ConnectException ex) {
            proxy.debug("Received connection error while creating Socket to outsource.");
            // do nothing, kill process.
            this.kill();
            return;
        } catch (IOException ex) {
            // do nothing, kill process.
            this.kill();
            return;
        }


        // Start up the SocketListener.
        this.socketListener = new SocketListener(this);
        Thread thread = new Thread(this.socketListener);
        thread.start();
    }

    /**
     * Ran as a separate thread.
     */
    public void run() {
        try {
            String line = null;

            final byte[] request = new byte[1024];

            int bytesRead;

            // Try and read lines from the client.
            while (isRunning && (bytesRead = this.clientIn.read(request)) != -1) {
                this.serverOut.write(request, 0, bytesRead);
                this.serverOut.flush();

                // It may seem obscure to surround it by an if loop, but 
                //otherwise we are forced to create a new String object; 
                //the parsing to UTF-8 causes some speed issues.
                if(this.getProxy().getDebug()) {
                    this.getProxy().debug("C -> S: " + new String(request, "UTF-8"));
                }
            }

            // Client disconnected.
            this.kill();

        } catch (IOException ex) {
            if(ex instanceof SocketTimeoutException) {
                // The socket simply timed out. Kill, then exit.
                this.kill();
                return;
            }

            //ex.printStackTrace();
            this.kill();
        }
    }

    /**
     * Gets the Proxy of the Registry.
     *
     * @return proxy
     */
    public Proxy getProxy() {
        return this.proxy;
    }
    
    /**
     * Gets the socket that is connected to the server.
     * @return A <code>Socket</code> that is connected to the server specified
     * in the <code>Proxy</code>
     */
    public Socket getServerSocket() {
        return this.serverSocket;
    }
    
    /**
     * Gets the socket that is connected to the client
     * @return A <code>Socket</code> that is connected to the client.
     */
    public Socket getClientSocket() {
        return this.clientSocket;
    }

    /**
     * Kills the Registry, this happens when either the client or server disconnects.
     */
    public void kill() {
        if(this.socketListener != null) this.socketListener.kill();
        isRunning = false;

        try {
            if(this.serverSocket != null) this.serverSocket.close();
            if(this.clientSocket != null) this.clientSocket.close();
        } catch (IOException ex) {
            // Do nothing.
        }

        proxy.debug("Client "+clientSocket.getInetAddress().getHostAddress()+" has disconnected.");
    }

}