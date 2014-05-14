/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.boboman13.raw_tcp_proxy.loadbalancing;

import java.net.InetAddress;
import java.util.HashMap;
import net.boboman13.raw_tcp_proxy.Proxy;

/**
 * The main class for all load balancing activities.
 * Upon being given information on what servers to balance across, it will set
 * up new <code>Proxy</code>s for each server.
 * <br />
 * The class load-balances in a round-robin format - each server gets an equal
 * share of clients.
 * <br />
 * Plans are made to have the ability to weight servers - however, we need a
 * configuration file engine in order to make that work.
 * 
 * @author Austin Bolstridge
 */
public class LoadBalancer {
    
    private HashMap<InetAddress, Proxy> servers = new HashMap<InetAddress, Proxy>();
    
    public LoadBalancer(String[] servers, int[] ports, String listeningIP, 
            int listeningPort) {
        
    }
    
    public void serverDown(InetAddress address) {
        
    }
}
