public class Ball {
    private int bounce;
    private String owner;
    private int velocityx;

    public Ball(int b, String o){
        bounce = b;
        owner=o;
    }

    public Ball() {
    }


    public String toString(){
        return owner;
    }

    public boolean equals(Ball b){
        return b.toString().equals(this.toString());
    }

    public void faster(){
        velocityx+=10;
    }

    public void inflate(int x){
        bounce +=x;
    }
}