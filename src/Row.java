public class Row {
    private int[] row;
    private Boolean[] isOriginalValue;

    public Row(int size){
        row = new int[size];
        isOriginalValue = new Boolean[size];

        for(int i = 0; i < size; i++){
            isOriginalValue[i] = false;
        }
    }

    public int getValue(int index){
        return row[index-1];
    }
    public void setValue(int index, int value){
        row[index-1] = value;
    }
    
    public Boolean isOriginalValue(int index){
        return isOriginalValue[index-1];
    }
    public void setOriginalValue(int index, Boolean value){
        isOriginalValue[index-1] = value;
    }
} 
