import java.util.Arrays;
import java.util.HashMap;

public class SudokuSolve extends SudokuBase{
    private int nextR,nextC,nextValue;
    private HashMap<String,Integer> nextPosition;
    public SudokuSolve(int[][] question){
        super(question);
        this.nextPosition = this.searchMaxConstraint();
        this.nextR = this.nextPosition.get("r");
        this.nextC = this.nextPosition.get("c");
        this.nextValue = 0;
    }
    public void setNextValue(int i){
        this.nextValue = i;
    }
    public void setNextValueToQuestion(){
        this.question[this.nextR][this.nextC] = this.nextValue;
    }
    private HashMap<String,Integer> outputSquareRange(int num){
        HashMap<String,Integer> map = new HashMap<>();
        int sqrtIdx = (int)(num/this.unit);
        int idxMin = sqrtIdx*this.unit;
        int idxMax = (sqrtIdx+1)*this.unit;
        map.put("idxMin",idxMin);
        map.put("idxMax",idxMax);
        return map;
    }
    private HashMap<String,Integer> searchMaxConstraint(){
        int[][] strengthConstraint = new int[this.size][this.size];
        int[] counter = new int[this.size];
        int maxStrength;
        int strength;
        HashMap<String,Integer> position = new HashMap<>();
        HashMap<String,Integer> rIdx;
        HashMap<String,Integer> cIdx;
        for(int r=0;r<this.size;r++){
            for(int c=0;c<this.size;c++){
                if (this.question[r][c]==0){
                    Arrays.fill(counter,0);
                    strength = 0;
                    // matrix check
                    rIdx = outputSquareRange(r);
                    cIdx = outputSquareRange(c);
                    for (int x = rIdx.get("idxMin");x<rIdx.get("idxMax");x++){
                        for (int y = cIdx.get("idxMin");y<cIdx.get("idxMax");y++){
                            if (this.question[x][y] != 0){ counter[this.question[x][y]-1] = 1;}
                        }
                    }

                    // row check
                    for(int y=0;y<this.size;y++){
                        if (this.question[r][y]!=0){ counter[this.question[r][y]-1]=1;}
                    }
                    // col check
                    for(int x=0;x<this.size;x++){
                        if (this.question[x][c]!=0){counter[this.question[x][c]-1]=1;}
                    }
                    for (int count:counter){ strength += count; }
                    strengthConstraint[r][c] = strength;
                }
            }
        }
        maxStrength=-1;
        for(int r=0;r<this.size;r++){
            for(int c=0;c<this.size;c++){
                if (strengthConstraint[r][c] > maxStrength){
                    maxStrength = strengthConstraint[r][c];
                    position.put("r",r);
                    position.put("c",c);
                }
            }
        }
        return position;
    }
    public boolean checkInsert(){
        boolean ableInsert = true;
        // horizontal check
        for (int c=0;c<this.size;c++){
            if (this.question[this.nextR][c]==this.nextValue){
                if (this.verbose){ System.out.println("row check violation");}
                ableInsert = false;
            } // 同じ行に同じ値があったら入れられない
        }
        // vertical check
        for (int r=0;r<this.size;r++){
            if (this.question[r][this.nextC]==this.nextValue){
                if (this.verbose){ System.out.println("col check violation");}
                ableInsert = false;
            } // 同じ列に同じ値があったら入れられない
        }
        // sqrt(this.size) matrix check
        HashMap<String,Integer> rIdx;
        HashMap<String,Integer> cIdx;
        rIdx = outputSquareRange(this.nextR);
        cIdx = outputSquareRange(this.nextC);
        for (int x = rIdx.get("idxMin");x<rIdx.get("idxMax");x++){
            for (int y = cIdx.get("idxMin");y<cIdx.get("idxMax");y++){
                if (this.question[x][y] == this.nextValue) {
                    if (this.verbose){ System.out.println("matrix check hit");}
                    ableInsert = false;
                }
            }
        }
        return ableInsert;
    }
    public boolean checkComplete(){
        boolean isCompleted = true;
        if(this.checkUnique()){
            for (int r=0; r<this.size; r++){
                for (int c=0; c<this.size; c++){
                    if (this.question[r][c]==0){
                        isCompleted = false;
                    }
                }
            }
        }else{
            isCompleted = false;
        }
        return isCompleted;
    }
}
