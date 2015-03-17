package edu.uj.jkordas.spy.POJO;

public class Call {
    private long id;
    private String number;
    private String contactName;
    private int type;
    private int duration;

    public final int MISSED_TYPE = android.provider.CallLog.Calls.MISSED_TYPE;
    public final int OUTGOING_TYPE = android.provider.CallLog.Calls.OUTGOING_TYPE;
    public final int INCOMING_TYPE = android.provider.CallLog.Calls.INCOMING_TYPE;

    public Call(String number, int type) {
        this.number = number;
        this.type = type;
        this.duration = -1;
        this.contactName = "unknown";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String s = "";
        if (this.type == this.MISSED_TYPE) {
            s += "missed call from : ";
        } else if (this.type == this.OUTGOING_TYPE) {
            s += "outgoing call to : ";
        } else if (this.type == this.INCOMING_TYPE) {
            s += "incoming call from : ";
        } else {
            s += "unknown action : ";
        }

        s += this.number;

        if (this.contactName != null) {
            s += " (" + this.contactName + ") ";
        }

        if (duration > 0) {
            s += " duration : " + this.duration + " s.";
        }

        return s;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
