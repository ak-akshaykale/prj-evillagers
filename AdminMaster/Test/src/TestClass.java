
public class TestClass {

	public static void main(String[] args) {
		
		int array[]= {5,6,1,3,9};
		int temp,j;
		for (int i = 0; i < array.length; i++) {
			j=i;
			while(j>0) {
				if (array[j-1] > array[j]) {
					temp = array[j];
					array[j]=array[j-1];
					array[j-1]=temp;
				} else {
					break;
				}
				j--;
			}
		}
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}

	}

}
