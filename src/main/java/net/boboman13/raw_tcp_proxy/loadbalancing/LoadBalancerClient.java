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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * A client that has connected to the load balancer. Allows for management of
 * this client's sockets, and management of the client in general.
 * @author Austin Bolstridge
 */
public class LoadBalancerClient {
    
    public final InputStream clientInputStream;
    public final OutputStream clientOutputStream;
    public final Socket clientSocket;
    
    public LoadBalancerClient(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientInputStream = clientSocket.getInputStream();
        this.clientOutputStream = clientSocket.getOutputStream();
    }
}
