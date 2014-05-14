package net.boboman13.raw_tcp_proxy;

import java.util.HashSet;

/**
 * @author boboman13
 * @author Austin Bolstridge
 */
public class Registry {
    private HashSet<RegistryEntry> clients = new HashSet<RegistryEntry>();
    private Proxy proxy;

    /**
     * Creates the RegistryManager instance.
     *
     * @param proxy
     */
    public Registry(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * Adds a Client to the Registry.
     *
     * @param client
     */
    public void addClient(RegistryEntry client) {
        clients.add(client);
    }

    /**
     * Removes a Client from the Registry.
     *
     * @param client
     */
    public void removeClient(RegistryEntry client) {
        clients.remove(client);
    }

}
