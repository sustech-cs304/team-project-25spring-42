package sustech.cs304.entity;

public class Friendship {
    String id1;
    String id2;
    boolean status;

    public Friendship(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.status = false;
    }

    public String getId1() {
        return id1;
    }

    public String getId2() {
        return id2;
    }

    public boolean getStatus() {
        return status;
    }
}