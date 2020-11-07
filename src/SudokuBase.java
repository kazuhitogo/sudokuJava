import java.util.Arrays;
import java.util.HashMap;

class SudokuBase {
    protected int size, unit, digit;
    protected boolean verbose;
    protected int[][] question;

    public SudokuBase(int[][] question){
        this.verbose = false;
        this.question = question;
        this.size = question.length;
        this.unit = (int)Math.sqrt(this.question.length);
        this.digit = String.valueOf( this.size ).length();
    }
    public int getSize(){return this.size;}
    public int[][] getQuestion(){
        int copyQuestion[][] = new int[this.size][];
        for (int r = 0;r<this.size;r++){
            copyQuestion[r] = question[r].clone();
        }
        return copyQuestion;
    }
    protected boolean checkUnique(){
        int[] counter = new int[this.size];
        boolean isUnique = true;
        // row check
        for (int r=0;r<this.size;r++){
            Arrays.fill(counter,0);
            for (int c=0;c<this.size;c++){
                if (this.question[r][c] != 0){ // 0の場合は無視
                    counter[this.question[r][c]-1]++;
                    if (counter[this.question[r][c]-1]>1){
                        if (this.verbose) {System.out.println("row check violation");}
                        isUnique = false; // 重複したら return
                    }
                }
            }
        }
        // col check
        for (int c=0;c<this.size;c++){
            Arrays.fill(counter,0);
            for (int r=0;r<this.size;r++){
                if (this.question[r][c] != 0){
                    counter[this.question[r][c]-1]++;
                    if (counter[this.question[r][c]-1]>1){
                        if (this.verbose) {System.out.println("col check violation");}
                        isUnique = false; // 重複したら return
                    }
                }
            }
        }

        // 3x3 check
        int unit = (int) Math.sqrt(this.size); // 9x9 なら3,16x16なら4が入る
        for (int r=0;r<unit;r++){
            for (int c=0;c<unit;c++){
                Arrays.fill(counter,0);
                for (int r2=0;r2<unit;r2++){
                    for (int c2=0;c2<unit;c2++){
                        if (this.question[r*unit+r2][c*unit+c2]!=0){
                            counter[this.question[r*unit+r2][c*unit+c2]-1]++;
                            if (counter[this.question[r*unit+r2][c*unit+c2]-1]>1){
                                if (this.verbose) {System.out.println("matrix check violation");}
                                isUnique = false; // 重複があったら return
                            }
                        }
                    }
                }
            }
        }
        if (this.verbose) {System.out.println("no problem");}
        return isUnique;
    }
    public void printQuestion(){
        String displayString,displayNumber;
        String formatter;
        for(int r=0;r<this.size;r++){
            if (r%this.unit==0 && r !=0){
                for (int i=0; i<(this.digit+1)*this.size-1;i++){
                    if (i%((this.digit+1)*this.unit)==((this.digit+1)*this.unit-1) && i != (this.digit+1) * this.size -2 ){
                        System.out.print("+");
                    }else{
                        System.out.print("-");
                    }
                }
                System.out.println("");
            }
            for(int c=0;c<this.size;c++){
                formatter = "%" + String.valueOf(this.digit) + "s";
                if (question[r][c] == 0){
                    displayNumber = " ";
                }else{
                    displayNumber = String.valueOf(question[r][c]);
                }

                displayString = String.format(formatter, displayNumber);
                if (c%this.unit == this.unit-1 && c!= this.size -1 && c!=0){
                    displayString += "|";
                }else if(c==this.size-1){
                    // None
                }else{
                    displayString += ",";
                }
                System.out.print(displayString);
            }
            System.out.println("");
        }
    }
}

