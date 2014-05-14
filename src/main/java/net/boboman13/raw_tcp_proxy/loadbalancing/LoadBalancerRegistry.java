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

import java.util.HashMap;

/**
 * Implements a safer and more appropriate version of
 * {@link net.boboman13.raw_tcp_proxy.Registry}.
 * @author Austin Bolstridge
 */
public class LoadBalancerRegistry {
    private HashMap<LoadBalancerClient, LoadBalancerServer> clients = 
            new HashMap<LoadBalancerClient, LoadBalancerServer>();
    
    public LoadBalancerRegistry() {
        
    }
    
    public void addClient(LoadBalancerClient client, LoadBalancerServer server) {
        clients.put(client, server);
    }
    
    public LoadBalancerServer deleteClient(LoadBalancerClient client) {
        return clients.remove(client);
    }
    
    public LoadBalancerServer getServer(LoadBalancerClient client) {
        return clients.get(client);
    }
}
