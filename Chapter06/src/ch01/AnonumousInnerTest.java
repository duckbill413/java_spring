package ch01;

class Outer2{
	int outNum = 100;
	static int sNum = 200;
	
	
	Runnable getRunnable(final int i) {
		int num = 10;
		
//		class MyRunnable implements Runnable{
//			int localNum = 1000;
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
////				num = 20; <= Final �����̹Ƿ� ���� �Ұ���
//				System.out.println("i = " + i);
//				System.out.println("num = " + num);
//				System.out.println("localNum = " + localNum);
//				System.out.println("outNum = " + outNum + "(�ܺ� Ŭ���� �ν��Ͻ� ����)");
//				System.out.println("Outter.sNum = " + Outer2.sNum + "(�ܺ� Ŭ���� ���� ����)");
//			}
//			
//		}
//		return new MyRunnable();
		
		
		//�͸� ���� Ŭ����
		return new Runnable() {
				int localNum = 1000;
				@Override
				public void run() {
					// TODO Auto-generated method stub
//					num = 20; <= Final �����̹Ƿ� ���� �Ұ���
					System.out.println("i = " + i);
					System.out.println("num = " + num);
					System.out.println("localNum = " + localNum);
					System.out.println("outNum = " + outNum + "(�ܺ� Ŭ���� �ν��Ͻ� ����)");
					System.out.println("Outter.sNum = " + Outer2.sNum + "(�ܺ� Ŭ���� ���� ����)");
				}
				
			};
	}
	
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("Runnable class");
		}
		
	};
}
public class AnonumousInnerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Outer2 out = new Outer2();
		Runnable runner = out.getRunnable(100);
		
		runner.run();
		
		System.out.println();
		out.runnable.run();
	}

}