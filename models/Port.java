package models;
public class Port {
    private String portId;
    private String name;

    public Port(String portId, String name) {
        this.portId = portId;
        this.name = name;
    }

    public String getPortId() {
        return portId;
    }

    public String getName() {
        return name;
    }
}
