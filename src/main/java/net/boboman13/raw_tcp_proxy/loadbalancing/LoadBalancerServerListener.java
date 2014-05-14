/*
 * Copyright (C) 2014 boboman13
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package net.boboman13.raw_tcp_proxy.loadbalancing;

import java.io.IOException;
import net.boboman13.raw_tcp_proxy.RegistryEntry;
import net.boboman13.raw_tcp_proxy.main.Main;

/**
 * Implements {@link net.boboman13.raw_tcp_proxy.SocketListener} in a way that
 * is safer and more appropriate for a load balancer.
 * 
 * @author Austin Bolstridge
 */
public class LoadBalancerServerListener implements Runnable{
    private final RegistryEntry client;
    private boolean isRunning = true;
    
    public LoadBalancerServerListener(RegistryEntry client) {
        this.client = client;
    }
    
    @Override
    public void run() {
        byte[] request = new byte[1024];
        int bytesRead;
        
        try {
            while (isRunning && (bytesRead = client.serverIn.read(request)) != -1) {
                client.clientOut.write(request, 0, bytesRead);
                client.clientOut.flush();

                // It may seem obscure to surround it by an if loop, 
                //but otherwise we are forced to create a new String object;
                //the parsing to UTF-8 causes some speed issues.
                if(client.getProxy().getDebug()) {
                    client.getProxy().debug("S -> C: " + new String(request, "UTF-8"));
                }

            }
        } catch (IOException ex) {
            if(!client.getServerSocket().isConnected()) {
                //Most likely, this means that the server has closed the socket.
                //We assume that the server is down.
                //For certain protocols, this will fail every time.
                //But not too many...
                //I don't think.
                Main.getLoadBalancerInstance().serverDown(client.getServerSocket()
                    .getInetAddress());
            }
            this.isRunning = false;
        }
    }
}
