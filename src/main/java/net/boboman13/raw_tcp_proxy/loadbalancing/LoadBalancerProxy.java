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

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A <code>Proxy</code> that has been written to support some of the necessary
 * features of a load balancer.
 * In particular, this proxy supports reporting back to the load balancer whenever
 * it has failed, indicating that the server has closed the socket for some
 * unknown reason.
 * 
 * @author Austin Bolstridge
 */
public class LoadBalancerProxy {
    private InetAddress address;
    private int port;
    private boolean isDown;
    
    public LoadBalancerProxy(String address, int port) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
        this.port = port;
    }
    
    public void markAsDown() {
        this.isDown = true;
    }
    
    public boolean isDown() {
        return isDown;
    }
}
