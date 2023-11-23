package dao;

import java.io.IOException;
import java.util.Scanner;

import dto.StudentDto;
import handler.FileIO;

// Data Access Object : 데이터를 취급하는 클래스
public class StudentDao {
	// 학생 데이터 관리 배열
	private StudentDto[] student;
	private Scanner sc = new Scanner(System.in);
	private int count;
	private FileIO fio;
	private final String fileName = "c:\\tmp\\student.txt";

	// 추가, 삭제, 검색, 수정 (CRUD)
	public StudentDao() {
		student = new StudentDto[10]; // 배열 객체만 생성
		count = 0;
		fio = new FileIO("student");
		fio.create();
		// dto를 생성
//		for (int i = 0; i < student.length; i++) {
//			student[i] = new StudentDto();
//		}
	}

	public void insert() {
		System.out.println("학생 정보 입력입니다");

		System.out.print("이름 >> ");
		String name = sc.next();

		System.out.print("나이 >> ");
		int age = sc.nextInt();

		System.out.print("신장 >> ");
		double height = sc.nextDouble();

		System.out.print("주소 >> ");
		String address = sc.next();

		System.out.print("국어 >> ");
		int kor = sc.nextInt();

		System.out.print("영어 >> ");
		int eng = sc.nextInt();

		System.out.print("수학 >> ");
		int math = sc.nextInt();

		student[count] = new StudentDto(name, age, height, address, kor, eng, math);
		count++; // 배열의 다음으로 이동
	}

	public void delete() {
		// 이름입력
		System.out.print("삭제하고 싶은 학생의 이름 >> ");
		String name = sc.next();

		// 검색
		int findIndex = search(name);

		if (findIndex == -1) {
			System.out.println("학생 정보를 찾을 수 없습니다.");
			return;
		}

		student[findIndex].setName("");
		student[findIndex].setAge(0);
		student[findIndex].setHeight(0.0);
		student[findIndex].setAddress("");
		student[findIndex].setKor(0);
		student[findIndex].setEng(0);
		student[findIndex].setMath(0);
		;

		System.out.println(name + "학생의 데이터가 정상적으로 삭제되었습니다.");
	}

	public void select() {
		System.out.print("검색하고 싶은 학생의 이름 >> ");
		String name = sc.next();

		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];

			if (dto != null && !dto.getName().equals("")) {
				if (name.equals(dto.getName())) {
					dto.Print();
				}
			}
		}

		int findIndex = search(name);

		if (findIndex != -1) {
			System.out.println("데이터를 찾았습니다.");
			System.out.println("이름:" + student[findIndex].getName());
			System.out.println("나이:" + student[findIndex].getAge());
			System.out.println("키:" + student[findIndex].getHeight());
			System.out.println("주소:" + student[findIndex].getAddress());
			System.out.println("국어:" + student[findIndex].getKor());
			System.out.println("영어:" + student[findIndex].getEng());
			System.out.println("수학:" + student[findIndex].getMath());
		} else {
			System.out.println("학생 명단에 없습니다.");
		}
	}

	public void update() {
		// 이름입력
		System.out.print("수정하고 싶은 학생의 이름입력 >> ");
		String name = sc.next();

		int findIndex = search(name);

		if (findIndex == -1) {
			System.out.println("학생명단에 없습니다");

			return;
		}

		// 국어, 영어, 수학 점수 수정
		System.out.println("수정할 데이터를 찾았습니다.");

		System.out.print("국어 >> ");
		int kor = sc.nextInt();

		System.out.print("영어 >> ");
		int eng = sc.nextInt();

		System.out.print("수학 >> ");
		int math = sc.nextInt();

		student[findIndex].setKor(kor);
		student[findIndex].setEng(eng);
		student[findIndex].setMath(math);

		System.out.println("수정을 완료했습니다");
	}

	public void allData() {
		for (int i = 0; i < student.length; i++) {
			StudentDto dto = student[i];

			if (dto != null && !dto.getName().equals("")) {
				System.out.println(dto.toString());
			}
		}
	}

	public int search(String name) {
		int findIndex = -1;

		for (int i = 0; i < student.length; i++) {
			if (student[i] != null) {
				if (name.equals(student[i].getName())) { // 찾았다
					findIndex = i;
					break;
				}
			}

//			if (name.equals(student[i].getName())) { // 찾았다
//				findIndex = i;
//				break;
//			}
		}

		return findIndex;
	}

	public void save() {
		// 실제로 삭제된 데이터를 제외한 (정상적인)데이터가 몇개?
		int ci = 0;
		for (int i = 0; i < student.length; i++) {
			if (student[i] != null 
					&& !student[i].getName().equals("")) {
				ci++;
			}
		}

		// 배열
		String[] arr = new String[ci];
		
		int j = 0;
		for (int i = 0; i < student.length; i++) {
			if (student[i] != null 
					&& !student[i].getName().equals("")) {
				arr[j] = student[i].toString();
				j++;
			}
		}
		
		fio.dataSave(arr);
	}

	public void load() {
		String[] arr = fio.dataLoad();

		if (arr == null || arr.length == 0) {
			count = 0;
			return;
		}

		// (추가될)다음 데이터의 index
		count = arr.length;

		// string[] -> studentDto[]
		for (int i = 0; i < arr.length; i++) {
			// 문자열 자르기
			String[] split = arr[i].split("-");

			// 자른 문자열을 dto에 저장하기 위한 처리
			String name = split[0];
			int age = Integer.parseInt(split[1]);
			double height = Double.parseDouble(split[2]);
			String address = split[3];
			int kor = Integer.parseInt(split[4]);
			int eng = Integer.parseInt(split[5]);
			int math = Integer.parseInt(split[6]);

			student[i] = new StudentDto(name, age, height, address, kor, eng, math);
		}

		System.out.println("데이터로드 성공!");
	}
}
