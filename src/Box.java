public class Box {
    private int[] box;

    public Box(int size){
        box = new int[size];
    }

    public int getValue(int index){
        System.out.println("Getting Box Value: " + box[index] + "At index " + index);
        return box[index];
    }
    public void setValue(int index, int value){
        box[index] = value;
    }
    
} 