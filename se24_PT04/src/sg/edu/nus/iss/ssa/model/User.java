package sg.edu.nus.iss.ssa.model;

public class User extends Entity {

    protected boolean isMember = false;

    public boolean isMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }
}
