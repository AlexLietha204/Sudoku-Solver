public class Row {
    private int[] row;

    public Row(int size){
        row = new int[size];
    }

    public int getValue(int index){
        return row[index];
    }
    public void setValue(int index, int value){
        row[index] = value;
    }
    
} 
