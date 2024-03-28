public class Football extends Ball{
    private int velocityy = 0;
    public Football(int b, String o){
        super(b,o);
    }
    public Football(){

    }

    public void faster(int y){
        velocityy+=y;
    }

    public void drop(int x){
        super.inflate(x);
    }
    public String toString() {
        return "football goes:"+velocityy;
    }
    public boolean equals(Football f) {
        return f.toString().equals(this.toString());
    }
}