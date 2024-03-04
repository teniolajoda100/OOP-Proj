/*press h and g to switch between glider and gun and click to draw */


package ie.tudublin;
import processing.core.PApplet;

public class Life extends PApplet {

    int drawMode = 0; // 0 for individual cells, 1 for glider, 2 for Gosper Gun
    LifeBoard lifeBoard;
    boolean isPaused = false; //variable to track the pause state
    
    public void setup()
    {
        lifeBoard = new LifeBoard(50, 50, this);
        lifeBoard.randomise();
        colorMode(HSB);
        size(600, 600);
        
    }
    public void keyPressed() {
        System.out.println("Key pressed: " + key);
        if (key == ' ') { //  pause and resume
            isPaused = !isPaused;
        } else if (key == '1') { // randomize the board
            lifeBoard.randomise();
        } else if (key == '2') { // clear the board  
            lifeBoard.clear();
        } else if (key == '3') { // draw a cross shape
            lifeBoard.drawCross();
        } else if (key == 'g' || key == 'G') { // switch to glider drawing mode
            drawMode = 1;
        } else if (key == 'h' || key == 'H') { // switch to gosper gun drawing mode
            drawMode = 2;
        }
    }
     public void settings()
    {
        size(600, 600);
    }

    public void draw()
    {
        if (!isPaused) { // if not paused
            background(0); // the state of the board
            lifeBoard.update();
        }
        lifeBoard.render();
        if(isPaused) {
            fill(0); // set fill color to black
            rect(0, 0, width, height); // draw a black rectangle over the entire screen to indicate paused state
            fill(255); // set fill color to white for text
            textSize(32); // set text size
            textAlign(CENTER, CENTER); // align text to be centered
            text("Paused", width / 2, height / 2); // display pause center of the screen
        }
    }
    public void mouseDragged() {
        System.out.println("Mouse dragged in mode: " + drawMode);
        if (!isPaused) { // only allow drawing if the simulation is not paused
            int col = mouseX / (width / lifeBoard.cols);
            int row = mouseY / (height / lifeBoard.rows);
    
            switch (drawMode) {
                case 0: // drawing individual cells
                    lifeBoard.setCellAlive(col, row);
                    break;
                case 1: // Drawing glider
                    lifeBoard.drawGlider(col, row);
                    break;
                case 2: // drawing gosper gun
                    lifeBoard.drawGosperGun(col, row);
                    break;
            }
        }
    }
    class LifeBoard {
        private int cols, rows;
        private boolean[][] board;
        private PApplet p;
    
        public LifeBoard(int cols, int rows, PApplet p) {
            this.cols = cols;
            this.rows = rows;
            this.p = p;
            board = new boolean[cols][rows];
        }
        public float getCell(float[][] board, int row, int col) {
            //  if the specified location is within the the board
            if (row >= 0 && row < board.length && col >= 0 && col < board[row].length) {
                return board[row][col]; // return the colour of the cell
            } else {
                return -1; //for cells outside the board boundaries
            }
        }

        public float averageAround(float[][] board, int row, int col)
    {
        float xsum = 0;
        float ysum = 0;
        for(int r = row - 1; r <= row + 1 ; r ++)
        {
            for(int c = col - 1 ; c <= col + 1 ; c++)
            {
                float color = getCell(board, r, c);
                if (!(r == row && c == col) && color != -1)
                {
                    
                    float angle = map(color , 0, 255, -PI, PI);
                    xsum += cos(angle);
                    ysum += sin(angle);
                }
            }
        }

        xsum /= 3.0f;
        ysum /= 3.0f;

        return map(atan2(ysum, xsum), -PI, PI, 0, 255);
        }
            

        public void setCellAlive(int col, int row) {
            if (col >= 0 && col < cols && row >= 0 && row < rows) {
                board[col][row] = true; // set the cell at the specified column and row to alive
            }
        }

        public void drawGlider(int col, int row) {
            // Glider pattern
            int[][] glider = {{0, 1, 0}, {0, 0, 1}, {1, 1, 1}};
            drawPattern(col, row, glider);
        }
        
        public void drawGosperGun(int col, int row) {
            // gosper gun pattern 
            int[][] gosperGun = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},} ;
            drawPattern(col, row, gosperGun);
        }
        
        private void drawPattern(int col, int row, int[][] pattern) {
            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern[i].length; j++) {
                    if (pattern[i][j] == 1) {
                        int c = col + j;
                        int r = row + i;
                        if (c >= 0 && c < cols && r >= 0 && r < rows) {
                            board[c][r] = true;
                        }
                    }
                }
            }
        }
        public void randomise() {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    board[i][j] = p.random(1) < 0.5;
                }
            }
        }
    
        public void update() {
            boolean[][] newBoard = new boolean[cols][rows];
    
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    int liveNeighbors = countLiveNeighbors(i, j);
    
                    if (board[i][j]) {
                        newBoard[i][j] = liveNeighbors == 2 || liveNeighbors == 3;
                    } else {
                        newBoard[i][j] = liveNeighbors == 3;
                    }
                }
            }
    
            board = newBoard;
        }
    
        public void render() {
            p.stroke(0);
            float cellWidth = p.width / (float) cols;
            float cellHeight = p.height / (float) rows;
    
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    if (board[i][j]) {
                        p.fill(255); 
                    } else {
                        p.fill(0);
                    }
                    p.rect(i * cellWidth, j * cellHeight, cellWidth / 2, cellHeight);
                }
            }
        }
        public void clear() {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    board[i][j] = false; // clearing the board by setting all cells to dead
                }
            }
        }
        public void drawCross() {
            int centerCol = cols / 2;
            int centerRow = rows / 2;
    
            // drawingine of the cross
            for (int i = -3; i <= 3; i++) {
                int row = centerRow + i;
                if (row >= 0 && row < rows) {
                    board[centerCol][row] = true;
                }
            }
    
            // drawing line of the cross
            for (int i = -3; i <= 3; i++) {
                int col = centerCol + i;
                if (col >= 0 && col < cols) {
                    board[col][centerRow] = true;
                }
            }
        }
    
    
        private int countLiveNeighbors(int x, int y) {
            int count = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!(i == 0 && j == 0)) {
                        int col = (x + i + cols) % cols;
                        int row = (y + j + rows) % rows;
                        count += board[col][row] ? 1 : 0;
                    }
                }
            }
            return count;
        }
    }
    
    
}
