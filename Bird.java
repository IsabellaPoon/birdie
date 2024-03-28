    import java.awt.*;
    public class Bird{

        private int boardWidth = 640;
        private int boardHeight = 640;

        private int birdX = boardWidth/8;
        private int birdY = boardHeight/2;
        private int birdWidth = 45;
        private int birdHeight = 40;
        private final Image img;

        public Bird(Image img){
            this.img = img;
        }

        public Image getImg() { return img; }

        public int getX() {
            return birdX;
        }

        public int getY(){
            return birdY;
        }

        public int getWidth(){ return this.birdWidth; }

        public int getHeight(){
            return this.birdHeight;
        }

        public void setY(int i){
            birdY+=i;
            birdY = Math.max(birdY, 0);
        }
        public void resetY(){
            birdY = boardHeight/2;
        }

        public void setImage(Image fireBirdImg) {
        }
    }