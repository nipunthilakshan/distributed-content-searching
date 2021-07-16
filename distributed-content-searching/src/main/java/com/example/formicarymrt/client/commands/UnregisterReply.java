package com.example.formicarymrt.client.commands;

public class UnregisterReply extends AbstractCommand {
    public static final String UNREGOK = "UNROK";
    private int status;  /*0 – successful,   9999 – error while unregistering.*/

    public UnregisterReply(String issuerIp, int issuerPort, String otherPartyIp, int otherPartyPort) {
        super(issuerIp, issuerPort, otherPartyIp, otherPartyPort);
    }

    @Override
    public String getCommandType() {
        return UNREGOK;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
