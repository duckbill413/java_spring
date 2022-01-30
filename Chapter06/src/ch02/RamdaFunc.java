package ch02;

public class RamdaFunc {
	public static void main(String[]args) {
		Add add = (x, y)->{return x+y;}; // 람다식 표현
		System.out.println(add.add(2,  3));
		add = (x, y)->x+y;
		System.out.println(add.add(5,  8)); // 중괄호와 리턴 생략 (리턴을 생략하지 않고 중괄호 생략 불가)
	}
}
