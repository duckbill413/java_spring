package ch03;

public class StringBuilderTest {
	public static void main(String[] args) {
		String java = new String("java");
		String android = new String("android");

		StringBuilder buffer = new StringBuilder(java);
		//버퍼는 가변하기 때문에 String 메모리 낭비의 문제가 없다
		buffer.append(android);
		
		String test = buffer.toString();
		System.out.println(test);
	}
}
